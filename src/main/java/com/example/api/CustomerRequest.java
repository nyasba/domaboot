package com.example.api;

import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;

public class CustomerRequest {

    @NotEmpty
    @Getter
    private String lastName;

    @NotEmpty
    @Getter
    private String firstName;
}
