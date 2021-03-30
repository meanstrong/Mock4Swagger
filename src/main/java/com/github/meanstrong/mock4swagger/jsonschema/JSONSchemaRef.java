package com.github.meanstrong.mock4swagger.jsonschema;

import com.alibaba.fastjson.JSONObject;

public class JSONSchemaRef extends JSONSchema {

	public JSONSchemaRef(JSONObject data) {
		super(data);
	}

	public String get_ref() {
		return this._json_data.getString("$ref");
	}

	public JSONSchema get_ref_value() {
		if (!this.get_ref().startsWith("#/")) {
			throw new RuntimeException("unknow JSON schema ref because NOT starts with '#/'.");
		}
		JSONObject json = this.get_internal_ref();
		for (String name : this.get_ref().substring(2).split("/")) {
			json = json.getJSONObject(name);
		}
		return this.born(json);
	}

	@Override
	public Boolean check_data(String data) {
		JSONSchema schema = this.get_ref_value();
		return schema.check_data(data);
	}

	@Override
	public Object generate() {
		JSONObject json = this.get_faker_defined();
		if (json != null) {
			Boolean fakered = true;
			for (String name : this.get_ref().substring(2).split("/")) {
				json = json.getJSONObject(name);
				if (json == null) {
					fakered = false;
					break;
				}
			}
			if (fakered) {
				return json;
			}
		}
		JSONSchema schema = this.get_ref_value();
		return schema.generate();
	}

	@Override
	public String toString() {
		return this.get_ref_value().toString();
	}
}
