package com.sky.core.bean;

import java.sql.Clob;
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
 * 征收机关/商业银行实时交易信息表
 * </p>
 *
 * @author lzm
 * @since 2019-07-16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "TransactionOnline对象", description = "征收机关/商业银行实时交易信息表")
@TableName("SKY_TRANSACTION_ONLINE")
public class TransactionOnline extends Model<TransactionOnline> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value ="ID",type = IdType.AUTO)
	@ApiModelProperty(value = "主键", name = "id")
	private Double id;
    /**
     * 报文类型
     */
	@TableField("MSG_TYPE")
	@ApiModelProperty(value = "报文类型", name = "msgType")
	private String msgType;
    /**
     * 报文流水
     */
	@TableField("MSG_ID")
	@ApiModelProperty(value = "报文流水", name = "msgId")
	private String msgId;
    /**
     * 关联报文流水
     */
	@TableField("REF_MSG_ID")
	@ApiModelProperty(value = "关联报文流水", name = "refMsgId")
	private String refMsgId;
    /**
     * 报文元数据
     */
	@TableField("MSG_METADATA")
	@ApiModelProperty(value = "报文元数据", name = "msgMetadata")
	private String msgMetadata;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
