package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {
	@Query(value = "SELECT * FROM spring_database.employees i WHERE (:name is null or i.name like %:name%)"
			+ " and (:email is null or i.email like %:email%)"
			+ " and (:phone is null or i.phone like %:phone%)", nativeQuery = true)
	List<Employees> search(@Param("name") String name, @Param("email") String email, @Param("phone") String phone);
}
