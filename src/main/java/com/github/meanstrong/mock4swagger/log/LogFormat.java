package com.github.meanstrong.mock4swagger.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormat extends Formatter {
	@Override
	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder(1000);
		builder.append("[")
				.append((new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS")).format(new Date(record.getMillis())))
				.append("] [").append(record.getLoggerName()).append(".").append(record.getSourceMethodName())
				.append("] [").append(record.getThreadID())
				.append("] [").append(record.getLevel())
				.append("] : ")
				.append(record.getMessage())
				.append("\n");
		return builder.toString();
	}
}
