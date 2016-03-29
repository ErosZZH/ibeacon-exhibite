//
//  BroadCastController.swift
//  yzlpie
//
//  Created by developer on 15-1-19.
//  Copyright (c) 2015年 yzlpie. All rights reserved.
//

import UIKit

class BroadCastController: UIViewController {

    @IBOutlet weak var bcText: UITextView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    convenience override init() {
        var nibNameOrNil = String?("BroadCastController")
        self.init(nibName: nibNameOrNil, bundle: nil)
    }
    
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: NSBundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
    }
    
    required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

}
