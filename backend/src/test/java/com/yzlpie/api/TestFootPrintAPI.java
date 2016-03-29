package com.yzlpie.api;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yzlpie.jpa.domain.BeaconDevice;
import com.yzlpie.jpa.domain.FootPrint;
import com.yzlpie.jpa.domain.User;
import com.yzlpie.jpa.repository.DeviceRepository;
import com.yzlpie.jpa.repository.FootPrintRepository;
import com.yzlpie.jpa.repository.UserRepository;
import com.yzlpie.service.FootPrintService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-all.xml")
@WebAppConfiguration
public class TestFootPrintAPI {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private DeviceRepository deviceRepo;
	
	@Autowired
	private FootPrintRepository fpRepo;
	
	@Autowired
	private FootPrintService fpService;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testGetFootPrint() throws Exception {
		mockMvc.perform(get("/api/v1/footprint").accept(MediaType.APPLICATION_JSON)).
		andDo(print()).
		andExpect(status().isOk());	
	}
	
	@Test
	public void testAddFootPrint() throws Exception {
		String requestBody = "{\"deviceId\":\"1_1\",\"preFpId\":\"xxx\"}";
		mockMvc.perform(post("/api/v1/footprint/add").contentType(MediaType.APPLICATION_JSON).content(requestBody).accept(MediaType.APPLICATION_JSON)).
		andDo(print()).
		andExpect(status().isOk());	
	}
	
	@Test
	public void testUpdateExitTime() {		
		fpService.updateExitFootPrint("xxx");
	}
	
	@Test
	public void testJson() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode onode = mapper.createObjectNode();
		String jsonStr = "{\"a\":\"b\"}";
//		ObjectNode node1 = (ObjectNode)mapper.readTree(jsonStr);
		onode.putPOJO("c", jsonStr);
		String s3 = onode.toString();
		System.out.println(s3);
	}
	
	@Test
	public void testJson1() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonStr = "{\"a\":\"b\"}";
		ObjectNode node = (ObjectNode)mapper.readTree(jsonStr);
		String s = node.path("a").asText();
		System.out.println(s);
	}
	
	@Test
	public void testJson2() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonStr = "deviceId=1_1";
		ObjectNode node = (ObjectNode)mapper.readTree(jsonStr);
		String s = node.path("deviceId").asText();
		System.out.println(s);
	}
	
	@Test
	public void testUser() {
		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setUsername("12345");
		user.setPassword("123");
		user.setCreateTime(new Date());
		user.setStatus(1);
		userRepo.saveAndFlush(user);
	}
	
	@Test
	public void testAddDevice1() {
		BeaconDevice device = new BeaconDevice();
		device.setDeviceId("30885_260");
		device.setImage("http://localhost/yzlpie/1.jpg");
		device.setTitle("梅花");
		device.setUrl("http://www.sina.com");
		device.setDetail("哈撒地方开始减肥的事水库附近的开发建设，抗击日军树。");
		deviceRepo.saveAndFlush(device);
	}
	
	@Test
	public void testAddDevice2() {
		BeaconDevice device = new BeaconDevice();
		device.setDeviceId("30885_266");
		device.setImage("http://localhost/yzlpie/2.jpg");
		device.setTitle("菊花");
		device.setUrl("http://www.baidu.com");
		device.setDetail("撒地方第三方 俄方we范围。");
		deviceRepo.saveAndFlush(device);
	}
	
	@Test
	public void testAddDevice3() {
		BeaconDevice device = new BeaconDevice();
		device.setDeviceId("30885_805");
		device.setImage("http://localhost/yzlpie/3.jpg");
		device.setTitle("茉莉花");
		device.setUrl("http://www.163.com");
		device.setDetail("日特各方俄方而非服务，热热风我为，凡事都纷纷。");
		deviceRepo.saveAndFlush(device);
	}
	
	@Test
	public void testAddFootPrintdb() {
		FootPrint fp = new FootPrint();
		fp.setFootPrintId(UUID.randomUUID().toString());
		fp.setDeviceId("1_1");
		fp.setUserId("rickid");
		fp.setEnterTime(new Date());
		fp.setExitTime(new Date());
		fpRepo.saveAndFlush(fp);
	}
	
	@Test
	public void testPns() {
		String deviceToken = "xxxxxx"; 
        String alert = "Hi！";
        int badge = 1;
        String sound = "default";

        List<String> tokens = new ArrayList<String>();
        tokens.add(deviceToken);
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String certificatePath = url.getPath() + "config/cert/aps_developer_identity.p12";
        String certificatePassword = "password";
        boolean sendCount = true;

        try {
            PushNotificationPayload payLoad = new PushNotificationPayload();
            payLoad.addAlert(alert); 
            payLoad.addBadge(badge); 
            if (!StringUtils.isBlank(sound)) {
                payLoad.addSound(sound);
            }
            PushNotificationManager pushManager = new PushNotificationManager();
            //true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, false));
            List<PushedNotification> notifications = new ArrayList<PushedNotification>();
            
            if (sendCount) {
                Device device = new BasicDevice();
                device.setToken(tokens.get(0));
                PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
                notifications.add(notification);
            } else {
                List<Device> device = new ArrayList<Device>();
                for (String token : tokens) {
                    device.add(new BasicDevice(token));
                }
                notifications = pushManager.sendNotifications(payLoad, device);
            }
            List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
            List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
            int failed = failedNotifications.size();
            int successful = successfulNotifications.size();
            pushManager.stopConnection();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}

}
