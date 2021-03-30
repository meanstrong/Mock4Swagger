package com.github.meanstrong.mock4swagger.swaggerparse;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class SwaggerPath {
	private JSONObject _json_data;

	public SwaggerPath(JSONObject data) {
		this._json_data = data;
	}

	public Map<String, SwaggerRequest> get_requests() {
		Map<String, SwaggerRequest> result = new HashMap<String, SwaggerRequest>();
		for (Map.Entry<String, Object> entry : this._json_data.entrySet()) {
			result.put(entry.getKey(), new SwaggerRequest((JSONObject) entry.getValue()));
		}
		return result;
	}
}
