package com.github.meanstrong.mock4swagger.log;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
	public static Level level = Level.ALL;
	private final static Map<String, Logger> __loggers = new HashMap<>();

	public static Logger getLogger(Class<?> clazz) {
		return Log.getLogger(clazz.getName());
	}

	public static Logger getLogger(String name) {
		Logger logger = Log.__loggers.get(name);
		if (logger == null) {
			logger = Logger.getLogger(name);
			logger.setUseParentHandlers(false);
			ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(Log.level);
			consoleHandler.setFormatter(new LogFormat());
			logger.addHandler(consoleHandler);
			logger.setLevel(Log.level);
		}
		return logger;
	}
}
