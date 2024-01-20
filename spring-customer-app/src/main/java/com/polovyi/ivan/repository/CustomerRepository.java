package com.polovyi.ivan.repository;

import com.polovyi.ivan.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    @Query("SELECT customer from CustomerEntity customer WHERE "
            + "(:fullName IS NULL OR customer.fullName = :fullName) AND "
            + "(:phoneNumber IS NULL OR customer.phoneNumber = :phoneNumber) AND "
            + "(:createdAt IS NULL OR customer.createdAt = :createdAt)")
    List<CustomerEntity> findCustomersWithFilters(String fullName, String phoneNumber, LocalDate createdAt);

}
