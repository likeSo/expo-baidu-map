package expo.modules.baidumap

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.viewevent.EventDispatcher
import expo.modules.kotlin.views.ExpoView

// Import our custom overlay types
import expo.modules.baidumap.Marker
import expo.modules.baidumap.Polyline
import expo.modules.baidumap.Arc
import expo.modules.baidumap.Polygon
import expo.modules.baidumap.Circle
import expo.modules.baidumap.TextMarker

class ExpoBaiduMapView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
    private val onLoad by EventDispatcher()
    private val onMapClick by EventDispatcher()
    private val onMarkerClick by EventDispatcher()
    private val onOverlayClick by EventDispatcher()

    var markers: Array<Marker>? = null
    var polylines: Array<Polyline>? = null
    var arcs: Array<Arc>? = null
    var polygons: Array<Polygon>? = null
    var circles: Array<Circle>? = null
    var textMarkers: Array<TextMarker>? = null

    internal val mapView = MapView(context.applicationContext).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    init {
        addView(mapView)
        setupMapListeners()
    }

    private fun setupMapListeners() {
        // Map click listener
        mapView.map.setOnMapClickListener(object : BaiduMap.OnMapClickListener {
            override fun onMapClick(latLng: LatLng?) {
                latLng?.let {
                    onMapClick(
                        mapOf(
                            "coordinate" to mapOf(
                                "latitude" to it.latitude,
                                "longitude" to it.longitude
                            )
                        )
                    )
                }
            }

            override fun onMapPoiClick(mapPoi: MapPoi?) {

            }
        })

        // Marker click listener
        mapView.map.setOnMarkerClickListener(object : BaiduMap.OnMarkerClickListener {

            override fun onMarkerClick(marker: com.baidu.mapapi.map.Marker?): Boolean {
                Log.d("ExpoBaiduMap", marker?.extraInfo.toString())
                return true
            }
        })
    }

    fun reloadOverlays() {
        mapView.map.clear()


        // Markers
        markers?.forEach { marker ->
            val pointOption = MarkerOptions()
                .position(marker.coordinate.toLatLng())
                .title(marker.title)
                .draggable(marker.isClickable ?: false)
                .zIndex(marker.zIndex ?: 0)
                .alpha(marker.alpha ?: 1.0f)
                .rotate(marker.rotation ?: 0.0f)
                .extraInfo(marker.toBundle())

            mapView.map.addOverlay(pointOption)

        }

        // Polylines
        polylines?.forEach { polyline ->
            val polylineOption =
                PolylineOptions().points(polyline.coordinates.map { it.toLatLng() })
                    .color(polyline.strokeColor ?: Color.BLUE).width(polyline.lineWidth ?: 5.0f)
                    .zIndex(polyline.zIndex ?: 0)
                    .extraInfo(polyline.toBundle())

            mapView.map.addOverlay(polylineOption)
        }

        // Arcs
        arcs?.forEach { arc ->
            if (arc.coordinates.size >= 3) {
                val arcOption = ArcOptions()
                    .color(arc.strokeColor ?: Color.RED)
                    .width(arc.lineWidth ?: 5)
                    .zIndex(arc.zIndex ?: 0)
                    .points(
                        arc.coordinates[0].toLatLng(),
                        arc.coordinates[1].toLatLng(),
                        arc.coordinates[2].toLatLng()
                    ).extraInfo(arc.toBundle())

                val overlay = mapView.map.addOverlay(arcOption)

            }
        }

        // Polygons
        polygons?.forEach { polygon ->
            val polygonOption = PolygonOptions().points(polygon.coordinates.map { it.toLatLng() })
                .stroke(Stroke(polygon.lineWidth ?: 5, polygon.strokeColor ?: Color.BLUE))
                .fillColor(polygon.fillColor ?: Color.argb(77, 0, 255, 0))
                .zIndex(polygon.zIndex ?: 0)
                .extraInfo(polygon.toBundle())

            mapView.map.addOverlay(polygonOption)
        }

        // Circles
        circles?.forEach { circle ->
            val circleOption = CircleOptions()
                .center(circle.center.toLatLng())
                .radius(circle.radius.toInt())
                .fillColor(circle.fillColor ?: Color.argb(77, 0, 0, 255))
                .stroke(Stroke(circle.lineWidth ?: 5, circle.strokeColor ?: Color.BLUE))
                .extraInfo(circle.toBundle())

            mapView.map.addOverlay(circleOption)
        }
    }

}
