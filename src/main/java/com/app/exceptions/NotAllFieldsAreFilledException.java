package com.app.exceptions;

/**
 * @author Максим Зеленский
 * @since 03.04.2020
 */
public class NotAllFieldsAreFilledException extends Exception {
    public NotAllFieldsAreFilledException(String message) {
        super(message);
    }
}
