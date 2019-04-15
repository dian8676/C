//
//  PersonViewController.swift
//  paybook
//
//  Created by ciepc on 20/12/2017.
//  Copyright © 2017 sohyeon. All rights reserved.
//

import UIKit
import os.log

class PersonViewController : UIViewController, UITextFieldDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    
    
    @IBOutlet weak var idTextField: UITextField!
    @IBOutlet weak var pwdTextField: UITextField!
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var sexPicker: UISegmentedControl!
    @IBOutlet weak var locxTextField: UITextField!
    @IBOutlet weak var locyTextField: UITextField!
    @IBOutlet weak var photoImageView: UIImageView!
    @IBOutlet weak var saveButton: UIBarButtonItem!
    
    var person:Person?
    var sex:String = "남"
    let dateFormatter = DateFormatter()

    
    override func viewDidLoad() {
        super.viewDidLoad()
        dateFormatter.dateFormat = "yyyy-MM-dd"
        
        if let person = person {
           
            var sexIndex = 0
            if person.sex == "여"{
                sexIndex = 1
            }
            
            navigationItem.title = person.id
            
            idTextField.text = person.id
            pwdTextField.text = person.password
            datePicker.date = person.birthday
            sexPicker.selectedSegmentIndex = sexIndex
            locxTextField.text = String(person.locx)
            locyTextField.text = String(person.locy)
            photoImageView.image = person.photo
        }else{
            saveButton.isEnabled = false
        }
        
        updateSaveButtonState()
        
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    //사진
    @IBAction func selectImageFromPhotoLibrary(_ sender:Any){
        // 키보드 버튼 숨기기
        let _ = textFieldShouldReturn(locyTextField)
        
        // PhotoLibrary로 부터 사진을 가져오기 위해 UIImagePicker생성
        let imagePickerController = UIImagePickerController()
        
        // 사진선택하기만 가능. 찍기는 안됨
        imagePickerController.sourceType = .photoLibrary
        
        //사용자가 사진을 선택하면 알려줌
        imagePickerController.delegate = self
        present(imagePickerController, animated: true, completion: nil)
    }
    //MARK: UIImagePickerControllerDelegate
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        dismiss(animated: true, completion: nil)
    }
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        guard let selectedImage = info[UIImagePickerControllerOriginalImage] as? UIImage else {
            fatalError("Expected a dictionary containing an image, but was provided the following: \(info)")
        }
        photoImageView.image = selectedImage
        dismiss(animated: true, completion: nil)
    }
    
    //MARK: Navigation
    @IBAction func cancel(_ sender: UIBarButtonItem) {
        let isPresentingInAddItemMode = presentingViewController is UINavigationController
        
        if isPresentingInAddItemMode{
            dismiss(animated: true, completion: nil)
        }else if let owningNavigationController = navigationController{
            owningNavigationController.popViewController(animated: true)
        }else{
            fatalError("??")
        }
    }
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        
        if segue.identifier == "mapSegue"{
            guard let mapViewController = segue.destination as? MapViewController else {
                fatalError("Unexpected destination: \(segue.destination)")
            }
            
            let personData = person
            mapViewController.person = personData
        }else{
            
            guard let button = sender as? UIBarButtonItem, button === saveButton else {
                os_log("The save button was not pressed, cancelling", log:OSLog.default, type: .debug)
                return
            }
            
            guard let id = idTextField.text else{
                let alert = UIAlertController(title:"Failed",message:"id 값이 없습니다.",preferredStyle:.alert)
                let okButton = UIAlertAction(title:"OK", style: .default, handler: nil)
                alert.addAction(okButton)
                self.present(alert, animated: true, completion: nil)
                return
            }
            guard let password = pwdTextField.text else{
                let alert = UIAlertController(title:"Failed",message:"password 값이 없습니다.",preferredStyle:.alert)
                let okButton = UIAlertAction(title:"OK", style: .default, handler: nil)
                alert.addAction(okButton)
                self.present(alert, animated: true, completion: nil)
                return
            }
            
            let birth = datePicker.date
            
            guard let locx = Double(locxTextField.text!) else{
                let alert = UIAlertController(title:"Failed",message:"위도 숫자가 아닙니다.",preferredStyle:.alert)
                let okButton = UIAlertAction(title:"OK", style: .default, handler: nil)
                alert.addAction(okButton)
                self.present(alert, animated: true, completion: nil)
                return
            }
            guard let locy = Double(locyTextField.text!) else{
                let alert = UIAlertController(title:"Failed",message:"경도 숫자가 아닙니다.",preferredStyle:.alert)
                let okButton = UIAlertAction(title:"OK", style: .default, handler: nil)
                alert.addAction(okButton)
                self.present(alert, animated: true, completion: nil)
                return
            }
            let photo = photoImageView.image
            
            person = Person(id:id, pwd:password, birth:birth, sex:sex, locx:locx, locy:locy, photo:photo)
        }
    }
    
    //MARK: segmentControl
    @IBAction func indexChanged(_ sender: UISegmentedControl) {
        switch sexPicker.selectedSegmentIndex {
        case 0:
            sex = "남"
        case 1:
            sex = "여"
        default:
            fatalError("segment control 뭔 일?")
        }
    }
    //MARK : TextField
    
    private func updateSaveButtonState(){
        let idText = idTextField.text ?? ""
        let pwdText = pwdTextField.text ?? ""
        let locxText = locxTextField.text ?? ""
        let locyText = locyTextField.text ?? ""
        
        if !(idText.isEmpty) && !(pwdText.isEmpty) && !(locxText.isEmpty) && !(locyText.isEmpty){
            saveButton.isEnabled = true
        }
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        updateSaveButtonState()
        navigationItem.title = idTextField.text
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        //키보드에 next버튼이 일하는 거
        switch textField{
        case idTextField :
            pwdTextField.becomeFirstResponder()
        case pwdTextField :
            locxTextField.becomeFirstResponder()
        case locxTextField :
            locyTextField.becomeFirstResponder()
        default:
            textField.resignFirstResponder()
        }
        return true
    }
    
  
    
}
