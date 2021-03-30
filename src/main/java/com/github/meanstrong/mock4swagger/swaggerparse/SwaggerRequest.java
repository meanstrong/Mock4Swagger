package com.github.meanstrong.mock4swagger.swaggerparse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class SwaggerRequest {
	private JSONObject _json_data;

	public SwaggerRequest(JSONObject data) {
		this._json_data = data;
	}

	public List<String> get_tags() {
		List<String> result = new ArrayList<String>();
		for (Object key : this._json_data.getJSONArray("tags")) {
			result.add((String) key);
		}
		return result;
	}

	public List<String> get_produces() {
		List<String> result = new ArrayList<String>();
		for (Object key : this._json_data.getJSONArray("produces")) {
			result.add((String) key);
		}
		return result;
	}

	public List<SwaggerRequestParameter> get_parameters() {
		List<SwaggerRequestParameter> result = new ArrayList<SwaggerRequestParameter>();
		for (Object key : this._json_data.getJSONArray("parameters")) {
			result.add(new SwaggerRequestParameter((JSONObject) key));
		}
		return result;
	}

	public Map<String, SwaggerResponse> get_responses() {
		Map<String, SwaggerResponse> result = new HashMap<String, SwaggerResponse>();
		for (Map.Entry<String, Object> entry : this._json_data.getJSONObject("responses").entrySet()) {
			result.put(entry.getKey(), new SwaggerResponse((JSONObject) entry.getValue()));
		}
		return result;
	}
}
