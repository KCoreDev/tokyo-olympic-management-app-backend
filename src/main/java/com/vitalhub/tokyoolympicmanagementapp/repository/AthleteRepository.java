package com.vitalhub.tokyoolympicmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitalhub.tokyoolympicmanagementapp.entity.Athlete;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long>{

}
