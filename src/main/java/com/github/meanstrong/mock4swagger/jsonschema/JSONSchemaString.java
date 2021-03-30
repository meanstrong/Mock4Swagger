package com.github.meanstrong.mock4swagger.jsonschema;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.github.meanstrong.mock4swagger.faker.Faker;
import com.github.meanstrong.mock4swagger.faker.FakerDatetimeFormat;

import br.com.six2six.fixturefactory.function.impl.RegexFunction;

/*
6.3. Validation Keywords for Strings

6.3.1. maxLength

The value of this keyword MUST be a non-negative integer.

A string instance is valid against this keyword if its length is less than, or equal to, the value of this keyword.

The length of a string instance is defined as the number of its characters as defined by RFC 7159 [RFC7159].

6.3.2. minLength

The value of this keyword MUST be a non-negative integer.

A string instance is valid against this keyword if its length is greater than, or equal to, the value of this keyword.

The length of a string instance is defined as the number of its characters as defined by RFC 7159 [RFC7159].

Omitting this keyword has the same behavior as a value of 0.

6.3.3. pattern

The value of this keyword MUST be a string. This string SHOULD be a valid regular expression, according to the ECMA 262 regular expression dialect.

A string instance is considered valid if the regular expression matches the instance successfully. Recall: regular expressions are not implicitly anchored.
 */
public class JSONSchemaString extends JSONSchema {

	public JSONSchemaString(JSONObject data) {
		super(data);
	}

	public Integer get_maxLength() {
		return this._json_data.getInteger("maxLength");
	}

	public Integer get_minLength() {
		return this._json_data.getInteger("minLength");
	}

	public String get_pattern() {
		return this._json_data.getString("pattern");
	}

