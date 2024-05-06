package com.dino.hotel.api.hotel.command.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String address1;
    private String address2;
    private String zipNo;

    public static Address of(String address1,
                             String address2,
                             String zipNo){
        return new Address(address1, address2, zipNo);
    }
}
