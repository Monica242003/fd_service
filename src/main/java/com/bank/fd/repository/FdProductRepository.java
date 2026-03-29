package com.bank.fd.repository;

import com.bank.fd.entity.FdProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FdProductRepository extends JpaRepository<FdProduct, Long> {
}
