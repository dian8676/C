//
//  SettingViewController.swift
//  paybook
//
//  Created by sohyeon on 2017. 12. 18..
//  Copyright © 2017년 sohyeon. All rights reserved.
//

import UIKit
import os.log

class SettingViewController : UIViewController, UITextFieldDelegate, UITableViewDataSource{
    
    
    
    @IBOutlet weak var categoriesTableView: UITableView!
    @IBOutlet weak var editCategoryTextField: UITextField!
    @IBOutlet weak var editButton: UIButton!
    @IBOutlet weak var editSegmentedControl: UISegmentedControl!
    @IBOutlet weak var editLabel: UILabel!
    
    var items:[Item] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let swipeRight = UISwipeGestureRecognizer(target: self, action: #selector(self.respondToSwipeGesture))
        swipeRight.direction = UISwipeGestureRecognizerDirection.right
        self.view.addGestureRecognizer(swipeRight)
        
        editButton.isEnabled = false
        editButton.tintColor = .gray
        
        if let savedItems = loadItems() {
            items += savedItems
        }
        
        updateSaveButtonState()
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    //TABLE VIEW
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return Item.categories.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cellIdentifier = "CategoryTableViewCell"
        guard let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as? CategoryTableViewCell else{
            fatalError("The dequeue cell is not an instance of CategoryTableViewCell")
        }
        
        let item = Item.categories[indexPath.row]
        
        cell.categoryTextLabel.text = item
        
        // Configure the cell...
        
        return cell
    }
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    // CATEGORY EDIT
    @IBAction func editCategory(_ sender: Any) {
        if let text = editCategoryTextField.text{
            var alert:UIAlertController
            let okAction = UIAlertAction( title: "OK", style:.default, handler: nil)
            
            if editSegmentedControl.selectedSegmentIndex == 0{
                // 존재하지 않아야 추가 가능
                if existence(text: text) == -1 {
                    Item.categories.append(text)
                    os_log("insert new catagory", log:OSLog.default, type: .debug)
                    alert = UIAlertController(title: "Success", message: "The category added successfully", preferredStyle: .alert)
                    
                    saveItems()
                    categoriesTableView.reloadData()
                }else {
                    alert = UIAlertController(title: "Failed", message: "Failed to add category. '\(text)' exists", preferredStyle: .alert)
                }
                
            }else if editSegmentedControl.selectedSegmentIndex == 1{
                // 존재해야 삭제 가능
                if existence(text: text) != -1 {
                    Item.categories.remove(at: existence(text: text))
                    os_log("delete catagory", log:OSLog.default, type: .debug)
                    alert = UIAlertController(title: "Success", message: "The category deleted successfully", preferredStyle: .alert)
                    saveItems()
                    categoriesTableView.reloadData()
                }else{
                alert = UIAlertController(title: "Failed", message: "Failed to delete category. '\(text)' does not exist", preferredStyle: .alert)
                }
                
            }else{
                alert = UIAlertController(title: "Error", message: "TextField Error", preferredStyle: .alert)
            }
            alert.addAction(okAction)
            present(alert, animated: true, completion: nil)
            editCategoryTextField.text = ""
            
        }
        
    }
    
    func existence(text:String)->Int{
        for i in 0..<Item.categories.count{
            if text == Item.categories[i]{
                return i        // 존재함
            }
        }
        return -1               // 존재하지 않음
    }
    
    @IBAction func indexChanged(_ sender: UISegmentedControl) {
        switch editSegmentedControl.selectedSegmentIndex {
        case 0:
            editLabel.text = "Add Category"
            editCategoryTextField.placeholder = "추가할 카테고리를 입력하세요."
        case 1:
            editLabel.text = "Delete Category"
            editCategoryTextField.placeholder = "삭제할 카테고리를 입력하세요."
        default:
            fatalError("segment control 뭔 일?")
        }
    }
    
    private func updateSaveButtonState(){
        let text = editCategoryTextField.text ?? ""
        
        if !text.isEmpty {
            editButton.isEnabled = !(text.isEmpty)
            editButton.tintColor = .blue
        }
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        updateSaveButtonState()
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    @objc func respondToSwipeGesture(gesture:Any){
        if let swipeGesture = gesture as? UISwipeGestureRecognizer{
            switch swipeGesture.direction {
            case UISwipeGestureRecognizerDirection.right:
                //print("Swiped right")
                let transition:CATransition = CATransition()
                transition.duration = 0.5
                transition.timingFunction = CAMediaTimingFunction(name: kCAMediaTimingFunctionEaseOut)
                transition.type = kCATransitionReveal
                transition.subtype = kCATransitionFromLeft
                view.window!.layer.add(transition, forKey: kCATransition)
                dismiss(animated: false, completion: nil)
            case UISwipeGestureRecognizerDirection.left:
                os_log("Swiped left", log: OSLog.default, type: .debug)
            case UISwipeGestureRecognizerDirection.up:
                os_log("Swiped up", log: OSLog.default, type: .debug)
            case UISwipeGestureRecognizerDirection.down:
                os_log("Swiped down", log: OSLog.default, type: .debug)
            default:
                os_log("swiped what..?", log: OSLog.default, type: .debug)
            }
        }
    }
    private func saveItems(){
        let isSuccessfulSave = NSKeyedArchiver.archiveRootObject(items, toFile: Item.ArchiveURL.path)
        if isSuccessfulSave {
            os_log("Items successfully saved.", log: OSLog.default, type: .debug)
        } else {
            os_log("Failed to save items...", log: OSLog.default, type: .error)
        }
    }
    
    private func loadItems() -> [Item]? {
        return NSKeyedUnarchiver.unarchiveObject(withFile: Item.ArchiveURL.path) as? [Item]
    }
}
