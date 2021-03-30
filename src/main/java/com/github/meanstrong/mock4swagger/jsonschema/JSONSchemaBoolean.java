package com.github.meanstrong.mock4swagger.jsonschema;

import com.alibaba.fastjson.JSONObject;
import com.github.meanstrong.mock4swagger.faker.Faker;

public class JSONSchemaBoolean extends JSONSchema {

	public JSONSchemaBoolean(JSONObject data) {
		super(data);
	}

	@Override
	public Boolean check_data(String data) {
		if (data.equalsIgnoreCase("true") || data.equalsIgnoreCase("false")) {
			return true;
		}
		return false;
	}

	@Override
	public Object generate() {
		return (new Faker()).generate_boolean();
	}
}
