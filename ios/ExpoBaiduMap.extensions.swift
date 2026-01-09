//
//  ExpoBaiduMap.extensions.swift
//  ExpoBaiduMap
//
//  Created by Aron on 2025/12/25.
//

import Foundation
import BaiduMapAPI_Base
import BaiduMapAPI_Map


var circleDataKey = "ExpoBaiduMapCircleDataKey"
extension BMKCircle {
    var circleData: Circle? {
        get {
            return objc_getAssociatedObject(self, circleDataKey) as? Circle
        }
        set {
            objc_setAssociatedObject(self, circleDataKey, newValue, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
        }
    }
}


extension BMKText {
    var textData: TextMarker?
}
