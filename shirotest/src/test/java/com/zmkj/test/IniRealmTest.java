package com.zmkj.test;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Auther: ct
 * @Date: 2018/11/21 05:05
 * @Description: 内置IniReam
 */
public class IniRealmTest {
    @Test
    public void testAuthenticationByIniRealm() {
        //创建IniRealm对象，模拟从数据库中取用户角色权限数据
        IniRealm iniRealm=new IniRealm("classpath:user.ini");//传入IniReam文件路径
        //1.创建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        //2.主体提交认证请求（securityUtils获取主体）
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        //3.提交认证
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
        subject.login(token);
        //4.判断是否认证
        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        //授权
        subject.checkRole("admin");
        //负责人是否具备删除权限
        subject.checkPermission("user:update");
    }
}
