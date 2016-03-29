//
//  Constants.swift
//  yzlpie
//
//  Created by user on 15/1/6.
//  Copyright (c) 2015年 yzlpie. All rights reserved.
//

import Foundation

struct Constants {
    
    static let UUID = "b9407f30-f5f8-466e-aff9-25556b57fe6d" //xunmi's device
//    static let UUID = "e2c56db5-dffb-48d2-b060-d0f5a71096e0"
    static let IDENTIFIER = "yzlpie"
    
    static let TOKEN = "ricktoken"
    static let HTTP_PROTOCOL = "http://"
//    static let SERVER_DOMAIN = "cipon-rack.vicp.cc:8081"
    static let SERVER_DOMAIN = "192.168.1.101"
    
    static let FULL_SERVER_NAME = Constants.HTTP_PROTOCOL + Constants.SERVER_DOMAIN
    
    static let dtFile = "dt.txt"
    
    static var deviceToken = ""
}