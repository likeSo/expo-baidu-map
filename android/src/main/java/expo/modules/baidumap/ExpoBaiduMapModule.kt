package expo.modules.baidumap

import android.content.pm.PackageManager
import android.graphics.Color
import androidx.annotation.ColorInt
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapLanguage
import com.baidu.mapapi.map.MapStatus
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MyLocationConfiguration
import com.baidu.mapapi.model.LatLng
import expo.modules.kotlin.exception.CodedException
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import java.net.URL

class ExpoBaiduMapModule : Module() {
    override fun definition() = ModuleDefinition {
        Name("ExpoBaiduMap")


        Events("onChange")

        OnCreate {
//            SDKInitializer.initialize(appContext.reactContext?.applicationContext)
        }

        AsyncFunction("agreePrivacy") { agree: Boolean ->
            SDKInitializer.setAgreePrivacy(appContext.reactContext, agree)
        }

        AsyncFunction("setCoordinateType") { type: String ->
            val coordType: CoordType = when (type) {
                "common" -> CoordType.GCJ02;
                else -> CoordType.BD09LL
            }
            SDKInitializer.setCoordType(coordType)
        }

        AsyncFunction("startEngine") {
            val applicationInfo = appContext.reactContext?.packageManager?.getApplicationInfo(
                appContext.reactContext?.packageName.toString(),
                PackageManager.GET_META_DATA
            )
            val apiKey = applicationInfo?.metaData?.getString("BaiduMapApiKey")
            if (apiKey != null && apiKey.isNotBlank()) {
                SDKInitializer.initialize(appContext.reactContext?.applicationContext)
                SDKInitializer.setApiKey(apiKey)
                return@AsyncFunction 1
            } else {
                throw CodedException(
                    "ERR_NO_API_KEY_PROVIDED",
                    "Missing `iosApiKey` field in app.json",
                    null
                )
            }
        }


        View(ExpoBaiduMapView::class) {
            Events("onLoad")

            Prop<Boolean>("active") { view: ExpoBaiduMapView, active: Boolean ->
                if (active) {
                    view.mapView.onResume()
                } else {
                    view.mapView.onPause()
                }
            }

            Prop<String>("mapType") { view: ExpoBaiduMapView, mapType: String ->
                val mapType: Int = when(mapType) {
                    "none" -> BaiduMap.MAP_TYPE_NONE;
                    "standard" -> BaiduMap.MAP_TYPE_NORMAL;
                    "satellite" -> BaiduMap.MAP_TYPE_SATELLITE
                    else -> BaiduMap.MAP_TYPE_NORMAL
                }
                view.mapView.map.mapType = mapType
            }

            Prop<String>("language") { view: ExpoBaiduMapView, language: String ->
                view.mapView.map.mapLanguage = if(language == "english") MapLanguage.ENGLISH else MapLanguage.CHINESE
            }

            Prop("backgroundColor") { view: ExpoBaiduMapView, backgroundColor: Color ->

            }

            Prop("backgroundImage") { view: ExpoBaiduMapView, backgroundColor: String ->
//                view.mapView.map.setMapBackgroundImage()
            }

            Prop("region") { view: ExpoBaiduMapView, region: CoordinateRegion ->
//                view.mapView.map.setMapBackgroundImage()
                view.mapView.map.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(region.toLatLngBounds()))
            }

            Prop("limitMapRegion") { view: ExpoBaiduMapView, region: CoordinateRegion ->
                // Pass: 没有这个属性
            }
            Prop("compassPosition") { view: ExpoBaiduMapView, point: Point ->
                view.mapView.map.compassPosition = android.graphics.Point(point.x, point.y)
            }

            Prop("centerCoordinate") { view: ExpoBaiduMapView, point: Coordinate2D ->
                view.mapView.map.animateMapStatus(MapStatusUpdateFactory.newLatLng(point.toLatLng()))
            }

            Prop("showsUserLocation") { view: ExpoBaiduMapView, showsUserLocation: Boolean ->
                view.mapView.map.isMyLocationEnabled = showsUserLocation
            }

            Prop("inDoorMapEnabled") { view: ExpoBaiduMapView, inDoorMapEnabled: Boolean ->
                view.mapView.map.setIndoorEnable(inDoorMapEnabled)
            }

            Prop("userTrackingMode") { view: ExpoBaiduMapView, userTrackingMode: String ->
                val locationMode: MyLocationConfiguration.LocationMode = when (userTrackingMode) {
                    "follow" ->  MyLocationConfiguration.LocationMode.FOLLOWING;
                    "heading" -> MyLocationConfiguration.LocationMode.COMPASS;
                    else -> MyLocationConfiguration.LocationMode.NORMAL;
                }
                val configuration = MyLocationConfiguration.Builder(locationMode, true).build();
                view.mapView.map.setMyLocationConfiguration(configuration)
            }

            Prop("circles") { view: ExpoBaiduMapView, circles: Array<Any> ->
//                view.circles = circles
            }
            Prop("polygons") { view: ExpoBaiduMapView, polygons: Array<Any> ->
//                view.polygons = polygons
            }

            Prop("textMarkers") { view: ExpoBaiduMapView, textMarkers: Array<TextMarker> ->
//                view.polygons = polygons
                view.textMarkers = textMarkers
                view.reloadOverlays()
            }


            AsyncFunction("zoomIn") { view: ExpoBaiduMapView ->
//                view.mapView.map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(MapStatus.Builder().zoom()))
            }

            AsyncFunction("zoomOut") { view: ExpoBaiduMapView ->
//                view.mapView.zoomOut()
            }

        }
    }
}
