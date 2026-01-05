import ExpoModulesCore
import WebKit
import BaiduMapAPI_Map

class ExpoBaiduMapView: ExpoView {
    let mapView = BMKMapView(frame: .zero)
    
    var active: Bool = true
    var zoomLevel: Double = 1
    
    var circles: [Circle]?
    var polygons: [Polygon]?
    var textMarkers: [TextMarker]?
    
    
    let onLoad = EventDispatcher()
    var delegate: MapViewDelegate?
    
    required init(appContext: AppContext? = nil) {
        super.init(appContext: appContext)
        clipsToBounds = true
        delegate = MapViewDelegate()
        
        addSubview(mapView)
    }
    
    override func layoutSubviews() {
        mapView.frame = bounds
    }
    
    override func didSetProps(_ changedProps: [String]!) {
        for propName in changedProps {
            switch propName {
            case "circles", "polygons", "markers":
                reloadOverlays()
            default: break
            }
        }
    }
    
    private func reloadOverlays() {
        if let overlays = mapView.overlays {
            mapView.removeOverlays(overlays)
        }
        
        var overlays: [any BMKOverlay] = []
        circles?.forEach { circle in
            if let overlay = BMKCircle(center: CLLocationCoordinate2D(latitude: circle.center.latitude,
                                                                   longitude: circle.center.longitude),
                                       radius: circle.radius) {
                overlays.append(overlay)
            }
        }
        
        polygons?.forEach { polygon in
            var coordinates = polygon.coordinates.map{ $0.toCLCoordinate() }
            if let overlay = BMKPolygon(coordinates: &coordinates, count: UInt(coordinates.count)) {
                overlays.append(overlay)
            }
        }
        
        textMarkers?.forEach { marker in
            if let textMarker = BMKText(center: marker.center.toCLCoordinate(), text: marker.text ?? "") {
                overlays.append(textMarker)
            }
        }
        
        if !overlays.isEmpty {
            mapView.addOverlays(overlays)
        }
    }
}

class MapViewDelegate: NSObject, BMKMapViewDelegate {
    
    func mapView(_ mapView: BMKMapView, viewFor overlay: any BMKOverlay) -> BMKOverlayView? {
        var overlayView = mapView.view(for: overlay)
        if let circle = overlay as? BMKCircle {
            if overlayView == nil {
                overlayView = BMKCircleView(circle: circle)
            }
            if let circleConfig = circle.circleData, let circleView = overlayView as? BMKCircleView {
                circleView.fillColor = circleConfig.fillColor
                circleView.strokeColor = circleConfig.strokeColor
                circleView.lineWidth = circleConfig.lineWidth ?? 0
            }
            
        } else if let polygon = overlay as? BMKPolygon {
            if overlayView == nil {
                overlayView = BMKPolygonView(polygon: polygon)
            }
            
        } else if let text = overlay as? BMKText {
            if overlayView == nil {
                overlayView = BMKTextView(textOverlay: text)
            }
            
        }
        return overlayView
    }
}
