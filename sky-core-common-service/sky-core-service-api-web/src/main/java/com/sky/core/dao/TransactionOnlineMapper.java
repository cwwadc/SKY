package com.sky.core.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sky.core.bean.TransactionOnline;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 征收机关/商业银行实时交易信息表 Mapper 接口
 * </p>
 *
 * @author lzm
 * @since 2019-07-16
 */
@Mapper
@Repository
public interface TransactionOnlineMapper extends BaseMapper<TransactionOnline> {

}
