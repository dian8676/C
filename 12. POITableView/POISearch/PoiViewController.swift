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
    @IBOutlet weak var imgView: UIImageView!
    
    override func viewDidLoad(){
        super.viewDidLoad()
    }
    
    @IBAction func prevPage(_ sender: Any) {
        self.performSegue(withIdentifier: "seguePrev", sender: self)
    }
    @IBAction func nextPage(_ sender: Any) {
        self.performSegue(withIdentifier: "segueNext", sender: self)
    }
    @IBAction func selectFromLib(_ sender: UITapGestureRecognizer) {
        let imagePickerController = UIImagePickerController()
        imagePickerController.sourceType = .photoLibrary
        imagePickerController.delegate = self
        present(imagePickerController, animated: true, completion: nil)
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
}
