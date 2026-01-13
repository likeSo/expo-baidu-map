package expo.modules.baidumap

import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.model.LatLngBounds
import expo.modules.kotlin.records.Record
import expo.modules.kotlin.records.Field


// Coordinate2D.kt
open class Coordinate2D : Record {
    @Field
    var latitude: Double = 0.0

    @Field
    var longitude: Double = 0.0

    constructor() : this(0.0, 0.0)

    constructor(latitude: Double = 0.0, longitude: Double = 0.0) {
        this.latitude = latitude
        this.longitude = longitude
    }

//    fun toCLCoordinate(): CLLocationCoordinate2D {
//        return CLLocationCoordinate2D(latitude, longitude)
//    }
//
//    fun toBMKCoordinate(): BMKCoordinate {
//        return BMKCoordinate(latitude, longitude)
//    }
//
    fun toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }
    
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "latitude" to latitude,
            "longitude" to longitude
        )
    }
}

open class CoordinateSpan : Record {
    @Field
    var latitudeDelta: Double = 0.0

    @Field
    var longitudeDelta: Double = 0.0

    constructor() : this(0.0, 0.0)

    constructor(latitudeDelta: Double = 0.0, longitudeDelta: Double = 0.0) {
        this.latitudeDelta = latitudeDelta
        this.longitudeDelta = longitudeDelta
    }

//    fun toBMKSpan(): BMKCoordinateSpan {
//        return BMKCoordinateSpan(latitudeDelta, longitudeDelta)
//    }
}

open class CoordinateRegion : Record {
    @Field
    var center: Coordinate2D = Coordinate2D()

    @Field
    var span: CoordinateSpan = CoordinateSpan()

    fun toLatLngBounds(): LatLngBounds {
        val northEast = LatLng(
            center.latitude + span.latitudeDelta / 2,
            center.longitude + span.longitudeDelta / 2
        )
        val southWest = LatLng(
            center.latitude - span.latitudeDelta / 2,
            center.longitude - span.longitudeDelta / 2
        )
        return LatLngBounds.Builder()
            .include(southWest)
            .include(northEast)
            .build()
    }

//    fun toBMKRegion(): BMKCoordinateRegion {
//        return BMKCoordinateRegion(
//            center = center.toBMKCoordinate(),
//            span = span.toBMKSpan()
//        )
//    }
//
//    fun toBMKBounds(): BMKBounds {
//        val northEast = BMKCoordinate(
//            center.latitude + span.latitudeDelta / 2,
//            center.longitude + span.longitudeDelta / 2
//        )
//        val southWest = BMKCoordinate(
//            center.latitude - span.latitudeDelta / 2,
//            center.longitude - span.longitudeDelta / 2
//        )
//        return BMKBounds(northEast, southWest)
//    }
}

open class Point : Record {
    @Field
    var x: Int = 0

    @Field
    var y: Int = 0

    constructor() : this(0, 0)

    constructor(x: Int = 0, y: Int = 0) {
        this.x = x
        this.y = y
    }

//    fun toBMKPoint(): BMKPoint {
//        return BMKPoint(x, y)
//    }
}

open class Size : Record {
    @Field
    var width: Double = 0.0

    @Field
    var height: Double = 0.0

    constructor() : this(0.0, 0.0)

    constructor(width: Double = 0.0, height: Double = 0.0) {
        this.width = width
        this.height = height
    }

//    fun toBMKSize(): BMKSize {
//        return BMKSize(width, height)
//    }
}

open class Polygon : Record {
    @Field
    var coordinates: List<Coordinate2D> = emptyList()

    @Field
    var count: Int = 0

    constructor() : this(emptyList(), 0)

