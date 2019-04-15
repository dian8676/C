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
    let dateFormatter = DateFormatter()
    let numFormatter = NumberFormatter()
    
    @IBOutlet weak var totalLabel: UILabel!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.leftBarButtonItem = editButtonItem
        navigationItem.leftBarButtonItem?.tintColor = navigationItem.rightBarButtonItem?.tintColor
        
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
        totalPrice()
        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return items.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cellIdentifier = "ItemTableViewCell"
        guard let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as? ItemTableViewCell else{
            fatalError("The dequeue cell is not an instance of MealTableViewCell")
        }
        
        let item = items[indexPath.row]
        
        
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
            items.remove(at: indexPath.row)
            saveItems()
            tableView.deleteRows(at: [indexPath], with: .fade)
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

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    // MARK: Navigation
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
            
            let selectedItem = items[indexPath.row]
            itemDetailViewController.item = selectedItem
            
        default:
            fatalError("그런 Segue 없습니다요^^ : \(String(describing: segue.identifier) )")
        }
    }
    
    // MARK:Action
    @IBAction func unwindToPayList(sender: UIStoryboardSegue){
        
        if let sourceViewController = sender.source as? ItemViewController, let item = sourceViewController.item {
            
            if let selectedIndexPath = tableView.indexPathForSelectedRow{
                // Update an existing item.
                items[selectedIndexPath.row] = item
                tableView.reloadRows(at:[selectedIndexPath], with: .none)
                totalPrice()
                
            }else{
                // Add a new item.
                let newIndexPath = IndexPath(row: items.count, section: 0)
                
                items.append(item)
                tableView.insertRows(at: [newIndexPath], with: .automatic)
                totalPrice()
            }
            saveItems()
        }
    }
    
    func totalPrice(){
        let sum = items.map{$0.price}.reduce(0,+)
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
        
        guard let item1:Item = Item(name:"비모 대형 인형", price:70000, store:"CN Shop", date:date1, memo:"귀여운거 :)", photo:photo1) else {
            fatalError("item1 생성이 안돼...")
        }
        guard let item2:Item = Item(name:"Harry Potter Film Wizardry", price:45000, store:"Amazon", date:date2, memo:"ver. UK", photo:photo2) else {
            fatalError("item2 생성이 안돼...")
        }
        guard let item3:Item = Item(name:"나스 쉬어 글로우 파운데이션", price:68000, store:"Nars Cosmetic", date:date3, memo:"고비", photo:photo3) else {
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
