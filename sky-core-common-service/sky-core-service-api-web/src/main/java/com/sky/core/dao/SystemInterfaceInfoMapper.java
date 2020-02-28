package com.sky.core.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sky.core.bean.SystemInterfaceInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统接口被调用耗时，成功率等 Mapper 接口
 * </p>
 *
 * @author lzm
 * @since 2019-07-16
 */
@Mapper
public interface SystemInterfaceInfoMapper extends BaseMapper<SystemInterfaceInfo> {

}
