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

package org.quick.meduo.core.exception;

/**
 * @Description:
 * @Changelog:
 *  1` Intial  @Brain.G
 * @Authors: Brian.G
 * @Since: 2019/11/11
 */
public class MeduoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = 500;
    
    public MeduoException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public MeduoException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public MeduoException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public MeduoException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
