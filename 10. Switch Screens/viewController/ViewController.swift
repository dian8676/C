//
//  ViewController.swift
//  viewController
//
//  Created by ciepc on 15/11/2017.
//  Copyright © 2017 ciepc. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var idTextField: UITextField!
    @IBOutlet weak var nextBotton: UIButton!
    @IBOutlet weak var pwdTextField: UITextField!
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
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let secondPage = segue.destination as! SecondViewController
        secondPage.userId = idTextField.text
        
    }
}

