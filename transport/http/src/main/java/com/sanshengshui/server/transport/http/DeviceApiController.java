package com.sanshengshui.server.transport.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author james mu
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class DeviceApiController {

    @RequestMapping(value = "/test",method = RequestMethod.GET,produces = "application/json")
    public String test(){
        return "hello,world!";
    }
}
