package com.yzlpie.service;

import java.util.List;

import com.yzlpie.jpa.domain.BeaconDevice;
import com.yzlpie.model.FootPrintVO;

public interface FootPrintService {

	List<FootPrintVO> getFootPrintByUserId(String userId);
	List<BeaconDevice> addFootPrint(String userId, String deviceId, String fpId);
	void updateExitFootPrint(String fpId);
}
