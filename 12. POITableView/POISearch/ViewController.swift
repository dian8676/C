//
//  ViewController.swift
//  POISearch
//
//  Created by ciepc on 28/11/2017.
//  Copyright © 2017 ciepc. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    @IBAction func nextPage(_ sender: Any) {
        self.performSegue(withIdentifier: "segueNext", sender: self)
    }
    
}

