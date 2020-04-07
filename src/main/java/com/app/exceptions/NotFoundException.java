package com.app.exceptions;

/**
 * @author Максим Зеленский
 * @since 13.03.2020
 */
public class NotFoundException extends DataBaseException {
    public NotFoundException(String message) {
        super(message);
    }
}
