//
//  StatisticsTableViewController.swift
//  paybook
//
//  Created by sohyeon on 2017. 12. 19..
//  Copyright © 2017년 sohyeon. All rights reserved.
//

import UIKit
import os.log

class StatisticsTableViewController: UITableViewController {

    var statsList:[Stats] = []
    var items:[Item] = []
    
    let dateFormatter = DateFormatter()
    let numFormatter = NumberFormatter()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        dateFormatter.dateFormat = "yyyy"
        numFormatter.numberStyle = .decimal
        
        let swipeRight = UISwipeGestureRecognizer(target: self, action: #selector(self.respondToSwipeGesture))
        swipeRight.direction = UISwipeGestureRecognizerDirection.right
        self.view.addGestureRecognizer(swipeRight)
        
        if let savedItems = loadItems() {
            items += savedItems
            compileStatistics()
        }

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func compileStatistics(){
        var tmpYear = 0
        var sum = 0
        for i in items.sorted(by: {$0.date < $1.date}){
            guard let tmp = Int(dateFormatter.string(from: i.date)) else{
                fatalError("date 변환이 안돼")
            }
            if tmpYear != tmp {
                if tmpYear != 0 {
                    statsList.append(Stats(year:tmpYear, sum:sum))
                    sum = 0
                }
                tmpYear = tmp
            }
            if tmpYear == tmp{
                sum += i.price
            }
        }
        if tmpYear != 0 {
        statsList.append(Stats(year:tmpYear, sum:sum))
        }
        
        os_log("statsList make successfully.", log: OSLog.default, type: .debug)
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
    
    
    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return statsList.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cellIdentifier = "StatisticsTableViewCell"
        guard let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as? StatisticsTableViewCell else{
            fatalError("The dequeue cell is not an instance of StatisticsTableViewCell")
        }
        
        let stats = statsList[indexPath.row]
        
        cell.yearLabel.text = String(stats.year) + "년"
        cell.sumLabel.text = numFormatter.string(from: stats.sum as NSNumber)! + "원"

        return cell
    }
    

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

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


    // MARK: - Navigation
  
    @IBAction func unwindToStatisticsList(sender: UIStoryboardSegue){
        
        if sender.source is ItemTableViewController {
            
            if let selectedIndexPath = tableView.indexPathForSelectedRow{
                // Update an existing item.
                if let savedItems = loadItems() {
                    print(selectedIndexPath.row)
                    items = savedItems
                    statsList = []
                    compileStatistics()
                }
                tableView.reloadRows(at:[selectedIndexPath], with: .none)
                
            }
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        switch (segue.identifier ?? "") {
        case "yearList":
            guard let itemTableViewController = segue.destination as? ItemTableViewController else{
                fatalError("보여줄 뷰에 문제가 생긴 거 같은데.. : \(segue.destination)")
            }
            guard let selectedItemCell = sender as? StatisticsTableViewCell else{
                fatalError("선택된 셀 문제가 생긴 거 같은데.. : \(String(describing: sender))")
            }
            guard let indexPath = tableView.indexPath(for: selectedItemCell) else{
                fatalError("선택된 셀이 테이블에 없다...")
            }
            
            let selectedItem = statsList[indexPath.row]
            itemTableViewController.year = String(selectedItem.year)
        default:
            fatalError("그런 Segue 없습니다요^^ : \(String(describing: segue.identifier) )")
        }
    }
    
    class Stats{
        var year:Int
        var sum:Int
        
        init(year:Int,sum:Int){
            self.year = year
            self.sum = sum
        }
    }
    
    private func loadItems() -> [Item]? {
        return NSKeyedUnarchiver.unarchiveObject(withFile: Item.ArchiveURL.path) as? [Item]
    }

}
