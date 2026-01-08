package expo.modules.baidumap

import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import com.baidu.mapapi.map.MapView
import com.baidu.mapapi.map.Overlay
import com.baidu.mapapi.map.OverlayOptions
import com.baidu.mapapi.map.TextOptions
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.viewevent.EventDispatcher
import expo.modules.kotlin.views.ExpoView

class ExpoBaiduMapView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
  private val onLoad by EventDispatcher()

  var textMarkers: Array<TextMarker>? = null

  internal val mapView = MapView(context).apply {
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

  }

  init {
    addView(mapView)
  }

  fun reloadOverlays() {
    mapView.map.clear()
    val overlays = mutableListOf<OverlayOptions>()
    textMarkers?.forEach { it ->
      val textOptions = TextOptions().apply {
        text(it.text)
      }
      overlays.add(textOptions)
    }

    mapView.map.addOverlays(overlays)
  }
  
}
