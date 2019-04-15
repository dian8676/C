//
//  PeopleTableViewController.swift
//  paybook
//
//  Created by ciepc on 20/12/2017.
//  Copyright © 2017 sohyeon. All rights reserved.
//

import UIKit
import os.log

class PeopleTableViewController: UITableViewController {

    
    @IBOutlet weak var cancelButton: UIBarButtonItem!
    var people:[Person] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()

        if let savedPeople = loadPeople() {
            people += savedPeople
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

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return people.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cellIdentifier = "PeopleTableViewCell"
        guard let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as? PeopleTableViewCell else{
            fatalError("The dequeue cell is not an instance of ItemTableViewCell")
        }
        
        let person = people[indexPath.row]
        
        cell.idLabel.text = person.id
        cell.photo.image = person.photo
    
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

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        switch (segue.identifier ?? "") {
        case "newPerson":
            os_log("adding a new person", log:OSLog.default, type:.debug)
        case "detailPerson":
            guard let personViewController = segue.destination as? PersonViewController else{
                fatalError("보여줄 뷰에 문제가 생긴 거 같은데.. : \(segue.destination)")
            }
            guard let selectedPeopleCell = sender as? PeopleTableViewCell else{
                fatalError("선택된 셀 문제가 생긴 거 같은데.. : \(String(describing: sender))")
            }
            guard let indexPath = tableView.indexPath(for: selectedPeopleCell) else{
                fatalError("선택된 셀이 테이블에 없다...")
            }
            
            let selectedPerson = people[indexPath.row]
            personViewController.person = selectedPerson
        default:
            guard let button = sender as? UIBarButtonItem, button === cancelButton else {
                fatalError("Segue Error")
            }
            
        }
        
    }
    
    @IBAction func unwindToPeopleList(sender: UIStoryboardSegue){
        
        if let sourceViewController = sender.source as? PersonViewController, let person = sourceViewController.person {
            
            if let selectedIndexPath = tableView.indexPathForSelectedRow{
                // Update an existing item.
                people[selectedIndexPath.row] = person
                tableView.reloadRows(at:[selectedIndexPath], with: .none)
            }else{
                // Add a new person.
                let newIndexPath = IndexPath(row: people.count, section: 0)
                
                people.append(person)
                tableView.insertRows(at: [newIndexPath], with: .automatic)
            }
            savePeople()
        }
    }
    
    
    private func savePeople(){
        let isSuccessfulSave = NSKeyedArchiver.archiveRootObject(people, toFile: Person.ArchiveURL.path)
        if isSuccessfulSave {
            os_log("People successfully saved.", log: OSLog.default, type: .debug)
        } else {
            os_log("Failed to save People...", log: OSLog.default, type: .error)
        }
    }
    
    private func loadPeople() -> [Person]? {
        return NSKeyedUnarchiver.unarchiveObject(withFile: Person.ArchiveURL.path) as? [Person]
    }

}
