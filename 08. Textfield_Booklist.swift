//

//  ViewController.swift

//  singleView

//

//  Created by CA_PC on 15/11/2017.

//  Copyright ⓒ 2017 CA_PC. All rights reserved.

//

 

import UIKit

 

class ViewController: UIViewController,UITextFieldDelegate {

 

    

    @IBOutlet weak var searchLabel: UILabel!

    @IBOutlet weak var nameTextField: UITextField!

    @IBOutlet weak var defaultButton: UIButton!

    @IBOutlet weak var bookListLable: UILabel!

    @IBOutlet weak var bookCountLabel: UILabel!

    

    var bookList:[String] = []

    

    

    override func viewDidLoad() {

        super.viewDidLoad()

        

        nameTextField.delegate = self

        // Do any additional setup after loading the view, typically from a nib.

    }

 

    //메모리 경고 받으면 할 일

    override func didReceiveMemoryWarning() {

        super.didReceiveMemoryWarning()

        // Dispose of any resources that can be recreated.

    }

    

    func textFieldShouldReturn(_ textField: UITextField) -> Bool {

        nameTextField.resignFirstResponder()

        returntrue

    }

 

    @IBAction func endEditingTextField(_ sender: Any) {

        searchLabel.text = nameTextField.text

    }

    @IBAction func clickDefaultBook(_ sender: Any) {

        bookList.append(nameTextField.text!)

        bookCountLabel.text = "책 \\(bookList.count)개"

        var text:String = ""

        for i in bookList{

            text.append("\\(i)\\n")

        }

        bookListLable.text = text

    }

    

}