package com.example.dits.service;

import com.example.dits.entity.Answer;
import com.example.dits.entity.Topic;
import com.example.dits.entity.User;

import java.util.List;

public interface UserService {
    public void create(User user);
    public void update(User user, int id);
    public void delete(User user);
    public void save(User user);
    public List<User> findAll();
}
