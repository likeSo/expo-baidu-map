//
//  ExpoBaiduMap.extensions.swift
//  ExpoBaiduMap
//
//  Created by Aron on 2025/12/25.
//

import Foundation
import BaiduMapAPI_Base
import BaiduMapAPI_Map
import ExpoModulesCore


var shapeRecordKey = "ExpoBaiduMapMarkerRecordKey"
extension BMKShape {
    var recordData: Record? {
        get {
            return objc_getAssociatedObject(self, shapeRecordKey) as? Record
        }
        
        set {
            objc_setAssociatedObject(self, shapeRecordKey, newValue, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
        }
    }
}
