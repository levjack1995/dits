package com.example.dits.service.impl;

import com.example.dits.DAO.RoleDAO;
import com.example.dits.DAO.UserDAO;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        com.example.dits.entity.User appUser = this.userDAO.findUserAccount(userName);

        if (appUser == null) {
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
        List<String> roleNames = this.roleDAO.getRoleNames((long) appUser.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNames != null) {
            for (String role : roleNames) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(appUser.getLogin(), appUser.getPassword(), grantList);
        return userDetails;
    }

}
