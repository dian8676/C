//
//  SearchDetailViewController.swift
//  paybook
//
//  Created by sohyeon on 2017. 12. 18..
//  Copyright © 2017년 sohyeon. All rights reserved.
//

import UIKit
import os.log

class SearchDetailViewController: UIViewController{
    
    @IBOutlet weak var photoImageView: UIImageView!
    @IBOutlet weak var categoryLabel: UILabel!
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var priceLabel: UILabel!
    @IBOutlet weak var storeLabel: UILabel!
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var memoLabel: UILabel!
    
    let dateFormatter = DateFormatter()
    
    var item:Item?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        
        if let item = item {
            navigationItem.title = item.name
            
            categoryLabel.text = item.category
            photoImageView.image = item.photo
            nameLabel.text = item.name
            priceLabel.text = String(item.price)
            storeLabel.text = item.store
            dateLabel.text = dateFormatter.string(from: item.date)
            memoLabel.text = item.memo
        }
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
}
