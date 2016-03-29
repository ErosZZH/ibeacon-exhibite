package com.yzlpie.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;

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
import com.yzlpie.common.BaseAPI;
import com.yzlpie.common.Constants;
import com.yzlpie.common.Relation;
import com.yzlpie.exception.APIException;
import com.yzlpie.jpa.domain.BeaconDevice;
import com.yzlpie.model.FootPrintVO;
import com.yzlpie.service.FootPrintService;


@Controller
@RequestMapping(value="/api/v1/footprint", produces={"application/json;charset=UTF-8"})
public class FootPrintAPI extends BaseAPI {	
	
	private static final Logger logger = LoggerFactory.getLogger(FootPrintAPI.class);
	
	@Autowired
	private FootPrintService fpService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Resource<Object>> getFootPrint(HttpServletRequest request) throws APIException {
		String userId = request.getAttribute("userid").toString();
//		String userId = "rickid";

		List<FootPrintVO> footPrints = fpService.getFootPrintByUserId(userId);
		
		ObjectMapper mapper = new ObjectMapper(); 
		String result;
		try {
			result = mapper.writeValueAsString(footPrints);
		} catch (JsonProcessingException e) {
			logger.error("Json parse error.");
			throw new APIException("Json parse error.");
		}
		
		Link link = linkTo(methodOn(FootPrintAPI.class).getFootPrint(request)).withRel(Relation.SELF.getName());		
		return this.wrapperResponse(result,link);
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Resource<Object>> addFootPrint(@RequestBody JsonNode deviceNode, HttpServletRequest request) throws APIException {
		String userId = request.getAttribute("userid").toString();
//		String userId = "rickid";

		String deviceId = deviceNode.path("deviceId").asText();
		String preFpId = deviceNode.path("preFpId").asText();
		if(!preFpId.equals("nil")) {
			fpService.updateExitFootPrint(preFpId);
		}
		
		String fpId = UUID.randomUUID().toString();
		List<BeaconDevice> devices = fpService.addFootPrint(userId, deviceId, fpId);
		
		BeaconDevice device = null;
		if(devices != null) {
			device = devices.get(0);
			device.setImage("http://" + Constants.domainName + device.getImage());
		} else {
			throw new APIException("Unknown device.", 404, "");
		}
		SendDevice sd = new SendDevice();
		sd.setDevice(device);
		sd.setFpId(fpId);
		ObjectMapper mapper = new ObjectMapper(); 
		String result;
		try {
			result = mapper.writeValueAsString(sd);
		} catch (JsonProcessingException e) {
			logger.error("Json parse error.");
			throw new APIException("Json parse error.");
		}
		
		Link link = linkTo(methodOn(FootPrintAPI.class).addFootPrint(deviceNode, request)).withRel(Relation.SELF.getName());		
		return this.wrapperResponse(result,link);
	}
	
	public class SendDevice {
		private BeaconDevice device;
		private String fpId;
		public BeaconDevice getDevice() {
			return device;
		}
		public void setDevice(BeaconDevice device) {
			this.device = device;
		}
		public String getFpId() {
			return fpId;
		}
		public void setFpId(String fpId) {
			this.fpId = fpId;
		}
				
	}
}
