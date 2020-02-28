package com.sky.core.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sky.core.bean.TransactionBatchDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 征收机关批量交易明细表 Mapper 接口
 * </p>
 *
 * @author lzm
 * @since 2019-07-16
 */
@Mapper
@Repository
public interface TransactionBatchDetailMapper extends BaseMapper<TransactionBatchDetail> {

}
