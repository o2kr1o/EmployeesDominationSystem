package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Areas;

@Repository
public interface AreasRepository extends JpaRepository<Areas, Long> {

}
