import ExpoModulesCore
import WebKit
import BaiduMapAPI_Map

class ExpoBaiduMapView: ExpoView {
    let mapView = BMKMapView(frame: .zero)
    
    var active: Bool = true
    var zoomLevel: Double = 1
    
    var circles: [Circle]?
    var polygons: [Polygon]?
    var markers: [Marker]?
    var polylines: [Polyline]?
    var arcs: [Arc]?
        
    let onTextMarkerOpressed = EventDispatcher()
    var delegate: MapViewDelegate?
    
    required init(appContext: AppContext? = nil) {
        super.init(appContext: appContext)
        clipsToBounds = true
        delegate = MapViewDelegate(onMarkerOpressed: { [weak self] marker in
            
        })
        
        addSubview(mapView)
    }
    
    override func layoutSubviews() {
        mapView.frame = bounds
    }
    
    override func didSetProps(_ changedProps: [String]!) {
        for propName in changedProps {
            switch propName {
            case "circles", "polygons", "markers", "polylines", "arcs", "textMarkers":
                reloadOverlays()
            default:
                break
            }
        }
    }
    
    private func reloadOverlays() {
        
        if let overlays = mapView.overlays {
            mapView.removeOverlays(overlays)
        }
        
        if let annotations = mapView.annotations {
            mapView.removeAnnotations(annotations)
        }
        
        // Markers (point annotations)
        markers?.forEach { marker in
            let annotation = BMKPointAnnotation()
            annotation.coordinate = marker.coordinate.toCLCoordinate()
            annotation.title = marker.title
            annotation.subtitle = marker.subtitle
            annotation.recordData = marker
            mapView.addAnnotation(annotation)
        }
        
        var overlays: [any BMKOverlay] = []
        // Polylines
        polylines?.forEach { polyline in
            var coordinates = polyline.coordinates.map{ $0.toCLCoordinate() }
            if let overlay = BMKPolyline(coordinates: &coordinates, count: UInt(coordinates.count)) {
                overlays.append(overlay)
                overlay.recordData = polyline
            }
        }
        
        // Arcs
        arcs?.forEach { arc in
            guard arc.coordinates.count >= 3 else {
                return
            }
            var coordinates = arc.coordinates.map { $0.toCLCoordinate() }
            guard let overlay = BMKArcline(coordinates: &coordinates) else {
                return
            }
            overlays.append(overlay)
            overlay.recordData = arc
        }
        
        // Polygons
        polygons?.forEach { polygon in
            var coordinates = polygon.coordinates.map{ $0.toCLCoordinate() }
            if let overlay = BMKPolygon(coordinates: &coordinates, count: UInt(coordinates.count)) {
                overlays.append(overlay)
                overlay.recordData = polygon
            }
        }
        
        // Circles
        circles?.forEach { circle in
            if let overlay = BMKCircle(center: CLLocationCoordinate2D(latitude: circle.center.latitude,
                                                                   longitude: circle.center.longitude),
                                       radius: circle.radius) {
                overlays.append(overlay)
                overlay.recordData = circle
            }
        }
        
        if !overlays.isEmpty {
            mapView.addOverlays(overlays)
        }
        
    }
}

class MapViewDelegate: NSObject, BMKMapViewDelegate {
    let onMarkerOpressed: ((_ marker: Marker) -> Void)?
    
    init(onMarkerOpressed: ((_: Marker) -> Void)?) {
        self.onMarkerOpressed = onMarkerOpressed
    }
    
    func mapView(_ mapView: BMKMapView, viewFor annotation: any BMKAnnotation) -> BMKAnnotationView? {
        if annotation is BMKPointAnnotation {
            let identifier = "marker"
            
            var annotationView = mapView.dequeueReusableAnnotationView(withIdentifier: identifier) as? BMKPinAnnotationView
            if annotationView == nil {
                annotationView = BMKPinAnnotationView(annotation: annotation, reuseIdentifier: identifier)
                annotationView?.animatesDrop = true
                annotationView?.isDraggable = false
                
            }
            annotationView?.annotation = annotation
            return annotationView
        }
        return nil
    }
    