	/*
	 * 7.3. Defined Formats
	 * 
	 * 7.3.1. Dates and Times
	 * 
	 * These attributes apply to string instances.
	 * 
	 * Date and time format names are derived from RFC 3339, section 5.6
	 * [RFC3339].
	 * 
	 * Implementations supporting formats SHOULD implement support for the
	 * following attributes:
	 * 
	 * date-time A string instance is valid against this attribute if it is a
	 * valid representation according to the "date-time" production. date A
	 * string instance is valid against this attribute if it is a valid
	 * representation according to the "full-date" production. time A string
	 * instance is valid against this attribute if it is a valid representation
	 * according to the "full-time" production. Implementations MAY support
	 * additional attributes using the other production names defined in that
	 * section. If "full-date" or "full-time" are implemented, the corresponding
	 * short form ("date" or "time" respectively) MUST be implemented, and MUST
	 * behave identically. Implementations SHOULD NOT define extension
	 * attributes with any name matching an RFC 3339 production unless it
	 * validates according to the rules of that production. [CREF2]
	 * 
	 * 7.3.2. Email Addresses
	 * 
	 * These attributes apply to string instances.
	 * 
	 * A string instance is valid against these attributes if it is a valid
	 * Internet email address as follows:
	 * 
	 * email As defined by RFC 5322, section 3.4.1 [RFC5322]. idn-email As
	 * defined by RFC 6531 [RFC6531] Note that all strings valid against the
	 * "email" attribute are also valid against the "idn-email" attribute.
	 * 
	 * 7.3.3. Hostnames
	 * 
	 * These attributes apply to string instances.
	 * 
	 * A string instance is valid against these attributes if it is a valid
	 * representation for an Internet hostname as follows:
	 * 
	 * hostname As defined by RFC 1034, section 3.1 [RFC1034], including host
	 * names produced using the Punycode algorithm specified in RFC 5891,
	 * section 4.4 [RFC5891]. idn-hostname As defined by either RFC 1034 as for
	 * hostname, or an internationalized hostname as defined by RFC 5890,
	 * section 2.3.2.3 [RFC5890]. Note that all strings valid against the
	 * "hostname" attribute are also valid against the "idn-hostname" attribute.
	 * 
	 * 7.3.4. IP Addresses
	 * 
	 * These attributes apply to string instances.
	 * 
	 * A string instance is valid against these attributes if it is a valid
	 * representation of an IP address as follows:
	 * 
	 * ipv4 An IPv4 address according to the "dotted-quad" ABNF syntax as
	 * defined in RFC 2673, section 3.2 [RFC2673]. ipv6 An IPv6 address as
	 * defined in RFC 4291, section 2.2 [RFC4291]. 7.3.5. Resource Identifiers
	 * 
	 * These attributes apply to string instances.
	 * 
	 * uri A string instance is valid against this attribute if it is a valid
	 * URI, according to [RFC3986]. uri-reference A string instance is valid
	 * against this attribute if it is a valid URI Reference (either a URI or a
	 * relative-reference), according to [RFC3986]. iri A string instance is
	 * valid against this attribute if it is a valid IRI, according to
	 * [RFC3987]. iri-reference A string instance is valid against this
	 * attribute if it is a valid IRI Reference (either an IRI or a
	 * relative-reference), according to [RFC3987]. Note that all valid URIs are
	 * valid IRIs, and all valid URI References are also valid IRI References.
	 * 
	 * 7.3.6. uri-template
	 * 
	 * This attribute applies to string instances.
	 * 
	 * A string instance is valid against this attribute if it is a valid URI
	 * Template (of any level), according to [RFC6570].
	 * 
	 * Note that URI Templates may be used for IRIs; there is no separate IRI
	 * Template specification.
	 * 
	 * 7.3.7. JSON Pointers
	 * 
	 * These attributes apply to string instances.
	 * 
	 * json-pointer A string instance is valid against this attribute if it is a
	 * valid JSON string representation of a JSON Pointer, according to RFC
	 * 6901, section 5 [RFC6901]. relative-json-pointer A string instance is
	 * valid against this attribute if it is a valid Relative JSON Pointer
	 * [relative-json-pointer]. To allow for both absolute and relative JSON
	 * Pointers, use "anyOf" or "oneOf" to indicate support for either format.
	 * 
	 * 7.3.8. regex
	 * 
	 * This attribute applies to string instances.
	 * 
	 * A regular expression, which SHOULD be valid according to the ECMA 262
	 * [ecma262] regular expression dialect.
	 * 
	 * Implementations that validate formats MUST accept at least the subset of
	 * ECMA 262 defined in the Regular Expressions [regexInterop] section of
	 * this specification, and SHOULD accept all valid ECMA 262 expressions.
	 */
	public String get_format() {
		return this._json_data.getString("format");
	}

	public static JSONSchemaString parseByFormat(JSONSchemaString schema) {
		if (schema.get_format() == null) {
			return schema;
		}
		switch (schema.get_format()) {
		case "joinarray":
			return new JSONSchemaJoinarray(schema.get_json());
		default:
			return schema;
		}
	}

	@Override
	public Boolean check_data(String data) {
		if (this.get_enum() != null) {
			return this.get_enum().contains(data);
		}
		if (this.get_minLength() != null && data.length() < this.get_minLength()) {
			return false;
		}
		if (this.get_maxLength() != null && data.length() > this.get_maxLength()) {
			return false;
		}
		if (this.get_pattern() != null) {
			Matcher matcher = Pattern.compile(this.get_pattern()).matcher(data);
			if (!matcher.find()) {
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
		if (this.get_format() != null && this.get_format().equals("date-time")) {
			return (new FakerDatetimeFormat()).generate();
		}
		// TODO:
		if (this.get_pattern() != null) {
			return (new RegexFunction(this.get_pattern())).generateValue();
		}
		int min_length = 5;
		if (this.get_minLength() != null) {
			min_length = this.get_minLength();
		}
		int max_length = 10;
		if (this.get_maxLength() != null) {
			max_length = this.get_maxLength();
		}
		Faker faker = new Faker();
		return faker.generate_string(faker.generate_number(min_length, max_length).intValue());
	}
}
