package com.example.apsoft.repositories;

import com.example.apsoft.entities.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<Schema, Long> { }
