//
//  ExpoBaiduMap.types.swift
//  ExpoBaiduMap
//
//  Created by Aron on 2025/12/8.
//

import Foundation
import ExpoModulesCore
import BaiduMapAPI_Base

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

struct Circle: Record {
    init() { }
    init(center: Coordinate2D = .init(), radius: Double = 0.0) {
        self.center = center
        self.radius = radius
    }
    
    @Field
    var center: Coordinate2D = .init()
    @Field
    var radius = 0.0
}
