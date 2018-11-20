package com.zmkj.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试认证
 */

public class Authentication {
    /**
     * 指定一个Realm
     */
    SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();
    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("admin","123456","admin","user");
    }
    @Test
    public  void testAuthentication(){
    //1.创建securityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        //2.主体提交认证请求（securityUtils获取主体）
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject= SecurityUtils.getSubject();
        //3.提交认证
        UsernamePasswordToken token=new UsernamePasswordToken("admin","123456");
        subject.login(token);
        //4.判断是否认证
        //System.out.println("isAuthenticated:"+subject.isAuthenticated());
        //5.退出登录
        //subject.logout();
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
        //授权角色
        subject.checkRoles("admin","user1");


    }


}