    func mapView(_ mapView: BMKMapView, viewFor overlay: any BMKOverlay) -> BMKOverlayView? {
        var overlayView = mapView.view(for: overlay)
        
        // Circle
        if let circle = overlay as? BMKCircle {
            if overlayView == nil {
                overlayView = BMKCircleView(circle: circle)
            }
            if let circleView = overlayView as? BMKCircleView {
                // Check if we have the corresponding Expo Circle object
                if let expoCircle = circle.recordData as? Circle {
                    circleView.fillColor = expoCircle.fillColor ?? UIColor(red: 0, green: 0.5, blue: 1, alpha: 0.3)
                    circleView.strokeColor = expoCircle.strokeColor ?? UIColor(red: 0, green: 0.5, blue: 1, alpha: 1)
                    circleView.lineWidth = expoCircle.lineWidth ?? 2.0
                } else {
                    circleView.fillColor = UIColor(red: 0, green: 0.5, blue: 1, alpha: 0.3)
                    circleView.strokeColor = UIColor(red: 0, green: 0.5, blue: 1, alpha: 1)
                    circleView.lineWidth = 2.0
                }
            }
            
        // Polyline
        } else if let polyline = overlay as? BMKPolyline {
            if overlayView == nil {
                overlayView = BMKPolylineView(polyline: polyline)
            }
            if let polylineView = overlayView as? BMKPolylineView {
                if let expoPolyline = polyline.recordData as? Polyline {
                    polylineView.strokeColor = expoPolyline.strokeColor ?? UIColor(red: 0, green: 0.5, blue: 1, alpha: 1)
                    polylineView.lineWidth = expoPolyline.strokeWidth ?? 2.0
                } else {
                    polylineView.strokeColor = UIColor(red: 0, green: 0.5, blue: 1, alpha: 1)
                    polylineView.lineWidth = 2.0
                }
            }
            
        // Arc
        } else if let arc = overlay as? BMKArcline {
            if overlayView == nil {
                overlayView = BMKArclineView(arcline: arc)
            }
            if let arcView = overlayView as? BMKArclineView {
                if let expoArc = arc.recordData as? Arc {
                    arcView.strokeColor = expoArc.strokeColor ?? UIColor(red: 1, green: 0, blue: 0, alpha: 1)
                    arcView.lineWidth = expoArc.lineWidth ?? 2.0
                } else {
                    arcView.strokeColor = UIColor(red: 1, green: 0, blue: 0, alpha: 1)
                    arcView.lineWidth = 2.0
                }
            }
            
        // Polygon
        } else if let polygon = overlay as? BMKPolygon {
            if overlayView == nil {
                overlayView = BMKPolygonView(polygon: polygon)
            }
            if let polygonView = overlayView as? BMKPolygonView {
                if let expoPolygon = polygon.recordData as? Polygon {
                    polygonView.fillColor = expoPolygon.fillColor ?? UIColor(red: 0, green: 1, blue: 0, alpha: 0.3)
                    polygonView.strokeColor = expoPolygon.strokeColor ?? UIColor(red: 0, green: 0.5, blue: 0, alpha: 1)
                    polygonView.lineWidth = expoPolygon.lineWidth ?? 2.0
                } else {
                    polygonView.fillColor = UIColor(red: 0, green: 1, blue: 0, alpha: 0.3)
                    polygonView.strokeColor = UIColor(red: 0, green: 0.5, blue: 0, alpha: 1)
                    polygonView.lineWidth = 2.0
                }
            }
            
        // Text
        }
//        else if let text = overlay as? BMKText {
//            if overlayView == nil {
//                overlayView = BMKTextView(textOverlay: text)
//            }
//            if let textConfig = overlayMap[text.hash] as? TextMarker, let textView = overlayView as? BMKTextView {
//                textView.fontSize = Int32(textConfig.fontSize ?? 12)
//                textView.textColor = textConfig.textColor
//                textView.backgroundColor = textConfig.backgroundColor
//            }
//        }
        return overlayView
    }
    
    func mapView(_ mapView: BMKMapView, onClickedBMKOverlayView overlayView: BMKOverlayView) {
//        if let textOverlay = overlayView as? BMKTextView {
//            if let textMarker = overlayMap[textOverlay.text.hash] as? TextMarker {
//                onTextMarkerOpressed?(textMarker)
//            }
//        }
    }
}
