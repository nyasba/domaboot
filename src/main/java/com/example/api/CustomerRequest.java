package com.example.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@ToString
public class CustomerRequest {

    @NotEmpty
    @Size(min=1, max=50)
    @Getter
    @Setter
    private String lastName;

    @NotEmpty
    @Size(min=1, max=50)
    @Getter
    @Setter
    private String firstName;
}
