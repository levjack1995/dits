package com.example.dits.service;

import com.example.dits.entity.Answer;
import com.example.dits.entity.Statistic;
import com.example.dits.entity.Test;

import java.util.List;

public interface TestService {
    public void create(Test test);
    public void update(Test test, int id);
    public void delete(Test test);
    public void save(Test test);
    public List<Test> findAll();
}
