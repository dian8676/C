//
//  StatisticsTableViewCell.swift
//  paybook
//
//  Created by sohyeon on 2017. 12. 19..
//  Copyright © 2017년 sohyeon. All rights reserved.
//

import UIKit

class StatisticsTableViewCell: UITableViewCell {

    @IBOutlet weak var yearLabel: UILabel!
    @IBOutlet weak var sumLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
