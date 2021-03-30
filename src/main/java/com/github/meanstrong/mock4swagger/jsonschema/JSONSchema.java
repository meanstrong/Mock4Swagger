package com.github.meanstrong.mock4swagger.jsonschema;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONSchema {
	protected JSONObject _json_data;
	protected JSONObject _internal_ref;
	protected JSONObject _faker_defined;

	public JSONSchema(JSONObject data) {
		this._json_data = data;
	}

	public JSONObject get_json() {
		return this._json_data;
	}

	public JSONObject get_internal_ref() {
		return this._internal_ref;
	}

	public void set_internal_ref(JSONObject json) {
		this._internal_ref = json;
	}

	public void set_faker_defined(JSONObject json) {
		this._faker_defined = json;
	}

	public JSONObject get_faker_defined() {
		return this._faker_defined;
	}

	public JSONSchema born(JSONObject json) {
		JSONSchema schema = JSONSchema.parseJSONObject(json);
		schema.set_internal_ref(this.get_internal_ref());
		schema.set_faker_defined(this.get_faker_defined());
		return schema;
	}

	public String get_title() {
		return this._json_data.getString("title");
	}

	public String get_description() {
		return this._json_data.getString("description");
	}

	public String get_default() {
		return this._json_data.getString("default");
	}

	public String get_type() {
		return this._json_data.getString("type");
	}

	public JSONArray get_enum() {
		return this._json_data.getJSONArray("enum");
	}

	public List<JSONSchema> get_allOf() {
		if (this._json_data.getJSONArray("allOf") == null) {
			return null;
		}
		List<JSONSchema> result = new ArrayList<JSONSchema>();
		for (Object item : this._json_data.getJSONArray("allOf")) {
			result.add(this.born((JSONObject) item));
		}
		return result;
	}

	public List<JSONSchema> get_anyOf() {
		if (this._json_data.getJSONArray("anyOf") == null) {
			return null;
		}
		List<JSONSchema> result = new ArrayList<JSONSchema>();
		for (Object item : this._json_data.getJSONArray("anyOf")) {
			result.add(this.born((JSONObject) item));
		}
		return result;
	}

	public List<JSONSchema> get_oneOf() {
		if (this._json_data.getJSONArray("oneOf") == null) {
			return null;
		}
		List<JSONSchema> result = new ArrayList<JSONSchema>();
		for (Object item : this._json_data.getJSONArray("oneOf")) {
			result.add(this.born((JSONObject) item));
		}
		return result;
	}

	public JSONSchema get_not() {
		if (this._json_data.getJSONObject("not") == null) {
			return null;
		}
		return this.born(this._json_data.getJSONObject("not"));
	}

	public Object get_example() {
		return this._json_data.get("example");
	}

	public Boolean check_data_allOf(String data) {
		if (this.get_allOf() != null) {
			for (JSONSchema schema : this.get_allOf()) {
				if (!schema.check_data(data)) {
					return false;
				}
			}
		}
		return true;
	}

	public Boolean check_data_anyOf(String data) {
		if (this.get_anyOf() != null) {
			boolean is_anyof = false;
			for (JSONSchema schema : this.get_anyOf()) {
				if (schema.check_data(data)) {
					is_anyof = true;
					break;
				}
			}
			if (!is_anyof) {
				return false;
			}
		}
		return true;
	}

	public Boolean check_data_oneOf(String data) {
		if (this.get_oneOf() != null) {
			int num = 0;
			for (JSONSchema schema : this.get_oneOf()) {
				if (schema.check_data(data)) {
					num += 1;
				}
			}
			if (num != 1) {
				return false;
			}
		}
		return true;
	}

	public Boolean check_data_not(String data) {
		if (this.get_not() != null && this.get_not().check_data(data)) {
			return false;
		}
		return true;
	}

	public Boolean check_data(String data) {
		throw new RuntimeException("unknow JSON schema to check data.");
	}

	public Object generate() {
		throw new RuntimeException("unknow JSON schema to generate data.");
	}

	public static JSONSchema parseJSONObject(JSONObject data) {
		if (data.getString("$ref") != null) {
			return new JSONSchemaRef(data);
		}
		if (data.getString("type") == null) {
			return new JSONSchema(data);
		}
		switch (data.getString("type")) {
		case "number":
			return new JSONSchemaNumber(data);
		case "integer":
			return new JSONSchemaInteger(data);
		case "string":
			return JSONSchemaString.parseByFormat(new JSONSchemaString(data));
		case "boolean":
			return new JSONSchemaBoolean(data);
		case "array":
			return new JSONSchemaArray(data);
		case "object":
			return new JSONSchemaObject(data);
		default:
			return new JSONSchema(data);
		}
	}
	// public JSONSchema JSONSchemaParse(){
	// return JSONSchema.parseJSONObject(this._json_data);
	// }

	@Override
	public String toString() {
		return this._json_data.toJSONString();
	}
}
