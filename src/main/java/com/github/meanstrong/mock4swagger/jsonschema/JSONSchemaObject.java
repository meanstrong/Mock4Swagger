package com.github.meanstrong.mock4swagger.jsonschema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;

/*
6.5. Validation Keywords for Objects

6.5.1. maxProperties

The value of this keyword MUST be a non-negative integer.

An object instance is valid against "maxProperties" if its number of properties is less than, or equal to, the value of this keyword.

6.5.2. minProperties

The value of this keyword MUST be a non-negative integer.

An object instance is valid against "minProperties" if its number of properties is greater than, or equal to, the value of this keyword.

Omitting this keyword has the same behavior as a value of 0.

6.5.3. required

The value of this keyword MUST be an array. Elements of this array, if any, MUST be strings, and MUST be unique.

An object instance is valid against this keyword if every item in the array is the name of a property in the instance.

Omitting this keyword has the same behavior as an empty array.

6.5.4. properties

The value of "properties" MUST be an object. Each value of this object MUST be a valid JSON Schema.

This keyword determines how child instances validate for objects, and does not directly validate the immediate instance itself.

Validation succeeds if, for each name that appears in both the instance and as a name within this keyword's value, the child instance for that name successfully validates against the corresponding schema.

Omitting this keyword has the same behavior as an empty object.

6.5.5. patternProperties

The value of "patternProperties" MUST be an object. Each property name of this object SHOULD be a valid regular expression, according to the ECMA 262 regular expression dialect. Each property value of this object MUST be a valid JSON Schema.

This keyword determines how child instances validate for objects, and does not directly validate the immediate instance itself. Validation of the primitive instance type against this keyword always succeeds.

Validation succeeds if, for each instance name that matches any regular expressions that appear as a property name in this keyword's value, the child instance for that name successfully validates against each schema that corresponds to a matching regular expression.

Omitting this keyword has the same behavior as an empty object.

6.5.6. additionalProperties

The value of "additionalProperties" MUST be a valid JSON Schema.

This keyword determines how child instances validate for objects, and does not directly validate the immediate instance itself.

Validation with "additionalProperties" applies only to the child values of instance names that do not match any names in "properties", and do not match any regular expression in "patternProperties".

For all such properties, validation succeeds if the child instance validates against the "additionalProperties" schema.

Omitting this keyword has the same behavior as an empty schema.

6.5.7. dependencies

[CREF1]

This keyword specifies rules that are evaluated if the instance is an object and contains a certain property.

This keyword's value MUST be an object. Each property specifies a dependency. Each dependency value MUST be an array or a valid JSON Schema.

If the dependency value is a subschema, and the dependency key is a property in the instance, the entire instance must validate against the dependency value.

If the dependency value is an array, each element in the array, if any, MUST be a string, and MUST be unique. If the dependency key is a property in the instance, each of the items in the dependency value must be a property that exists in the instance.

Omitting this keyword has the same behavior as an empty object.

6.5.8. propertyNames

The value of "propertyNames" MUST be a valid JSON Schema.

If the instance is an object, this keyword validates if every property name in the instance validates against the provided schema. Note the property name that the schema is testing will always be a string.

Omitting this keyword has the same behavior as an empty schema.

6.6. Keywords for Applying Subschemas Conditionally

These keywords work together to implement conditional application of a subschema based on the outcome of another subschema.

These keywords MUST NOT interact with each other across subschema boundaries. In other words, an "if" in one branch of an "allOf" MUST NOT have an impact on a "then" or "else" in another branch.

6.6.1. if

This keyword's value MUST be a valid JSON Schema.

Instances that successfully validate against this keyword's subschema MUST also be valid against the subschema value of the "then" keyword, if present.

Instances that fail to validate against this keyword's subschema MUST also be valid against the subschema value of the "else" keyword.

Validation of the instance against this keyword on its own always succeeds, regardless of the validation outcome of against its subschema.

6.6.2. then

This keyword's value MUST be a valid JSON Schema.

When present alongside of "if", the instance successfully validates against this keyword if it validates against both the "if"'s subschema and this keyword's subschema.

When "if" is absent, or the instance fails to validate against its subschema, validation against this keyword always succeeds. Implementations SHOULD avoid attempting to validate against the subschema in these cases.

6.6.3. else

This keyword's value MUST be a valid JSON Schema.

When present alongside of "if", the instance successfully validates against this keyword if it fails to validate against the "if"'s subschema, and successfully validates against this keyword's subschema.

When "if" is absent, or the instance successfully validates against its subschema, validation against this keyword always succeeds. Implementations SHOULD avoid attempting to validate against the subschema in these cases.
 */
public class JSONSchemaObject extends JSONSchema {

	public JSONSchemaObject(JSONObject data) {
		super(data);
	}

	public Map<String, JSONSchema> get_properties() {
		if (this._json_data.getJSONObject("properties") == null) {
			return null;
		}
		Map<String, JSONSchema> result = new HashMap<String, JSONSchema>();
		for (Map.Entry<String, Object> entry : this._json_data.getJSONObject("properties").entrySet()) {
			result.put(entry.getKey(), this.born((JSONObject) entry.getValue()));
		}
		return result;
	}

	public Integer get_maxProperties() {
		return this._json_data.getInteger("maxProperties");
	}

	public Integer get_minProperties() {
		return this._json_data.getInteger("minProperties");
	}

	public List<String> get_required() {
		if (this._json_data.getJSONArray("required") == null) {
			return null;
		}
		List<String> required = new ArrayList<String>();
		for (Object key : this._json_data.getJSONArray("required")) {
			required.add(key.toString());
		}
		return required;
	}

	@Override
	public Boolean check_data(String data) {
		JSONObject data_json = null;
		try {
			data_json = JSONObject.parseObject(data);
		} catch (Exception e) {
			return false;
		}
		if (this.get_minProperties() != null && data_json.size() < this.get_minProperties()) {
			return false;
		}
		if (this.get_maxProperties() != null && data_json.size() > this.get_maxProperties()) {
			return false;
		}
		if (this.get_required() != null) {
			for (String key : this.get_required()) {
				if (!data_json.containsKey(key)) {
					return false;
				}
			}
		}
		for (Map.Entry<String, JSONSchema> entry : this.get_properties().entrySet()) {
			Object value = data_json.get(entry.getKey());
			if (value == null){
				continue;
			}
			JSONSchema schema = entry.getValue();
			if (!schema.check_data(value.toString())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object generate() {
		JSONObject result = new JSONObject();
		if (this.get_properties() != null) {
			for (Map.Entry<String, JSONSchema> entry : this.get_properties().entrySet()) {
				JSONSchema schema = entry.getValue();
				result.put(entry.getKey(), schema.generate());
			}
		}
		return result;
	}
}
