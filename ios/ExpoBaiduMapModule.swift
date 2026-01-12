import ExpoModulesCore
import BaiduMapAPI_Base
import BaiduMapAPI_Map
import BaiduMapAPI_Search

public class ExpoBaiduMapModule: Module {
    var poiSearchDelegate: PoiSearchDelegate?
    var suggestionSearchDelegate: SuggestionSearchDelegate?
    var pendingPoiTasks: [String: PoiSearchDelegate] = [:]
    
        
    fileprivate func sendGetPoiSearchResultEvent(_ record: PoiSearchResultRecord, _ errorCode: BMKErrorCode) {
        
    }
    

  public func definition() -> ModuleDefinition {
    Name("ExpoBaiduMap")

    Events("onGetPoiCitySearchResult", "onGetPoiNearbySearchResult", "onGetPoiBoundsSearchResult", "onGetPoiDetailSearchResult", "onGetSuggestionResult")

      AsyncFunction("agreePrivacy") { (isAgree: Bool) in
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
      
      AsyncFunction("poiSearchInCity") { (options: PoiCitySearchOptions) in
          let searcher = BMKPoiSearch()
          self.poiSearchDelegate = PoiSearchDelegate(onGetSearchResult: { [weak self] record, errorCode in
              self?.sendEvent("onGetPoiCitySearchResult", makeDictionary(from: record, errorCode: errorCode))
              self?.poiSearchDelegate = nil
          }, onGetPoiDetailResult: nil)
          
          searcher.delegate = poiSearchDelegate
          
          let opt = BMKPOICitySearchOption()
          opt.city = options.city
          opt.keyword = options.keyword
          opt.pageIndex = options.pageIndex ?? 0
          opt.pageSize = options.pageSize ?? 10
          opt.scope = options.scope == "basic" ? BMKPOISearchScopeType.BMK_POI_SCOPE_BASIC_INFORMATION : BMKPOISearchScopeType.BMK_POI_SCOPE_DETAIL_INFORMATION
          opt.isCityLimit = options.cityLimit ?? false
          
          return searcher.poiSearch(inCity: opt)
      }
      
      AsyncFunction("poiSearchNearby") { (options: PoiNearbySearchOptions) in
          let searcher = BMKPoiSearch()
          self.poiSearchDelegate = PoiSearchDelegate(onGetSearchResult: { [weak self] record, errorCode in
              self?.sendEvent("onGetPoiNearbySearchResult", makeDictionary(from: record, errorCode: errorCode))
              self?.poiSearchDelegate = nil
          }, onGetPoiDetailResult: nil)
          
          searcher.delegate = poiSearchDelegate
          
          let opt = BMKPOINearbySearchOption()
          opt.location = options.location.toCLCoordinate()
          opt.keywords = options.keywords
          opt.radius = Int(options.radius ?? 1000)
          opt.pageIndex = Int(options.pageIndex ?? 0)
          opt.pageSize = Int(options.pageSize ?? 10)
          opt.scope = options.scope == "basic" ? BMKPOISearchScopeType.BMK_POI_SCOPE_BASIC_INFORMATION : BMKPOISearchScopeType.BMK_POI_SCOPE_DETAIL_INFORMATION
          opt.isRadiusLimit = options.radiusLimit ?? false
          opt.tags = options.tags
          
          return searcher.poiSearchNear(by: opt)
      }
      
      AsyncFunction("poiSearchInBounds") { (options: PoiBoundsSearchOptions) in
          let searcher = BMKPoiSearch()
          self.poiSearchDelegate = PoiSearchDelegate(onGetSearchResult: { [weak self] record, errorCode in
              self?.sendEvent("onGetPoiBoundsSearchResult", makeDictionary(from: record, errorCode: errorCode))
              self?.poiSearchDelegate = nil
          }, onGetPoiDetailResult: nil)
          searcher.delegate = poiSearchDelegate
          
          let opt = BMKPOIBoundSearchOption()
          opt.leftBottom = options.bounds.southWest.toCLCoordinate()
          opt.rightTop = options.bounds.northEast.toCLCoordinate()
          opt.keywords = options.keywords
          opt.pageIndex = options.pageIndex ?? 0
          opt.pageSize = options.pageSize ?? 10
          opt.scope = options.scope == "basic" ? BMKPOISearchScopeType.BMK_POI_SCOPE_BASIC_INFORMATION : BMKPOISearchScopeType.BMK_POI_SCOPE_DETAIL_INFORMATION
          opt.tags = options.tags
          
          return searcher.poiSearchInbounds(opt)
      }
      
      AsyncFunction("poiSearchDetail") { (options: PoiDetailSearchOptions) in
          let searcher = BMKPoiSearch()
          self.poiSearchDelegate = PoiSearchDelegate(onGetSearchResult: nil, onGetPoiDetailResult: { [weak self] record, errorCode in
              self?.sendEvent("onGetPoiDetailSearchResult", makeDictionary(from: record, errorCode: errorCode))
              self?.poiSearchDelegate = nil
          })
          searcher.delegate = poiSearchDelegate
          
          let opt = BMKPOIDetailSearchOption()
          opt.poiUIDs = options.uids
          
          return searcher.poiDetailSearch(opt)
      }
      
      AsyncFunction("poiSuggestion") { (options: PoiSuggestionOptions) in
          let searcher = BMKSuggestionSearch()
          suggestionSearchDelegate = SuggestionSearchDelegate(onGetSuggestionResult: { [weak self] record, errorCode in
              self?.sendEvent("onGetSuggestionResult", makeDictionary(from: record, errorCode: errorCode))
              self?.suggestionSearchDelegate = nil
          })
          searcher.delegate = suggestionSearchDelegate
          
          let opt = BMKSuggestionSearchOption()
          opt.keyword = options.keyword
          opt.cityname = options.city
          opt.cityLimit = options.cityLimit ?? false
          if options.location != nil {
              opt.location = options.location!.toCLCoordinate()
          }
          
          return searcher.suggestionSearch(opt)
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
        
        Prop("textMarkers") { (view: ExpoBaiduMapView, textMarkers: [TextMarker]) in
            view.textMarkers = textMarkers
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


final class PoiSearchDelegate: NSObject, BMKPoiSearchDelegate {
    let onGetSearchResult: ((_ onSuccess: PoiSearchResultRecord?, _ onError: BMKSearchErrorCode?) -> Void)?
    let onGetPoiDetailResult: ((_ onSuccess: PoiDetailResultRecord?, _ onError: BMKSearchErrorCode?) -> Void)?
    
    init(onGetSearchResult: ((_ onSuccess: PoiSearchResultRecord?, _ onError: BMKSearchErrorCode?) -> Void)?, onGetPoiDetailResult: ((_ onSuccess: PoiDetailResultRecord?, _ onError: BMKSearchErrorCode?) -> Void)?) {
        self.onGetSearchResult = onGetSearchResult
        self.onGetPoiDetailResult = onGetPoiDetailResult
    }
    
    func onGetPoiResult(_ searcher: BMKPoiSearch!, result poiResult: BMKPOISearchResult!, errorCode: BMKSearchErrorCode) {
        if errorCode != BMK_SEARCH_NO_ERROR {
            onGetSearchResult?(nil, errorCode)
            return
        }
        
        let pagination = PoiSearchPagination()
        pagination.totalCount = Int(poiResult.totalPOINum)
        pagination.totalPage = Int(poiResult.totalPageNum)
        pagination.pageIndex = Int(poiResult.curPageIndex)
        
        if let poiList = poiResult?.poiInfoList {
            let pois: [PoiInfoRecord] = poiList.map { info in
                let record = PoiInfoRecord()
                record.uid = info.uid ?? ""
                record.name = info.name ?? ""
                
                record.location = Coordinate2D(latitude: info.pt.latitude, longitude: info.pt.longitude)
                record.address = info.address
                record.province = info.province
                record.city = info.city
                record.area = info.area
                record.phone = info.phone
                record.tag = info.tag
                return record
            }
            
            let resultRecord = PoiSearchResultRecord()
            resultRecord.pagination = pagination
            resultRecord.pois = pois
            onGetSearchResult?(resultRecord, nil)
        }
        
    }
    
    // Callback for POI Detail Search
    func onGetPoiDetailResult(_ searcher: BMKPoiSearch!, result poiDetailResult: BMKPOIDetailSearchResult!, errorCode: BMKSearchErrorCode) {
        if errorCode != BMK_SEARCH_NO_ERROR {
            onGetPoiDetailResult?(nil, errorCode)
            return
        }
        
        var record: [PoiDetailInfoRecord]? = poiDetailResult.poiInfoList?.map { detailInfo in
            let detailRecord = PoiDetailInfoRecord()
            detailRecord.uid = detailInfo.uid ?? ""
            detailRecord.name = detailInfo.name ?? ""
            detailRecord.location = Coordinate2D(latitude: detailInfo.pt.latitude, longitude: detailInfo.pt.longitude)
            detailRecord.address = detailInfo.address
            detailRecord.province = detailInfo.province
            detailRecord.city = detailInfo.city
            detailRecord.area = detailInfo.area
            detailRecord.phone = detailInfo.phone
            detailRecord.tag = detailInfo.tag
            return detailRecord
        }
        let resultRecord = PoiDetailResultRecord()
        resultRecord.poiList = record ?? []
        onGetPoiDetailResult?(resultRecord, nil)
    }
    
    func onGetPoiIndoorResult(_ searcher: BMKPoiSearch!, result poiIndoorResult: BMKPOIIndoorSearchResult!, errorCode: BMKSearchErrorCode) {
        // Not used
    }
    
}

final class SuggestionSearchDelegate: NSObject, BMKSuggestionSearchDelegate {
    let onGetSuggestionResult: ((_ onSuccess: PoiSuggestionResultRecord?, _ onError: BMKSearchErrorCode?) -> Void)?
    
    init(onGetSuggestionResult: ((_ onSuccess: PoiSuggestionResultRecord?, _ onError: BMKSearchErrorCode?) -> Void)?) {
        self.onGetSuggestionResult = onGetSuggestionResult
    }
    
    func onGetSuggestionResult(_ searcher: BMKSuggestionSearch!, result: BMKSuggestionSearchResult!, errorCode error: BMKSearchErrorCode) {
        if error != BMK_SEARCH_NO_ERROR {
            onGetSuggestionResult?(nil, error)
            return
        }
        
        let list = result.suggestionList ?? []
        let suggestions: [PoiSuggestionRecord] = list.map { info in
            var record = PoiSuggestionRecord()
            record.uid = info.uid
            record.key = info.key
            record.city = info.city
            record.district = info.district
            record.location = Coordinate2D(latitude: info.location.latitude, longitude: info.location.longitude)
            return record
        }
        var record = PoiSuggestionResultRecord()
        record.suggestions = suggestions
        onGetSuggestionResult?(record, nil)
    }
}


class ExpoBaiduMapGeneralDelegate: NSObject, BMKGeneralDelegate {
    func onGetNetworkState(_ iError: Int32) {
        
    }
    
    func onGetPermissionState(_ iError: Int32) {
        
    }
}
