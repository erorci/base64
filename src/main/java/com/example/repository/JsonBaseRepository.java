package com.example.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.JsonBase;

@Repository
public interface JsonBaseRepository extends CrudRepository<JsonBase, Long>  {

	JsonBase findById(Long id);
	
	List<JsonBase> findAll();
	
}