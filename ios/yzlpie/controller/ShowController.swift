//
//  ShowController.swift
//  yzlpie
//
//  Created by user on 15/1/4.
//  Copyright (c) 2015年 yzlpie. All rights reserved.
//

import UIKit

class ShowController: UIViewController {
    
    var url:String?
    var device:String?
    var noticeController = NoticeController()
    var bcController = BroadCastController()
    var reach:Reachability?
    var reachable = true
    var preFootPrintId:String = "nil"

    @IBOutlet weak var warningView: UITextView!
    @IBOutlet var tap: UITapGestureRecognizer!
    
    @IBAction func onTap(sender: UITapGestureRecognizer) {
        let printDetailController = FootPrintDetailController()
        printDetailController.url = self.url
        self.hidesBottomBarWhenPushed = true
        self.navigationController?.pushViewController(printDetailController, animated: true)
        self.hidesBottomBarWhenPushed = false
    }
    
    func showNotice(deviceid:String) {
        
        self.reach?.startNotifier()
        
        
        if(self.reachable) {
            
            let manager = AFHTTPRequestOperationManager()
            let url = Constants.FULL_SERVER_NAME + "/yzlpie/api/v1/footprint/add"
            let param:NSDictionary = ["deviceId":deviceid,"preFpId":self.preFootPrintId]
            
            var type = "application/json"
            var sets = NSSet()
            
            manager.responseSerializer.acceptableContentTypes = sets.setByAddingObject(type)
            let serializer:AFJSONRequestSerializer = AFJSONRequestSerializer()
            serializer.requestWithMethod("POST", URLString: url, parameters: param, error: nil)
            manager.requestSerializer = serializer
            manager.requestSerializer.setValue(Constants.TOKEN, forHTTPHeaderField: "Authorization")
            manager.requestSerializer.setValue(type, forHTTPHeaderField: "Content-Type")
            
            manager.POST(url,
                parameters: param,
                success: { (operation: AFHTTPRequestOperation!,
                    responseObject: AnyObject!) in
                    let json = JSON(object: responseObject)
                    
                    let status = json["content"]["health"]["code"].integerValue
                    if status == 200 {
                        
                        self.noticeController.view.removeGestureRecognizer(self.tap)
                        self.noticeController.view.removeFromSuperview()
                        
//                        let dict = json["content"]["data"]["device"].dictionaryValue
                        let title = json["content"]["data"]["device"]["title"].stringValue
                        let detail = json["content"]["data"]["device"]["detail"].stringValue
                        let image = json["content"]["data"]["device"]["image"].stringValue
                        let url = json["content"]["data"]["device"]["url"].stringValue
                        
                        let fpId = json["content"]["data"]["fpId"].stringValue
                        self.preFootPrintId = fpId!
                        
                        self.url = url
                        
                        self.noticeController = NoticeController()
                        
                        
                        self.noticeController.view.addGestureRecognizer(self.tap)
                        self.noticeController.view.frame = CGRectMake(UIScreen.mainScreen().bounds.width/2 - self.noticeController.view.bounds.width/2, 0, self.noticeController.view.bounds.width, self.noticeController.view.bounds.height)
                        
                        self.view.addSubview(self.noticeController.view)
                        
                        self.noticeController.goodsTitle.text = title
                        self.noticeController.detail.text = detail
                        self.noticeController.imageView.image = UIImage(data: NSData(contentsOfURL: NSURL(string: image!)!)!)
                    }
                },
                failure: { (operation: AFHTTPRequestOperation!,
                    error: NSError!) in
                    println("Error: " + error.localizedDescription)
            })

        }
        
    }
    
    func showBroadcast(alert: String) {
        self.bcController.view.removeFromSuperview()
        self.bcController = BroadCastController()
        
        self.bcController.view.frame = CGRectMake(UIScreen.mainScreen().bounds.width, 100, bcController.view.bounds.width, bcController.view.bounds.height)
        self.view.addSubview(bcController.view)
        
        self.bcController.bcText.text = alert
        
        var bcTrans = self.view.layer.pop_animationForKey("bcTrans") as? POPSpringAnimation
        
        if bcTrans != nil {
            bcTrans?.toValue = -(UIScreen.mainScreen().bounds.width/2 + bcController.view.bounds.width/2)
        } else {
            bcTrans = POPSpringAnimation(propertyNamed: kPOPLayerTranslationX)
            bcTrans?.toValue = -(UIScreen.mainScreen().bounds.width/2 + bcController.view.bounds.width/2)
            bcTrans?.springBounciness = 8
            bcTrans?.springSpeed = 0.1
            bcController.view.layer.pop_addAnimation(bcTrans, forKey: "bcTrans")
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.reach = Reachability(hostname: Constants.SERVER_DOMAIN)
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "reachabilityChanged:", name: kReachabilityChangedNotification, object: nil)
        
    }
    
    func reachabilityChanged(notify:NSNotification) {

        let reached = notify.object as Reachability
        if(reached == self.reach) {
            if(reached.isReachable()) {
                self.warningView.hidden = true
                self.reachable = true
                if(device != nil) {
                    self.showNotice(device!)
                }
            } else {
                self.warningView.hidden = false
                self.reachable = false
            }
        }
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        self.noticeController.view.removeGestureRecognizer(self.tap)
        self.noticeController.view.removeFromSuperview()
        if(device != nil) {
            self.showNotice(device!)
        }
    }
    
    override func viewWillDisappear(animated: Bool) {
        super.viewWillDisappear(animated)
//        self.reach?.stopNotifier()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    
}
