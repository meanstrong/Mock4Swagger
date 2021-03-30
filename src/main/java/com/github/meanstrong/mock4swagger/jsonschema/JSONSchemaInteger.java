package com.github.meanstrong.mock4swagger.jsonschema;

import org.apache.commons.lang.math.NumberUtils;
import com.alibaba.fastjson.JSONObject;

/*
6.2. Validation Keywords for Numeric Instances (number and integer)

6.2.1. multipleOf

The value of "multipleOf" MUST be a number, strictly greater than 0.

A numeric instance is valid only if division by this keyword's value results in an integer.

6.2.2. maximum

The value of "maximum" MUST be a number, representing an inclusive upper limit for a numeric instance.

If the instance is a number, then this keyword validates only if the instance is less than or exactly equal to "maximum".

6.2.3. exclusiveMaximum

The value of "exclusiveMaximum" MUST be number, representing an exclusive upper limit for a numeric instance.

If the instance is a number, then the instance is valid only if it has a value strictly less than (not equal to) "exclusiveMaximum".

6.2.4. minimum

The value of "minimum" MUST be a number, representing an inclusive lower limit for a numeric instance.

If the instance is a number, then this keyword validates only if the instance is greater than or exactly equal to "minimum".

6.2.5. exclusiveMinimum

The value of "exclusiveMinimum" MUST be number, representing an exclusive lower limit for a numeric instance.

If the instance is a number, then the instance is valid only if it has a value strictly greater than (not equal to) "exclusiveMinimum".
 */
public class JSONSchemaInteger extends JSONSchemaNumber {

	public JSONSchemaInteger(JSONObject data) {
		super(data);
	}

	@Override
	public Boolean check_data(String data) {
		if (!NumberUtils.isDigits(data)) {
			return false;
		}
		return super.check_data(data);
	}

	@Override
	public Object generate() {
		return ((Number) super.generate()).intValue();
	}
}
