package com.example.dits.DAO;

import com.example.dits.entity.Question;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Integer> {

}
