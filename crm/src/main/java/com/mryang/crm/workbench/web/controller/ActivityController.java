package com.mryang.crm.workbench.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mryang.crm.exception.AjaxRequestException;
import com.mryang.crm.exception.TraditionRequestException;
import com.mryang.crm.settings.pojo.User;
import com.mryang.crm.settings.service.UserService;
import com.mryang.crm.utils.DateTimeUtil;
import com.mryang.crm.utils.HandleFlag;
import com.mryang.crm.utils.UUIDUtil;
import com.mryang.crm.workbench.pojo.Activity;
import com.mryang.crm.workbench.pojo.ActivityRemark;
import com.mryang.crm.workbench.service.ActivityRemarkService;
import com.mryang.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName ActivityController.java
 * @Description TODO 工作台-市场活动
 * @createTime 2021年10月09日 10:51:00
 */
@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemarkService;


    /**
     * 跳转到市场活动页面，展示所有市场活动数据--未分页
     *
     * @return
     */
    /*@RequestMapping("/toindex.do")
    public String toindex(Model model) throws AjaxRequestException {

        // 用户信息查询
        List<User> users = userService.queryAllUser();

        // 市场活动类表数据查询-未分页
        List<Activity> activities = activityService.getActivityList();
        if (activities == null) {
            throw new AjaxRequestException("数据列表加载失败");
        }
        model.addAttribute("activities", activities);
        // 将用户数据放入作用域
        model.addAttribute("users", users);

        return "/workbench/activity/index";
    }*/

    // 数据检索，通过名称、所有者、开始日期、结束日期查询相关数据
    /**
     * 市场活动数据列表查询-分页查询
     *
     * @param model
     * @return
     */
    @RequestMapping("/getActivitisByPageHelper.do")
    public String getActivityList(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "8") int pageSize,
                                  String activityName,
                                  String owner,
                                  String startDate,
                                  String endDate,
                                  Model model) throws AjaxRequestException {


        // 等同于 limit a,b  使用拦截器实现的
        PageHelper.startPage(page, pageSize);

        List<Activity> activities =null;
        // 查询数据
        if (activityName == null && owner == null && startDate== null && endDate==null){ // 单纯分页查询执行
            activities = activityService.getActivityList();
            if (activities == null || activities.size() <= 0) {
                throw new AjaxRequestException("市场活动数据列表查询失败");
            }
        }else {// 数据检索时使用此方法
            activities = activityService.getActivityByConditions(activityName,owner,startDate,endDate);
        }


        PageInfo pageInfo = new PageInfo<Activity>(activities);

        // 总记录数
        Long count = pageInfo.getTotal();

        // 总页数
        int pages = pageInfo.getPages();

        // 获取要 加载页 的 第一条数据索引(数据库数据索引从0开始)
        int pageNum = (page - 1) * pageSize;


        // 将数据放入作用域
        // 总记录数
        model.addAttribute("count", count);
        // 当前页码
        model.addAttribute("pageNow", page);
        // 市场活动列表数据
        model.addAttribute("activities", pageInfo.getList());
        // 总页数
        model.addAttribute("pages", pages);
        // 每页显示条数
        model.addAttribute("pageSize", pageSize);

        // 转发到列表页
        return "/workbench/activity/index";
    }


    // 添加
    /**
     * 跳转到添加数据界面
     * @return
     * @throws AjaxRequestException
     */
    @RequestMapping("/toSaveActivity.do")
    @ResponseBody
    public Map<String, Object> toSaveActivity() throws AjaxRequestException {
        // 用户信息查询
        List<User> users = userService.queryAllUser();

        if (users == null && users.size() <= 0) {
            throw new AjaxRequestException("用户信息查询失败");
        }

        // 返回响应信息
        return HandleFlag.successObj("data", users);
    }

    /**
     * 添加数据操作
     * @param owner
     * @param name
     * @param startDate
     * @param endDate
     * @param cost
     * @param description
     * @return
     * @throws AjaxRequestException
     */
    @RequestMapping("/saveActivity.do")
    @ResponseBody
    public Map<String, Object> saveActivity(String owner,
                                            String name,
                                            String startDate,
                                            String endDate,
                                            String cost,
                                            String description) throws AjaxRequestException {

        // 获取创建者姓名
        User user = userService.queryUserById(owner);
        /*System.out.println("user:::>>>"+user);
        System.out.println("owner:::>>>"+owner);
        System.out.println("name:::>>>"+name);
        System.out.println("startDate:::>>>"+startDate);
        System.out.println("endDate:::>>>"+endDate);
        System.out.println("cost:::>>>"+cost);
        System.out.println("description:::>>>"+description);*/

        if (user == null){ // 用户信息不存在
            throw new AjaxRequestException("创建者用户信息错误");
        }

        // 获取当前时间为创建时间
        String createTime = DateTimeUtil.getSysTime();

        // 开始时间不能比结束时间晚
        if (startDate.compareTo(endDate) > 0){ // 开始时间 > 结束时间
            throw new AjaxRequestException("开始时间不能晚于结束时间");
        }

        // 获取一个随机uuId
        String activityId = UUIDUtil.getUUID();

        // 添加市场活动数据
        int i = activityService.saveActivity(activityId, owner, name, startDate, endDate, cost, description, createTime, user.getName());

        if (i <= 0){
            throw new AjaxRequestException("市场活动数据添加失败");
        }

        return HandleFlag.successTrue();
    }

    // 修改
    /**
     * 修改页面跳转，数据加载
     * @return
     */
    @RequestMapping("/toUpdate.do")
    @ResponseBody
    public Map<String,Object> toUpdate(String id,Model model) throws AjaxRequestException {

        List<User> users = userService.queryAllUser();

        if(users == null && users.size() <=0){
            throw new AjaxRequestException("无法获取用户信息");
        }

        Activity activity = activityService.queryActivityById(id);

        if (activity == null ){
            throw new AjaxRequestException("要修改的市场活动信息不存在");
        }

        HashMap<String, Object> resultMap = new HashMap<>();

        resultMap.put("success",true);
        resultMap.put("users",users);
        resultMap.put("activity",activity);

        return resultMap;
    }


    /**
     * 修改市场活动
     * @param editBy
     * @param id
     * @param owner
     * @param name
     * @param startDate
     * @param endDate
     * @param cost
     * @param description
     * @return
     * @throws AjaxRequestException
     */
    @RequestMapping("/updateActivity.do")
    @ResponseBody
    public Map<String,Object> updateActivity(String editBy,
                                             String id,
                                             String owner,
                                             String name,
                                             String startDate,
                                             String endDate,
                                             String cost,
                                             String description) throws AjaxRequestException {

        // 获取当前时间，作为修改时间
        String editTime = DateTimeUtil.getSysTime();
        // 获取操作者名称
        User user = userService.queryUserById(editBy);
        if (user == null ){
            throw new AjaxRequestException("修改操作失败！-操作者信息有误");
        }
        editBy = user.getName();

        // 修改操作
        int i = activityService.updateActivity(id, owner, name, startDate, endDate, cost, description, editBy, editTime);

        if (i<=0){
            throw new AjaxRequestException("修改数据失败！");
        }

        return HandleFlag.successTrue();
    }

    // 删除
    /**
     * 根据id删除市场活动数据
     * @param ids
     * @return
     * @throws AjaxRequestException
     */
    @RequestMapping("/deleteActivityByIds.do")
    @ResponseBody
    public Map<String ,Object> deleteActivityByIds(String ids) throws AjaxRequestException {

        // 删除数据
        activityService.deleteActivityByIds(ids);

//        return HandleFlag.error("msg","error");
        return HandleFlag.successTrue();
    }


    // 数据 导入、导出

    /**
     * 全部数据导出
     * @param response
     * @throws AjaxRequestException
     * @throws IOException
     */
    @RequestMapping("/exportActivityAll.do")
    public void exportActivityAll(HttpServletResponse response) throws AjaxRequestException, IOException {
        // 获取所有市场活动数据
        List<Activity> activityList = activityService.getActivityList();

        if (activityList == null || activityList.size() <= 0){
            throw new AjaxRequestException("市场活动数据查询失败");
        }


        // 创建一个文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一页
        HSSFSheet sheet = workbook.createSheet();

        // 表头
            // id
            // name
            // owner
            // startDate
            // endDate
            // cost
            // description
            // createBy
            // createTime
            // editBy
            // editTime
        // 创建一行
        // rownum为索引 从0开始
        HSSFRow row = sheet.createRow(0);

        // 每一个单元格中的值,column 索引从0开始
        HSSFCell cell1 = row.createCell(0);
        cell1.setCellValue("唯一索引");

        HSSFCell cell2 = row.createCell(1);
        cell2.setCellValue("市场活动名称");

        HSSFCell cell3 = row.createCell(2);
        cell3.setCellValue("所有者标识");

        HSSFCell cell4 = row.createCell(3);
        cell4.setCellValue("开始时间");

        HSSFCell cell5 = row.createCell(4);
        cell5.setCellValue("结束时间");

        HSSFCell cell6 = row.createCell(5);
        cell6.setCellValue("成本");

        HSSFCell cell7 = row.createCell(6);
        cell7.setCellValue("描述");

        HSSFCell cell8 = row.createCell(7);
        cell8.setCellValue("创建人");

        HSSFCell cell9 = row.createCell(8);
        cell9.setCellValue("创建时间");

        HSSFCell cell10 = row.createCell(9);
        cell10.setCellValue("修改人");

        HSSFCell cell11 = row.createCell(10);
        cell11.setCellValue("修改时间");


        for (int i = 0; i < activityList.size(); i++) {
            // 创建一行
            // rownum为索引 从0开始
            row = sheet.createRow(i+1);

            Activity activity = activityList.get(i);

            HSSFCell c1 = row.createCell(0);
            c1.setCellValue(activity.getId());

            HSSFCell c2 = row.createCell(1);
            c2.setCellValue(activity.getName());

            HSSFCell c3 = row.createCell(2);
            c3.setCellValue(activity.getOwner());

            HSSFCell c4 = row.createCell(3);
            c4.setCellValue(activity.getStartDate());

            HSSFCell c5 = row.createCell(4);
            c5.setCellValue(activity.getEndDate());

            HSSFCell c6 = row.createCell(5);
            c6.setCellValue(activity.getCost());

            HSSFCell c7 = row.createCell(6);
            c7.setCellValue(activity.getDescription());

            HSSFCell c8 = row.createCell(7);
            c8.setCellValue(activity.getCreateBy());

            HSSFCell c9 = row.createCell(8);
            c9.setCellValue(activity.getCreateTime());

            HSSFCell c10 = row.createCell(9);
            c10.setCellValue(activity.getEditBy());

            HSSFCell c11 = row.createCell(10);
            c11.setCellValue(activity.getEditTime());

        }

        //为客户浏览器提供下载框
        response.setContentType("octets/stream");
        response.setHeader("Content-Disposition","attachment;filename=Activity-"+DateTimeUtil.getSysTime()+".xls");

        OutputStream out = response.getOutputStream();

        workbook.write(out);


    }


    /**
     * 部分数据导出
     * @param ids
     * @param response
     * @return
     * @throws AjaxRequestException
     */
    @RequestMapping("/exportActivityXz.do")
    public void exportActivityXz(String ids,
                                 HttpServletResponse response) throws AjaxRequestException, IOException {

        List<Activity> activityListByIds = activityService.getActivityListByIds(ids);


        // 创建一个文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一页
        HSSFSheet sheet = workbook.createSheet();

        // 表头
        // id
        // name
        // owner
        // startDate
        // endDate
        // cost
        // description
        // createBy
        // createTime
        // editBy
        // editTime
        // 创建一行
        // rownum为索引 从0开始
        HSSFRow row = sheet.createRow(0);

        // 每一个单元格中的值,column 索引从0开始
        HSSFCell cell1 = row.createCell(0);
        cell1.setCellValue("唯一索引");

        HSSFCell cell2 = row.createCell(1);
        cell2.setCellValue("市场活动名称");

        HSSFCell cell3 = row.createCell(2);
        cell3.setCellValue("所有者标识");

        HSSFCell cell4 = row.createCell(3);
        cell4.setCellValue("开始时间");

        HSSFCell cell5 = row.createCell(4);
        cell5.setCellValue("结束时间");

        HSSFCell cell6 = row.createCell(5);
        cell6.setCellValue("成本");

        HSSFCell cell7 = row.createCell(6);
        cell7.setCellValue("描述");

        HSSFCell cell8 = row.createCell(7);
        cell8.setCellValue("创建人");

        HSSFCell cell9 = row.createCell(8);
        cell9.setCellValue("创建时间");

        HSSFCell cell10 = row.createCell(9);
        cell10.setCellValue("修改人");

        HSSFCell cell11 = row.createCell(10);
        cell11.setCellValue("修改时间");


        for (int i = 0; i < activityListByIds.size(); i++) {
            // 创建一行
            // rownum为索引 从0开始
            row = sheet.createRow(i+1);

            Activity activity = activityListByIds.get(i);

            HSSFCell c1 = row.createCell(0);
            c1.setCellValue(activity.getId());

            HSSFCell c2 = row.createCell(1);
            c2.setCellValue(activity.getName());

            HSSFCell c3 = row.createCell(2);
            c3.setCellValue(activity.getOwner());

            HSSFCell c4 = row.createCell(3);
            c4.setCellValue(activity.getStartDate());

            HSSFCell c5 = row.createCell(4);
            c5.setCellValue(activity.getEndDate());

            HSSFCell c6 = row.createCell(5);
            c6.setCellValue(activity.getCost());

            HSSFCell c7 = row.createCell(6);
            c7.setCellValue(activity.getDescription());

            HSSFCell c8 = row.createCell(7);
            c8.setCellValue(activity.getCreateBy());

            HSSFCell c9 = row.createCell(8);
            c9.setCellValue(activity.getCreateTime());

            HSSFCell c10 = row.createCell(9);
            c10.setCellValue(activity.getEditBy());

            HSSFCell c11 = row.createCell(10);
            c11.setCellValue(activity.getEditTime());

        }

        //为客户浏览器提供下载框
        response.setContentType("octets/stream");
        response.setHeader("Content-Disposition","attachment;filename=Activity-"+DateTimeUtil.getSysTime()+".xls");

        OutputStream out = response.getOutputStream();

        workbook.write(out);

    }


    /**
     * 批量文件导入(Excel)
     *
     * @param activityFile 文件信息
     * @return
     * @throws IOException
     */
    @RequestMapping("/importActivity.do")
    @ResponseBody
    public Map<String, Object> importActivity(MultipartFile activityFile,
                                              HttpSession session) throws IOException, AjaxRequestException {
        // 此处的参数 activityFile 必须与放入 FormData 中的属性值对应
        // 获取session中的数据信息（登录的用户信息）
        User user = (User) session.getAttribute("user");
        String owner = user.getId();
        String createBy = user.getName();

//        System.out.println("owner :::>>>> " + owner);
//        System.out.println("name :::>>>> " + createBy);

        // 获取创建时间
        String createTime = DateTimeUtil.getSysTime();


        // 获取原文件名称
        String originalFilename = activityFile.getOriginalFilename();

        // 获取扩展名
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        // 设置新的文件名称
        String newFileName = DateTimeUtil.getSysTimeForUpload() + "." + ext;

        // 设置文件上传路径（真实路径）
        String path = "F:/yx2009/projectCRM/crm/src/main/webapp/temDir";
        // 获取target目录中的temDir
//        String path = request.getServletContext().getRealPath("/tmpDir");

        // 将文件上传到服务器中
        activityFile.transferTo(new File(path + "/" + newFileName));

        // TODO 将文件的类容批量导入到数据库中
        // 批量导入功能
        // 将刚刚上传的文件转换成输入流
        InputStream inputStream = new FileInputStream(new File(path + "/" + newFileName));

        // Excel工作簿对象需要一个输入流，会将输入流转换成工作簿对象
        // 也就是将实体的Excel文件读取到内存中
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        // 读取第一页的内容
        HSSFSheet sheet = workbook.getSheetAt(0);

        // 将内存的工作簿对象，值读取出来，封装到市场活动的实体类中
        // 根据sheet页获取行号进行遍历
        int lastRowNum = sheet.getLastRowNum(); // 获取最后的行号
//        System.out.println("lastRowNum :::>>>> " + lastRowNum);

        //创建封装的集合
        List<Activity> activityList = new ArrayList<>();

        // 循环的时候，跳过表头，循环第二行,下标从0开始
        for (int i = 1;i <= lastRowNum -1; i++) {

            // 创建封装的Activity对象
            Activity activity = new Activity();

            // 获取每一行的数据
            HSSFRow row = sheet.getRow(i);

            // 获取最后一行的行号
            short lastCellNum = row.getLastCellNum();
//            System.out.println("lastCellNum :::>>>> " + lastCellNum);

            // 遍历获取每个单元格中的数据
            for (int j = 0; j < lastCellNum; j++) {
                // 将单元格中的数据赋值给activity对象
                if (j == 0) {
                    // 市场活动名称
                    String activityName = row.getCell(j).getStringCellValue();
                    activity.setName(activityName);
                } else if (j == 1) {
                    // 开始日期
                    String startDate = row.getCell(j).getStringCellValue();
                    activity.setStartDate(startDate);
                } else if (j == 2) {
                    // 结束日期
                    String endDate = row.getCell(j).getStringCellValue();
                    activity.setEndDate(endDate);
                } else if (j == 3) {
                    // 成本
                    String cost = row.getCell(j).getStringCellValue();
                    activity.setCost(cost);
                } else if (j == 4) {
                    // 描述
                    String description = row.getCell(j).getStringCellValue();
                    activity.setDescription(description);
                }
            }

            // 设置其他属性值
            activity.setId(UUIDUtil.getUUID());
            activity.setOwner(owner);
            activity.setCreateBy(createBy);
            activity.setCreateTime(createTime);

            // 放入集合中
            activityList.add(activity);
        }

//        System.out.println("activityList :::>>> "+activityList.toString());

        if (activityList == null || activityList.size() <= 0){
            throw new AjaxRequestException("批量导入失败!");
        }
        // 批量导入操作
        int count = activityService.saveImportActivity(activityList);

        if (count <= 0){
            throw new AjaxRequestException("批量导入失败!");
        }

        // 返回批量导入的数据数
        return HandleFlag.successObj("data",count);
    }



    // 市场活动详情页操作
    /**
     * 市场活动细节页面显示-跳转
     *
     * @return
     */
    @RequestMapping("/toDetail.do")
    public String toDetail(String id,Model model) throws TraditionRequestException {

        // 根据id查询市场活动详细信息
        Activity activity = activityService.queryActivityById(id);
        if(activity == null){
            throw new TraditionRequestException("数据查询失败");
        }

        // 放入作用域
        model.addAttribute("activity",activity);

        return "/workbench/activity/detail";
    }


    /**
     * 查询市场活动备注信息
     * @param activityId 市场活动的id
     * @return 备注信息
     * @throws AjaxRequestException 查询失败异常
     */
    @RequestMapping("getActivityRemarkList.do")
    @ResponseBody
    public Map<String, Object> getActivityRemarkList(String activityId) throws AjaxRequestException {

        // 获取对应活动备注信息列表
        List<ActivityRemark> activityRemarks = activityRemarkService.queryActivityRemarkListByAid(activityId);
        if (activityRemarks == null || activityRemarks.size() <= 0){
            // 没有数据则加载一个空的数据到页面
            return HandleFlag.successObj("data",activityRemarks);
        }

        // 成功拿到数据，并返回
        return HandleFlag.successObj("data",activityRemarks);
    }

    public static void main(String[] args) {
        int a=12;
        for (int i =1;i<=a-1;i++){
            System.out.println(i);
        }
    }


}
