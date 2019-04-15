//
//  SecondViewController.swift
//  viewController
//
//  Created by ciepc on 15/11/2017.
//  Copyright © 2017 ciepc. All rights reserved.
//

import Foundation
import UIKit

class SecondViewController: UIViewController{
    var userId : String?
    
    @IBOutlet weak var idLabel: UILabel!
    override func viewDidLoad(){
        super.viewDidLoad()
        
        idLabel.text = userId!+"님 안녕하세요"
    }
    
    @IBAction func prevPage(_ sender: Any) {
        self.performSegue(withIdentifier: "seguePrev", sender: self)
    }
    
}
