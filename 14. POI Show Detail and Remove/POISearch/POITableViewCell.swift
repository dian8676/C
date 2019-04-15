//
//  POITableViewCell.swift
//  POISearch
//
//  Created by yeji on 28/11/2017.
//  Copyright © 2017 ciepc. All rights reserved.
//

import UIKit

class POITableViewCell: UITableViewCell {

    @IBOutlet weak var nameCellLabel: UILabel!
    @IBOutlet weak var addrCellLabel: UILabel!
    @IBOutlet weak var feeCellLabel: UILabel!
    @IBOutlet weak var photoImg: UIImageView!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
