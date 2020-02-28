package com.sky.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rabbitmq.client.Channel;
import com.sky.base.lang.string.StringUtils;
import com.sky.core.serialize.ApiSerializer;
import com.sky.core.service.InsertHandler;
import com.sky.core.utils.RabbitUtil;
import com.sky.core.utils.SkyApiMsgUtils;
import com.sky.service.api.exception.ApiException;
import com.sky.service.api.struct.domain.SkyMsg;

/**
 * 监听消费用户日志
 */
@Controller
@RequestMapping(value="/index")
@Component
public class RecordingMessageListener  {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordingMessageListener.class);
    private static final String DEFAULT_MESSAGE_CHARSET = "UTF-8";
    
    @Autowired
    private InsertHandler insertHandler;
    @Autowired
    private RabbitUtil rabbitUtil;
    @Autowired
    private ApiSerializer<SkyMsg> apiSerializer;
    @Value("${rabbit.queue.offlineRecording}")
    private String destinationQ;
    @Value("${rabbit.queue.offlineRecording.error}")
    private String errorQ;

    @RabbitListener(containerFactory = "rabbitListenerContainerFactory", queues = "${rabbit.queue.offlineRecording}")
    public void handleMessage(Message messageData, Channel channel){
        try{
        	LOGGER.info("receive message from [{}], message-id -> [{}]", destinationQ, messageData.getMessageProperties().getMessageId());
            String messageText = new String(messageData.getBody(), DEFAULT_MESSAGE_CHARSET);
            String[] unpack = unpackMsg(messageText);
            if(unpack == null) {
            	acknowlegeMessage(messageData, channel);   //非结构化消息自动确认丢弃
            }
            if(consumeRecordingMessage(unpack[1])) {
            	LOGGER.info("do database-recording finish, message-id -> [{}]", messageData.getMessageProperties().getMessageId());
            	channel.basicAck(messageData.getMessageProperties().getDeliveryTag(), false);   //手动确认消费成功
            } else {  //写入DB失败
            	LOGGER.info("do database-recording failed, message-id -> [{}]", messageData.getMessageProperties().getMessageId());
            	try {
            		rabbitUtil.sendToQueue(errorQ, messageText);    //消费失败转发至指定的错误队列
            		acknowlegeMessage(messageData, channel);   //原队列消息手动确认消费成功
            		LOGGER.info("relay message to errorQ finish, message-id -> [{}]", messageData.getMessageProperties().getMessageId());
				} catch (Exception e) {
					LOGGER.error("relay message to errorQ fail, message-id -> [{}], context-> [{}]", messageData.getMessageProperties().getMessageId(), messageText, e);
				}
            }
            LOGGER.info("handle message finish, from -> [{}], message-id -> [{}]", destinationQ, messageData.getMessageProperties().getMessageId());
        } catch (Exception e) {
            LOGGER.error("handle message error, from -> [{}], message-id -> [{}]", destinationQ, messageData.getMessageProperties().getMessageId(), e);
        }
    }
    
    public void acknowlegeMessage(Message message, Channel channel) {
    	try {
    		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    		LOGGER.info("acknowlege message, id -> [{}] finish", message.getMessageProperties().getMessageId());
		} catch (Exception e) {
			LOGGER.error("acknowlege message error, id -> [{}]",  message.getMessageProperties().getMessageId(), e);
		}
    }

    public boolean consumeRecordingMessage(String message){
        String msgType = SkyApiMsgUtils.unpackMsgType(message);
        if(StringUtils.isEmpty(msgType)) {
            throw new ApiException("cannot found '<MsgType>' node in receiveMetadata");
        }
        SkyMsg msgWrapper = null;
        try {
        	 Class<?> deserializeType = apiSerializer.lookupDeserializeType(msgType);
             msgWrapper = SkyApiMsgUtils.unpackMsg(message, deserializeType);
             LOGGER.info("<MsgType> -> [{}], <MsgId> -> [{}], <RefMsgId> -> [{}]", 
            		 msgWrapper.getHead().getMsgType(), msgWrapper.getHead().getMsgId(), 
            		 StringUtils.isEmpty(msgWrapper.getHead().getRefMsgId()) ? "" : msgWrapper.getHead().getRefMsgId());
		} catch (Exception e) {
			LOGGER.error("unrecognized message-type, ignore -> [{}]", message);
			return false;
		}
        return recordingToDatabase(msgWrapper);
    }
    
    public boolean recordingToDatabase(SkyMsg skyMsg) {
    	try {
        	if(isBatch(skyMsg.getHead().getMsgType())){
                insertHandler.insertBatchMsg(skyMsg);   //批量交易入库
            }else{
                insertHandler.insertRealTimeMsg(skyMsg);    //实时交易入库
            }
        	return true;
		} catch (Exception e) {
			LOGGER.error("recoding into database error", e);
			return false;
		}
    }

    public String[] unpackMsg(String receiveMetadata){
        String[] msg =  receiveMetadata.split("->");
        if(msg == null || msg.length != 2) {
        	LOGGER.error("unrecognized receiveMetadata -> " + receiveMetadata);
        	return null;
        }
        LOGGER.info("trace-id -> [{}]", msg[0]);
        if(LOGGER.isDebugEnabled()) {
    		LOGGER.info("message -> [{}]", msg[1]);
    	}
        return msg;
    }
    
    public Boolean isBatch(String msgType){
        if(msgType.startsWith("B")){
            return true;
        }else
            return false;
    }
    
}
