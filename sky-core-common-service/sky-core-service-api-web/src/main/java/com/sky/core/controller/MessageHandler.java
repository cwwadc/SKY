//package com.sky.core.controller;
//
//import com.sky.core.serialize.ApiSerializer;
//import com.sky.core.service.InsertHandler;
//import com.sky.core.utils.SkyApiMsgUtils;
//import com.sky.service.api.struct.domain.SkyMsg;
//import com.sky.service.api.support.ApiContextSupport;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//
//public class MessageHandler {
//
//    private static final Logger log = LoggerFactory.getLogger(MessageHandler.class);
//
//    @Autowired
//    private InsertHandler insertHandler;
//    private ApiSerializer<SkyMsg> apiSerializer;
//
//    @RabbitListener(queues = "${rabbit.queue.offlineRecording}")
//    public void handleMessage(byte[] message){
//        System.out.println("消费消息");
//        System.out.println(new String(message));
//        String receiveMSg = String.valueOf(message);
//        log.info(" 监听到消息： {} ",new String(message));
//        String msgType = SkyApiMsgUtils.unpackMsgType(receiveMSg);
//        Class<?> deserializeType = apiSerializer.lookupDeserializeType(msgType);
//        SkyMsg msgWrapper = SkyApiMsgUtils.unpackMsg(receiveMSg, deserializeType);
//        if(isBatch(msgType)){
//            //批量入库
//            insertHandler.insertBatchMsg(msgWrapper);
//        }else{
//            //实时入库
//            insertHandler.insertRealTimeMsg(msgWrapper);
//        }
//    }
//
//
//    public Boolean isBatch(String msgType){
//        return msgType.startsWith("B");
//    }
//}
