package com.demo.common.excepiton;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaoxu123
 */
@Getter
@Setter
public class RSAException extends RuntimeException{
    private final String message;

    public RSAException(String message) {
        this.message = message;
    }

}
