import ExpoModulesCore
import WebKit
import BaiduMapAPI_Map

class ExpoBaiduMapView: ExpoView {
    let mapView = BMKMapView(frame: .zero)
    
    var active: Bool = true
    var zoomLevel: Double = 1
    
    var circles: [Circle]?
    var polygons: [Polygon]?
    
    
    let onLoad = EventDispatcher()
    var delegate: MapViewDelegate?
    
    required init(appContext: AppContext? = nil) {
        super.init(appContext: appContext)
        clipsToBounds = true
        delegate = MapViewDelegate { url in
            self.onLoad(["url": url])
        }
        
        addSubview(mapView)
    }
    
    override func layoutSubviews() {
        mapView.frame = bounds
    }
    
    override func didSetProps(_ changedProps: [String]!) {
        for propName in changedProps {
            switch propName {
            case "circles", "polygons":
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
        if !overlays.isEmpty {
            mapView.addOverlays(overlays)
        }
    }
}

class MapViewDelegate: NSObject, BMKMapViewDelegate {


    
    func mapView(_ mapView: BMKMapView, viewFor overlay: any BMKOverlay) -> BMKOverlayView? {
        if let circle = overlay as? BMKCircle {
            let circleView = BMKCircleView(circle: circle)
            circleView
        }
    }
}
