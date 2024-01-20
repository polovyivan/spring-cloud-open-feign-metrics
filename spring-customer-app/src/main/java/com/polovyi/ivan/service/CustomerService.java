package com.polovyi.ivan.service;

import com.polovyi.ivan.dto.CreateCustomerRequest;
import com.polovyi.ivan.dto.CustomerResponse;
import com.polovyi.ivan.dto.PartiallyUpdateCustomerRequest;
import com.polovyi.ivan.dto.UpdateCustomerRequest;
import com.polovyi.ivan.entity.CustomerEntity;
import com.polovyi.ivan.repository.CustomerRepository;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerResponse> getCustomersWithFilters(String fullName, String phoneNumber,
            LocalDate createdAt) {
        log.info("Getting all customers with filters fullName {}, phoneNumber {}, createdAt {} ...", fullName, phoneNumber,
                createdAt);
        return customerRepository.findCustomersWithFilters(fullName, phoneNumber, createdAt)
                .stream()
                .map(CustomerResponse::valueOf)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomersById(String customerId) {
        log.info("Getting customer by id {}...", customerId);
        return customerRepository.findById(customerId).map(CustomerResponse::valueOf).orElse(null);

    }

    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        log.info("Creating a customer... ");
        CustomerEntity customer = CustomerEntity.valueOf(createCustomerRequest);
        return CustomerResponse.valueOf(customerRepository.save(customer));
    }

    public CustomerResponse updateCustomer(String customerId, @Valid UpdateCustomerRequest updateCustomerRequest) {
        log.info("Updating a customer... ");
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        customer.ifPresent(entity -> {
            entity.setFullName(updateCustomerRequest.getFullName());
            entity.setPhoneNumber(updateCustomerRequest.getPhoneNumber());
            entity.setAddress(updateCustomerRequest.getAddress());
            customerRepository.save(entity);
        });
        return customer
                .map(CustomerResponse::valueOf)
                .orElse(null);
    }

    public CustomerResponse partiallyUpdateCustomer(String customerId,
            PartiallyUpdateCustomerRequest partiallyUpdateCustomerRequest) {
        log.info("Partially updating a customer... ");

        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        customer.ifPresent(entity -> {
            entity.setPhoneNumber(partiallyUpdateCustomerRequest.getPhoneNumber());
            customerRepository.save(entity);
        });
        return customer
                .map(CustomerResponse::valueOf)
                .orElse(null);
    }

    public void deleteCustomer(String customerId) {
        log.info("Deleting a customer... ");
        customerRepository.findById(customerId).ifPresent(customerRepository::delete);
    }
}
