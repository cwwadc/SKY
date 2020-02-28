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
 * 系统接口被调用耗时，成功率等
 * </p>
 *
 * @author lzm
 * @since 2019-07-16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SystemInterfaceInfo对象", description = "系统接口被调用耗时，成功率等")
@TableName("SKY_SYSTEM_INTERFACE_INFO")
public class SystemInterfaceInfo extends Model<SystemInterfaceInfo> {

    private static final long serialVersionUID = 1L;
	/**

     * 主键
     */
    @TableId(value = "ID",type = IdType.AUTO)
	@ApiModelProperty(value = "主键", name = "id")
	private Double id;
    /**
     * 接口名
     */
	@TableField("INTERFACE_NAME")
	@ApiModelProperty(value = "接口名", name = "interfaceName")
	private String interfaceName;
    /**
     * 耗时
     */
	@TableField("TIME_CONSUMING")
	@ApiModelProperty(value = "耗时", name = "timeConsuming")
	private String timeConsuming;
    /**
     * 是否成功:0-是1-否
     */
	@TableField("IS_SUCCESS")
	@ApiModelProperty(value = "是否成功: 0-是 1-否", name = "isSuccess")
	private String isSuccess;
    /**
     * 保留字段
     */
	@TableField("RESERVED")
	@ApiModelProperty(value = "保留字段", name = "reserved")
	private String reserved;
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
