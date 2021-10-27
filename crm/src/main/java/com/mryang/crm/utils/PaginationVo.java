package com.mryang.crm.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页的公共返回值
 * vo:
 *      view object,实体类
 *      封装的是分页的参数
 *
 * 该实体类经过json转换:
 *      {
 *          success:true/false,
 *          msg:xxx,
 *          xxx:xxx...
 *          data:[{市场活动...}...]
 *      }
 */
public class PaginationVo<T> implements Serializable {

    private boolean success;
    private String  msg;

    private Integer totalPages;
    private Integer totalCount;
    private Integer pageNo;
    private Integer pageSize;

    //封装的分页参数
    private List<T> data;

    public PaginationVo() {
    }

    public PaginationVo(boolean success, String msg,Integer pageNo, Integer pageSize, Integer totalCount,Integer totalPages,   List<T> data) {
        this.success = success;
        this.msg = msg;
        this.totalPages = totalPages;
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.data = data;
    }

    @Override
    public String toString() {
        return "PaginationVo{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", totalPages=" + totalPages +
                ", totalCount=" + totalCount +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", data=" + data +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
