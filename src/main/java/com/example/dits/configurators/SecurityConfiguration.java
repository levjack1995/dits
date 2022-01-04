package com.example.dits.configurators;

import com.example.dits.handlers.CustomSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomSuccessHandler customSuccessHandler;

    private final PasswordEncoder passwordEncoder;
    // из какого пакета
    private final DataSource dataSource;

    @Autowired
    public SecurityConfiguration(CustomSuccessHandler customSuccessHandler, DataSource dataSource) {
        this.customSuccessHandler = customSuccessHandler;
        this.dataSource = dataSource;
        passwordEncoder = new BCryptPasswordEncoder();
    }


    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws  Exception{
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select login, password, 'true' from user" +
                        " join users_role on user.userId = users_role.userId where login =?")
                .authoritiesByUsernameQuery("select login, roleName from user " +
                        "join users_role on user.userId = users_role.userId join role on " +
                        "users_role.roleId = role.roleId where login =?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/home").access("hasRole('ROLE_USER')")
                .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
                .and().formLogin().loginPage("/login")
                .successHandler(customSuccessHandler)
                .usernameParameter("ssoId").passwordParameter("password")
                .and().csrf()
                .and().exceptionHandling().accessDeniedPage("/accessDenied");
    }
}
