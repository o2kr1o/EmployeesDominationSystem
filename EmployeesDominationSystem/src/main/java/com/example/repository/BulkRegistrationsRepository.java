package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.BulkRegistrations;

@Repository
public interface BulkRegistrationsRepository extends JpaRepository<BulkRegistrations, Long> {

}
