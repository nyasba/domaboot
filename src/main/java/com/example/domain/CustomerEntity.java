package com.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.seasar.doma.*;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * 顧客Entity
 */
@Entity(naming = NamingType.SNAKE_UPPER_CASE, immutable = true)
@Table(name = "customers")
@EqualsAndHashCode
@ToString( includeFieldNames = false)
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequence = "seq_customers")
    @Getter
    private final Integer id;

    @Getter
    private final String lastName;

    @Getter
    private final String firstName;

    public CustomerEntity( String lastName, String firstName ){
        // DBの自動採番を使うためにnullを設定
        this.id = null;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public CustomerEntity( Integer id ,String lastName, String firstName ){
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
