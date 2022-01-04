package com.example.dits.DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class RoleDAO {

    public List<String> getRoleNames(Long userId) {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        return roles;
    }
}
