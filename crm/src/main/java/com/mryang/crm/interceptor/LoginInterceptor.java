package com.mryang.crm.interceptor;

import com.mryang.crm.exception.InterceptorException;
import com.mryang.crm.settings.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO 登录拦截器
 */

public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 控制器方法执行 之前 执行的方法
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        // 只有用户登录后才放行
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){//用户未登录，抛出异常
            // 异常处理器会帮我们重定向到登录页面
            throw new InterceptorException();
        }
        return true;
    }

    /**
     * 控制器方法执行 之后 执行的方法
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 页面接在完成后执行的方法
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
