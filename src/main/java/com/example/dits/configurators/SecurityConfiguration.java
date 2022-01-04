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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws  Exception{
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select login, password, 'true' from user" +
                        " join users_role on user.userId = users_role.userId where login =?")
                .authoritiesByUsernameQuery("select login, roleName from user " +
                        "join users_role on user.userId = users_role.userId join role on " +
                        "users_role.roleId = role.roleId where login =?");
    }

//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception{
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder)
//                .withUser("user").password(passwordEncoder.encode("user"))
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password(passwordEncoder.encode("admin")).roles("ADMIN");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/home","/user").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
                .and().formLogin().loginPage("/login")
                .successHandler(customSuccessHandler)
                .usernameParameter("login").passwordParameter("password").failureUrl("/login?error=1")
                .and().csrf()
                .and().exceptionHandling().accessDeniedPage("/accessDenied");
    }
}
