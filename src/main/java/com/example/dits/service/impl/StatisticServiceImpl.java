package com.example.dits.service.impl;

import com.example.dits.DAO.StatisticRepository;
import com.example.dits.entity.Role;
import com.example.dits.entity.Statistic;
import com.example.dits.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository repository;

    @Transactional
    public void create(Statistic statistic) {
        repository.save(statistic);
    }

    @Transactional
    public void update(Statistic statistic, int id) {
        Optional<Statistic> st = repository.findById(id);
        if(st.isEmpty())
            return;
        else
            repository.save(statistic);
    }

    @Transactional
    public void delete(Statistic statistic) {
        repository.delete(statistic);
    }

    @Transactional
    public void save(Statistic statistic) {
        repository.save(statistic);
    }

    @Transactional
    public List<Statistic> findAll() {
        return repository.findAll();
    }
}
