package com.mryang.crm.listener;


import com.mryang.crm.settings.pojo.DictionaryValue;
import com.mryang.crm.settings.service.DictionaryTypeService;
import com.mryang.crm.settings.service.DictionaryValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;

/**
 * @author Genius
 * @version 1.0.0
 * @ClassName SysLoadListener.java
 * @Description TODO 监听器-用于将数据库中的数据存入缓存中，以提高数据的查询效率
 * @createTime 2021年10月28日 16:43:00
 */
// 在web.xml中配置，使其被加载
// <listener>
//      <listener-class>com.mryang.crm.listener.SysLoadListener</listener-class>
// </listener>
public class SysLoadListener implements ServletContextListener {

//    @Autowired
//    private DictionaryValue dictionaryValue;

    /**
     * 服务器运行时所加载的方法
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("服务器启动了。。。。");
        System.out.println("----------加载缓存数据----------开始----------");

        // 如何获取数据字典的service对象呢？
        // 通过@Autowired注解获取的地址为null
        // System.out.println(dictionaryValue); // null
        // 因为我们在服务器刚运行的时候，spring容器中的数据，并没有加载完全
        // 通过@Autowired向容器中注入对象是失败的，故值为null

        // 自动注入对象失效了，我们需要进行手动注入，通过调用api获取spring容器
        // 从容器中获取对象
        ClassPathXmlApplicationContext app =
                new ClassPathXmlApplicationContext("spring/applicationContext-service.xml");

        DictionaryTypeService dvs = app.getBean(DictionaryTypeService.class);

        System.out.println(dvs); // 有值，不为null,可以进行相关操作

        /*
            存入缓存中的数据：
                {
                    "applicationList":[{数据字典值...}...],
                    "clueStateList":[{数据字典值...}...],
                    "sourceList":[{数据字典值...}...],
                    "typeCodeList":[{数据字典值...}...],
                    ...
                }
         */

        // 查询所有数据字典值，以键值对的形式存储
        Map<String, List<DictionaryValue>> sysLoadMap = dvs.findDicValueList();
        System.out.println(sysLoadMap);

        // 遍历Map集合中的数据，将数据存入缓存中
        for (String key : sysLoadMap.keySet()) {
            // 将数据存入缓存
            sce.getServletContext().setAttribute(key, sysLoadMap.get(key));

            System.out.println("key:::>>>"+key);
            System.out.println("value:::>>>"+sysLoadMap.get(key));
        }



        System.out.println("----------加载缓存数据----------结束----------");
    }

    /**
     * 服务器结束时所加载的方法
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
