//
//  MapViewController.swift
//  paybook
//
//  Created by ciepc on 20/12/2017.
//  Copyright © 2017 sohyeon. All rights reserved.
//

import Foundation
import UIKit
import MapKit

class MapViewController: UIViewController, MKMapViewDelegate{
    
    var person:Person?
    @IBOutlet weak var mapKit: MKMapView!
    
    override func viewDidLoad(){
        super.viewDidLoad()
    }
    
    
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
        
        var annotationView = mapKit.dequeueReusableAnnotationView(withIdentifier: "Person")
        
        if annotationView == nil{
            annotationView = MKAnnotationView(annotation: annotation, reuseIdentifier: "Person")
            annotationView?.image = UIImage(named: "Japan_small_icon.png")
            annotationView?.canShowCallout = false
        }else{
            annotationView!.annotation = annotation
        }
        return annotationView
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if let person:Person = person{
            let annotation = MKPointAnnotation()
            annotation.title = person.id
            annotation.coordinate = CLLocationCoordinate2D(latitude: person.locx, longitude: person.locy)
            //지도가 표시하는 영역의 중앙 위치를 설정
            mapKit.setCenter(annotation.coordinate, animated: false)
            //줌 가능 여부
            mapKit.isZoomEnabled = true
            //스크롤 가능 여부
            mapKit.isScrollEnabled = true
            //회전 가능 여부
            mapKit.isRotateEnabled = true
            //각도 가능 여부
            mapKit.isPitchEnabled = true
            
            //맵뷰의 중앙 좌표 얻기
            let _ = mapKit.centerCoordinate
            
            let camera = mapKit.camera
            camera.heading = 1000
            camera.altitude = 1000
            mapKit.addAnnotation(annotation)
        }
    }
    
}
