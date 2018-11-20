package com.zmkj.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Auther: ct
 * @Date: 2018/11/21 05:47
 * @Description:jdbcRealm测试类
 */
public class jdbcRealmTest {
//创建数据源
    DruidDataSource dataSource=new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/jdbcRealmTest");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }

    @Test
    public void testAuthenticationByJdbcRealm() {
        //创建jdbcRealm对象，需要访问数据库中取用户角色权限数据
        JdbcRealm jdbcRealm=new JdbcRealm();
        //设置jdbc数据源
        jdbcRealm.setDataSource(dataSource);
        //注：设置权限的开关
        jdbcRealm.setPermissionsLookupEnabled(true);
        //传入IniReam文件路径
        //1.创建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
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
        subject.checkPermission("user:delete");
    }
}
