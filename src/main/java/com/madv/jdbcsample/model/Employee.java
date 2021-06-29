package com.madv.jdbcsample.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class Employee {

    private Long id;
    private String name;
    private BigDecimal salary;
    private LocalDateTime createdDate;

    public Employee() {
    }

    public Employee(Long id, String name, BigDecimal salary, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.createdDate = createdDate;
    }

}
