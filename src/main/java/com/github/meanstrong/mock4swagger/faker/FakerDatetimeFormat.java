package com.github.meanstrong.mock4swagger.faker;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;

public class FakerDatetimeFormat extends Faker {
	public static final long MAX_VALUE = Timestamp.valueOf("9999-12-31 23:59:59").getTime();
	private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Object generate() {
		return this.generate_between(0, FakerDatetimeFormat.MAX_VALUE);
	}

	public String generate_before(long before) {
		return this.generate_between(0, before);
	}

	public String generate_after(long after) {
		return this.generate_between(after, FakerDatetimeFormat.MAX_VALUE);
	}

	public String generate_between(long min, long max) {
		long number = this.generate_number(min, max).longValue();
		return this.dateformat.format(number);
	}

	public void set_format(SimpleDateFormat dateformat) {
		this.dateformat = dateformat;
	}
}
