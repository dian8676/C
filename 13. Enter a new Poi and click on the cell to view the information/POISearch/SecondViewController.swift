//
//  SecondViewController.swift
//  viewController
//
//  Created by ciepc on 15/11/2017.
//  Copyright © 2017 ciepc. All rights reserved.
//

import Foundation
import UIKit
import MapKit

class SecondViewController: UIViewController, MKMapViewDelegate{
    
    var poi:POI?
    @IBOutlet weak var mapKit: MKMapView!
    
    override func viewDidLoad(){
        super.viewDidLoad()
    }
    
    @IBAction func prevPage(_ sender: Any) {
        self.performSegue(withIdentifier: "seguePrev", sender: self)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        super.prepare(for: segue, sender: sender)
        
        if segue.identifier == "seguePrev"{
            guard let poiViewController = segue.destination as? PoiViewController else {
                fatalError("Unexpected destination: \(segue.destination)")
            }
            let poiData = poi
            poiViewController.poi = poiData
        }
    }
  
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
    
        var annotationView = mapKit.dequeueReusableAnnotationView(withIdentifier: "Museum")
    
        if annotationView == nil{
            annotationView = MKAnnotationView(annotation: annotation, reuseIdentifier: "Museum")
            annotationView?.image = UIImage(named: "Japan_small_icon.png")
            annotationView?.canShowCallout = false
        }else{
            annotationView!.annotation = annotation
        }
        return annotationView
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if let poi:POI = poi{
            let annotation = MKPointAnnotation()
            annotation.title = poi.name
            annotation.coordinate = CLLocationCoordinate2D(latitude: poi.locx, longitude: poi.locy)
            //지도가 표시하는 영역의 중앙 위치를 설정
            mapKit.setCenter(annotation.coordinate, animated: true)
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
