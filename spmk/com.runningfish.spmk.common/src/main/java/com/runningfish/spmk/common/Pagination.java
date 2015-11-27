/**
 * @(#)Pagination.java 1.0 2015-1-7
 * @Copyright:  Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-1-7
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package com.runningfish.spmk.common;

import java.io.Serializable;

/**
 * 分页
 * Copyright:   Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-1-7 上午10:15:14
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: Initialize
 */
public class Pagination implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    // 当前第几页(默认页数为:1)
    private long page = 1;
    
    // 每页显示多少条(默认每页显示10条)
    private long rows = 10;
    
    // 总共有多少条记录(总计录数)
    private long totalRows;
    
    // 总共有多少页
    private long totalPages;
    
    //起始记录下标
    private long start;
    
    //结束记录下标
    private long end;
    
    // 是否分页
    private boolean isPagination = true;
    
    // 排序字段
    private String order;
    
    // 排序类型
    private String sort;
    
    public Pagination()
    {
    }
    
    public Pagination(Long page, Long rows)
    {
        // 当前第几页
        this.page = page;
        // 每页显示多少条记录
        this.rows = rows;
    }
    
    public long getPage()
    {
        return page;
    }
    
    /**
     * 如果起始页大于总页数,则起始页就是总页数 如果起始页小于等于零,则起始页就是1
     * 
     * @param pageIndex
     */
    public void setPage(long pageIndex)
    {
        if (pageIndex > totalPages)
        {
            this.page = totalPages;
        }
        if (pageIndex <= 0)
        {
            this.page = 1;
        }
        else
        {
            this.page = pageIndex;
        }
    }
    
    public long getRows()
    {
        return rows;
    }
    
    public void setRows(long rows)
    {
        this.rows = rows;
    }
    
    public long getTotalRows()
    {
        return totalRows;
    }
    
    public void setTotalRows(long totalRows)
    {
        if (totalRows != 0)
        {
            totalPages =
                totalRows % rows == 0
                    ? totalRows / rows : (totalRows / rows) + 1;
        }
        this.totalRows = totalRows;
        this.start = (this.page - 1) * this.rows;
        this.end = this.start + this.rows;
    }
    
    public long getTotalPages()
    {
        if (totalPages == 0)
        {
            totalPages = 1;
        }
        return totalPages;
    }
    
    public long getStart()
    {
        return start;
    }
    
    public void setStart(long start)
    {
        this.start = start;
    }
    
    public long getEnd()
    {
        return end;
    }
    
    public void setEnd(long end)
    {
        this.end = end;
    }
    
    /**
     * 禁用分页器(查询出所有的数据)
     */
    public void disabled()
    {
        this.isPagination = false;
        this.page = 1;
        this.rows = 10;
    }
    
    /**
     * 启用分页器(只查询符合条件的数据)
     */
    public void enabled()
    {
        this.isPagination = true;
    }
    
    public boolean getPagination()
    {
        return this.isPagination;
    }
    
    public String getOrder()
    {
        return order;
    }
    
    public void setOrder(String order)
    {
        this.order = order;
    }
    
    public String getSort()
    {
        return sort;
    }
    
    public void setSort(String sort)
    {
        this.sort = sort;
    }
}
