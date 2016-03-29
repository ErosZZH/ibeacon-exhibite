package com.yzlpie.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yzlpie.common.BaseAPI;
import com.yzlpie.common.Relation;
import com.yzlpie.exception.APIException;
import com.yzlpie.service.UserService;


@Controller
@RequestMapping(value="/api/v1/user", produces={"application/json;charset=UTF-8"})
public class UserAPI extends BaseAPI {	
	
	private static final Logger logger = LoggerFactory.getLogger(UserAPI.class);
	
	@Autowired
	private UserService us;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Resource<Object>> getTokenFromDeviceToken(HttpServletRequest request) throws APIException {
		String deviceToken = request.getAttribute("deviceToken").toString();
//		String deviceToken = "dddevicetoken";

		String userToken = us.getTokenFromDeviceToken(deviceToken);
		
		ObjectMapper mapper = new ObjectMapper(); 
		ObjectNode onode = mapper.createObjectNode();
		onode.putPOJO("token", userToken);
		String result;
		try {
			result = mapper.writeValueAsString(onode);
		} catch (JsonProcessingException e) {
			logger.error("Json parse error.");
			throw new APIException("Json parse error.");
		}
		
		Link link = linkTo(methodOn(UserAPI.class).getTokenFromDeviceToken(request)).withRel(Relation.SELF.getName());		
		return this.wrapperResponse(result,link);
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Resource<Object>> registerUser(HttpServletRequest request) throws APIException {
		String deviceToken = request.getAttribute("deviceToken").toString();
//		String deviceToken = "aadevicetoken";
		String userToken = us.registerUser(deviceToken);
		ObjectMapper mapper = new ObjectMapper(); 
		ObjectNode onode = mapper.createObjectNode();
		onode.putPOJO("token", userToken);
		String result;
		try {
			result = mapper.writeValueAsString(onode);
		} catch (JsonProcessingException e) {
			logger.error("Json parse error.");
			throw new APIException("Json parse error.");
		}
		Link link = linkTo(methodOn(UserAPI.class).registerUser(request)).withRel(Relation.SELF.getName());		
		return this.wrapperResponse(result,link);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Resource<Object>> updateUser(@RequestBody JsonNode oldTokenNode, HttpServletRequest request) throws APIException {
		String deviceToken = request.getAttribute("deviceToken").toString();
//		String deviceToken = "newtoken";
		String oldDeviceToken = oldTokenNode.path("oldDeviceToken").asText();
		us.updateUser(oldDeviceToken, deviceToken);
		String result = "";
		Link link = linkTo(methodOn(UserAPI.class).updateUser(oldTokenNode, request)).withRel(Relation.SELF.getName());		
		return this.wrapperResponse(result,link);
	}
}