    constructor(coordinates: List<Coordinate2D> = emptyList(), count: Int = 0) {
        this.coordinates = coordinates
        this.count = count
    }

//    fun fromBMKPolygon(bmkPolygon: BMKPolygon): Polygon {
//        val points = mutableListOf<Coordinate2D>()
//        for (i in 0 until bmkPolygon.pointCount) {
//            val latLng = bmkPolygon.getPoint(i)
//            points.add(Coordinate2D(latLng.latitude, latLng.longitude))
//        }
//        return Polygon(points, points.size)
//    }
//
//    fun toBMKPolygon(): BMKPolygon {
//        val points = coordinates.map { it.toBMKCoordinate() }.toTypedArray()
//        return BMKPolygon(points)
//    }
//
//    fun toBMKPolygonOptions(): BMKPolygonOptions {
//        val options = BMKPolygonOptions()
//        coordinates.forEach { coord ->
//            options.addPoint(coord.toBMKCoordinate())
//        }
//        return options
//    }
}

open class Circle : Record {
    @Field
    var center: Coordinate2D = Coordinate2D()

    @Field
    var radius: Double = 0.0

    @Field
    var fillColor: Int? = null

    @Field
    var strokeColor: Int? = null

    @Field
    var lineWidth: Float? = null

    constructor() : this(Coordinate2D(), 0.0, null, null, null)

    constructor(
        center: Coordinate2D = Coordinate2D(),
        radius: Double = 0.0,
        fillColor: Int? = null,
        strokeColor: Int? = null,
        lineWidth: Float? = null
    ) {
        this.center = center
        this.radius = radius
        this.fillColor = fillColor
        this.strokeColor = strokeColor
        this.lineWidth = lineWidth
    }

//    fun fromBMKCircle(bmkCircle: BMKCircle): Circle {
//        return Circle(
//            center = Coordinate2D(bmkCircle.center.latitude, bmkCircle.center.longitude),
//            radius = bmkCircle.radius,
//            fillColor = bmkCircle.fillColor,
//            strokeColor = bmkCircle.strokeColor,
//            lineWidth = bmkCircle.lineWidth
//        )
//    }
//
//    fun toBMKCircle(): BMKCircle {
//        val circle = BMKCircle(
//            center.toBMKCoordinate(),
//            radius
//        )
//
//        fillColor?.let { circle.fillColor = it }
//        strokeColor?.let { circle.strokeColor = it }
//        lineWidth?.let { circle.lineWidth = it }
//
//        return circle
//    }

//    fun toBMKCircleOptions(): BMKCircleOptions {
//        val options = BMKCircleOptions()
//            .center(center.toBMKCoordinate())
//            .radius(radius)
//
//        fillColor?.let { options.fillColor(it) }
//        strokeColor?.let { options.strokeColor(it) }
//        lineWidth?.let { options.lineWidth(it) }
//
//        return options
//    }
}

class TextMarker: Record {

    @Field
    var center: Coordinate2D = Coordinate2D()

    @Field
    var text: String? = null
}

open class Bounds : Record {
    @Field
    var northEast: Coordinate2D = Coordinate2D()

    @Field
    var southWest: Coordinate2D = Coordinate2D()

    constructor() : this(Coordinate2D(), Coordinate2D())

    constructor(northEast: Coordinate2D = Coordinate2D(), southWest: Coordinate2D = Coordinate2D()) {
        this.northEast = northEast
        this.southWest = southWest
    }

    fun toLatLngBounds(): LatLngBounds {
        return LatLngBounds.Builder()
            .include(southWest.toLatLng())
            .include(northEast.toLatLng())
            .build()
    }

//    fun toBMKBounds(): BMKBounds {
//        return BMKBounds(
//            northEast.toBMKCoordinate(),
//            southWest.toBMKCoordinate()
//        )
//    }
}

open class PoiCitySearchOptions : Record {
    @Field
    var city: String = ""

    @Field
    var keyword: String = ""

    @Field
    var pageIndex: Int? = null

    @Field
    var pageSize: Int? = null

    @Field
    var scope: String? = null

    @Field
    var tag: String? = null

    @Field
    var cityLimit: Boolean? = null

