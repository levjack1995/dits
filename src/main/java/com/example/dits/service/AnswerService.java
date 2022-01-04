package com.example.dits.service;

import com.example.dits.entity.Answer;

import java.util.List;

public interface AnswerService {

    public void create(Answer a);
    public void update(Answer a, int id);
    public void delete(Answer a);
    public void save(Answer a);
    public List<Answer> findAll();

}
