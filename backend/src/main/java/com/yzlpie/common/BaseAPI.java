package com.yzlpie.common;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yzlpie.exception.APIException;
import com.yzlpie.vo.FailVO;
import com.yzlpie.vo.SuccessVO;



public abstract class BaseAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseAPI.class);
	
	public static String getTrackID() {
		return String.valueOf(System.currentTimeMillis());
	}
	

	@ExceptionHandler(Exception.class)  
    protected @ResponseBody  
    ResponseEntity<Resource<Object>> exceptionHandler(Exception e) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode onode = mapper.createObjectNode();
		FailVO fvo;
		String trackid = getTrackID();
		HttpStatus status = null;
		if (e instanceof APIException) {
			logger.error("APIException throwed when calling API. Error code: " + ((APIException) e).getErrorCode() + " Error message: " + e.getMessage() + " Trackid is: " + ((APIException) e).getTrackId(), e);
			fvo = new FailVO(((APIException) e).getErrorCode(), "Error", e.getMessage(), ((APIException) e).getTrackId());
			status = HttpStatus.valueOf(((APIException) e).getErrorCode());
		} else {
			logger.error("APIException throwed when calling API. Error code: 500. Error message: " + e.getMessage() + " Trackid is: " + trackid, e);
			fvo = new FailVO(500, "Error", e.getMessage(), trackid);
			status = HttpStatus.valueOf(500);
		}
		onode.putPOJO("health", fvo);
		Resource<Object> res = new Resource<Object>(onode); 
		return enableCorsRequests(res, status);
    }
	
	protected <T> ResponseEntity<T> enableCorsRequests(T entity, HttpStatus statusCode) {
		String mode = "DENY";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Control-Allow-Origin", "*");
		headers.set("X-FRAME-OPTIONS", mode);
		return new ResponseEntity<T>(entity, headers, statusCode);
	}
	
	protected ResponseEntity<Resource<Object>> wrapperResponse(String result) {
		return wrapperResponse(result, null);
	}
	
	protected ResponseEntity<Resource<Object>> wrapperResponse(String result, Link link) {
		int statusCode;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode onode = mapper.createObjectNode();
		if (result.length() == 3) {
			statusCode = Integer.valueOf(result);
			FailVO fvo;
			if (statusCode == 404) {
				fvo = new FailVO(statusCode, "Error", "Resource not found!", getTrackID());
			} else if(statusCode == 403){
				fvo = new FailVO(statusCode, "Error", "API authority error!", getTrackID());
			}else{
				fvo = new FailVO(statusCode, "Error", "Internal error!", getTrackID());
			}
			onode.putPOJO("health", fvo);
		} else {
			statusCode = 200;	
			JsonNode node = null;
			try {
				node = mapper.readTree(result);
			} catch (JsonProcessingException e) {
				logger.error("Error occured when translating json to jackson json.", e);
			} catch (IOException e) {
				logger.error("Error occured when translating json to jackson json.", e);
			}
			onode.putPOJO("data", node);
			SuccessVO svo = new SuccessVO(200, "OK");
			onode.putPOJO("health", svo);
		}	
		Resource<Object> res = new Resource<Object>(onode);
		if(statusCode == 200 && link != null){
			res.add(link);
		}
				
		return this.enableCorsRequests(res,  HttpStatus.valueOf(statusCode));		
	}	
}
