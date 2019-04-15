//
//  SearchTableViewController.swift
//  paybook
//
//  Created by sohyeon on 2017. 12. 18..
//  Copyright © 2017년 sohyeon. All rights reserved.
//

import UIKit
import os.log

class SearchTableViewController: UITableViewController, UISearchBarDelegate {
    
    @IBOutlet var searchTableView: UITableView!
    @IBOutlet weak var searchBar: UISearchBar!
    
    var items = [Item]()
    let numFormatter = NumberFormatter()
    let dateFormatter = DateFormatter()
    
    var filtedArray : [Item]!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let swipeRight = UISwipeGestureRecognizer(target: self, action: #selector(self.respondToSwipeGesture))
        swipeRight.direction = UISwipeGestureRecognizerDirection.right
        self.view.addGestureRecognizer(swipeRight)
        
        numFormatter.numberStyle = .decimal
        dateFormatter.dateFormat = "yyyy-MM-dd"
        
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
        
        setUpSearchBar()
        
        
        self.filtedArray = self.items
        
        alterLayout()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
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
        return filtedArray.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cellIdentifier = "SearchTableViewCell"
        guard let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as? SearchTableViewCell else{
            fatalError("The dequeue cell is not an instance of SearchTableViewCell")
        }
        
        let item = filtedArray[indexPath.row]
        
        cell.nameLabel.text = item.name
        cell.priceLabel.text = numFormatter.string(from: item.price as NSNumber)!+"원"
        cell.photoImageView.image = item.photo

        // Configure the cell...

        return cell
    }
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 100
    }

    //search Bar 고정
    //override func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
    //    return searchBar
    //}
    
    //override func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
    //    return UITableViewAutomaticDimension
    //}
    
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

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        switch (segue.identifier ?? "") {
        case "searchDetail":
            guard let itemDetailViewController = segue.destination as? SearchDetailViewController else{
                fatalError("보여줄 뷰에 문제가 생긴 거 같은데.. : \(segue.destination)")
            }
            guard let selectedItemCell = sender as? SearchTableViewCell else{
                fatalError("선택된 셀 문제가 생긴 거 같은데.. : \(String(describing: sender))")
            }
            guard let indexPath = tableView.indexPath(for: selectedItemCell) else{
                fatalError("선택된 셀이 테이블에 없다...")
            }
            
            let selectedItem = filtedArray[indexPath.row]
            itemDetailViewController.item = selectedItem
        default:
            fatalError("그런 Segue 없습니다요^^ : \(String(describing: segue.identifier) )")
        }
    }
 
    
    func alterLayout(){
        searchBar.scopeButtonTitles = ["All"] + Item.categories
        searchTableView.tableHeaderView = UIView() //항상 위에 존재하게 하려고
        searchTableView.estimatedSectionHeaderHeight = 50  // selection도
        navigationItem.titleView = searchBar
        
    }
    
    
    // searchbar
    
    func searchBar(_ searchBar: UISearchBar, textDidChange searchText:String) {
        filtedArray = items.filter({ item -> Bool in
            if searchBar.selectedScopeButtonIndex == 0{
                if searchText.isEmpty { return true }
                return item.name.lowercased().contains(searchText.lowercased())
            }else{
                for i in 0..<Item.categories.count{
                    if searchBar.selectedScopeButtonIndex == i+1 {
                        if searchText.isEmpty { return item.category == Item.categories[i] }
                        return ( item.name.lowercased().contains(searchText.lowercased()) && (item.category == Item.categories[i]) )
                    }
                }
            }
            return false
        })
        
        searchTableView.reloadData()
    }
    
    func searchBar(_ searchBar: UISearchBar, selectedScopeButtonIndexDidChange selectedScope: Int) {
        if selectedScope == 0 {
            filtedArray = items
        }else {
            for i in 0..<Item.categories.count{
                if selectedScope == i+1 {
                    filtedArray = items.filter({ item -> Bool in
                        item.category == Item.categories[i]
                    })
                }
            }
        }
        searchTableView.reloadData()
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

    
    
    //MARK: Private Methods
    
    private func setUpSearchBar(){
        searchBar.delegate = self
    }
    
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
        os_log("load Samples.", log: OSLog.default, type: .debug)
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
