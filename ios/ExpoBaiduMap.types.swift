//
//  ExpoBaiduMap.types.swift
//  ExpoBaiduMap
//
//  Created by Aron on 2025/12/8.
//

import Foundation
import ExpoModulesCore
import BaiduMapAPI_Base
import BaiduMapAPI_Map
import BaiduMapAPI_Search

struct Coordinate2D: Record {
    init() { }
    init(latitude: Double = 0.0, longitude: Double = 0.0) {
        self.latitude = latitude
        self.longitude = longitude
    }
    
    @Field
    var latitude = 0.0
    @Field
    var longitude = 0.0
    
    func toCLCoordinate() -> CLLocationCoordinate2D {
        CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
    }
}

struct CoordinateSpan: Record {
    init() { }
    init(latitudeDelta: Double = 0.0, longitudeDelta: Double = 0.0) {
        self.latitudeDelta = latitudeDelta
        self.longitudeDelta = longitudeDelta
    }
    
    @Field
    var latitudeDelta = 0.0
    @Field
    var longitudeDelta = 0.0
}

struct CoordinateRegion: Record {
    @Field
    var center: Coordinate2D = .init()
    @Field
    var span: CoordinateSpan = .init()
    
    func toBMKRegion() -> BMKCoordinateRegion {
        return BMKCoordinateRegion(center: CLLocationCoordinate2D(latitude: center.latitude,
                                                                  longitude: center.longitude),
                                   span: BMKCoordinateSpan(latitudeDelta: span.latitudeDelta,
                                                           longitudeDelta: span.longitudeDelta))
    }
}

struct Point: Record {
    init() { }
    init(x: Double = 0.0, y: Double = 0.0) {
        self.x = x
        self.y = y
    }
    
    @Field
    var x = 0.0
    @Field
    var y = 0.0
}

struct Size: Record {
    init() { }
    init(width: Double = 0.0, height: Double = 0.0) {
        self.width = width
        self.height = height
    }
    
    @Field
    var width = 0.0
    @Field
    var height = 0.0
}

struct Bounds: Record {
    init() { }
    
    @Field
    var northEast: Coordinate2D = .init()
    
    @Field
    var southWest: Coordinate2D = .init()
}

struct Polygon: Record {
    init() { }
    init(coordinates: [Coordinate2D] = [], count: Int = 0) {
        self.coordinates = coordinates
        self.count = count
    }
    
    @Field
    var coordinates: [Coordinate2D] = []
    @Field
    var count = 0
}

final class Circle: Record {
    init() { }
    init(center: Coordinate2D = .init(), radius: Double = 0.0) {
        self.center = center
        self.radius = radius
    }
    
    @Field
    var center: Coordinate2D = .init()
    @Field
    var radius = 0.0
    @Field
    var fillColor: UIColor?
    @Field
    var strokeColor: UIColor?
    
    @Field
    var lineWidth: CGFloat?
}

final class TextMarker: Record {
    
    @Field
    var center: Coordinate2D = .init()
    
    @Field
    var text: String?
    
    @Field
    var textColor: UIColor?
    
    @Field
    var textSize: CGFloat?
    
    @Field
    var backgroundColor: UIColor?
}

struct PoiCitySearchOptions: Record {
    init() { }
    
    @Field
    var city: String = ""
    
    @Field
    var keyword: String = ""
    
    @Field
    var pageIndex: Int?
    
    @Field
    var pageSize: Int?
    
    @Field
    var scope: String?
    
    @Field
    var tag: String?
    
    @Field
    var cityLimit: Bool?
    
    @Field
    var returnAddress: Bool?
}

struct PoiNearbySearchOptions: Record {
    init() { }
    
    @Field
    var keywords: [String] = []
    
    @Field
    var location: Coordinate2D = .init()
    
    @Field
    var radius: Int?
    
    @Field
    var pageIndex: Int?
    
    @Field
    var pageSize: Int?
    
    @Field
    var scope: String?
    
    @Field
    var tag: String?
    
    @Field
    var radiusLimit: Bool?
    
    @Field
    var tags: [String]?
}

struct PoiBoundsSearchOptions: Record {
    init() { }
    
    @Field
    var keywords: [String] = []
    
    @Field
    var bounds: Bounds = .init()
    
    @Field
    var pageIndex: Int?
    
    @Field
    var pageSize: Int?
    
    @Field
    var scope: String?
    
    @Field
    var tags: [String]?
    
    @Field
    var returnAddress: Bool?
}

struct PoiDetailSearchOptions: Record {
    init() { }
    
    @Field
    var uids: [String] = []
}

struct PoiSuggestionOptions: Record {
    init() { }
    
    @Field
    var keyword: String = ""
    
    @Field
    var city: String?
    
    @Field
    var cityLimit: Bool?
    
    @Field
    var location: Coordinate2D?
}

struct PoiSearchPagination: Record {
    init() { }
    
    @Field
    var pageIndex: Int = 0
    
    @Field
    var pageSize: Int = 0
    
    @Field
    var totalCount: Int = 0
    
    @Field
    var totalPage: Int = 0
}

struct PoiInfoRecord: Record {
    init() { }
    
    @Field
    var uid: String = ""
    
    @Field
    var name: String = ""
    
    @Field
    var location: Coordinate2D?
    
    @Field
    var address: String?
    
    @Field
    var province: String?
    
    @Field
    var city: String?
    
    @Field
    var area: String?
    
    @Field
    var phone: String?
    
    @Field
    var tag: String?
}

struct PoiSearchResultRecord: Record {
    init() { }
    
    @Field
    var pagination: PoiSearchPagination = .init()
    
    @Field
    var pois: [PoiInfoRecord] = []
}

struct PoiDetailInfoRecord: Record {
    init() { }
    
    @Field
    var uid: String = ""
    
    @Field
    var name: String = ""
    
    @Field
    var location: Coordinate2D?
    
    @Field
    var address: String?
    
    @Field
    var province: String?
    
    @Field
    var city: String?
    
    @Field
    var area: String?
    
    @Field
    var phone: String?
    
    @Field
    var tag: String?
}

struct PoiDetailResultRecord: Record {
    init() { }
    
    @Field
    var poiList: [PoiDetailInfoRecord] = []
}

struct PoiSuggestionRecord: Record {
    init() { }
    
    @Field
    var uid: String?
    
    @Field
    var key: String?
    
    @Field
    var city: String?
    
    @Field
    var district: String?
    
    @Field
    var location: Coordinate2D?
}

struct PoiSuggestionResultRecord: Record {
    init() { }
    
    @Field
    var suggestions: [PoiSuggestionRecord] = []
}
