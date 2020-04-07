package com.app.exceptions;

/**
 * @author Максим Зеленский
 * @since 11.03.2020
 */
public class TwinLoginException extends DataBaseException {
    public TwinLoginException(String message) {
        super(message);
    }
}
