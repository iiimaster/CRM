package com.mryang.crm.workbench.web.controller;

import com.mryang.crm.exception.AjaxRequestException;
import com.mryang.crm.settings.pojo.DictionaryValue;
import com.mryang.crm.settings.service.DictionaryValueService;
import com.mryang.crm.utils.HandleFlag;
import com.mryang.crm.utils.PaginationVo;
import com.mryang.crm.workbench.pojo.Clue;
import com.mryang.crm.workbench.service.ActivityService;
import com.mryang.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ClueController.java
 * @Description TODO 线索模块
 * @createTime 2021年10月19日 21:04:00
 */
@Controller
@RequestMapping("/workbench/clue")
public class ClueController {

    @Autowired
    private ClueService clueService;

    @Autowired
    private DictionaryValueService dictionaryValueService;

    /**
     * 线索页面-首页面展示-页面相关数据加载
     *
     * @return
     */
    @RequestMapping("/toIndex.do")
    public String toIndex(Model model) {

        // 获取线索状态
        List<DictionaryValue> clueState = dictionaryValueService.getClueState();
        // 获取线索来源
        List<DictionaryValue> clueSource = dictionaryValueService.getClueSource();

        model.addAttribute("clueState", clueState);
        model.addAttribute("clueSource", clueSource);

//        System.out.println(clueState);
//        System.out.println(clueSource);


        return "/workbench/clue/index";
    }


    /**
     * 分页数据展示
     *
     * @param page
     * @param pageSize
     * @return
     * @throws AjaxRequestException
     */
    @RequestMapping("/getClueListByPage.do")
    @ResponseBody
    public PaginationVo<Clue> getClueListByPage(Integer page,
                                                Integer pageSize,
                                                String fullname,
                                                String company,
                                                String phone,
                                                String source,
                                                String createBy,
                                                String myphone,
                                                String state) throws AjaxRequestException {

        // 获取加载的页数对应的第一条数据索引
        Integer pageNo = (page - 1) * pageSize;

        // 分页查询
//        List<Clue> clues = clueService.queryClueListByPage(pageNo, pageSize);
        // 条件检索
        List<Clue> clues = clueService.queryClueByRetrieve(fullname, company, phone, source, createBy, myphone, state);

        if (clues == null || clues.size() <= 0) {
//            throw new AjaxRequestException("线索查询失败");
            return new PaginationVo<>(true, "分页查询失败", page, pageSize, 0, 0, clues);
        }

        // 获取数据总条数
        int clueCount = clueService.clueListCount();


        // 总页数
        int pageNum = clueCount % pageSize == 0 ? clueCount / pageSize : clueCount / pageSize + 1;


        return new PaginationVo<>(true, "分页查询成功", page, pageSize, clueCount, pageNum, clues);
    }

    /**
     * 条件检索
     */
    /*@RequestMapping("/clueRetrieve.do")
    @ResponseBody
    public Map<String, Object> clueRetrieve(String fullname,
                                            String company,
                                            String phone,
                                            String source,
                                            String createBy,
                                            String myphone,
                                            String state) {
        // 条件检索
        List<Clue> clues = clueService.queryClueByRetrieve(fullname, company, phone, source, createBy, myphone, state);
//        System.out.println("clues :::>>>> "+clues);

        return HandleFlag.successObj("msg", clues);
    }*/

    /**
     * 跳转到线索详情页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toDetail.do")
    public String toDetail(String id, Model model) {

        Clue clue = clueService.queryClueById(id);

        model.addAttribute("clue'", clue);

        return "/workbench/clue/detail";
    }


}
