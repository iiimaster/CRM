package com.mryang.crm.settings.web.controller;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mryang.crm.exception.AjaxRequestException;
import com.mryang.crm.exception.TraditionRequestException;
import com.mryang.crm.settings.pojo.DictionaryType;
import com.mryang.crm.settings.pojo.DictionaryValue;
import com.mryang.crm.settings.service.DictionaryTypeService;

import org.apache.poi.ss.formula.udf.IndexedUDFFinder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.xml.transform.Result;
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

    private int pageSize = 10;

    /**
     * 跳转 数据字典模块 首页
     *
     * @return
     */
    @RequestMapping("/toIndex.do")
    public String toIndex() {
        return "/settings/dictionary/index";
    }

    // -------------------------------字典类型-模块-------------------------------

    /**
     * 字典类型-首页加载
     *
     * @return
     */
    @RequestMapping("/type/toTypeIndex.do")
    public String toTypeIndex() {
        return "/settings/dictionary/type/index";
    }

    /**
     * 加载首页面数据-传递到前端
     *
     * @return
     */
    @RequestMapping("/type/findAllTypeList.do")
    @ResponseBody
    public Map<String, Object> findAllTypeList() {
        List<DictionaryType> dictionaryList = dictionaryTypeService.findAll();
        Map<String, Object> resultMap = new HashMap<>();

        if (dictionaryList == null && dictionaryList.size() == 0) {
            resultMap.put("success", false);
            resultMap.put("msg", "数据加载失败");
            resultMap.put("data", dictionaryList);//null
        } else {
            resultMap.put("success", true);
            resultMap.put("msg", "数据加载成功");
            resultMap.put("data", dictionaryList);
        }

        return resultMap;
    }

    /**
     * 跳转到 保存数据页面 save.jsp
     */
    @RequestMapping("/type/toTypeSave.do")
    public String toTypeSave() {
        return "/settings/dictionary/type/save";
    }


    /**
     * 保存数据-页面
     *
     * @param code
     * @param name
     * @param describe
     * @return
     */
    @RequestMapping("/type/typeSave.do")
    @ResponseBody
    public Map<String, Object> typeSave(String code, String name, String describe) throws AjaxRequestException {

        // 1.校验
        if (code == null || code == "") {
            throw new AjaxRequestException("字典类型编码必须填写");
        }

        // 查询数据字典类型是否存在
        DictionaryType dictionaryType = dictionaryTypeService.checkType(code);

        if (dictionaryType != null) {
            throw new AjaxRequestException("字典类型已经存在，不能重复添加！");
        }

        // 2.创建map用于存放结果信息
        Map<String, Object> resultMap = new HashMap<>();

//        System.out.println(code+"::>>>"+name+"::>>>"+describe);
        // 3.添加数据
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
    public String toTypeEdit(String code, Model model) throws TraditionRequestException {

        // 查询要编辑的数据
        DictionaryType dictionaryType = dictionaryTypeService.findByCode(code);

        // 将查询出来的数据信息放入request中
        model.addAttribute("dt", dictionaryType);

        // 跳转页面
        return "/settings/dictionary/type/edit";
    }

    /**
     * 修改数据
     *
     * @param code     修改后的主键
     * @param name     修改后的名称
     * @param describe 修改后的描述
     * @return
     * @throws AjaxRequestException
     */
    @RequestMapping("/type/updateType.do")
    @ResponseBody
    public Map<String, Object> updateType(String code, String name, String describe) throws TraditionRequestException {

        // 1.修改
        dictionaryTypeService.updateType(code, name, describe);

        // 2.返回结果集
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        resultMap.put("msg", "修改成功");

        return resultMap;

    }

    @RequestMapping("/type/delType.do")
    @ResponseBody
    public Map<String, Object> delType(String delTypeIds) throws AjaxRequestException {

        HashMap<String, Object> resultMap = new HashMap<>();

        // 分割字符串
        String[] ids = delTypeIds.split("-");
//        System.out.println(ids.toString());

        // 调用服务层方法进行删除操作
        dictionaryTypeService.deleteType(ids);

        resultMap.put("success", true);
        resultMap.put("msg", "删除成功");

        return resultMap;

    }


    // -------------------------------字典类型-模块-------------------------------


    // -------------------------------字典值-模块-------------------------------

    /**
     * 字典值-首页加载
     *
     * @return
     */
    @RequestMapping("/value/toValueIndex.do")
    public String toValueIndex(Model model) throws TraditionRequestException {

        List<DictionaryValue> values = dictionaryTypeService.findAllValues();

        if (values == null && values.size() == 0) {
            throw new TraditionRequestException("数据加载失败");
        }

        model.addAttribute("values", values);

        return "/settings/dictionary/value/index";
    }


    /**
     * 分页操作-展示数据-pageHelper插件实现
     *
     * @return
     */
    @RequestMapping("/value/queryByPageHelper.do")
    public String queryByPageHelper(@RequestParam(defaultValue = "1") int page,
                                    Model model) {
        // 等同于 limit a,b  使用拦截器实现的
        PageHelper.startPage(page, pageSize);

        // 查询数据
        List<DictionaryValue> values = dictionaryTypeService.findAllValues();
        PageInfo pageInfo = new PageInfo<DictionaryValue>(values);

//        System.out.println("values :::>>> "+values.toString());
//        System.out.println("pageInfo :::>>> "+pageInfo);


        // 总记录数
        Long count = pageInfo.getTotal();
        // 总页数
        int pages = pageInfo.getPages();

//        System.out.println("count :::>>> "+count);
//        System.out.println("pages :::>>> "+pages);


        // 获取要 加载页 的 第一条数据索引(数据库数据索引从0开始)
        int pageNum = (page - 1) * pageSize;
        model.addAttribute("pageNum", pageNum);

        // 当前页码
        model.addAttribute("pageNow", page);

        // 将数据放入作用域
        model.addAttribute("values", pageInfo.getList());
        model.addAttribute("pages", pages);
//        System.out.println("pageInfo.getList() ::>>> "+pageInfo.getList());


        // 转发到列表页
        return "/settings/dictionary/value/index";
    }

    @RequestMapping("/value/toValueSave.do")
    public String toValueSave(Model model) {

        // 查询字典类型
        List<DictionaryType> types = dictionaryTypeService.findAll();

        // 将数据放入作用域
        model.addAttribute("types", types);

        return "/settings/dictionary/value/save";
    }


    @RequestMapping("/value/saveValue.do")
    @ResponseBody
    public Map<String, Object> saveValue(String typeCode,
                                         String value,
                                         String text,
                                         String orderNo) throws TraditionRequestException {

        HashMap<String, Object> resultMap = new HashMap<>();

        // 保存（添加）字典值
        int i = dictionaryTypeService.saveValue(typeCode, value, text, orderNo);

        if (i<=0){// 添加失败
            throw new TraditionRequestException("数据字典值添加失败");
        }else {
            resultMap.put("success",true);
            resultMap.put("msg","数据字典值添加成功.");
        }

        return resultMap;

    }


    @RequestMapping("/value/toEditValue.do")
    public String toEditValue(String id,Model model) throws TraditionRequestException {
        // 根据id查询字典值
        DictionaryValue value = dictionaryTypeService.findValueById(id);

        if (value == null){
            throw new TraditionRequestException("要修改的数据不存在！");
        }

        // 将数据放入作用域
        model.addAttribute("value", value);

        return "/settings/dictionary/value/edit";
    }

    @RequestMapping("/value/editValue.do")
    @ResponseBody
    public Map<String,Object> editValue(DictionaryValue dictionaryValue) throws TraditionRequestException {

//        System.out.println(dictionaryValue);

        // 修改数据
        int i = dictionaryTypeService.updateValue(dictionaryValue);

        HashMap<String, Object> resultMap = new HashMap<>();

        if (i<=0){
            resultMap.put("success",false);
            resultMap.put("msg","修改失败");
        }else{
            resultMap.put("success",true);
            resultMap.put("msg","修改成功");
        }

        return resultMap;
    }




    // -------------------------------字典值-模块-------------------------------

}



