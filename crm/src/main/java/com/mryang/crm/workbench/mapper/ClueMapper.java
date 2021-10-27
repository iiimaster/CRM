package com.mryang.crm.workbench.mapper;

import com.mryang.crm.workbench.pojo.Clue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ClueMapper.java
 * @Description TODO
 * @createTime 2021年10月20日 21:05:00
 */
public interface ClueMapper {
    /**
     * 分页查询
     * @param page 第几页
     * @param pageSize 每页显示条数
     * @return
     */
    List<Clue> queryClueListByPage(@Param("pageNo") Integer pageNo,
                                   @Param("pageSize") Integer pageSize);

    /**
     * 查询数据总条数
     * @return
     */
    int clueListCount();

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Clue queryClueById(String id);

    // 条件检索
    List<Clue> queryClueByRetrieve(@Param("fullname") String fullname,
                                   @Param("company") String company,
                                   @Param("phone") String phone,
                                   @Param("source") String source,
                                   @Param("createBy") String createBy,
                                   @Param("myphone") String myphone,
                                   @Param("state") String state);

}
