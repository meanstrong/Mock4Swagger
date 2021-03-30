package com.github.meanstrong.mock4swagger.faker;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class Faker {
	public Object generate() {
		throw new RuntimeException("Faker nothing.");
	}

	public boolean generate_boolean() {
		Random random = new Random();
		return random.nextBoolean();
	}

	// public int generate_int(int min, int max) {
	// Random random = new Random();
	// return random.nextInt(max - min) + min;
	// }

	public Number generate_number(Number min, Number max) {
		Random random = new Random();
		return random.nextDouble() * (max.doubleValue() - min.doubleValue()) + min.doubleValue();
	}

	public <T> T generate_choice(List<T> list) {
		Random random = new Random();
		return list.get(random.nextInt(list.size()));
	}

	public String generate_string(int count) {
		return RandomStringUtils.randomAlphabetic(count);
	}
}