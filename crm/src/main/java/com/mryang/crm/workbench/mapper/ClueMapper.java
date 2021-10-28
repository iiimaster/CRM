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

    // 添加线索
    int saveClue(@Param("id") String id,
                 @Param("owner") String owner,
                 @Param("company") String company,
                 @Param("appellation") String appellation,
                 @Param("fullname") String fullname,
                 @Param("job") String job,
                 @Param("email") String email,
                 @Param("phone") String phone,
                 @Param("website") String website,
                 @Param("mphone") String mphone,
                 @Param("state") String state,
                 @Param("source") String source,
                 @Param("description") String description,
                 @Param("contactSummary") String contactSummary,
                 @Param("nextContactTime") String nextContactTime,
                 @Param("createBy") String createBy,
                 @Param("createTime") String createTime,
                 @Param("address") String address);
}
