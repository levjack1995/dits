package com.example.dits.configurators;

import com.example.dits.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

//         Setting Service to find User in the database.
//         And Setting PassswordEncoder
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
        http.authorizeRequests().antMatchers("/user").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/accessDenied");

        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/security_check")
                .loginPage("/login")
                .defaultSuccessUrl("/user")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().deleteCookies("remove").invalidateHttpSession(true)
                .logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");

        http.authorizeRequests().and()
                .rememberMe().tokenRepository(this.persistentTokenRepository())
                .tokenValiditySeconds(1 * 24 * 60 * 60);

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
}
