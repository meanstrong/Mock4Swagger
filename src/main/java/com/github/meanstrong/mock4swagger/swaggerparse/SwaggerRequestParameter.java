package com.github.meanstrong.mock4swagger.swaggerparse;

import com.alibaba.fastjson.JSONObject;
import com.github.meanstrong.mock4swagger.jsonschema.JSONSchema;

public class SwaggerRequestParameter extends JSONSchema {

	public SwaggerRequestParameter(JSONObject data) {
		super(data);
	}

	public String get_name() {
		return this._json_data.getString("name");
	}

	public String get_in() {
		return this._json_data.getString("in");
	}

	public Boolean get_required() {
		return this._json_data.getBooleanValue("required");
	}

	public JSONSchema toJSONSchema() {
		JSONObject json = this._json_data.getJSONObject("schema");
		return json == null ? this.born(this._json_data) : this.born(json);
	}

	@Override
	public Boolean check_data(String data) {
		if (this.get_required() && data == null) {
			return false;
		}
		if (data != null && !this.toJSONSchema().check_data(data)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString(){
		return this.toJSONSchema().toString();
	}
}
