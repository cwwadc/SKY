package com.sky.core.common;

/**
 * 全局常量
 *
 * @author : lzm
 * @date : 2017/9/19
 */
public abstract class GlobalConstant {

    /** 全局父节点 */
    public static final String DEFAULT_PARENT_KEY            = "0";

    /** 默认分页起始值 */
    public static final int    DEFAULT_PAGE_NUM              = 1;
    /** 默认分页大小值 */
    public static final int    DEFAULT_PAGE_SIZE             = 10;
    /** 分页起始参数名称 */
    public static final String PAGE_NUM_PARAM_NAME           = "offset";
    /** 分页大小参数名称 */
    public static final String PAGE_SIZE_PARAM_NAME          = "limit";

    /**
     * 支付密码长度
     */
    public static final Integer PAY_PASS_LENTH = 6;
    
    /**
     * 祖先id
     */
    public static final Long   PERMISSION_RESOURCE_ROOT_ID   = 0L;
    public static final Long   GROUP_ROOT_ID                 = 0L;
    public static final Long DEPT_ROOT_ID = 0L;
    
    /** 验证码请求头key **/
    public static final String CAPTCHA_REQUEST_HEADER_KEY    = "captcha";
    /** 验证码有效期,单位:秒 **/
    public static final int    CAPTCHA_TIME_OUT_SECOND       = 600;
    
    /** 多数据源key : 商城数据源 **/
    public static final String MALL_DATA_SOURCE_KEY          = "dataSourceOfMall";
    /** 多数据源key : 业务平台数据源 **/
    public static final String BIZ_PLATFORM_DATA_SOURCE_KEY  = "dataSourceOfBizPlatform";
    /** 全局定义得图片文件夹路径前缀 **/
    public static final String IMAGES_PATH = "/images/";
    /** 全局定义得资源文件夹路径前缀 **/
    public static final String FILES_PATH = "/files/";

}
