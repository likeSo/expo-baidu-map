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
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.core.PoiDetailInfo
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener
import com.baidu.mapapi.search.poi.PoiBoundSearchOption
import com.baidu.mapapi.search.poi.PoiCitySearchOption
import com.baidu.mapapi.search.poi.PoiDetailSearchOption
import com.baidu.mapapi.search.poi.PoiDetailSearchResult
import com.baidu.mapapi.search.poi.PoiNearbySearchOption
import com.baidu.mapapi.search.poi.PoiResult
import com.baidu.mapapi.search.poi.PoiSearch
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.baidu.mapapi.search.sug.SuggestionSearchOption
import expo.modules.kotlin.Promise
import expo.modules.kotlin.exception.CodedException
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import java.net.URL

class ExpoBaiduMapModule : Module() {

    override fun definition() = ModuleDefinition {
        Name("ExpoBaiduMap")

        // Defines event names that the module can send to JavaScript.
        Events("onGetPoiCitySearchResult", "onGetPoiNearbySearchResult", "onGetPoiBoundsSearchResult", "onGetPoiDetailSearchResult", "onGetSuggestionResult")

        OnCreate {
            // No initialization here, resources will be created when needed
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

        AsyncFunction("poiSearchInCity") { options: PoiCitySearchOptions ->
            // Create search instance when needed
            val searcher = PoiSearch.newInstance()
            
            // Set listener
            searcher.setOnGetPoiSearchResultListener(object : OnGetPoiSearchResultListener {
                override fun onGetPoiResult(result: PoiResult?) {
                    if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                        sendEvent("onGetPoiCitySearchResult", result.toPoiSearchResultRecord().toMap())
                    } else {
                        val errorCode = result?.error?.ordinal ?: -1
                        sendEvent("onGetPoiCitySearchResult", mapOf("errorCode" to errorCode))
                    }
                }

                override fun onGetPoiDetailResult(result: PoiDetailSearchResult?) {
                    // Not used for this method
                }

                override fun onGetPoiIndoorResult(result: com.baidu.mapapi.search.poi.PoiIndoorResult?) {
                    // Not used
                }

                @Deprecated("Deprecated in Java")
                override fun onGetPoiDetailResult(result: com.baidu.mapapi.search.poi.PoiDetailResult?) {
                    // Not used
                }
            })
            
            // Build search option
            val option = PoiCitySearchOption()
                .city(options.city)
                .keyword(options.keyword)
                .pageNum(options.pageIndex ?: 0)
                .pageCapacity(options.pageSize ?: 10)
                .scope(if (options.scope == "detail") 2 else 1)
                .cityLimit(options.cityLimit ?: false)
            options.tag?.let { option.tag(it) }
            
            // Execute search and get result
            val result = searcher.searchInCity(option)
            
            // Destroy searcher to free resources
            searcher.destroy()
            
            // Return result status
            return@AsyncFunction result
        }

        AsyncFunction("poiSearchNearby") { options: PoiNearbySearchOptions ->
            // Create search instance when needed
            val searcher = PoiSearch.newInstance()
            
            // Set listener
            searcher.setOnGetPoiSearchResultListener(object : OnGetPoiSearchResultListener {
                override fun onGetPoiResult(result: PoiResult?) {
                    if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                        sendEvent("onGetPoiNearbySearchResult", result.toPoiSearchResultRecord().toMap())
                    } else {
                        val errorCode = result?.error?.ordinal ?: -1
                        sendEvent("onGetPoiNearbySearchResult", mapOf("errorCode" to errorCode))
                    }
                }

                override fun onGetPoiDetailResult(result: PoiDetailSearchResult?) {
                    // Not used for this method
                }

                override fun onGetPoiIndoorResult(result: com.baidu.mapapi.search.poi.PoiIndoorResult?) {
                    // Not used
                }

                @Deprecated("Deprecated in Java")
                override fun onGetPoiDetailResult(result: com.baidu.mapapi.search.poi.PoiDetailResult?) {
                    // Not used
                }
            })
            
            // Build search option
            val option = PoiNearbySearchOption()
                .keyword(options.keyword)
                .location(options.location.toLatLng())
                .radius(options.radius ?: 1000)
                .radiusLimit(options.radiusLimit ?: false)
                .pageNum(options.pageIndex ?: 0)
                .pageCapacity(options.pageSize ?: 10)
                .scope(if (options.scope == "detail") 2 else 1)
            options.tag?.let { option.tag(it) }
            
            // Execute search and get result
            val result = searcher.searchNearby(option)
            
            // Destroy searcher to free resources
            searcher.destroy()
            
            // Return result status
            return@AsyncFunction result
        }

        AsyncFunction("poiSearchInBounds") { options: PoiBoundsSearchOptions ->
            // Create search instance when needed
            val searcher = PoiSearch.newInstance()
            
            // Set listener
            searcher.setOnGetPoiSearchResultListener(object : OnGetPoiSearchResultListener {
                override fun onGetPoiResult(result: PoiResult?) {
                    if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                        sendEvent("onGetPoiBoundsSearchResult", result.toPoiSearchResultRecord().toMap())
                    } else {
                        val errorCode = result?.error?.ordinal ?: -1
                        sendEvent("onGetPoiBoundsSearchResult", mapOf("errorCode" to errorCode))
                    }
                }

                override fun onGetPoiDetailResult(result: PoiDetailSearchResult?) {
                    // Not used for this method
                }

                override fun onGetPoiIndoorResult(result: com.baidu.mapapi.search.poi.PoiIndoorResult?) {
                    // Not used
                }

                @Deprecated("Deprecated in Java")
                override fun onGetPoiDetailResult(result: com.baidu.mapapi.search.poi.PoiDetailResult?) {
                    // Not used
                }
            })
            
