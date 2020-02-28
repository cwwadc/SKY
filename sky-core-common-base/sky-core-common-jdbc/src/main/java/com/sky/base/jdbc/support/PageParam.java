package com.sky.base.jdbc.support;

/**
 * @author lizp
 * @version 1.0.0
 * @Title
 * @Description
 * @date 2019/4/26
 */
public class PageParam {
    private int page;
    private int pageSize;
    private int startRow;
    private int endRow;

    public PageParam() {
        this(0, 10000);
    }

    public PageParam(int pageSize) {
        this(0, pageSize);
    }

    public PageParam(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        refresh();
    }

    private void refresh() {
        this.startRow = page * pageSize;
        this.endRow = startRow + pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void nextPage() {
        page++;
        refresh();
    }
}