//
//  NoticeController.swift
//  yzlpie
//
//  Created by user on 15/1/5.
//  Copyright (c) 2015年 yzlpie. All rights reserved.
//

import UIKit

class NoticeController: UIViewController {
    
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var goodsTitle: UILabel!
    @IBOutlet weak var detail: UITextView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        
        //圆角
        //        self.view.layer.cornerRadius = 10
        //        self.view.clipsToBounds = true
        
        //阴影
        self.view.layer.shadowColor = UIColor.blackColor().CGColor
        self.view.layer.shadowOffset = CGSizeMake(0, 5)
        self.view.layer.shadowOpacity = 0.8
        self.view.layer.shadowRadius = 10
        
        self.view.alpha = 0
        
        UIView.animateWithDuration(0.5, delay: 0, options: UIViewAnimationOptions.CurveEaseInOut, animations: { () -> Void in
            self.view.alpha = 1
            }, completion: nil)
        
        
        var noticeTrans = self.view.layer.pop_animationForKey("noticeTrans") as? POPSpringAnimation
        
        if noticeTrans != nil {
            noticeTrans?.toValue = UIScreen.mainScreen().bounds.height - 250
        } else {
            noticeTrans = POPSpringAnimation(propertyNamed: kPOPLayerTranslationY)
            noticeTrans?.toValue = UIScreen.mainScreen().bounds.height - 250
            noticeTrans?.springBounciness = 11
            noticeTrans?.springSpeed = 1
            self.view.layer.pop_addAnimation(noticeTrans, forKey: "noticeTrans")
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    convenience override init() {
        var nibNameOrNil = String?("NoticeController")
        self.init(nibName: nibNameOrNil, bundle: nil)
    }
    
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: NSBundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
    }
    
    required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }


}
