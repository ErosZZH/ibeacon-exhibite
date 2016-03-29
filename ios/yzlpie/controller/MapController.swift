//
//  MapController.swift
//  yzlpie
//
//  Created by user on 15/1/4.
//  Copyright (c) 2015年 yzlpie. All rights reserved.
//

import UIKit

class MapController: UIViewController, UIWebViewDelegate {

    @IBOutlet weak var mapWebView: UIWebView!    
    @IBOutlet weak var loading: UIActivityIndicatorView!
    
    var bridge:WebViewJavascriptBridge?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        mapWebView.delegate = self
    }
    
    func webViewDidStartLoad(webView: UIWebView) {
        self.loading.startAnimating()
        UIApplication.sharedApplication().networkActivityIndicatorVisible = true
    }
    
    func webViewDidFinishLoad(webView: UIWebView) {
        self.loading.stopAnimating()
        UIApplication.sharedApplication().networkActivityIndicatorVisible = false
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        
        let homeCon = self.tabBarController as HomeController
        
        if((bridge) != nil) {
            if(homeCon.nearest != "nil_nil") {
                bridge?.send(homeCon.nearest)
            }
            return
        }
        
        bridge = WebViewJavascriptBridge(forWebView: mapWebView, webViewDelegate: self, handler: nil)
        
        if(homeCon.nearest != "nil_nil") {
            bridge?.send(homeCon.nearest)
        }
        
        self.loadPage(mapWebView)
    }
    
    func loadPage(webView:UIWebView) {
        let html = NSBundle.mainBundle().pathForResource("map", ofType: "html")
        let baseURL = NSURL(fileURLWithPath: html!)
        let request = NSURLRequest(URL: baseURL!)
        webView.loadRequest(request)
    }


    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}
