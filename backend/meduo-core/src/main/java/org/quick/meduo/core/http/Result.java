/*
 * Copyright (c) 2019 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quick.meduo.core.http;

import org.quick.meduo.core.constant.ErrorConstants;

/**
 * @Description:
 * HTTP response result wrapper
 * @Changelog:
 *  1`
 * @Authors: Brian.G
 * @Since: 2019/11/11
 */
public class Result {
	private int code      = HttpStatus.SC_OK;
	private int subCode   = AppStatus.APC_OK;
	private String notify;
	private Object data;

	private void fill(int code, int subCode,String notify, Object data){
         this.code = code;
         this.subCode = subCode;
         this.notify = notify;
         this.data = data;
	}

	public static Result build(boolean success, Object data, String errMessage){
		if(success){
			return Result.ok(data);
		}else {
			return Result.error(errMessage);
		}
	}
	
	public static Result error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, ErrorConstants.UNKOWN_ERROR);
	}
	
	public static Result error(String notify) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, notify);
	}
	
	public static Result error(int error, String notify) {
		Result result = new Result();
		result.fill(HttpStatus.SC_INTERNAL_SERVER_ERROR,error,notify,"");
		return result;
	}

	public static Result ok(String notify) {
		return Result.ok(AppStatus.APC_OK,notify);
	}

	public static Result ok(int code, String notify) {
		return Result.ok(code,notify,"");
	}
	
	public static Result ok(Object data) {
		return Result.ok("",data);
	}

	public static Result ok(int code, String notify, Object data) {
		Result result = new Result();
		result.fill(HttpStatus.SC_OK,code,notify,data);
		return result;
	}

	public static Result ok(String notify, Object data) {
		return Result.ok(AppStatus.APC_OK,notify,data);
	}
	
	public static Result ok() {
		return new Result();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getSubCode() {
		return subCode;
	}

	public void setSubCode(int subCode) {
		this.subCode = subCode;
	}

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}
}
