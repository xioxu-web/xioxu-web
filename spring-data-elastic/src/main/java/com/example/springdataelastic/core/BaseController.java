package com.example.springdataelastic.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * api 返回使用，状态
     */
    protected int success = 0;
    protected int error = 1;
}
