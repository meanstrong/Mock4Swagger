package com.github.meanstrong.mock4swagger.swaggerparse;

import com.alibaba.fastjson.JSONObject;
import com.github.meanstrong.mock4swagger.jsonschema.JSONSchema;

public class SwaggerResponse extends JSONSchema {
	public SwaggerResponse(JSONObject data) {
		super(data);
	}

	public String get_description() {
		return this._json_data.getString("description");
	}

	public JSONSchema toJSONSchema() {
		JSONObject json = this._json_data.getJSONObject("schema");
		return json == null ? this.born(this._json_data) : this.born(json);
	}

	@Override
	public Object generate() {
		JSONSchema schema = this.toJSONSchema();
		return schema.generate();
	}

	@Override
	public String toString() {
		return this.toJSONSchema().toString();
	}
}
