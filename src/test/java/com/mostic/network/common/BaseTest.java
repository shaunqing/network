package com.mostic.network.common;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author LIQing
 * @create 2017-09-22 12:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseTest {
    protected final static Logger logger = LoggerFactory.getLogger(BaseTest.class);
}
