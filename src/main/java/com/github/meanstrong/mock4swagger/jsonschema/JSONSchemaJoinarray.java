package com.github.meanstrong.mock4swagger.jsonschema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.github.meanstrong.mock4swagger.faker.Faker;


public class JSONSchemaJoinarray extends JSONSchemaString {

	public JSONSchemaJoinarray(JSONObject data) {
		super(data);
	}

	public String get_separator() {
		return this._json_data.getString("separator");
	}

	public Boolean get_uniqueItems() {
		return this._json_data.getBoolean("uniqueItems");
	}

	public JSONSchema get_items() {
		return this.born(this._json_data.getJSONObject("items"));
	}

	@Override
	public Boolean check_data(String data) {
		for (String item : data.split(this.get_separator())) {
			JSONSchema schema = this.get_items();
			if (!schema.check_data(item)) {
				return false;
			}
		}
		if (this.get_uniqueItems() != null && this.get_uniqueItems()) {
			String[] list_data = data.split(this.get_separator());
			if (list_data.length != (new HashSet<String>(Arrays.asList(list_data))).size()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object generate() {
		String result = "";
		List<Object> list_result = new ArrayList<Object>();
		JSONSchema schema = this.get_items();
		int size = (new Faker()).generate_number(1, 5).intValue();
		for (int i = 0; i < size; i++) {
			list_result.add(schema.generate());
		}
		if (this.get_uniqueItems() != null && this.get_uniqueItems()) {
			for (Object item : new HashSet<Object>(list_result)) {
				result += item.toString();
				result += this.get_separator();
			}
		} else {
			for (Object item : list_result) {
				result += item.toString();
				result += this.get_separator();
			}
		}
		return result;
	}
}
