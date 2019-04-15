//
//  IdViewController.swift
//  paybook
//
//  Created by ciepc on 20/12/2017.
//  Copyright © 2017 sohyeon. All rights reserved.
//

import UIKit
import os.log

class IdViewController : UIViewController,UITextFieldDelegate {
    
    @IBOutlet weak var idTextField: UITextField!
    @IBOutlet weak var pwdTextField: UITextField!
    @IBOutlet weak var loginButton: UIButton!
    
    var people:[Person] = []
    
    var alertLogIn:UIAlertController?
    
    
    let dateFormatter = DateFormatter()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        dateFormatter.dateFormat = "yyyy-MM-dd"
        
        if let savePeole = loadPeople(){
            people = savePeole
        }else{
            loadSamplePeople()
        }
        
        loginButton.isEnabled = false
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func alertLogin(){
        let okAction = UIAlertAction( title: "OK", style:.default, handler: nil)
        if let alert = alertLogIn{
            alert.addAction(okAction)
            present(alert, animated: true, completion: nil)
        }
    }
    
    func checkId()->Bool{
        let id = idTextField.text ?? ""
        let pwd = pwdTextField.text ?? ""
        for i in people{
            if i.id == id{
                if i.password == pwd{
                    
                    return true
                }
                alertLogIn = UIAlertController(title: "Failed", message: "패스워드가 틀립니다.", preferredStyle: .alert)
                pwdTextField.text = ""
            }
            
        }
        if alertLogIn == nil{
            alertLogIn = UIAlertController(title: "Failed", message: "해당 아이디가 없습니다.", preferredStyle: .alert)
            idTextField.text = ""
            pwdTextField.text = ""
        }
        return false
    }
    
    @IBAction func nextPage(_ sender: Any) {
        alertLogIn = nil
        if checkId(){
            self.performSegue(withIdentifier: "loginSegue", sender: self)
        }else{
            os_log("login Fail",  log: OSLog.default, type: .debug)
            dismiss(animated: true, completion: nil)
            alertLogin()
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        
        switch (segue.identifier ?? "") {
        case "loginSegue":
            os_log("login",  log: OSLog.default, type: .debug)
            //dismiss(animated: true, completion: nil)
        case "peopleSegue":
            os_log("사용자 관리",  log: OSLog.default, type: .debug)
        default:
           fatalError("segue error")
            
        }
    
    }
    
    @IBAction func unwindToLogin(sender: UIStoryboardSegue){
        os_log("login화면으로 unwind",  log: OSLog.default, type: .debug)
        
        people = loadPeople()!
    }
    
    //텍스트 필드가 비어있는 경우 저장 버튼 비활성화
    private func updateSaveButtonState() {
        // Disable the Save button if the text field is empty.
        let text = idTextField.text ?? ""
        let text2 = pwdTextField.text ?? ""
        
        loginButton.isEnabled = !text.isEmpty && !text2.isEmpty
    }
    func textFieldDidEndEditing(_ textField: UITextField) {
        //텍스트 필드에 텍스트가 있는지 확인 후 저장버튼 활성화
        updateSaveButtonState()
    }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        //키보드에 next버튼이 일하는 거
        textField.resignFirstResponder()
        
        return true
    }
    
    //MARK: Private Methods
    
    private func loadSamplePeople(){
        let photo1 = UIImage( named : "bmo" )
        
        guard let date1 = dateFormatter.date(from: "2017-12-10") else{
            fatalError("date1 변환오류")
        }
        
        guard let person1 = Person(id:"test", pwd:"0000", birth:date1, sex:"여", locx:35.987390, locy: 126.711211, photo:photo1) else{
            fatalError("person1 생성오류")
        }
        
        guard let person2 = Person(id:"test2", pwd:"0000", birth:date1, sex:"남", locx: 35.945560 , locy: 126.682218, photo:photo1) else{
            fatalError("person2 생성오류")
        }
        
        people.append(person1)
        people.append(person2)
        
        savePeople()
        
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