            // Build search option
            val option = PoiBoundSearchOption()
                .keyword(options.keyword)
                .bound(options.bounds.toLatLngBounds())
                .pageNum(options.pageIndex ?: 0)
                .pageCapacity(options.pageSize ?: 10)
                .scope(if (options.scope == "detail") 2 else 1)
            options.tag?.let { option.tag(it) }
            
            // Execute search and get result
            val result = searcher.searchInBound(option)
            
            // Destroy searcher to free resources
            searcher.destroy()
            
            // Return result status
            return@AsyncFunction result
        }

        AsyncFunction("poiSearchDetail") { options: PoiDetailSearchOptions ->
            // Create search instance when needed
            val searcher = PoiSearch.newInstance()
            
            // Set listener
            searcher.setOnGetPoiSearchResultListener(object : OnGetPoiSearchResultListener {
                override fun onGetPoiResult(result: PoiResult?) {
                    // Not used for this method
                }

                override fun onGetPoiDetailResult(result: PoiDetailSearchResult?) {
                    if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                        val detailRecords = result.poiDetailInfoList?.map { it.toPoiDetailInfoRecord() }
                        val poiDetailResult = PoiDetailResultRecord().apply { poiList = detailRecords }
                        sendEvent("onGetPoiDetailSearchResult", poiDetailResult.toMap())
                    } else {
                        val errorCode = result?.error?.ordinal ?: -1
                        sendEvent("onGetPoiDetailSearchResult", mapOf("errorCode" to errorCode))
                    }
                }

                override fun onGetPoiIndoorResult(result: com.baidu.mapapi.search.poi.PoiIndoorResult?) {
                    // Not used
                }

                @Deprecated("Deprecated in Java")
                override fun onGetPoiDetailResult(result: com.baidu.mapapi.search.poi.PoiDetailResult?) {
                    // Not used
                }
            })
            
            // Build search option
            val option = PoiDetailSearchOption().poiUid(options.uid)
            
            // Execute search and get result
            val result = searcher.searchPoiDetail(option)
            
            // Destroy searcher to free resources
            searcher.destroy()
            
            // Return result status
            return@AsyncFunction result
        }

        AsyncFunction("poiSuggestion") { options: PoiSuggestionOptions ->
            // Create search instance when needed
            val searcher = SuggestionSearch.newInstance()
            
            // Set listener
            searcher.setOnGetSuggestionResultListener(object : OnGetSuggestionResultListener {
                override fun onGetSuggestionResult(result: SuggestionResult?) {
                    if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                        sendEvent("onGetSuggestionResult", result.toPoiSuggestionResultRecord().toMap())
                    } else {
                        val errorCode = result?.error?.ordinal ?: -1
                        sendEvent("onGetSuggestionResult", mapOf("errorCode" to errorCode))
                    }
                }
            })
            
            // Build search option
            val option = SuggestionSearchOption()
                .keyword(options.keyword)
            options.city?.let { option.city(it) }
            options.cityLimit?.let { option.citylimit(it) }
            options.location?.let { option.location(it.toLatLng()) }
            
            // Execute search and get result
            val result = searcher.requestSuggestion(option)
            
            // Destroy searcher to free resources
            searcher.destroy()
            
            // Return result status
            return@AsyncFunction result
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

    private fun PoiResult.toPoiSearchResultRecord(): PoiSearchResultRecord {
        val pagination = PoiSearchPagination().apply {
            pageIndex = currentPageNum
            pageSize = currentPageCapacity
            totalCount = totalPoiNum
            totalPage = totalPageNum
        }
        val pois = (allPoi ?: emptyList()).map { it.toPoiInfoRecord() }
        return PoiSearchResultRecord().apply {
            this.pagination = pagination
            this.pois = pois
        }
    }

    private fun PoiInfo.toPoiInfoRecord(): PoiInfoRecord {
        return PoiInfoRecord().apply {
            uid = this@toPoiInfoRecord.uid ?: ""
            name = this@toPoiInfoRecord.name ?: ""
            location = this@toPoiInfoRecord.location?.let { Coordinate2D(it.latitude, it.longitude) }
            address = this@toPoiInfoRecord.address
            province = this@toPoiInfoRecord.province
            city = this@toPoiInfoRecord.city
            area = this@toPoiInfoRecord.area
            phone = this@toPoiInfoRecord.phoneNum
            tag = this@toPoiInfoRecord.tag
        }
    }

    private fun PoiDetailInfo.toPoiDetailInfoRecord(): PoiDetailInfoRecord {
        return PoiDetailInfoRecord().apply {
            uid = this@toPoiDetailInfoRecord.uid ?: ""
            name = this@toPoiDetailInfoRecord.name ?: ""
            location = this@toPoiDetailInfoRecord.location?.let { Coordinate2D(it.latitude, it.longitude) }
            address = this@toPoiDetailInfoRecord.address
            province = this@toPoiDetailInfoRecord.province
            city = this@toPoiDetailInfoRecord.city
            area = this@toPoiDetailInfoRecord.area
            phone = this@toPoiDetailInfoRecord.telephone
            tag = this@toPoiDetailInfoRecord.tag
        }

    }

    private fun SuggestionResult.toPoiSuggestionResultRecord(): PoiSuggestionResultRecord {
        val suggestions = (allSuggestions ?: emptyList()).map { info ->
            PoiSuggestionRecord().apply {
                uid = info.uid
                key = info.key
                city = info.city
                district = info.district
                location = info.pt?.let { Coordinate2D(it.latitude, it.longitude) }
            }
        }
        return PoiSuggestionResultRecord().apply {
            this.suggestions = suggestions
        }
    }
}


