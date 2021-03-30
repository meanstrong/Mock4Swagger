package com.github.meanstrong.mock4swagger.swaggerparse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.github.meanstrong.mock4swagger.log.Log;

public class SwaggerObject {
	private static final Logger LOG = Log.getLogger("SwaggerObject");

	private JSONObject _json_data;

	public SwaggerObject(JSONObject data) {
		this._json_data = data;
	}

	public JSONObject get_json_data() {
		return this._json_data;
	}

	public String get_version() {
		return this._json_data.getString("swagger");
	}

	public JSONObject get_info() {
		return this._json_data.getJSONObject("info");
	}

	public String get_host() {
		return this._json_data.getString("host");
	}

	public String get_basePath() {
		return this._json_data.getString("basePath");
	}

	public List<String> get_schemes() {
		List<String> result = new ArrayList<String>();
		for (Object key : this._json_data.getJSONArray("schemes")) {
			result.add((String) key);
		}
		return result;
	}

	public Map<String, SwaggerPath> get_paths() {
		Map<String, SwaggerPath> result = new HashMap<String, SwaggerPath>();
		for (Map.Entry<String, Object> entry : this._json_data.getJSONObject("paths").entrySet()) {
			result.put(entry.getKey(), new SwaggerPath((JSONObject) entry.getValue()));
		}
		return result;
	}

	public Map<String, SwaggerDefinition> get_definitions() {
		Map<String, SwaggerDefinition> result = new HashMap<String, SwaggerDefinition>();
		for (Map.Entry<String, Object> entry : this._json_data.getJSONObject("definitions").entrySet()) {
			result.put(entry.getKey(), new SwaggerDefinition((JSONObject) entry.getValue()));
		}
		return result;
	}

	public static SwaggerObject parse(String data) {
		return new SwaggerObject(JSONObject.parseObject(data, Feature.DisableSpecialKeyDetect));
	}

	public static SwaggerObject read_from_file(String filename) throws IOException {
		FileReader fr = new FileReader(filename);
		String json_data = "";
		String line = null;
		BufferedReader br = new BufferedReader(fr);
		while ((line = br.readLine()) != null) {
			json_data += line;
		}
		br.close();
		return SwaggerObject.parse(json_data);
	}

	public static SwaggerObject get_from_remote(String url) throws IOException {
		URL realUrl = new URL(url);
		URLConnection connection = realUrl.openConnection();
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		connection.connect();
		String json_data = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			json_data += line;
		}
		LOG.info("swagger file download from remote OK.");
		return SwaggerObject.parse(json_data);
	}

	@Override
	public String toString() {
		JSONObject info = this.get_info();
		if (info == null) {
			return "Swagger<>";
		}
		return "Swagger<" + info.getString("title") + ">";
	}
}