    @Field
    var returnAddress: Boolean? = null
}

open class PoiNearbySearchOptions : Record {
    @Field
    var keyword: String = ""

    @Field
    var location: Coordinate2D = Coordinate2D()

    @Field
    var radius: Int? = null

    @Field
    var pageIndex: Int? = null

    @Field
    var pageSize: Int? = null

    @Field
    var scope: String? = null

    @Field
    var tag: String? = null

    @Field
    var radiusLimit: Boolean? = null

    @Field
    var returnAddress: Boolean? = null
}

open class PoiBoundsSearchOptions : Record {
    @Field
    var keyword: String = ""

    @Field
    var bounds: Bounds = Bounds()

    @Field
    var pageIndex: Int? = null

    @Field
    var pageSize: Int? = null

    @Field
    var scope: String? = null

    @Field
    var tag: String? = null

    @Field
    var returnAddress: Boolean? = null
}

open class PoiDetailSearchOptions : Record {
    @Field
    var uid: String = ""
}

open class PoiSuggestionOptions : Record {
    @Field
    var keyword: String = ""

    @Field
    var city: String? = null

    @Field
    var cityLimit: Boolean? = null

    @Field
    var location: Coordinate2D? = null

    @Field
    var hotWord: Boolean? = null
}

open class PoiSearchPagination : Record {
    @Field
    var pageIndex: Int = 0

    @Field
    var pageSize: Int = 0

    @Field
    var totalCount: Int = 0

    @Field
    var totalPage: Int = 0
    
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "pageIndex" to pageIndex,
            "pageSize" to pageSize,
            "totalCount" to totalCount,
            "totalPage" to totalPage
        )
    }
}

open class PoiInfoRecord : Record {
    @Field
    var uid: String = ""

    @Field
    var name: String = ""

    @Field
    var location: Coordinate2D? = null

    @Field
    var address: String? = null

    @Field
    var province: String? = null

    @Field
    var city: String? = null

    @Field
    var area: String? = null

    @Field
    var phone: String? = null

    @Field
    var tag: String? = null
    
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "name" to name,
            "location" to location?.toMap(),
            "address" to address,
            "province" to province,
            "city" to city,
            "area" to area,
            "phone" to phone,
            "tag" to tag
        )
    }
}

open class PoiSearchResultRecord : Record {
    @Field
    var pagination: PoiSearchPagination = PoiSearchPagination()

    @Field
    var pois: List<PoiInfoRecord> = emptyList()
    
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "pagination" to pagination.toMap(),
            "pois" to pois.map { it.toMap() }
        )
    }
}

open class PoiDetailInfoRecord : Record {
    @Field
    var uid: String = ""

    @Field
    var name: String = ""

    @Field
    var location: Coordinate2D? = null

    @Field
    var address: String? = null

    @Field
    var province: String? = null

    @Field
    var city: String? = null

    @Field
    var area: String? = null

    @Field
    var phone: String? = null

    @Field
    var tag: String? = null
    
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "name" to name,
            "location" to location?.toMap(),
            "address" to address,
            "province" to province,
            "city" to city,
            "area" to area,
            "phone" to phone,
            "tag" to tag
        )
    }
}

open class PoiDetailResultRecord : Record {
    @Field
    var poiList: List<PoiDetailInfoRecord>? = null
    
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "poiList" to poiList?.map { it.toMap() }
        )
    }
}

open class PoiSuggestionRecord : Record {
    @Field
    var uid: String? = null

    @Field
    var key: String? = null

    @Field
    var city: String? = null

    @Field
    var district: String? = null

    @Field
    var location: Coordinate2D? = null
    
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "key" to key,
            "city" to city,
            "district" to district,
            "location" to location?.toMap()
        )
    }
}

open class PoiSuggestionResultRecord : Record {
    @Field
    var suggestions: List<PoiSuggestionRecord> = emptyList()
    
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "suggestions" to suggestions.map { it.toMap() }
        )
    }
}

