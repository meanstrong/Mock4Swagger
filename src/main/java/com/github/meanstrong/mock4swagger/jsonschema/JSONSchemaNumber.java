package com.github.meanstrong.mock4swagger.jsonschema;

import com.alibaba.fastjson.JSONObject;
import com.github.meanstrong.mock4swagger.faker.Faker;

import org.apache.commons.lang.math.NumberUtils;

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
public class JSONSchemaNumber extends JSONSchema {

	public JSONSchemaNumber(JSONObject data) {
		super(data);
	}

	public Number get_minimum() {
		return (Number) this._json_data.get("minimum");
	}

	public Number get_maximum() {
		return (Number) this._json_data.get("maximum");
	}

	public Integer get_multipleOf() {
		return this._json_data.getInteger("multipleOf");
	}

	public Boolean get_exclusiveMinimum() {
		return this._json_data.getBoolean("exclusiveMinimum");
	}

	public Boolean get_exclusiveMaximum() {
		return this._json_data.getBoolean("exclusiveMaximum");
	}

	@Override
	public Boolean check_data(String data) {
		if (!NumberUtils.isNumber(data)) {
			return false;
		}
		double data_number = NumberUtils.toDouble(data);
		if (this.get_enum() != null) {
			return this.get_enum().contains(data_number);
		}
		if (this.get_minimum() != null) {
			if (this.get_exclusiveMinimum() != null && this.get_exclusiveMinimum()) {
				if (data_number <= this.get_minimum().doubleValue()) {
					return false;
				}
			} else {
				if (data_number < this.get_minimum().doubleValue()) {
					return false;
				}
			}
		}
		if (this.get_maximum() != null) {
			if (this.get_exclusiveMaximum() != null && this.get_exclusiveMaximum()) {
				if (data_number >= this.get_maximum().doubleValue()) {
					return false;
				}
			} else {
				if (data_number > this.get_maximum().doubleValue()) {
					return false;
				}
			}
		}
		if (this.get_multipleOf() != null) {
			if (data_number % this.get_multipleOf() != 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object generate() {
		if (this.get_enum() != null) {
			return (new Faker()).generate_choice(this.get_enum());
		}
		Number minimum = 0;
		Number maximum = 100;
		if (this.get_minimum() != null) {
			minimum = this.get_minimum();
		}
		if (this.get_maximum() != null) {
			maximum = this.get_maximum();
		}
		return (new Faker()).generate_number(minimum, maximum);
	}
}
