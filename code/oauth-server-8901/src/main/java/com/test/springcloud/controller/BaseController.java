package com.test.springcloud.controller;

import com.test.base.dto.SimpleResponse;

public interface BaseController {
	
	public static final String EXECUTOR_NAME = "PdeDefaultThreadPool";
	
	public static final long  TIMEOUT_30S = 30 * 1000L;  //30*1000L设置超时时间为30秒
	
	public static final long  TIMEOUT_60S = 60 * 1000L;  //30*1000L设置超时时间为60秒
	
	public static final long  TIMEOUT_120S = 120 * 1000L;  //30*1000L设置超时时间为120秒
	
	public static final long  TIMEOUT_600S = 600 * 1000L;  //30*1000L设置超时时间为600秒
	
	public SimpleResponse<?> PDE501 = new SimpleResponse<Object>(false,"请求执行超时",null,"PDE-ERR-901");
	
	public SimpleResponse<?> PDE500 = new SimpleResponse<Object>(false,"请求异常",null,"PDE-ERR-902");

}
