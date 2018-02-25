package com.mostic.network.itscy.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by LIQing
 * 2018/2/25 16:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AsynTaskServiceTest {

    @Autowired
    private AsynTaskService taskService;

    @Test
    public void test() throws Exception {
        for (int i = 0; i < 10; i++) {
            taskService.executeAsyncTask(i);
            taskService.executeAsyncTaskPlus(i);
        }

    }

}