package com.rmgs.codeChecker.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    DataSource dataSource;
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AjaxTimeoutRedirectFilter ajaxTimeoutRedirectFilter;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/login.xhtml/**").permitAll()
                .antMatchers("/landing.xhtml/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.xhtml")
                .permitAll()
                .and()
                //      .addFilterAfter(ajaxTimeoutRedirectFilter, ExceptionTranslationFilter.class)
                .logout()
                .permitAll()
                .and()
                .csrf().disable();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login.xhtml?logout");

        http.formLogin().defaultSuccessUrl("/index.xhtml", true);

        http.rememberMe().
                key("rem-me-key").
                rememberMeParameter("remember-me-param").
                rememberMeCookieName("my-remember-me").
                tokenValiditySeconds(86400);

    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/forgetPassword/forgetPasswordRequest.xhtml");
        web.ignoring().antMatchers("/forgetPassword/createPassword.xhtml");

    }
}
