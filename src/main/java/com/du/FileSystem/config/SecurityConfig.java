//package com.du.FileSystem.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * 在代码中实现配置固定用户名和密码
// */
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
////    注入passwordEncoder类到spring容器中
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String passwd = passwordEncoder.encode("test");
//        auth.inMemoryAuthentication().withUser("test").password(passwd).roles("admin");
//    }
//}
