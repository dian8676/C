//
//  POI.swift
//  POISearch
//
//  Created by yeji on 28/11/2017.
//  Copyright © 2017 ciepc. All rights reserved.
//

import Foundation
import UIKit

class POI{
    var name : String
    var addr : String
    var fee : Int
    var locx : Double
    var locy : Double
    var photo : UIImage?
    
    init?(name: String, addr: String, fee : Int, locx: Double, locy : Double, photo: UIImage?) {
        
        if name.isEmpty || fee < 0 {
            return nil
        } else { self.name = name }
        self.addr = addr
        self.fee = fee
        self.locx = locx
        self.locy = locy
        self.photo = photo
    }
}
