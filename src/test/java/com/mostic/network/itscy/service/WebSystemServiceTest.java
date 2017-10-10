package com.mostic.network.itscy.service;

import com.mostic.network.common.BaseTest;
import com.mostic.network.common.web.AjaxResult;
import com.mostic.network.itscy.domain.WebScan;
import com.mostic.network.itscy.domain.WebSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author LIQing
 * @create 2017-09-21 13:39
 */
public class WebSystemServiceTest extends BaseTest {
    @Autowired
    private WebSystemService webSystemService;


    public void updateWebSystem() throws Exception {
        WebSystem webSystem = new WebSystem();
        webSystem.setSystemId("fe123");
        webSystem.setName("dfdfdf");
        try {
            webSystemService.updateWebSystem(webSystem);
        } catch (Exception e) {
            logger.error("异常", e);
        }

    }





}