package com.sanshengshui.server.transport.http;

import com.google.gson.JsonParser;
import com.sanshengshui.server.common.data.kv.AttributeKvEntry;
import com.sanshengshui.server.common.data.kv.KvEntry;
import com.sanshengshui.server.common.transport.adaptor.JsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author james mu
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class DeviceApiController {

    @RequestMapping(value = "/{deviceToken}/attributes",method = RequestMethod.POST)
    public DeferredResult<ResponseEntity> postDeviceAttributes(
            @PathVariable("deviceToken") String deviceToken,
            @RequestBody String json) {
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        responseWriter.setResult(new ResponseEntity<>(HttpStatus.ACCEPTED));
        Set<AttributeKvEntry> attributeKvEntrySet = JsonConverter.convertToAttributes(new JsonParser().parse(json)).getAttributes();
        for (AttributeKvEntry attributeKvEntry : attributeKvEntrySet){
            System.out.println("属性名="+attributeKvEntry.getKey()+" 属性值="+attributeKvEntry.getValueAsString());
        }
        return responseWriter;
    }

    @RequestMapping(value = "/{deviceToken}/telemetry", method = RequestMethod.POST)
    public DeferredResult<ResponseEntity> postTelemetry(@PathVariable("deviceToken") String deviceToken,
                                                        @RequestBody String json){
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        Map<Long, List<KvEntry>> data = JsonConverter.convertToTelemetry(new JsonParser().parse(json)).getData();
        for (Long key : data.keySet()){
            System.out.println("上传数据时间 = " + key);
        }
        for (List<KvEntry> kvEntryList : data.values()){
            for (int i = 0;i<kvEntryList.size();i++){
                System.out.println("上传数据属性="+kvEntryList.get(i).getKey()+" 上传数据值="+kvEntryList.get(i).getValueAsString());
            }
        }
        return responseWriter;

    }


}
