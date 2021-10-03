package com.mryang.crm.settings.web.controller;


import com.mryang.crm.exception.AjaxRequestException;
import com.mryang.crm.settings.pojo.DictionaryType;
import com.mryang.crm.settings.service.DictionaryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName DictionaryController.java
 * @Description TODO 数据字典表-模块
 * @createTime 2021年09月29日 13:52:00
 */
@Controller
@RequestMapping("/settings/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    /**
     * 跳转 数据字典模块 首页
     * @return
     */
    @RequestMapping("/toIndex.do")
    public String toIndex(){
        return "/settings/dictionary/index";
    }

    // -------------------------------字典类型-模块-------------------------------
    /**
     * 字典类型-首页加载
     * @return
     */
    @RequestMapping("/type/toTypeIndex.do")
    public String toTypeIndex(){
        return "/settings/dictionary/type/index";
    }

    /**
     * 加载首页面数据-传递到前端
     * @return
     */
    @RequestMapping("/type/findAllTypeList.do")
    @ResponseBody
    public Map<String,Object> findAllTypeList(){
        List<DictionaryType> dictionaryList = dictionaryTypeService.findAll();
        Map<String, Object> resultMap = new HashMap<>();

        if (dictionaryList == null && dictionaryList.size()==0){
            resultMap.put("success",false);
            resultMap.put("msg","数据加载失败");
            resultMap.put("data",dictionaryList);//null
        }else {
            resultMap.put("success",true);
            resultMap.put("msg","数据加载成功");
            resultMap.put("data",dictionaryList);
        }

        return resultMap;
    }

    /**
     * 跳转到 保存数据页面 save.jsp
     */
    @RequestMapping("/type/toTypeSave.do")
    public String toTypeSave(){
        return "/settings/dictionary/type/save";
    }


    /**
     * 修改数据页面
     *
     * @param code
     * @param name
     * @param describe
     * @return
     */
    @RequestMapping("/type/typeSave.do")
    @ResponseBody
    public Map<String, Object> typeSave(String code, String name, String describe) throws AjaxRequestException {

        // 校验-查询数据字典类型是否存在
        dictionaryTypeService.checkType(code);

        // 创建map用于存放结果信息
        Map<String, Object> resultMap = new HashMap<>();

//        System.out.println(code+"::>>>"+name+"::>>>"+describe);
        // 添加数据
        dictionaryTypeService.saveType(code, name, describe);

        // 数据添加成功
        resultMap.put("success", true);
        resultMap.put("msg", "数据添加成功！");

        return resultMap;
    }

    /**
     * 跳转到 修改数据页面 edit.jsp
     */
    @RequestMapping("/type/toTypeEdit.do")
    public String toTypeEdit(){
        return "/settings/dictionary/type/edit";
    }


    // -------------------------------字典类型-模块-------------------------------


    // -------------------------------字典值-模块-------------------------------
    /**
     * 字典值-首页加载
     * @return
     */
    @RequestMapping("/value/toValueIndex.do")
    public String toValueIndex(){
        return "/settings/dictionary/value/index";
    }

    // -------------------------------字典值-模块-------------------------------


}
