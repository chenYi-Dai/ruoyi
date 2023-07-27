package com.ruoyi.web.controller.monitor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/druidinfo")
public class TestInfo {

    @ApiOperation(value = "数据监控登陆")
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public void submitLogin(){
        log.info("submitLogin");
        System.out.println(111);
    }
}
