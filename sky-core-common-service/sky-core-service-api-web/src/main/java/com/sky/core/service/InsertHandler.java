package com.sky.core.service;


import com.sky.base.serialize.xml.XmlUtils;
import com.sky.base.serialize.xml.strategy.DefaultXstreamStrategy;
import com.sky.core.bean.TransactionBatch;
import com.sky.core.bean.TransactionBatchDetail;
import com.sky.core.bean.TransactionOnline;
import com.sky.core.dao.TransactionBatchDetailMapper;
import com.sky.core.dao.TransactionBatchMapper;
import com.sky.core.dao.TransactionOnlineMapper;
import com.sky.core.serialize.ApiSerializer;
import com.sky.core.utils.SkyApiMsgUtils;
import com.sky.service.api.struct.domain.BatchInfo;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.domain.SkyMsgHead;
import com.sky.service.api.struct.transaction.b1010.TransB10101;
import com.thoughtworks.xstream.XStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableTransactionManagement
public class InsertHandler {
	
    private static XStream skyMsgXStream;
    private static XStream skyBatchXStream;
    {
        skyMsgXStream = XmlUtils.getXStream(new DefaultXstreamStrategy());
    	skyMsgXStream.processAnnotations(SkyMsgHead.class);

        skyBatchXStream = XmlUtils.getXStream(new DefaultXstreamStrategy());
        skyBatchXStream.processAnnotations(BatchInfo.class);
    }
    @Autowired
    private TransactionOnlineMapper transactionOnlineMapper;
    @Autowired
    private TransactionBatchMapper transactionBatchMapper;
    @Autowired
    private TransactionBatchDetailMapper transactionBatchDetailMapper;
    /**
     * 实时消息入库
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertRealTimeMsg(SkyMsg skyMsg) {

        TransactionOnline transactionOnline = new TransactionOnline();
        transactionOnline.setCreateTime(new Date());
        transactionOnline.setMsgId(skyMsg.getHead().getMsgId());
        transactionOnline.setMsgType(skyMsg.getHead().getMsgType());
        transactionOnline.setRefMsgId(skyMsg.getHead().getRefMsgId());
        transactionOnline.setMsgMetadata(SkyApiMsgUtils.toPlainXml(skyMsg));
        transactionOnline.setRelayError("1");
        transactionOnlineMapper.insert(transactionOnline);
    }

    /**
     * 批量消息入库
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertBatchMsg(SkyMsg skyMsg) {
        if(isCommonReplyContext(skyMsg)){
            insertBatch(skyMsg);
        }else{
            insertBatch(skyMsg);
            insertBatchDetil(skyMsg);
        }
    }

    /**
     * 批量交易批次入库
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertBatch(SkyMsg skyMsg){
        //批量交易批次入库
        TransactionBatch transactionBatch = new TransactionBatch();
        transactionBatch.setBatchId(skyMsg.getHead().getMsgId());
        transactionBatch.setRefBatchId(skyMsg.getHead().getRefMsgId());
        transactionBatch.setMsgType(skyMsg.getHead().getMsgType());
        transactionBatch.setHeadMetadata(skyMsgXStream.toXML(skyMsg.getHead()));
        getBody(skyMsg,transactionBatch);
        transactionBatch.setRelayError("1");
        transactionBatch.setCreateTime(new Date());
        transactionBatchMapper.insert(transactionBatch);
    }

    /**
     * 批量交易明细入库
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertBatchDetil(SkyMsg skyMsg) {
        TransactionBatchDetail transactionBatchDetail = new TransactionBatchDetail();
        transactionBatchDetail.setBatchId(skyMsg.getHead().getMsgId());
        transactionBatchDetail.setMsgType(skyMsg.getHead().getMsgType());
        transactionBatchDetail.setRefBatchId(skyMsg.getHead().getRefMsgId());
        transactionBatchDetail.setMsgMetadata(SkyApiMsgUtils.toPlainXml(skyMsg));
        transactionBatchDetail.setCreateTime(new Date());
        transactionBatchDetailMapper.insert(transactionBatchDetail);
    }
    /**
     * 获取消息批量头并放入对应属性
     */
    public void getBody(SkyMsg skyMsg,TransactionBatch transactionBatch) {
        String msgType = skyMsg.getHead().getMsgType();
        if(!isCommonReplyContext(skyMsg)){
            List<?> list = skyMsg.getBody();
            switch (msgType) {
                case "B10101":
                    BatchInfo batchInfo101 = (BatchInfo) list.get(0);
                    transactionBatch.setBatchInfoMetadata(skyBatchXStream.toXML(batchInfo101));
                    break;
                case "B10102":
                    BatchInfo batchInfo201 = (BatchInfo) list.get(0);
                    transactionBatch.setBatchInfoMetadata(skyBatchXStream.toXML(batchInfo201));
                    break;
                case "B10501":
                    BatchInfo batchInfo501 = (BatchInfo) list.get(0);
                    transactionBatch.setBatchInfoMetadata(skyBatchXStream.toXML(batchInfo501));
                    break;
                case "B10601":
                    BatchInfo batchInfo601 = (BatchInfo) list.get(0);
                    transactionBatch.setBatchInfoMetadata(skyBatchXStream.toXML(batchInfo601));
                    break;
                default:
                    break;

            }
        }
    }

    /** 是否通用应答类消息
     * @param message 消息对象
     * @return true-应答类消息, false-非应答类消息
     */
    public static boolean isCommonReplyContext(SkyMsg message) {
        return message.getBody() == null || message.getBody().isEmpty();
    }
}
