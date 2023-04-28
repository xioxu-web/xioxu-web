package com.example.springdataelastic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskStatus{

    OK(0), // 成功
    DEFAULT(10), //失败
    BAD_REQUEST(100), // 未知异常
    ;

    /**
     * 结果
     */
    private final Integer result;
}
