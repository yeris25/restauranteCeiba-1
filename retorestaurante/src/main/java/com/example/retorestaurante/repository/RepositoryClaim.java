package com.example.retorestaurante.repository;

import com.example.retorestaurante.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryClaim extends JpaRepository<Claim, Long> {
    List<Claim> findByStatus(String status);
}
