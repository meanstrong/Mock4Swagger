package com.github.meanstrong.mock4swagger.match;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.github.meanstrong.mock4swagger.log.Log;
import com.github.meanstrong.mock4swagger.swaggerparse.SwaggerObject;
import com.github.meanstrong.mock4swagger.swaggerparse.SwaggerPath;
import com.github.meanstrong.mock4swagger.swaggerparse.SwaggerRequest;
import com.github.meanstrong.mock4swagger.swaggerparse.SwaggerRequestParameter;

public class MatchSwagger {
	private static final Logger LOG = Log.getLogger("MatchSwagger");

	private SwaggerObject _swagger;

	public MatchSwagger(SwaggerObject swagger) {
		this._swagger = swagger;
	}

	public SwaggerRequest swagger_request_matched(String method, String uri) {
		LOG.fine("args: method=" + method + ", uri=" + uri);
		String url_base = uri;
		String url_query = "";
		if (uri.contains("?")) {
			url_base = uri.substring(0, uri.indexOf('?'));
			url_query = uri.substring(uri.indexOf('?') + 1);
		}
		for (Map.Entry<String, SwaggerPath> entry : this._swagger.get_paths().entrySet()) {
			String swagger_url = this._swagger.get_basePath() + entry.getKey();
			String swagger_url_base = swagger_url;
			String swagger_url_query = "";
			if (swagger_url.contains("?")) {
				swagger_url_base = swagger_url.substring(0, swagger_url.indexOf('?'));
				swagger_url_query = swagger_url.substring(swagger_url.indexOf('?') + 1);
			}
			if (swagger_url_base.equals(url_base)) {
				boolean is_match_query = true;
				for (String query : swagger_url_query.split("&")) {
					if (!url_query.contains(query)) {
						is_match_query = false;
						break;
					}
				}
				if (is_match_query) {
					Map<String, SwaggerRequest> requests = entry.getValue().get_requests();
					for (String key : requests.keySet()) {
						if (key.equalsIgnoreCase(method)) {
							LOG.info("Get matched request by " + this._swagger);
							return requests.get(key);
						}
					}
				}

			}
		}
		LOG.fine("NOT matched any request by " + this._swagger);
		return null;
	}

	public Map<String, Object> check_parameters(List<SwaggerRequestParameter> list_parameter, String query_string,
			String body) {
		LOG.fine("function args query_string=" + query_string);
		LOG.fine("function args body=" + body);
		Map<String, Object> result = new HashMap<String, Object>();
		for (SwaggerRequestParameter parameter : list_parameter) {
			String in = parameter.get_in();
			if (in.equalsIgnoreCase("query")) {
				result = this.check_parameter_by_form_urlencoded(parameter, query_string);
			} else if (in.equalsIgnoreCase("formData")) {
				result = this.check_parameter_by_form_urlencoded(parameter, body);
			} else if (in.equalsIgnoreCase("body")) {
				result = this.check_parameter_by_string(parameter, body);
			} else {
				result.put("result", false);
				result.put("msg", "parameter in [" + in + "] NOT implemented.");
			}
			if (!(boolean) result.get("result")) {
				return result;
			}
		}
		result.put("result", true);
		return result;
	}

	public Map<String, String> form_urldecode(String str) {
		Map<String, String> map = new HashMap<String, String>();
		if (str != null && str.length() > 0) {
			for (String s : str.split("&")) {
				map.put(s.split("=", 2)[0], s.split("=", 2)[1]);
			}
		}
		return map;
	}

	public Map<String, Object> check_parameter_by_form_urlencoded(SwaggerRequestParameter parameter, String query) {
		LOG.fine("function args parameter=" + parameter.get_name());
		LOG.fine("function args query=" + query);
		Map<String, String> map_query = this.form_urldecode(query);
		String name = parameter.get_name();
		String value = map_query.get(name);
		return this.check_parameter_by_string(parameter, value);
	}

	public Map<String, Object> check_parameter_by_string(SwaggerRequestParameter parameter, String string) {
		LOG.fine("function args parameter=" + parameter.get_name());
		LOG.fine("function args string=" + string);
		Map<String, Object> result = new HashMap<String, Object>();
		parameter.set_internal_ref(this._swagger.get_json_data());
		if (!parameter.check_data(string)) {
			LOG.fine("parameter check data failed.");
			result.put("result", false);
			result.put("msg", "parameter[" + parameter.get_name() + "] check failed by " + parameter.toString());
			return result;
		}
		LOG.fine("parameter check data success.");
		result.put("result", true);
		return result;
	}
}
