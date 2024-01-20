package com.polovyi.ivan.controller;

import com.polovyi.ivan.client.CustomerAPIClient;
import com.polovyi.ivan.dto.CreateCustomerRequest;
import com.polovyi.ivan.dto.CustomerResponse;
import com.polovyi.ivan.dto.PartiallyUpdateCustomerRequest;
import com.polovyi.ivan.dto.UpdateCustomerRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/customers")
public class CustomerControllerForFeignClient {

    private final CustomerAPIClient customerAPIClient;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllCustomersWithFilters(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdAt) {
        return customerAPIClient.getCustomersWithFilters(fullName, phoneNumber, createdAt);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest,
            UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        String location = customerAPIClient.createCustomer(createCustomerRequest).headers().get("location").stream().findFirst()
                .orElse(null);
        response.addHeader("location", location);
    }

    @GetMapping(path = "/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse getCustomerById(@PathVariable String customerId) {
        return customerAPIClient.getCustomerById(customerId);
    }

    @PutMapping(path = "/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable String customerId,
            @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        customerAPIClient.updateCustomer(customerId, updateCustomerRequest);
    }

    @PatchMapping(path = "/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void partiallyUpdateCustomer(@PathVariable String customerId,
            @Valid @RequestBody PartiallyUpdateCustomerRequest partiallyUpdateCustomerRequest) {
        customerAPIClient.partiallyUpdateCustomer(customerId, partiallyUpdateCustomerRequest);
    }

    @DeleteMapping(path = "/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable String customerId) {
        customerAPIClient.deleteCustomer(customerId);
    }

}
