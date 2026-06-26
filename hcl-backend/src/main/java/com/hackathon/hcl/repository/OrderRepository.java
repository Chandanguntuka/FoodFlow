package com.hackathon.hcl.repository;

import com.hackathon.hcl.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @EntityGraph(attributePaths = {"user", "restaurant", "items", "items.menuItem"})
    List<Order> findByUserIdOrderByCreatedAtDesc(Integer userId);

    @EntityGraph(attributePaths = {"user", "restaurant", "items", "items.menuItem"})
    List<Order> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = {"user", "restaurant", "items", "items.menuItem"})
    Optional<Order> findWithDetailsById(Integer id);
}
