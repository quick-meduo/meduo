package org.quick.meduo.tools.json.test.bean;

import lombok.Data;

import java.util.Map;

@Data
public class UserWithMap {
	private Map<String, String> data;
}
