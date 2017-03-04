package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.JsonBase;


public interface JsonBaseJpaRepository extends JpaRepository<JsonBase, Long> {

}
