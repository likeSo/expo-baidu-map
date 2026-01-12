//
//  ExpoBaiduMapModule.utils.swift
//  ExpoBaiduMap
//
//  Created by Aron on 2026/1/10.
//

import Foundation
import ExpoModulesCore
import BaiduMapAPI_Base

func makeDictionary(from record: Record?, errorCode: BMKSearchErrorCode?) -> [String: Any] {
    if record != nil {
        return record!.toDictionary()
    }
    return ["errorCode": errorCode?.rawValue ?? -1]
}
