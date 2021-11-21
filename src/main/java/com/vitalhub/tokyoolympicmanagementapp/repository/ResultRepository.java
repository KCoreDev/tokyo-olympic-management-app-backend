package com.vitalhub.tokyoolympicmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitalhub.tokyoolympicmanagementapp.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long>{

}
