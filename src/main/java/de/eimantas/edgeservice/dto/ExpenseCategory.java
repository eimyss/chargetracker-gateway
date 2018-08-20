package de.eimantas.edgeservice.dto;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public enum ExpenseCategory {
	ESSEN, STEUER, TRINKEN, DIESEL, ZIGARETTEN;

	private static final List<ExpenseCategory> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static ExpenseCategory randomCategory() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

}
