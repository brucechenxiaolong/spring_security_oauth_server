package com.test.springcloud.controller;

import com.test.base.dto.SimpleResponse;
import com.test.springcloud.dto.FormQueryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.HashMap;
import java.util.Map;

/**
 * 单机版，登记查询
 */
@RestController
@Api(tags = { "登记模块接口" })
@RequestMapping("/apply")
public class ApplyController implements BaseController {


    @ApiOperation(value = "登记单列表")
    @PostMapping("/list")
    public WebAsyncTask<SimpleResponse<?>> list(@RequestBody FormQueryDTO query) {
        return new WebAsyncTask<>(TIMEOUT_30S, EXECUTOR_NAME, () -> {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total", 10);
            map.put("rows", query);
            return new SimpleResponse<>(true,"获取数据成功",map);
        });
    }

}
