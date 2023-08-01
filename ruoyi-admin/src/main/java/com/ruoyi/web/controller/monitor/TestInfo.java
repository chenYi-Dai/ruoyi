package com.ruoyi.web.controller.monitor;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.ruoyi.common.entry.ResponseEntity;
import com.ruoyi.common.feignService.OpenFeignService;
import com.ruoyi.common.prarm.NodeListForm;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/druidData")
//@DefaultProperties(defaultFallback = "globalFallbackMethod")
public class TestInfo {

    @Resource
    private OpenFeignService openFeignService;

    @ApiOperation(value = "查询单个数据")
    @HystrixCommand(fallbackMethod = "tomeOutFailCallBack")
    @RequestMapping(value = "/query/one",method = RequestMethod.POST)
    public ResponseEntity queryOneData(@RequestBody NodeListForm nodeListForm) throws Exception {
        log.info("submitLogin request |{}",nodeListForm);
        ResponseEntity responseEntity = openFeignService.queryOneData(nodeListForm);
        return responseEntity;
    }

    @ApiOperation(value = "查询所有列表")
    @HystrixCommand(fallbackMethod = "globalFallbackMethod")
    @RequestMapping(value = "/query/list",method = RequestMethod.POST)
    public ResponseEntity queryAllData(@RequestBody NodeListForm nodeListForm) throws Exception {
        log.info("submitLogin request |{}",nodeListForm);
        ResponseEntity responseEntity = openFeignService.queryAllData(nodeListForm);
        return responseEntity;
    }

    public ResponseEntity tomeOutFailCallBack(@RequestBody NodeListForm nodeListForm){
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(500);
        responseEntity.setMessage(nodeListForm.getSpaceSetId() + "服务超时");
        Map map  = new HashMap<>();
        map.put("msg","tomeOutFailCallBack 服务超时 处理");
        responseEntity.setValue(map);
        return responseEntity;
    }

    public ResponseEntity globalFallbackMethod(@RequestBody NodeListForm nodeListForm){
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(500);
        Map map  = new HashMap<>();
        map.put("msg","globalFallbackMethod 服务超时 处理");
        responseEntity.setValue(map);
        return responseEntity;
    }
}
