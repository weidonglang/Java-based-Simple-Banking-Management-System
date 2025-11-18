package com.web.entity;

import java.util.List;

/**
 * 分页通用实体类。
 * @param <T> 当前页数据的类型
 */
public class Page<T> {

    /** 总记录数 */
    private int total;

    /** 每页条数 */
    private int size;

    /** 当前页码（从 1 开始） */
    private int currentPage;

    /** 总页数 */
    private int totalPage;

    /** 当前页数据集合 */
    private List<T> data;

    public Page() {
    }

    public Page(int total, int size, int currentPage, int totalPage, List<T> data) {
        this.total = total;
        this.size = size;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Page{" +
                "total=" + total +
                ", size=" + size +
                ", currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", dataSize=" + (data == null ? 0 : data.size()) +
                '}';
    }
}
