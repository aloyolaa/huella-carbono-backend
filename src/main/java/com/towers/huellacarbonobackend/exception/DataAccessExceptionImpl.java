package com.towers.huellacarbonobackend.exception;

import org.springframework.dao.DataAccessException;

public class DataAccessExceptionImpl extends DataAccessException {
    public DataAccessExceptionImpl(String message) {
        super(message);
    }
}
