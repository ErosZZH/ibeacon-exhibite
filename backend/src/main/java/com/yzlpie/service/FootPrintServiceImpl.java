package com.yzlpie.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzlpie.common.Constants;
import com.yzlpie.jpa.domain.BeaconDevice;
import com.yzlpie.jpa.domain.FootPrint;
import com.yzlpie.jpa.repository.DeviceRepository;
import com.yzlpie.jpa.repository.FootPrintRepository;
import com.yzlpie.model.FootPrintVO;

@Service
public class FootPrintServiceImpl implements FootPrintService {
	
	@Autowired
	private FootPrintRepository fpRepo;
	
	@Autowired
	private DeviceRepository deviceRepo;

	@Override
	public List<FootPrintVO> getFootPrintByUserId(String userId) {
		List<FootPrintVO> fpvos = new ArrayList<FootPrintVO>();
		List<FootPrint> fps = fpRepo.getFootPrintByUserId(userId);
		Iterator<FootPrint> iter = fps.iterator();
		while(iter.hasNext()) {
			FootPrint fp = iter.next();
			FootPrintVO fpvo = new FootPrintVO();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			fpvo.setDate(sdf.format(fp.getEnterTime()));
			String deviceId = fp.getDeviceId();
			List<BeaconDevice> devices = deviceRepo.getDeviceById(deviceId);
			if(devices.size() == 1) {
				BeaconDevice device = devices.get(0);
				fpvo.setImage("http://" + Constants.domainName + device.getImage());
				fpvo.setTitle(device.getTitle());
				fpvo.setUrl(device.getUrl());
			}
			fpvos.add(fpvo);
		}
		return fpvos;
	}

	@Override
	public List<BeaconDevice> addFootPrint(String userId, String deviceId, String fpId) {
		List<BeaconDevice> devices = null;
		if(deviceRepo.countDeviceByDeviceId(deviceId) > 0) {
			FootPrint fp = new FootPrint();
			fp.setFootPrintId(fpId);
			fp.setDeviceId(deviceId);
			fp.setUserId(userId);
			fp.setEnterTime(new Date());
			fpRepo.saveAndFlush(fp);
			devices = deviceRepo.getDeviceById(deviceId);
		}
		
		return devices;
	}

	@Override
	public void updateExitFootPrint(String fpId) {
		fpRepo.updateExitTime(fpId);
	}

}
