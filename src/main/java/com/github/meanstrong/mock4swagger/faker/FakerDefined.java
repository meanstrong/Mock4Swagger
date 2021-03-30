package com.github.meanstrong.mock4swagger.faker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.github.meanstrong.mock4swagger.log.Log;

public class FakerDefined {
	private static final Logger LOG = Log.getLogger("FakerDefined");

	private JSONObject _json_data;

	public FakerDefined(JSONObject data) {
		this._json_data = data;
	}

	public JSONObject get_json_data() {
		return this._json_data;
	}

	public static FakerDefined parse(String data) {
		return new FakerDefined(JSONObject.parseObject(data, Feature.DisableSpecialKeyDetect));
	}

	public static FakerDefined read_from_file(String filename) throws IOException {
		FileReader fr = new FileReader(filename);
		String json_data = "";
		String line = null;
		BufferedReader br = new BufferedReader(fr);
		while ((line = br.readLine()) != null) {
			json_data += line;
		}
		br.close();
		return FakerDefined.parse(json_data);
	}
}
