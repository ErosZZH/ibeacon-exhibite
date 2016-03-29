//
//  HomeController.swift
//  yzlpie
//
//  Created by user on 14/12/25.
//  Copyright (c) 2014年 yzlpie. All rights reserved.
//

import UIKit
import CoreLocation
import CoreBluetooth

class HomeController: UITabBarController, CLLocationManagerDelegate, CBCentralManagerDelegate {
    
    let kUUID:String = Constants.UUID
    let kIdentifier:String = Constants.IDENTIFIER
    
    var trackLocationManager:CLLocationManager = CLLocationManager()
    var beaconRegion:CLBeaconRegion = CLBeaconRegion()
    var bluetooth:CBCentralManager?
    
    var nearest = "nil_nil"
    var candidate = "nil_nil"
    var count = 0

    override func viewDidLoad() {
        super.viewDidLoad()
        self.initRegion()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func centralManagerDidUpdateState(central: CBCentralManager!) {
    }
    
    func initRegion(){
        var uuid:NSUUID = NSUUID(UUIDString: kUUID)!
        beaconRegion = CLBeaconRegion(proximityUUID: uuid, identifier: kIdentifier)
        beaconRegion.notifyEntryStateOnDisplay = true
        trackLocationManager.delegate = self
        bluetooth = CBCentralManager(delegate:self, queue: nil)
        
        if ((UIDevice.currentDevice().systemVersion as NSString).floatValue >= 8.0 && CLLocationManager.authorizationStatus() != CLAuthorizationStatus.AuthorizedWhenInUse) {
            trackLocationManager.requestWhenInUseAuthorization()
        }
        self.trackLocationManager.startMonitoringForRegion(self.beaconRegion)
    }
    
    func locationManager(manager: CLLocationManager!, didStartMonitoringForRegion region: CLRegion!) {
        self.trackLocationManager.startRangingBeaconsInRegion(self.beaconRegion)
    }
    
    func locationManager(manager: CLLocationManager!, didEnterRegion region: CLRegion!) {
        self.trackLocationManager.startRangingBeaconsInRegion(self.beaconRegion)
    }
    
    func locationManager(manager: CLLocationManager!, didExitRegion region: CLRegion!) {
        self.trackLocationManager.stopRangingBeaconsInRegion(self.beaconRegion)
    }
    
    func locationManager(manager: CLLocationManager!, didRangeBeacons beacons: [AnyObject]!, inRegion region: CLBeaconRegion!) {
        
        if beacons.count <= 0 {
            return
        }
        
        var farest = -10000;
        var index = 0
        for(var i = 0; i < beacons.count; i++) {
            if(beacons[i].rssi > farest) {
                farest = beacons[i].rssi
                index = i
            }
        }
        
        let best = "\(beacons[index].major)_\(beacons[index].minor)"
        
        if(best == nearest) {   //最近的基站没变，直接返回
            count = 0
            return
        } else if (best == candidate) { //最近的基站变成了疑似基站
//            if(count < 1) { //但是还没有连续3次以上都变成某个新基站
//                count++
//                return
//            } else {    //连续3次以上都是新的某个基站，则确认基站变化成新的
                nearest = best
                candidate = "nil_nil"
                count = 0
                //TODO find out the nearest device
                println(nearest)
                let arrController: AnyObject? = self.viewControllers?.first
                if(arrController is UINavigationController) {
                    let con = arrController as UINavigationController
                    let firstTab = con.viewControllers
                    let showCon = firstTab[0] as ShowController
                    showCon.showNotice(nearest)
                    showCon.device = nearest
                }
                let mapCon = self.viewControllers?.last as MapController
                if(mapCon.bridge != nil && nearest != "30885_260" && nearest != "30885_266") {
                    mapCon.bridge?.send(nearest)
                }

//            }
            
        } else {    //发现全新基站，先定为疑似
            candidate = best
            count = 0
        }
    }


}
