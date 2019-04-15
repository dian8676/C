//
//  PoiViewController.swift
//  POISearch
//
//  Created by ciepc on 28/11/2017.
//  Copyright © 2017 ciepc. All rights reserved.
//

import Foundation
import UIKit

class PoiViewController: UIViewController, UITextFieldDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    @IBOutlet weak var nameTextField: UITextField!
    @IBOutlet weak var addrTextField: UITextField!
    @IBOutlet weak var feeTextField: UITextField!
    @IBOutlet weak var locxTextField: UITextField!
    @IBOutlet weak var locyTextField: UITextField!
    @IBOutlet weak var imgView: UIImageView!
    @IBOutlet weak var insertButton: UIButton!
    @IBOutlet weak var mapButton: UIButton!
    @IBOutlet weak var cancelButton: UIButton!
    
    var poi:POI?
    var insert:Bool = true
    
    override func viewDidLoad(){
        super.viewDidLoad()
        nameTextField.delegate = self
        addrTextField.delegate = self
        feeTextField.delegate = self
        locxTextField.delegate = self
        locyTextField.delegate = self
        
        updateSaveButtonState()
        
        if let poi:POI = poi{
            insertButton.isEnabled = false
            nameTextField.text = poi.name
            addrTextField.text = poi.addr
            feeTextField.text = "\(poi.fee)"
            locxTextField.text = "\(poi.locx)"
            locyTextField.text = "\(poi.locy)"
            imgView.image = poi.photo
        }else{
            mapButton.isEnabled = false
        }
        
    }
   
    @IBAction func nextPage(_ sender: Any) {
        self.performSegue(withIdentifier: "segueNext", sender: self)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        
        if segue.identifier == "segueNext"{
            guard let poiMapViewController = segue.destination as? SecondViewController else {
                fatalError("Unexpected destination: \(segue.destination)")
            }
            
            let poiData = poi
            poiMapViewController.poi = poiData
            
        }else if let button = sender as? UIButton, button === cancelButton {
            insert = false
        }
        else{
            // Configure the destination view controller only when the save button is pressed.
            guard let button = sender as? UIButton, button === insertButton else {
                fatalError("insert error")
            }
            let name = nameTextField.text
            let addr = addrTextField.text
            let fee = Int(feeTextField.text!)
            let locx = Double(locxTextField.text!)
            let locy = Double(locyTextField.text!)
            let img = imgView.image
            
            poi = POI(name: name!, addr: addr!, fee: fee!, locx: locx!, locy: locy!, photo: img)
        }
    }
    
    @IBAction func selectFromLib(_ sender: Any) {
        let imagePickerController = UIImagePickerController()
        imagePickerController.sourceType = .photoLibrary
        imagePickerController.delegate = self
        present(imagePickerController, animated: true, completion: nil)
    }
    
    @IBAction func shootPhoto(_ sender: Any) {
        let picker = UIImagePickerController()
        if UIImagePickerController.isSourceTypeAvailable(.camera) {
            picker.allowsEditing = false
            picker.sourceType = UIImagePickerControllerSourceType.camera
            picker.cameraCaptureMode = .photo
            picker.modalPresentationStyle = .fullScreen
            present(picker,animated: true,completion: nil)
        }else{
            noCamera()
        }
    }
    //취소(사진 선택 중 취소)
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        // Dismiss the picker if the user canceled.
        dismiss(animated: true, completion: nil)
    }
    
    //이미지 변경
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        // The info dictionary may contain multiple representations of the image. You want to use the original.
        guard let selectedImage = info[UIImagePickerControllerOriginalImage] as? UIImage else {
            fatalError("Expected a dictionary containing an image, but was provided the following: \(info)")
        }
        // Set photoImageView to display the selected image.
        imgView.image = selectedImage
        // Dismiss the picker.
        dismiss(animated: true, completion: nil)
        
    }
    
    func noCamera(){
        let alertVC = UIAlertController( title: "No Camera", message: "Sorry, this device has no camera",preferredStyle: .alert)
        let okAction = UIAlertAction( title: "OK", style:.default, handler: nil)
        alertVC.addAction(okAction)
        present( alertVC,animated: true, completion: nil)
    }
    
    func textFieldDidBeginEditing(_ textField: UITextField) {
        // Disable the Save button while editing.
        insertButton.isEnabled = false
    }
    
    //텍스트 필드가 비어있는 경우 저장 버튼 비활성화
    private func updateSaveButtonState() {
        // Disable the Save button if the text field is empty.
        let text = nameTextField.text ?? ""
        insertButton.isEnabled = !text.isEmpty
    }
    func textFieldDidEndEditing(_ textField: UITextField) {
        //텍스트 필드에 텍스트가 있는지 확인 후 저장버튼 활성화
        updateSaveButtonState()
        //제목을 해당 텍스트로 설정
        navigationItem.title = textField.text
    }
}