open class GeoCoderOptions : Record {
    @Field
    var address: String = ""
    
    @Field
    var city: String? = null
    
    @Field
    var output: String? = null
    
    @Field
    var retCoordType: String? = null
}

open class ReGeoCoderOptions : Record {
    @Field
    var location: Coordinate2D = Coordinate2D()
    
    @Field
    var isLatestAdmin: Boolean? = null
    
    @Field
    var radius: Int? = null
    
    @Field
    var tags: List<String>? = null
    
    @Field
    var extensionsRoad: Boolean? = null
    
    @Field
    var pageSize: Int? = null
    
    @Field
    var pageNum: Int? = null
}

open class GeoCodeResultRecord : Record {
    @Field
    var location: Coordinate2D = Coordinate2D()
    
    @Field
    var precise: Int = 0
    
    @Field
    var confidence: Int = 0
    
    @Field
    var level: String = ""
    
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "location" to location.toMap(),
            "precise" to precise,
            "confidence" to confidence,
            "level" to level
        )
    }
}

open class ReGeoCodeAddressComponentRecord : Record {
    @Field
    var country: String = ""
    
    @Field
    var countryCode: String = ""
    
    @Field
    var countryCodeISO: String = ""
    
    @Field
    var countryCodeISO2: String = ""
    
    @Field
    var province: String = ""
    
    @Field
    var city: String = ""
    
    @Field
    var cityLevel: Int = 0
    
    @Field
    var district: String = ""
    
    @Field
    var town: String = ""
    
    @Field
    var townCode: String = ""
    
    @Field
    var street: String = ""
    
    @Field
    var streetNumber: String = ""
    
    @Field
    var direction: String = ""
    
    @Field
    var distance: String = ""
    
    @Field
    var adcode: String = ""
    
    @Field
    var adcodeFull: String = ""
    
    @Field
    var provinceCode: String = ""
    
    @Field
    var cityCode: String = ""
    
    @Field
    var districtCode: String = ""
    
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "country" to country,
            "countryCode" to countryCode,
            "countryCodeISO" to countryCodeISO,
            "countryCodeISO2" to countryCodeISO2,
            "province" to province,
            "city" to city,
            "cityLevel" to cityLevel,
            "district" to district,
            "town" to town,
            "townCode" to townCode,
            "street" to street,
            "streetNumber" to streetNumber,
            "direction" to direction,
            "distance" to distance,
            "adcode" to adcode,
            "adcodeFull" to adcodeFull,
            "provinceCode" to provinceCode,
            "cityCode" to cityCode,
            "districtCode" to districtCode
        )
    }
}

open class ReGeoCodeResultRecord : Record {
    @Field
    var formattedAddress: String = ""
    
    @Field
    var addressComponent: ReGeoCodeAddressComponentRecord = ReGeoCodeAddressComponentRecord()
    
    @Field
    var business: String = ""
    
    @Field
    var pois: List<PoiInfoRecord> = emptyList()
    
    @Field
    var roads: List<Any> = emptyList()
    
    @Field
    var roadIntersections: List<Any> = emptyList()
    
    @Field
    var aois: List<Any> = emptyList()
    
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "formattedAddress" to formattedAddress,
            "addressComponent" to addressComponent.toMap(),
            "business" to business,
            "pois" to pois.map { it.toMap() },
            "roads" to roads,
            "roadIntersections" to roadIntersections,
            "aois" to aois
        )
    }
}

// 扩展函数可以单独放一个文件
//fun LatLng.toCoordinate2D(): Coordinate2D {
//    return Coordinate2D(this.latitude, this.longitude)
//}
//
//fun BMKCoordinate.toCoordinate2D(): Coordinate2D {
//    return Coordinate2D(this.latitude, this.longitude)
//}
//
//fun CLLocationCoordinate2D.toCoordinate2D(): Coordinate2D {
//    return Coordinate2D(this.latitude, this.longitude)
//}
