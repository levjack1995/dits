package com.example.dits.DAO;

import com.example.dits.entity.Statistic;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<Statistic,Integer> {

}
