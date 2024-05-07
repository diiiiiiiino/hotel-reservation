package com.dino.hotel.api.helper.builder;

import com.dino.hotel.api.hotel.command.domain.Address;

public class AddressBuilder {
    private String address1 = "서울시";
    private String address2 = "동대문";
    private String zipNo = "123456";

    public static AddressBuilder builder(){
        return new AddressBuilder();
    }

    public AddressBuilder address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public AddressBuilder address2(String address2){
        this.address2 = address2;
        return this;
    }

    public AddressBuilder zipNo(String zipNo){
        this.zipNo = zipNo;
        return this;
    }

    public Address build(){
        return Address.of(address1, address2, zipNo);
    }
}
