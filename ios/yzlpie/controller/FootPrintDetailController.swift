//
//  FootPrintDetailController.swift
//  yzlpie
//
//  Created by user on 14/12/26.
//  Copyright (c) 2014年 yzlpie. All rights reserved.
//

import UIKit

class FootPrintDetailController: UIViewController, UIWebViewDelegate {
    
    var url:String?

    @IBOutlet weak var loading: UIActivityIndicatorView!
    @IBOutlet weak var webView: UIWebView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        webView.delegate = self
        
        var url1 = NSURL(string: url!)
        var request = NSURLRequest(URL: url1!)
        
        webView.loadRequest(request)

    }
    
    func webViewDidStartLoad(webView: UIWebView) {
        self.loading.startAnimating()
        UIApplication.sharedApplication().networkActivityIndicatorVisible = true
    }
    
    func webViewDidFinishLoad(webView: UIWebView) {
        self.loading.stopAnimating()
        UIApplication.sharedApplication().networkActivityIndicatorVisible = false
    }
    
    convenience override init() {
        var nibNameOrNil = String?("FootPrintDetailController")
        self.init(nibName: nibNameOrNil, bundle: nil)
    }
    
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: NSBundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
    }
    
    required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}
