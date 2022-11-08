package com.daniel.delivery.dto;

import com.daniel.delivery.entity.Status;
import lombok.Data;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class OrderCreateEditDto {

    private Long id;

    private String product;

    private Status status;

    private String address;
}
