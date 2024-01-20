package com.polovyi.ivan.configuration;

import static java.util.stream.Collectors.toList;

import com.github.javafaker.Faker;
import com.polovyi.ivan.entity.CustomerEntity;
import com.polovyi.ivan.repository.CustomerRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader {

    final private CustomerRepository customerRepository;

    @Bean
    private InitializingBean sendDatabase() {
        Faker faker = new Faker();
        return () -> customerRepository.saveAll(generateCustomerList(faker));
    }

    private List<CustomerEntity> generateCustomerList(Faker faker) {
        return IntStream.range(0, 2)
                .mapToObj(i -> CustomerEntity.builder().createdAt(
                                LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 10)))))
                        .fullName(faker.name().fullName())
                        .phoneNumber(faker.phoneNumber().cellPhone())
                        .address(faker.address().fullAddress())
                        .build())
                .collect(toList());
    }
}
