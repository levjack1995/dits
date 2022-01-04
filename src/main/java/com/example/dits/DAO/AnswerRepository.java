package com.example.dits.DAO;

import com.example.dits.entity.Answer;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {

}
