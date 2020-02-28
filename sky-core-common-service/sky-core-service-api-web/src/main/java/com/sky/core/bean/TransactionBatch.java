package com.sky.core.bean;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 征收机关批量交易批次表
 * </p>
 *
 * @author lzm
 * @since 2019-07-16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TransactionBatch对象", description = "征收机关批量交易批次表")
@TableName("SKY_TRANSACTION_BATCH")
public class TransactionBatch extends Model<TransactionBatch> {

    private static final long serialVersionUID = 1L;

	/**
     * 主键

     */
    @TableId(value ="ID",type = IdType.AUTO)
	@ApiModelProperty(value = "主键", name = "id")
	private Double id;
    /**
     * 批量号
     */
	@TableField("BATCH_ID")
	@ApiModelProperty(value = "批量号", name = "batchId")
	private String batchId;
    /**
     * 关联批量号
     */
	@TableField("REF_BATCH_ID")
	@ApiModelProperty(value = "关联批量号", name = "refBatchId")
	private String refBatchId;
    /**
     * 报文类型
     */
	@TableField("MSG_TYPE")
	@ApiModelProperty(value = "报文类型", name = "msgType")
	private String msgType;
    /**
     * 报文头元信息
     */
	@TableField("HEAD_METADATA")
	@ApiModelProperty(value = "报文头元信息", name = "headMetadata")
	private String headMetadata;
    /**
     * 批量头元信息
     */
	@TableField("BATCH_INFO_METADATA")
	@ApiModelProperty(value = "批量头元信息", name = "batchInfoMetadata")
	private String batchInfoMetadata;
    /**
     * 转发错误
     */
	@TableField("RELAY_ERROR")
	@ApiModelProperty(value = "转发错误", name = "relayError")
	private String relayError;
    /**
     * 创建时间
     */
	@TableField("CREATE_TIME")
	@ApiModelProperty(value = "创建时间", name = "createTime")
	private Date createTime;
    /**
     * 保留字段
     */
	@TableField("RESERVED")
	@ApiModelProperty(value = "保留字段", name = "reserved")
	private String reserved;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
