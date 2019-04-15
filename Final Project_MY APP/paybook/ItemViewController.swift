//
//  ViewController.swift
//  paybook
//
//  Created by sohyeon on 2017. 12. 9..
//  Copyright © 2017년 sohyeon. All rights reserved.
//

import UIKit
import os.log

class ItemViewController: UIViewController, UITextFieldDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate, UIPickerViewDelegate, UIPickerViewDataSource {
    

    @IBOutlet weak var photoImageView: UIImageView!
    @IBOutlet weak var nameTextField: UITextField!
    @IBOutlet weak var priceTextField: UITextField!
    @IBOutlet weak var storeTextField: UITextField!
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var memoTextField: UITextField!
    @IBOutlet weak var saveButton: UIBarButtonItem!
    @IBOutlet weak var categoryPickerTextField: UITextField!
    
    let dateFormatter = DateFormatter()
    
    var item:Item?
    
    var pickOption:[String] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        dateFormatter.dateFormat = "yyyy-MM-dd"
        pickOption = Item.categories
        
        if let item = item {
            navigationItem.title = item.name
            
            categoryPickerTextField.text = item.category
            photoImageView.image = item.photo
            nameTextField.text = item.name
            priceTextField.text = String(item.price)
            storeTextField.text = item.store
            datePicker.date = item.date
            memoTextField.text = item.memo
        }else{
            saveButton.isEnabled = false
        }
        
        
        updateSaveButtonState()
        
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillShow(_:)), name: .UIKeyboardWillShow, object: nil)
        
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillHide(_:)), name: .UIKeyboardWillHide, object: nil)
        
        let pickerView = UIPickerView()
        pickerView.delegate = self as UIPickerViewDelegate
        categoryPickerTextField.inputView = pickerView
        
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    //MARK:PickerView
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return pickOption.count
    }
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return pickOption[row]
    }
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        categoryPickerTextField.text = pickOption[row]
    }
    
    //MARK: Navigation
    @IBAction func cancel(_ sender: UIBarButtonItem) {
        let isPresentingInAddItemMode = presentingViewController is UINavigationController
        
        if isPresentingInAddItemMode{
            dismiss(animated: true, completion: nil)
        }else if let owningNavigationController = navigationController{
            owningNavigationController.popViewController(animated: true)
        }else{
            fatalError("ItemViewController가 navigation controller안에 있지 않아..")
        }
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        
        guard let button = sender as? UIBarButtonItem, button === saveButton else {
            os_log("The save button was not pressed, cancelling", log:OSLog.default, type: .debug)
            return
        }
        
        let category = categoryPickerTextField.text
        let name = nameTextField.text
        guard let price = Int(priceTextField.text!) else{
            let alert = UIAlertController(title:"",message:"",preferredStyle:.alert)
            let okButton = UIAlertAction(title:"OK", style: .default, handler: nil)
            alert.addAction(okButton)
            self.present(alert, animated: true, completion: nil)
            return
        }
        let store = storeTextField.text
        let date = datePicker.date
        let photo = photoImageView.image
        let memo = memoTextField.text
        
        item = Item (category:category!, name:name!, price:price, store:store!, date:date, memo:memo, photo:photo)
        
        
    }
    
    //사진
    @IBAction func selectImageFromPhotoLibrary(_ sender:UIButton){
        // 키보드 버튼 숨기기
        let _ = textFieldShouldReturn(memoTextField)
        
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
    
    @IBAction func shootPhoto(_ sender: UIButton) {
        if UIImagePickerController.isSourceTypeAvailable(.camera) {
            let imagePickerController = UIImagePickerController()
            imagePickerController.allowsEditing = false
            imagePickerController.sourceType = UIImagePickerControllerSourceType.camera
            imagePickerController.cameraCaptureMode = .photo
            imagePickerController.modalPresentationStyle = .fullScreen
            
            //사용자가 사진을 선택하면 알려줌
            imagePickerController.delegate = self
            present(imagePickerController, animated: true, completion: nil)
            
        
        } else {
            noCamera()
        }
    }
    
    func noCamera(){
        let alertVC = UIAlertController(title: "No Camera", message: "Sorry, this device has no camera", preferredStyle: .alert)
        let okAction = UIAlertAction( title: "OK", style:.default, handler: nil)
        alertVC.addAction(okAction)
        present( alertVC,animated: true, completion: nil)
    }
    
    //MARK : TextField
    
    private func updateSaveButtonState(){
        let nameText = nameTextField.text ?? ""
        let priceText = priceTextField.text ?? ""
        let storeText = storeTextField.text ?? ""
        let categoryText = categoryPickerTextField.text ?? ""
        
        if !(nameText.isEmpty) && !(priceText.isEmpty) && !(storeText.isEmpty) && !(categoryText.isEmpty){
            saveButton.isEnabled = true
        }
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        updateSaveButtonState()
        navigationItem.title = nameTextField.text
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        //키보드에 next버튼이 일하는 거
        switch textField{
        case nameTextField :
            priceTextField.becomeFirstResponder()
        case priceTextField :
            storeTextField.becomeFirstResponder()
        case storeTextField :
            memoTextField.becomeFirstResponder()
        default:
            textField.resignFirstResponder()
        }
        return true
    }

    //키보드사이즈
    @objc func keyboardWillShow(_ sender: Notification) {
        
        self.view.frame.origin.y = -220
        // Move view 220 points upward
    }
    @objc func keyboardWillHide(_ sender: Notification) {
        self.view.frame.origin.y = 0
        // Move view to original position
    }
 
}


