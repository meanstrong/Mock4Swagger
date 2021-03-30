package com.github.meanstrong.mock4swagger.jsonschema;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.meanstrong.mock4swagger.faker.Faker;

/*
6.4. Validation Keywords for Arrays

6.4.1. items

The value of "items" MUST be either a valid JSON Schema or an array of valid JSON Schemas.

This keyword determines how child instances validate for arrays, and does not directly validate the immediate instance itself.

If "items" is a schema, validation succeeds if all elements in the array successfully validate against that schema.

If "items" is an array of schemas, validation succeeds if each element of the instance validates against the schema at the same position, if any.

Omitting this keyword has the same behavior as an empty schema.

6.4.2. additionalItems

The value of "additionalItems" MUST be a valid JSON Schema.

This keyword determines how child instances validate for arrays, and does not directly validate the immediate instance itself.

If "items" is an array of schemas, validation succeeds if every instance element at a position greater than the size of "items" validates against "additionalItems".

Otherwise, "additionalItems" MUST be ignored, as the "items" schema (possibly the default value of an empty schema) is applied to all elements.

Omitting this keyword has the same behavior as an empty schema.

6.4.3. maxItems

The value of this keyword MUST be a non-negative integer.

An array instance is valid against "maxItems" if its size is less than, or equal to, the value of this keyword.

6.4.4. minItems

The value of this keyword MUST be a non-negative integer.

An array instance is valid against "minItems" if its size is greater than, or equal to, the value of this keyword.

Omitting this keyword has the same behavior as a value of 0.

6.4.5. uniqueItems

The value of this keyword MUST be a boolean.

If this keyword has boolean value false, the instance validates successfully. If it has boolean value true, the instance validates successfully if all of its elements are unique.

Omitting this keyword has the same behavior as a value of false.

6.4.6. contains

The value of this keyword MUST be a valid JSON Schema.

An array instance is valid against "contains" if at least one of its elements is valid against the given schema.
 */
public class JSONSchemaArray extends JSONSchema {

	public JSONSchemaArray(JSONObject data) {
		super(data);
	}

	public Object get_items() {
		Object items = this._json_data.get("items");
		if (items instanceof JSONObject) {
			return this.born((JSONObject) items);
		} else if (items instanceof JSONArray) {
			List<JSONSchema> result = new ArrayList<JSONSchema>();
			for (Object item : (JSONArray) items) {
				result.add(this.born((JSONObject) item));
			}
			return result;
		}
		throw new JSONException("illegal items");
	}

	public Integer get_maxItems() {
		return this._json_data.getInteger("maxItems");
	}

	public Integer get_minItems() {
		return this._json_data.getInteger("minItems");
	}

	public Boolean get_uniqueItems() {
		return this._json_data.getBoolean("uniqueItems");
	}

	@Override
	public Boolean check_data(String data) {
		Object items = this.get_items();
		JSONArray data_json = null;
		try {
			data_json = JSONArray.parseArray(data);
		} catch (Exception e) {
			return false;
		}
		int size_data = data_json.size();
		if (this.get_minItems() != null && size_data < this.get_minItems()) {
			return false;
		}
		if (this.get_maxItems() != null && size_data > this.get_maxItems()) {
			return false;
		}
		if (items instanceof List && data_json instanceof JSONArray) {
			int size_schema = ((List<JSONSchema>) items).size();
			for (int i = 0; i < size_schema; i++) {
				if (i < size_data && !(((List<JSONSchema>) items).get(i)).check_data(data_json.get(i).toString())) {
					return false;
				}
			}
			return true;
		}
		if (items instanceof JSONSchema && data_json instanceof JSONArray) {
			for (Object value : (JSONArray) data_json) {
				if (!((JSONSchema) items).check_data(value.toString())) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public Object generate() {
		JSONArray result = new JSONArray();
		Object items = this.get_items();
		if (items instanceof JSONSchema) {
			JSONSchema schema = (JSONSchema) items;
			int size = (new Faker()).generate_number(1, 5).intValue();
			for (int i = 0; i < size; i++) {
				result.add(schema.generate());
			}
		} else {
			for (JSONSchema item : (List<JSONSchema>) items) {
				result.add(item.generate());
			}
		}
		return result;
	}
}
