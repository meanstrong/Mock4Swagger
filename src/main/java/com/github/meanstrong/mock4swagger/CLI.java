package com.github.meanstrong.mock4swagger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;

import com.github.meanstrong.mock4swagger.faker.FakerDefined;
import com.github.meanstrong.mock4swagger.log.Log;
import com.github.meanstrong.mock4swagger.mockserver.SwaggerHandler;
import com.github.meanstrong.mock4swagger.swaggerparse.SwaggerObject;

public class CLI {
	private static final Logger LOG = Log.getLogger("CLI");

	public static void main(String[] args) throws Exception {
		Log.level = Level.INFO;
		System.setProperty("org.eclipse.jetty.util.log.class", "com.github.meanstrong.mock4swagger.log.Log");
		System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
		int port = 8080;
		List<String> list_swagger_file = new ArrayList<String>();
		String faker_file = "";
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-h") || args[i].equals("--help")) {
				CLI.show_help();
				return;
			}
			if (args[i].startsWith("--port=")) {
				port = Integer.parseInt(args[i].split("=", 2)[1]);
				LOG.info("set option port=" + port);
			} else if (args[i].startsWith("--swagger-file=")) {
				String swagger_file = args[i].split("=", 2)[1];
				LOG.info("set option swagger-file=" + swagger_file);
				list_swagger_file.add(swagger_file);

			} else if (args[i].startsWith("--faker-file=")) {
				faker_file = args[i].split("=", 2)[1];
				LOG.info("set option faker-file=" + faker_file);
			} else if (args[i].equalsIgnoreCase("--debug")) {
				Log.level = Level.ALL;
			}
		}
		if (list_swagger_file.size() == 0) {
			CLI.show_help();
			return;
		}

		Server server = new Server(port);
		List<SwaggerObject> list_swagger = new ArrayList<SwaggerObject>();
		for (String swagger_file : list_swagger_file) {
			if (swagger_file.startsWith("http://") || swagger_file.startsWith("https://")) {
				list_swagger.add(SwaggerObject.get_from_remote(swagger_file));
			} else {
				list_swagger.add(SwaggerObject.read_from_file(swagger_file));
			}
		}
		SwaggerHandler handler = new SwaggerHandler(list_swagger);
		if (faker_file.length() > 0) {
			FakerDefined faker_defined = FakerDefined.read_from_file(faker_file);
			handler.set_faker_defined(faker_defined);
		}
		server.setHandler(handler);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		LOG.info("^_^ server start OK ^_^");
		server.join();
	}

	public static void show_help() {
		System.out.println(
				"Usage: java -jar Mock4Swagger.jar --swagger-file=swagger.json [--port=8080] [--faker-file=faker.json]");
		System.out.println("Options:");
		System.out.println(
				"\t--swagger-file:\t The swagger json file path, it can be local path or internet path, you can use this option more than once");
		System.out.println("\t--port:\t The mock server internet port serve for.");
		System.out.println("\t--faker-file:\t The faker definitions file.");
	}
}
