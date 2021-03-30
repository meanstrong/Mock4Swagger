package com.github.meanstrong.mock4swagger.mockserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.github.meanstrong.mock4swagger.faker.FakerDefined;
import com.github.meanstrong.mock4swagger.log.Log;
import com.github.meanstrong.mock4swagger.match.MatchSwagger;
import com.github.meanstrong.mock4swagger.swaggerparse.SwaggerObject;
import com.github.meanstrong.mock4swagger.swaggerparse.SwaggerRequest;
import com.github.meanstrong.mock4swagger.swaggerparse.SwaggerResponse;

public class SwaggerHandler extends AbstractHandler {
	private static final Logger LOG = Log.getLogger("SwaggerHandler");
	private List<SwaggerObject> _list_swagger = new ArrayList<SwaggerObject>();
	private FakerDefined _faker_defined = null;

	public SwaggerHandler(SwaggerObject swagger) {
		this._list_swagger.add(swagger);
	}

	public SwaggerHandler(List<SwaggerObject> list_swagger) {
		this._list_swagger = list_swagger;
	}

	public void set_faker_defined(FakerDefined faker_defined) {
		this._faker_defined = faker_defined;
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		baseRequest.setHandled(true);
		String uri = request.getRequestURI();
		if (request.getQueryString() != null) {
			uri += "?";
			uri += request.getQueryString();
		}
		LOG.info("Handle request: " + request.getMethod() + " " + uri);

		SwaggerObject swagger_object = null;
		SwaggerRequest swagger_request = null;
		MatchSwagger match = null;
		try {
			for (SwaggerObject swagger : this._list_swagger) {
				swagger_object = swagger;
				match = new MatchSwagger(swagger_object);
				swagger_request = match.swagger_request_matched(request.getMethod(), uri);
				if (swagger_request != null) {
					break;
				}
			}
			if (swagger_request == null) {
				LOG.info("Request not matched, return 404");
				response.setStatus(404);
				response.setContentType("text/plain;charset=utf-8");
				response.getWriter().print("request not matched.");
				return;
			}
			String body = "";
			String line = null;
			BufferedReader buffer = request.getReader();
			while ((line = buffer.readLine()) != null) {
				body += line;
			}
			Map<String, Object> result = match.check_parameters(swagger_request.get_parameters(),
					request.getQueryString(), body);
			if (!(boolean) result.get("result")) {
				String msg = "parameters not matched: " + result.get("msg").toString();
				LOG.info(msg + ", return 412");
				response.setStatus(412);
				response.setContentType("text/plain;charset=utf-8");
				response.getWriter().print(msg);
				return;
			}
			response.setStatus(200);
			String produce = "text/plain;charset=utf-8";
			if (swagger_request.get_produces() != null) {
				produce = swagger_request.get_produces().get(0);
			}
			response.setContentType(produce);
			SwaggerResponse swagger_response = swagger_request.get_responses().get("200");
			if (swagger_response != null) {
				swagger_response.set_internal_ref(swagger_object.get_json_data());
				if (this._faker_defined != null) {
					swagger_response.set_faker_defined(this._faker_defined.get_json_data());
				}
				Object object = swagger_response.generate();
				LOG.info("request matched OK, return 200, response=" + object.toString());
				response.getWriter().print(object.toString());
			} else {
				LOG.info("request matched OK, return 200 but Undocumented");
				response.getWriter().print("Undocumented OK.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
