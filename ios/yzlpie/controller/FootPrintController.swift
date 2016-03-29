//
//  FootPrintController.swift
//  yzlpie
//
//  Created by user on 14/12/25.
//  Copyright (c) 2014年 yzlpie. All rights reserved.
//

import UIKit

class FootPrintController: UIViewController {
    
    var reach:Reachability?
    var reachable = true

    @IBOutlet weak var scrollView: UIScrollView!
    
    @IBOutlet weak var loading: UIActivityIndicatorView!
    @IBOutlet weak var warningView: UITextView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.reach = Reachability(hostname: Constants.SERVER_DOMAIN)
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "reachabilityChanged:", name: kReachabilityChangedNotification, object: nil)
        
        self.reach?.startNotifier()
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(true)
        
        self.loading.startAnimating()
        UIApplication.sharedApplication().networkActivityIndicatorVisible = true
        
        let sv:NSArray = scrollView.subviews as NSArray
        for(var i = 0; i < sv.count; i++) {
            sv[i].removeFromSuperview()
        }
        
        if(self.reachable) {
            initData()
        }
        
    }
    
    func reachabilityChanged(notify:NSNotification) {
        
        let reached = notify.object as Reachability
        if(reached == self.reach) {
            if(reached.isReachable()) {
                self.warningView.hidden = true
                self.reachable = true
                let sv:NSArray = scrollView.subviews as NSArray
                for(var i = 0; i < sv.count; i++) {
                    sv[i].removeFromSuperview()
                }
                initData()
            } else {
                self.warningView.hidden = false
                self.reachable = false
            }
        }
    }
    
    override func viewWillDisappear(animated: Bool) {
        super.viewWillDisappear(animated)
//        self.reach?.stopNotifier()
    }
    
    func initData() {
        let manager = AFHTTPRequestOperationManager()
        let url = Constants.FULL_SERVER_NAME + "/yzlpie/api/v1/footprint"
        
        var type = "application/json"
        var sets = NSSet()
        
        manager.responseSerializer.acceptableContentTypes = sets.setByAddingObject(type)
        manager.requestSerializer.setValue(Constants.TOKEN, forHTTPHeaderField: "Authorization")
        
        manager.GET(url,
            parameters: nil,
            success: { (operation: AFHTTPRequestOperation!,
                responseObject: AnyObject!) in
                let json = JSON(object: responseObject)
                let status = json["content"]["health"]["code"].integerValue
                if status == 200 {
                    let footPrintCount = json["content"]["data"].arrayValue?.count
                    var footPrintList:[FootPrint] = [FootPrint]()
                    for(var i = 0; i < footPrintCount; i++) {
                        let fp = FootPrint()
                        fp.date = json["content"]["data"][i]["date"].stringValue
                        fp.text = json["content"]["data"][i]["title"].stringValue
                        fp.url = json["content"]["data"][i]["url"].stringValue
                        let imageUrl = json["content"]["data"][i]["image"].stringValue
                        fp.image = UIImage(data: NSData(contentsOfURL: NSURL(string: imageUrl!)!)!)
                        footPrintList.append(fp)
                    }
                    self.initTimeline(footPrintList)
                    self.loading.stopAnimating()
                    UIApplication.sharedApplication().networkActivityIndicatorVisible = false
                }
            },
            failure: { (operation: AFHTTPRequestOperation!,
                error: NSError!) in
                println("Error: " + error.localizedDescription)
        })
    }
    
    func initTimeline(footPrintList:[FootPrint]) {
        var timeFrames:[TimeFrame] = [TimeFrame]()
        for(var i = 0; i < footPrintList.count; i++) {
            let tf = TimeFrame(text: footPrintList[i].text!, date: footPrintList[i].date!, image: footPrintList[i].image, url: footPrintList[i].url!)
            timeFrames.append(tf)
        }
        let timeline = TimelineView(bulletType: .Diamond, timeFrames: timeFrames, currentController: self)
        
        scrollView.addSubview(timeline)
        scrollView.addConstraints([
            NSLayoutConstraint(item: timeline, attribute: .Left, relatedBy: .Equal, toItem: scrollView, attribute: .Left, multiplier: 1.0, constant: 0),
            NSLayoutConstraint(item: timeline, attribute: .Bottom, relatedBy: .LessThanOrEqual, toItem: scrollView, attribute: .Bottom, multiplier: 1.0, constant: 0),
            NSLayoutConstraint(item: timeline, attribute: .Top, relatedBy: .Equal, toItem: scrollView, attribute: .Top, multiplier: 1.0, constant: -65),
            NSLayoutConstraint(item: timeline, attribute: .Right, relatedBy: .Equal, toItem: scrollView, attribute: .Right, multiplier: 1.0, constant: 0),
            NSLayoutConstraint(item: timeline, attribute: .Width, relatedBy: .Equal, toItem: scrollView, attribute: .Width, multiplier: 1.0, constant: 0)
            ])
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}
