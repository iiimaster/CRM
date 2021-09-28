package com.mryang.crm.settings.web.controller;

import com.mryang.crm.exception.AjaxRequestException;
import com.mryang.crm.exception.LoginException;
import com.mryang.crm.settings.pojo.User;
import com.mryang.crm.settings.service.UserService;
import com.mryang.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description TODO 系统设置模块--用户模块
 * @createTime 2021年09月24日 11:50:00
 */
@Controller
@RequestMapping("/settings/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录页面-登录功能-10天免登录
     * @return json串
     */
    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String, Object> login(String loginAct,
                                     String loginPwd,
                                     String flag,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws LoginException {

        // 获取ip地址
        String ip = request.getRemoteAddr();
//        System.out.println("当前的ip ::>> "+ip);

        // 由于数据库中的密码都是经过MD5的加密算法加密过的，
        // 所以我们在登录的时候需要对密码进行加密操作
        String md5Pwd = MD5Util.getMD5(loginPwd);

        // 根据用户名和密码查询用户信息，是否能够登录
        User loginUser = userService.login(loginAct, md5Pwd, ip);

        Map<String, Object> resultMap = new HashMap<>();

        // 此部分逻辑放入service层进行判断
//        if (loginUser == null) {
//            // 查询失败，用户名或密码错误
//            resultMap.put("success", false);
//            resultMap.put("msg", "用户名或密码错误");
//            return resultMap;
//        }

        resultMap.put("success", true);
        resultMap.put("msg", "登录成功");

        /*
        * 10天免登录操作
        *   第一部分业务：在登录的时候操作，login.do
        *   1.用户登录，勾选10天免登录
        *       i.给复选框一个标识 flag == select，将标识传递到后台
        *       ii.后台接收到标识后，开始进行免登录操作
        *   2.将用户名和密码存入cookie中(传统项目可以，金融/电商不允许这样操作)
        *     安全问题？
        *       i.拦截器会拦截其他请求，只放行了登录请求和跳转到登录页面的请求
        *       ii.由于在我们输入密码以后，会对密码进行MD5加密操作，
        *           故存入cookie中的数据都是加密后的数据，即使加密后的数据泄漏，也无法登录，
        *           因为在登录时会对密码再次加密，二次加密后的密码，与数据库中的密码无法对应
        *   3.设置cookie的一些属性：
        *       i.cookie过期时间：60*60*24*10  10天
        *       ii.cookie的存放路径：/ 根目录下，保证在任意路径下都能访问到cookie中的数据
        *   第二部分业务：对跳转到登录页面的请求进行操作，toLogin.do
        *   4.获取cookie中的用户名和密码
        *   5.自动进行登录
        *       调用service.login()方法进行登录
        *   6.将自动登录后的用户存入session中
        *
        * */

        if ("select".equals(flag)){

            // 将用户名和密码存放到cookie中
            Cookie loginActCookie = new Cookie("loginAct", loginAct);
            // 密码要存入加密后的密码
            Cookie loginPwdCookie = new Cookie("loginPwd", md5Pwd);
            // cookie的相关参数
            int editTime = 60*60*24*10;// 10天
            // 设置cookie的过期时间
            loginActCookie.setMaxAge(editTime);
            loginActCookie.setPath("/");
            loginPwdCookie.setMaxAge(editTime);
            loginPwdCookie.setPath("/");

            // 将cookie对象写回浏览器
            response.addCookie(loginActCookie);
            response.addCookie(loginPwdCookie);

        }

        // 将用户存入到session 中，后续进行权限控制
        request.getSession().setAttribute("user", loginUser);

        return resultMap;

    }

    /**
     * 跳转到 登录页面
     * @throws LoginException 自定义异常（登录异常）
     */
    @RequestMapping("/toLogin.do")
    public String toLogin(HttpServletRequest request) throws LoginException {
        //如果我们的login.jsp页面在webapp目录下
        //我们的视图解析器需要跳转的路径是/WEB-INF/jsp目录下
        //我们需要将login.jsp移动到jsp目录中
        //注意:::login.jsp移动之后,jsp中的相对路径会发生变化
        //IDEA会自动添加一些相对路径的../
        //我们需要将它们去除掉
        //此时,我们才能够通过视图解析器访问到login.jsp

        //再次启动tomcat,发现,访问不到login.jsp页面了,因为它已经被我们移动到WEB-INF/jsp页面中了
        //此时,无法通过外界的方式访问到我们的jsp页面

        //找到webapp目录下的index.html页面,将它设置为我们的欢迎页面
        //在web.xml中设置欢迎页面
        //当访问http://127.0.0.1:8080/crm时,会自动找到index.html欢迎页面
        //在index.html中设置触发的跳转路径,跳转到登录页面
        /*
            <script type="text/javascript">
                document.location.href = "settings/user/toLogin.do";
            </script>
            只要index.html页面被打开,立即执行,页面跳转(发送请求,控制器中跳转到登录页面)
         */
        //当测试时,浏览器访问http://127.0.0.1:8080/crm
        //会帮我们跳转到http://127.0.0.1:8080/crm/settings/user/toLogin.do登录页面的地址

        //由于我们的拦截器拦截了我们的登录页面的跳转路径,所以我们需要在拦截器中进行放行
        //找到配置文件applicationContext-web.xml,将settings/user/toLogin.do访问路径,进行放行操作

        //将login.jsp放到了jsp目录中,所以我们跳转的路径直接写/login即可

        //-------------------------------------10天免登录---------------------------------------
        // 获取cookie
        Cookie[] cookies = request.getCookies();
        // 将cookie中的值与用户名和密码进行对比
        String loginAct=null;
        String loginPwd=null;
        for (Cookie cookie : cookies) {
            if ("loginAct".equals(cookie.getName())){
                // 获取到用户名称
                loginAct = cookie.getValue();
                // 结束本次循环
                continue;
            }
            if ("loginPwd".equals(cookie.getName())){
                loginPwd = cookie.getValue();
            }
        }

        // 自动登录操作
        if (loginAct != null && loginPwd != null) {// 获取到了用户名和密码

            // 获取用户ip
            String ip = request.getRemoteAddr();

            // 执行自动登录
            User autoLoginUser = userService.login(loginAct, loginPwd, ip);

            if (autoLoginUser != null){// 登录成功
                // 将user存储到session中
                request.getSession().setAttribute("user", autoLoginUser);
                // 如果登录成功，直接跳转到首页
                return "redirect:/workbench/toIndex.do";
            }
        }
        //-------------------------------------10天免登录---------------------------------------
        return "/login";
    }

    /**
     * 用户退出 操作
     */
    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        // 1.清理session中的登录用户
        request.getSession().removeAttribute("user");
        // 2.清除cookie中的10天免登录操作
        // 清除cookie中的loginAct和loginPwd

        // --------------方式一:通过遍历的方式进行查询和覆盖--------------
        // Cookie[] cookies = request.getCookies();
        // if (cookies != null){//只有进行了10天免登录操作才需要清除cookie中的值
        //     for (Cookie cookie : cookies) {
        //         if ("loginAct".equals(cookie.getName())){
        //             // cookie的销毁是没有方法的，只能通过覆盖为空字符串的方式进行销毁
        //             cookie.setValue("");
        //         }
        //         if ("loginPwd".equals(cookie.getName())){
        //             cookie.setValue("");
        //         }
        //         //设置cookie的参数
        //         cookie.setMaxAge(0);
        //         cookie.setPath("/");
        //         // 将cookie写回到客户端
        //         response.addCookie(cookie);
        //     }
        // }

        // --------------方式二:通过遍历的方式进行查询和覆盖--------------
        //创建两个新的Cookie,将旧的Cookie给覆盖
        Cookie loginActCookie = new Cookie("loginAct","");
        Cookie loginPwdCookie = new Cookie("loginPwd","");

        //设置cookie的参数
        loginActCookie.setMaxAge(0);
        loginActCookie.setPath("/");

        loginPwdCookie.setMaxAge(0);
        loginPwdCookie.setPath("/");

        response.addCookie(loginActCookie);
        response.addCookie(loginPwdCookie);

        // 3.跳转到登录页面
        return "redirect:/settings/user/toLogin.do";
    }

    /**
     * 跳转到 系统设置 页面
     * @return
     */
    @RequestMapping("/toSettings.do")
    public String toSettings(){
        return "/settings/index";
    }


    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @param confirmPwd
     * @return
     */
    @RequestMapping("/updatePwd.do")
    @ResponseBody
    public Map<String, Object> updatePwd(String oldPwd, String newPwd, String confirmPwd, HttpServletRequest request) throws AjaxRequestException {

//        System.out.println(oldPwd);
//        System.out.println(newPwd);
//        System.out.println(confirmPwd);


        // 从session中获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");

        // 将输入的旧密码进行加密，数据库中的密码是加密后的
        String md5OldPwd = MD5Util.getMD5(oldPwd);

        // 与数据库中的密码进行对比，
        // 有用户返回表示 旧密码输入正确
        user = userService.queryUser(user.getLoginAct(), md5OldPwd);

        if (user == null) {// 用户旧密码输入错误
            throw new AjaxRequestException("旧密码输入有误");
        }
        if (newPwd.equals(oldPwd)) {
            throw new AjaxRequestException("新密码与旧密码不能相同");
        }
        if (!newPwd.equals(confirmPwd)) {
            throw new AjaxRequestException("两次密码输入不一致");
        }

        // 对新密码进行加密
        String md5NewPwd = MD5Util.getMD5(newPwd);

        // 修改密码
        int i = userService.updateUserPwd(user.getId(), md5NewPwd);

        Map<String, Object>  resultMap= new HashMap<>();

        resultMap.put("success",true);
        resultMap.put("msg","修改成功");

        return resultMap;
    }

}
