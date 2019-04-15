//
//  Item.swift
//  paybook
//
//  Created by sohyeon on 2017. 12. 9..
//  Copyright © 2017년 sohyeon. All rights reserved.
//

import Foundation
import UIKit
import os.log

class Item : NSObject, NSCoding{

    static var categories:[String] = []
    var category:String
    var name:String
    var price:Int
    var store:String
    var date:Date
    var memo:String?
    var photo:UIImage?
    
    //MARK: Archiving Paths
    static let DocumentsDirectory = FileManager().urls(for: .documentDirectory, in: .userDomainMask).first!
    static let ArchiveURL = DocumentsDirectory.appendingPathComponent("items")
    
    //MARK : Properties
    struct PropertyKey{
        static let categories = "categories"
        static let category = "category"
        static let name = "name"
        static let price = "price"
        static let store = "store"
        static let date = "date"
        static let memo = "memo"
        static let photo = "photo"
    }

    override init() {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        
        self.category = ""
        self.name = ""
        self.price = 0
        self.store = ""
        self.date = dateFormatter.date(from: "2017-01-01")!
        self.memo = ""
        
    }
    
    init?(category:String, name:String, price:Int, store:String, date:Date, memo:String?, photo:UIImage?) {
        if name.isEmpty || store.isEmpty {
            return nil
        }
        self.category = category
        self.name = name
        self.price = price
        self.store = store
        self.date = date
        self.memo = memo
        self.photo = photo
        
    }
   
    
    //MARK: NSCoding
    func encode(with aCoder: NSCoder) {
        aCoder.encode(Item.categories, forKey:PropertyKey.categories)
        aCoder.encode(category, forKey:PropertyKey.category)
        aCoder.encode(name, forKey:PropertyKey.name)
        aCoder.encode(price, forKey:PropertyKey.price)
        aCoder.encode(store, forKey:PropertyKey.store)
        aCoder.encode(date, forKey:PropertyKey.date)
        aCoder.encode(memo, forKey:PropertyKey.memo)
        aCoder.encode(photo, forKey:PropertyKey.photo)
    }
    
    required convenience init?(coder aDecoder: NSCoder) {
        
        guard let categories = aDecoder.decodeObject(forKey: PropertyKey.categories) as? [String] else{
            os_log("Unable to decode the categories for a Item objects", log:OSLog.default, type:.debug)
            return nil
        }
        
        guard let category = aDecoder.decodeObject(forKey: PropertyKey.category) as? String else{
            os_log("Unable to decode the category for a Item object", log:OSLog.default, type:.debug)
            return nil
        }
        guard let name = aDecoder.decodeObject(forKey: PropertyKey.name) as? String else{
            os_log("Unable to decode the name for a Item object", log:OSLog.default, type:.debug)
            return nil
        }
        guard let store = aDecoder.decodeObject(forKey: PropertyKey.store) as? String else{
            os_log("Unable to decode the store for a Item object", log:OSLog.default, type:.debug)
            return nil
        }
        guard let date = aDecoder.decodeObject(forKey: PropertyKey.date) as? Date else{
            os_log("Unable to decode the date for a Item object", log:OSLog.default, type:.debug)
            return nil
        }
        let price = aDecoder.decodeInteger(forKey: PropertyKey.price)
        let memo = aDecoder.decodeObject(forKey: PropertyKey.memo) as? String
        let photo = aDecoder.decodeObject(forKey: PropertyKey.photo) as? UIImage
        
        self.init(category:category, name:name, price:price, store:store, date:date, memo:memo, photo:photo)
        Item.categories = categories
    }
    
    
    
}




