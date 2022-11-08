package com.daniel.delivery.dto;

import com.daniel.delivery.entity.Status;
import lombok.Value;

import java.time.Instant;

@Value
public class OrderReadDto {

    private Long id;

    private String product;

    private Status status;

    private String address;

    private String createdBy;

    private Instant createdAt;

}