package com.example.domain;

import lombok.Getter;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * 顧客Entity
 */
@Entity(naming = NamingType.SNAKE_UPPER_CASE, immutable = true)
public class CustomerEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private final int id;

    @Getter
    private final String lastName;

    @Getter
    private final String firstName;

    public CustomerEntity( int id, String lastName, String firstName ){
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }


}
