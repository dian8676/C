//
//  Person.swift
//  paybook
//
//  Created by ciepc on 20/12/2017.
//  Copyright © 2017 sohyeon. All rights reserved.
//

import Foundation
import UIKit
import os.log

class Person : NSObject, NSCoding{
    
    var id:String
    var password:String
    var birthday:Date
    var sex:String
    var locx:Double
    var locy:Double
    var photo:UIImage?
    
    
    //MARK: Archiving Paths
    static let DocumentsDirectory = FileManager().urls(for: .documentDirectory, in: .userDomainMask).first!
    static let ArchiveURL = DocumentsDirectory.appendingPathComponent("people")
    
    //MARK : Properties
    struct PropertyKey{
        static let id = "id"
        static let password = "password"
        static let birthday = "birthday"
        static let sex = "sex"
        static let locx = "locx"
        static let locy = "locy"
        static let photo = "photo"
    }
    
    /*override init() {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        
        self.category = ""
        self.name = ""
        self.price = 0
        self.store = ""
        self.date = dateFormatter.date(from: "2017-01-01")!
        self.memo = ""
        
    }*/
    
    init?(id:String, pwd:String, birth:Date, sex:String, locx:Double, locy:Double, photo:UIImage?) {
        self.id = id
        self.password = pwd
        self.birthday = birth
        self.sex = sex
        self.locx = locx
        self.locy = locy
        self.photo = photo
    }
    
    
    //MARK: NSCoding
    func encode(with aCoder: NSCoder) {
        aCoder.encode(id, forKey:PropertyKey.id)
        aCoder.encode(password, forKey:PropertyKey.password)
        aCoder.encode(birthday, forKey:PropertyKey.birthday)
        aCoder.encode(sex, forKey:PropertyKey.sex)
        aCoder.encode(locx, forKey:PropertyKey.locx)
        aCoder.encode(locy, forKey:PropertyKey.locy)
        aCoder.encode(photo, forKey:PropertyKey.photo)
    }
    
    required convenience init?(coder aDecoder: NSCoder) {
        
        
        guard let id = aDecoder.decodeObject(forKey: PropertyKey.id) as? String else{
            os_log("Unable to decode the id for a Person object", log:OSLog.default, type:.debug)
            return nil
        }
        guard let password = aDecoder.decodeObject(forKey: PropertyKey.password) as? String else{
            os_log("Unable to decode the passwd for a Person object", log:OSLog.default, type:.debug)
            return nil
        }
        guard let birthday = aDecoder.decodeObject(forKey: PropertyKey.birthday) as? Date else{
            os_log("Unable to decode the date for a Item object", log:OSLog.default, type:.debug)
            return nil
        }
        guard let sex = aDecoder.decodeObject(forKey: PropertyKey.sex) as? String else{
            os_log("Unable to decode the sex for a Person object", log:OSLog.default, type:.debug)
            return nil
        }
        
        let locx = aDecoder.decodeDouble(forKey: PropertyKey.locx)
        let locy = aDecoder.decodeDouble(forKey: PropertyKey.locy)
        let photo = aDecoder.decodeObject(forKey: PropertyKey.photo) as? UIImage
        
        self.init(id:id, pwd:password, birth:birthday, sex:sex, locx:locx, locy:locy, photo:photo)
    }
    
    
    
}
