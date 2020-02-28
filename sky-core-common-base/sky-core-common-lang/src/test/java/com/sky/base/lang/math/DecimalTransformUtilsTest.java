package com.sky.base.lang.math;

import org.junit.Test;

import com.sky.base.lang.math.DecimalTransformUtils;

import static org.junit.Assert.*;

/**
 * @author zzq
 * @version 1.0.0
 * @Title {@link}
 * @Description
 * @date 2019/3/14
 */
public class DecimalTransformUtilsTest {

    @Test
    public void transform() {
        assertEquals(DecimalTransformUtils.transform(DecimalTransformUtils.DATASOURCE_10,1L),"1");
        assertEquals(DecimalTransformUtils.transform(DecimalTransformUtils.DATASOURCE_10,2L),"2");
        assertEquals(DecimalTransformUtils.transform(DecimalTransformUtils.DATASOURCE_10,3L),"3");
        assertEquals(DecimalTransformUtils.transform(DecimalTransformUtils.DATASOURCE_16,10L),"A");
        assertEquals(DecimalTransformUtils.transform(DecimalTransformUtils.DATASOURCE_16,11L),"B");
        assertEquals(DecimalTransformUtils.transform(DecimalTransformUtils.DATASOURCE_16,12L),"C");
        assertEquals(DecimalTransformUtils.transform(DecimalTransformUtils.DATASOURCE_36,1666L),"1AA");
        assertEquals(DecimalTransformUtils.transform(DecimalTransformUtils.DATASOURCE_36,1667L),"1AB");
        assertEquals(DecimalTransformUtils.transform(DecimalTransformUtils.DATASOURCE_36,1668L),"1AC");
    }

    @Test
    public void reduction() {
        assertEquals(DecimalTransformUtils.reduction(DecimalTransformUtils.DATASOURCE_10,"1"),Long.valueOf(1));
        assertEquals(DecimalTransformUtils.reduction(DecimalTransformUtils.DATASOURCE_10,"2"),Long.valueOf(2L));
        assertEquals(DecimalTransformUtils.reduction(DecimalTransformUtils.DATASOURCE_10,"3"),Long.valueOf(3L));
        assertEquals(DecimalTransformUtils.reduction(DecimalTransformUtils.DATASOURCE_16,"A"),Long.valueOf(10L));
        assertEquals(DecimalTransformUtils.reduction(DecimalTransformUtils.DATASOURCE_16,"B"),Long.valueOf(11L));
        assertEquals(DecimalTransformUtils.reduction(DecimalTransformUtils.DATASOURCE_16,"C"),Long.valueOf(12L));
        assertEquals(DecimalTransformUtils.reduction(DecimalTransformUtils.DATASOURCE_36,"1AA"),Long.valueOf(1666));
        assertEquals(DecimalTransformUtils.reduction(DecimalTransformUtils.DATASOURCE_36,"1AB"),Long.valueOf(1667));
        assertEquals(DecimalTransformUtils.reduction(DecimalTransformUtils.DATASOURCE_36,"1AC"),Long.valueOf(1668));
    }
}