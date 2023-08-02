package com.example.retorestaurante.repository;

import com.example.retorestaurante.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryOrder  extends JpaRepository<Order, Long> {

    Page<Order> findByStatusAndSite(String Status, String site, Pageable pagerList);

    List<Order> findByStatus(String Status);
}
