package com.mostic.network.itscy.repository;

import com.mostic.network.itscy.domain.WebSystemVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author LIQing
 * @create 2017-10-27 13:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebSystemVoRepositoryTest {

    @Autowired
    private  WebSystemVoRepository repository;

    @Test
    public void findGroupBySystemIdByStateAndLastState() throws Exception {

    }

}