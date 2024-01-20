package com.polovyi.ivan.entity;

import com.polovyi.ivan.dto.CreateCustomerRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;


import java.time.LocalDate;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String fullName;

    private String phoneNumber;

    private String address;

    private LocalDate createdAt;

    public static CustomerEntity valueOf(CreateCustomerRequest createCustomerRequest) {
        return builder()
                .fullName(createCustomerRequest.getFullName())
                .phoneNumber(createCustomerRequest.getPhoneNumber())
                .address(createCustomerRequest.getAddress())
                .createdAt(LocalDate.now())
                .build();
    }
}