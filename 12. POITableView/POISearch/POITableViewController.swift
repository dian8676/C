//
//  POITableViewController.swift
//  POISearch
//
//  Created by yeji on 28/11/2017.
//  Copyright © 2017 ciepc. All rights reserved.
//

import UIKit

class POITableViewController: UITableViewController {

    var pois = [POI]()
    let cellIdentifier = "POITableViewCell"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        loadSamplePOIs();
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    @IBAction func nextPage(_ sender: Any) {
        self.performSegue(withIdentifier: "segueNext", sender: self)
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
        return pois.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        guard let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as? POITableViewCell
            else { fatalError("The dequeued cell is not an instance of POITableViewCell.") }
        let poi = pois[indexPath.row]
        cell.nameCellLabel.text = poi.name
        cell.addrCellLabel.text = poi.addr
        cell.feeCellLabel.text = "요금 : \(poi.fee)"
        cell.photoImg.image = poi.photo

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
    private func loadSamplePOIs() {
        let photo1 = UIImage(named: "poi1")
        let photo2 = UIImage(named: "poi2")
        let photo3 = UIImage(named: "poi3")
        
        guard let poi1 = POI(name: "이성당", addr: "전북 군산시 중앙로 177",
                             fee: 300, locx:35.987390, locy: 126.711211, photo: photo1)
            else { fatalError("Unable to instantiate poi1") }
        guard let poi2 = POI(name: "복성루", addr: "전북 군산시 중앙로 177",
                             fee: 500, locx: 35.978415 , locy: 126.715812, photo: photo2)
            else { fatalError("Unable to instantiate poi2") }
        guard let poi3 = POI(name: "군산대학교", addr: "전북 군산시 대학로 558", fee: 500, locx: 35.945560 , locy: 126.682218 , photo: photo3)
            else { fatalError("Unable to instantiate poi3") }
        pois += [poi1, poi2, poi3]
        
    }

}
