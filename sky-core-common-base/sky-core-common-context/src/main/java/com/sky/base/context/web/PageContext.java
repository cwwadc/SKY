package com.sky.base.context.web;

/**
 * @author zzq
 * @version 1.0.0
 * @Title {@link PageContext}
 * @Description 分页参数上下文
 * @date 2019/5/20
 */
public class PageContext {

    private static final ThreadLocal<PageContext> CONTEXT = ThreadLocal.withInitial(PageContext::new);

    private int page;
    private int limit;
    private long count;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public static PageContext getCurrentContext() {
        return CONTEXT.get();
    }

    public void unset() {
        CONTEXT.remove();
    }
}
