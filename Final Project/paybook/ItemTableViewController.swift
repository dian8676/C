//
//  ItemTableViewController.swift
//  paybook
//
//  Created by sohyeon on 2017. 12. 9..
//  Copyright © 2017년 sohyeon. All rights reserved.
//

import UIKit
import os.log

class ItemTableViewController: UITableViewController {

    var items = [Item]()
    var filteredItems:[Item] = []
    var year:String = ""
    let dateFormatter = DateFormatter()
    let numFormatter = NumberFormatter()
    
    @IBOutlet weak var totalLabel: UILabel!
    @IBOutlet weak var backButton: UIBarButtonItem!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let swipeRight = UISwipeGestureRecognizer(target: self, action: #selector(self.respondToSwipeGesture))
        swipeRight.direction = UISwipeGestureRecognizerDirection.right
        self.view.addGestureRecognizer(swipeRight)
        
        
        
        dateFormatter.dateFormat = "yyyy-MM-dd"
        numFormatter.numberStyle = .decimal
        
        // Load any saved meals, otherwise load sample data.
        if let savedItems = loadItems() {
            items += savedItems
        }
        else {
            // Load the sample data.
            loadSampleItems()
        }
        if Item.categories.count == 0 {
            Item.categories = ["옷","음식","책","인형","기타"]
        }
        
        if year.isEmpty{
            filteredItems = items
            navigationItem.leftBarButtonItem = editButtonItem
            navigationItem.leftBarButtonItem?.tintColor = navigationItem.rightBarButtonItem?.tintColor
        }else{
            arrayFilter()
            navigationItem.rightBarButtonItem = editButtonItem
            navigationItem.rightBarButtonItem?.tintColor = navigationItem.leftBarButtonItem?.tintColor
        }
        totalPrice()
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func arrayFilter(){
        filteredItems = items.filter({(s1:Item) -> Bool in
            let dateFormatter2 = DateFormatter()
            dateFormatter2.dateFormat = "yyyy"
            return year == dateFormatter2.string(from: s1.date)
        })
    }
    

    // MARK: - Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return filteredItems.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cellIdentifier = "ItemTableViewCell"
        guard let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as? ItemTableViewCell else{
            fatalError("The dequeue cell is not an instance of ItemTableViewCell")
        }
        
        let item = filteredItems[indexPath.row]
        
        cell.nameLabel.text = item.name
        cell.priceLabel.text = numFormatter.string(from: item.price as NSNumber)!+"원"
        cell.dateLabel.text = dateFormatter.string(from: item.date)
        cell.photoImageView.image = item.photo
        // Configure the cell...

        return cell
    }
    

    
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    

    
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            let modIndex = items.index(of: filteredItems[indexPath.row])
            items.remove(at: modIndex!)
            filteredItems.remove(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .fade)
            saveItems()
            totalPrice()
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    
   
    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */


    //MARK: Navigation
    @IBAction func back(_ sender: UIBarButtonItem) {
        let isPresentingInAddItemMode = presentingViewController is UINavigationController
        
        if isPresentingInAddItemMode{
            dismiss(animated: true, completion: nil)
        }else if let owningNavigationController = navigationController{
            owningNavigationController.popViewController(animated: true)
        }else{
            fatalError("navigation controller안에 있지 않아..")
        }
    }
 
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        switch (segue.identifier ?? "") {
        case "addItem":
            os_log("adding a new item", log:OSLog.default, type:.debug)
        case "detailItem":
            guard let itemDetailViewController = segue.destination as? ItemViewController else{
                fatalError("보여줄 뷰에 문제가 생긴 거 같은데.. : \(segue.destination)")
            }
            guard let selectedItemCell = sender as? ItemTableViewCell else{
                fatalError("선택된 셀 문제가 생긴 거 같은데.. : \(String(describing: sender))")
            }
            guard let indexPath = tableView.indexPath(for: selectedItemCell) else{
                fatalError("선택된 셀이 테이블에 없다...")
            }
            
            let selectedItem = filteredItems[indexPath.row]
            itemDetailViewController.item = selectedItem
        case "yearList":
            os_log("statistics", log:OSLog.default, type:.debug)
        default:
            guard let button = sender as? UIBarButtonItem, button === backButton else {
                fatalError("??")
            }
        }
        
    }
    
    // MARK:Action
    @IBAction func unwindToPayList(sender: UIStoryboardSegue){
        
        if let sourceViewController = sender.source as? ItemViewController, let item = sourceViewController.item {
            
            if let selectedIndexPath = tableView.indexPathForSelectedRow{
                // Update an existing item.
                guard let modIndex = items.index(of: filteredItems[selectedIndexPath.row]) else{
                    fatalError("items 아이템 위치 못찾음")
                }
                items[modIndex] = item
                if year.isEmpty{
                    filteredItems = items
                }else{
                    arrayFilter()
                }
                tableView.reloadRows(at:[selectedIndexPath], with: .none)
                //arrayFilter()
                totalPrice()
            }else{
                // Add a new item.
                let newIndexPath = IndexPath(row: items.count, section: 0)
                
                items.append(item)
                filteredItems=items
                tableView.insertRows(at: [newIndexPath], with: .automatic)
                totalPrice()
            }
            saveItems()
        }
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
    
    func totalPrice(){
        let sum = filteredItems.map{$0.price}.reduce(0,+)
        totalLabel.text = "\(numFormatter.string(from: sum as NSNumber)!)원"
    }
    
    //MARK: Private Methods
    
    private func loadSampleItems(){
        let photo1 = UIImage( named : "bmo" )
        let photo2 = UIImage( named : "harrypotter" )
        let photo3 = UIImage( named : "nars" )
        
        guard let date1 = dateFormatter.date(from: "2017-12-10") else{
            fatalError("date1 변환오류")
        }
        guard let date2 = dateFormatter.date(from: "2017-12-11") else{
            fatalError("date2 변환오류")
        }
        guard let date3 = dateFormatter.date(from: "2017-12-12") else{
            fatalError("date3 변환오류")
        }
        
        guard let item1:Item = Item(category:"인형", name:"비모 대형 인형", price:70000, store:"CN Shop", date:date1, memo:"귀여운거 :)", photo:photo1) else {
            fatalError("item1 생성이 안돼...")
        }
        guard let item2:Item = Item(category:"책",name:"Harry Potter Film Wizardry", price:45000, store:"Amazon", date:date2, memo:"ver. UK", photo:photo2) else {
            fatalError("item2 생성이 안돼...")
        }
        guard let item3:Item = Item(category:"기타",name:"나스 쉬어 글로우 파운데이션", price:68000, store:"Nars Cosmetic", date:date3, memo:"고비", photo:photo3) else {
            fatalError("item3 생성이 안돼...")
        }
        
        items.append(item1)
        items.append(item2)
        items.append(item3)
        
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
