package com.example.backendinternp11.repository;

import com.example.backendinternp11.entity.SimpleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleRepository extends JpaRepository<SimpleEntity, String> {
}
