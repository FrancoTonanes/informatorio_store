package com.informatorio.storeinformatorio.repository;

import com.informatorio.storeinformatorio.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByStatus(String status);
}
