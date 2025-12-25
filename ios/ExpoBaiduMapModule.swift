import ExpoModulesCore
import BaiduMapAPI_Base
import BaiduMapAPI_Map

public class ExpoBaiduMapModule: Module {

  public func definition() -> ModuleDefinition {
    Name("ExpoBaiduMap")

    // Defines event names that the module can send to JavaScript.
    Events("onChange")

      AsyncFunction("agreePolicy") { (isAgree: Bool) in
          BMKMapManager.setAgreePrivacy(isAgree)
      }

      AsyncFunction("setCoordinateType") { (type: String) in
          let coordinateType: BMK_COORD_TYPE
          switch (type) {
          case "gps":
              coordinateType = .COORDTYPE_GPS
          case "common":
              coordinateType = .COORDTYPE_COMMON
          default: coordinateType = .COORDTYPE_BD09LL
          }
          return BMKMapManager.setCoordinateTypeUsedInBaiduMapSDK(coordinateType)
      }
      
      AsyncFunction("startEngine") {
          if let apiKey = Bundle.main.object(forInfoDictionaryKey: "BaiduMapApiKey") as? String {
              return BMKMapManager().start(apiKey, generalDelegate: nil)
          }
          throw Exception(name: "ERR_NO_API_KEY_PROVIDED", description: "Missing `iosApiKey` field in app.json")
      }

    View(ExpoBaiduMapView.self) {
        Events("onLoad")
        Prop("active") { (view: ExpoBaiduMapView, active: Bool) in
            if active {
                view.mapView.viewWillAppear()
            } else {
                view.mapView.viewWillDisappear()
            }
        }
        
        Prop("mapType") { (view: ExpoBaiduMapView, mapTypeString: String) in
            let mapType: BMKMapType
            switch mapTypeString {
            case "none":
                mapType = BMKMapType.none
            case "standard":
                mapType = BMKMapType.standard
            case "satellite":
                mapType = BMKMapType.satellite
            default: mapType = BMKMapType.standard
            }
            view.mapView.mapType = mapType
        }
        
        Prop("language") { (view: ExpoBaiduMapView, language: String) in
            view.mapView.languageType = language == "english" ? kBMKMapLanguageTypeEnglish : kBMKMapLanguageTypeChinese
        }
        
        Prop("backgroundColor") { (view: ExpoBaiduMapView, color: UIColor) in
            view.mapView.backgroundColor = color
        }
        
        Prop("backgroundImage") { (view: ExpoBaiduMapView, image: String) in
            if !image.isEmpty {
                if image.starts(with: "file://"),
                    let url = URL(string: image),
                   let data = try? Data(contentsOf: url),
                   let image = UIImage(data: data) {
                    view.mapView.backgroundImage = image
                } else if let imageData = Data(base64Encoded: image, options: .ignoreUnknownCharacters),
                            let image = UIImage(data: imageData) {
                    view.mapView.backgroundImage = image
                }
            }
        }
        
        Prop("region") { (view: ExpoBaiduMapView, region: CoordinateRegion) in
            view.mapView.region = region.toBMKRegion()
        }
        
        Prop("limitMapRegion") { (view: ExpoBaiduMapView, region: CoordinateRegion) in
            view.mapView.limitMapRegion = region.toBMKRegion()
        }
        
        Prop("compassPosition") { (view: ExpoBaiduMapView, point: Point) in
            view.mapView.compassPosition = CGPoint(x: point.x, y: point.y)
        }
        
        Prop("centerCoordinate") { (view: ExpoBaiduMapView, coordinate: Coordinate2D) in
            view.mapView.centerCoordinate = CLLocationCoordinate2D(latitude: coordinate.latitude,
                                                                   longitude: coordinate.longitude)
        }
        
        Prop("showsUserLocation") { (view: ExpoBaiduMapView, showsUserLocation: Bool) in
            view.mapView.showsUserLocation = showsUserLocation
        }
        
        Prop("inDoorMapEnabled") { (view: ExpoBaiduMapView, indoorMapEnabled: Bool) in
            view.mapView.baseIndoorMapEnabled = indoorMapEnabled
        }
        
        Prop("userTrackingMode") { (view: ExpoBaiduMapView, userTrackingMode: String) in
            let trackingMode: BMKUserTrackingMode
            switch userTrackingMode {
            case "follow":
                trackingMode = BMKUserTrackingModeFollow
            case "heading":
                trackingMode = BMKUserTrackingModeHeading
            case "followWithHeading":
                trackingMode = BMKUserTrackingModeFollowWithHeading
            default:
                trackingMode = BMKUserTrackingModeNone
            }
            view.mapView.userTrackingMode = trackingMode
        }
        
        Prop("circles") { (view: ExpoBaiduMapView, circles: [Circle]) in
            view.circles = circles
        }
        Prop("polygons") { (view: ExpoBaiduMapView, polygons: [Polygon]) in
            view.polygons = polygons
        }
        
        
        AsyncFunction("zoomIn") { (view: ExpoBaiduMapView) in
            view.mapView.zoomIn()
        }
        
        AsyncFunction("zoomOut") { (view: ExpoBaiduMapView) in
            view.mapView.zoomOut()
        }
    }
  }
}


class ExpoBaiduMapGeneralDelegate: NSObject, BMKGeneralDelegate {
    func onGetNetworkState(_ iError: Int32) {
        
    }
    
    func onGetPermissionState(_ iError: Int32) {
        
    }
}
