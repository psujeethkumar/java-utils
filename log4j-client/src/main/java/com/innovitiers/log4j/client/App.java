package com.innovitiers.log4j.client;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class App {

	private static Logger log = LogManager.getLogger(App.class);

	public static void main(String[] args) throws FileNotFoundException, IOException {

		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

		AppenderComponentBuilder file = builder.newAppender("log", "File");
		file.addAttribute("fileName", "logging.log");

		LayoutComponentBuilder standard = builder.newLayout("PatternLayout");
		standard.addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");

		file.add(standard);

		builder.add(file);

		LoggerComponentBuilder logger = builder.newLogger("com", Level.ALL);
		logger.add(builder.newAppenderRef("log"));
		logger.addAttribute("additivity", false);

		//builder.add(logger);

		RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.ALL);
		rootLogger.add(builder.newAppenderRef("log"));

		builder.add(rootLogger);

		builder.writeXmlConfiguration(System.out);

		builder.build();

		log.debug("It is a debug logger.");
		log.error("It is an error logger.");
		log.fatal("It is a fatal logger.");
		log.info("It is a info logger.");
		log.trace("It is a trace logger.");
		log.warn("It is a warn logger.");

	}
}
