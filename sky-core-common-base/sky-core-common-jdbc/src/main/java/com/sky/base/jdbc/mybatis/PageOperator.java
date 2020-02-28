package com.sky.base.jdbc.mybatis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author lizp
 * @version 1.0.0
 * @Title
 * @Description
 * @date 2019-04-23
 */
public class PageOperator<T> {

    private final Supplier<List<T>> supplier;

    private PageOperator(Supplier<List<T>> supplier) {
        super();
        this.supplier = supplier;
    }

    public static <T> PageInfo<T> paging(Supplier<List<T>> supplier, int offset, int limit) {
        PageOperator<T> operator = new PageOperator<T>(supplier);
        return operator.paging(offset, limit);
    }

    public static <T> PageInfo<T> paging(Supplier<List<T>> supplier, int offset, int limit, boolean count) {
        PageOperator<T> operator = new PageOperator<T>(supplier);
        return operator.paging(offset, limit, count);
    }

    public static <T> List<T> pagingForList(Supplier<List<T>> supplier, int offset, int limit) {
        PageOperator<T> operator = new PageOperator<T>(supplier);
        return operator.pagingForList(offset, limit);
    }

    public static <T> List<T> pagingForList(Supplier<List<T>> supplier, int offset, int limit, boolean count) {
        PageOperator<T> operator = new PageOperator<T>(supplier);
        return operator.pagingForList(offset, limit, count);
    }


    public PageInfo<T> paging(int offset, int limit) {
        List<T> result = pagingForList(offset, limit);
        return new PageInfo<T>(Optional.ofNullable(result).orElse(new ArrayList<T>(0)));
    }

    public List<T> pagingForList(int offset, int limit) {
        PageHelper.startPage(offset, limit);
        try {
            List<T> result = supplier.get();
            return Optional.ofNullable(result).orElse(new ArrayList<T>(0));
        } finally {
            PageHelper.clearPage();
        }
    }

    public PageInfo<T> paging(int offset, int limit, boolean count) {
        List<T> result = pagingForList(offset, limit, count);
        return new PageInfo<T>(Optional.ofNullable(result).orElse(new ArrayList<T>(0)));
    }

    public List<T> pagingForList(int offset, int limit, boolean count) {
        PageHelper.startPage(offset, limit, count);
        try {
            List<T> result = supplier.get();
            return Optional.ofNullable(result).orElse(new ArrayList<T>(0));
        } finally {
            PageHelper.clearPage();
        }
    }

}
