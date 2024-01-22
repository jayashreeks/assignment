package com.ecommerce.assignment.exceptions;

public class InvalidDiscountCodeException extends RuntimeException{

    Long discountCode;
    Long orderCount;

    public InvalidDiscountCodeException() {
        super(String.format("Discount code could not be generated as the order count condition is not satisfied"));

    }
    public InvalidDiscountCodeException(Long discountCode,Long orderCount) {
        super(String.format("Discount code %ld is Invalid as order count condition doesnt satisfy as current orders is %ld", discountCode, orderCount));
        this.discountCode = discountCode;
        this.orderCount = orderCount;
    }

    public InvalidDiscountCodeException(Long discountCode) {
        super(String.format("Discount code %ld is Invalid as code is already utilised", discountCode));
        this.discountCode = discountCode;
    }
}
