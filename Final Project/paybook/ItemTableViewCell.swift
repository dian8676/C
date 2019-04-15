//
//  ItemTableViewCell.swift
//  paybook
//
//  Created by sohyeon on 2017. 12. 9..
//  Copyright © 2017년 sohyeon. All rights reserved.
//

import UIKit

class ItemTableViewCell: UITableViewCell {

    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var priceLabel: UILabel!
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var photoImageView: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
