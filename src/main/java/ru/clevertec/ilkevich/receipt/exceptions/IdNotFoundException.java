package ru.clevertec.ilkevich.receipt.exceptions;

public class IdNotFoundException extends RuntimeException {

    private static final String ID_EXCEPTION = "This entity was not found! It is recommended to check the validity of the ID.";

    public IdNotFoundException() {
        super(ID_EXCEPTION);
    }
}
