package com.example.franchise_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.franchise_api.entity.Branch;

@Repository
public interface BranchRepository extends JpaRepository <Branch, Long>{
    
}
