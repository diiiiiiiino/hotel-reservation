package com.dino.hotel.api.hotel.command.domain;

import com.dino.hotel.api.util.VerifyUtil;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String address1;
    private String address2;
    private String zipNo;

    public static Address of(String address1,
                             String address2,
                             String zipNo){
        VerifyUtil.verifyText(address1, "address1");
        VerifyUtil.verifyText(address2, "address2");
        VerifyUtil.verifyText(zipNo, "zipNo");

        return new Address(address1, address2, zipNo);
    }
}
