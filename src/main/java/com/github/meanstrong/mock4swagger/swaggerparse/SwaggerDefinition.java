package com.github.meanstrong.mock4swagger.swaggerparse;

import com.alibaba.fastjson.JSONObject;
import com.github.meanstrong.mock4swagger.jsonschema.JSONSchema;

public class SwaggerDefinition extends JSONSchema {

	public SwaggerDefinition(JSONObject data) {
		super(data);
	}

	public JSONSchema toJSONSchema() {
		return this.born(this._json_data);
	}
}
