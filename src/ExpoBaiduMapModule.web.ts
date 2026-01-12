import { registerWebModule, NativeModule } from "expo";

import {
  ExpoBaiduMapModuleEvents,
  PoiBoundsSearchOptions,
  PoiCitySearchOptions,
  PoiDetailResult,
  PoiDetailSearchOptions,
  PoiNearbySearchOptions,
  PoiSearchResult,
  PoiSuggestionOptions,
  PoiSuggestionResult,
} from "./ExpoBaiduMap.types";

class ExpoBaiduMapModule extends NativeModule<ExpoBaiduMapModuleEvents> {
  PI = Math.PI;
  async setValueAsync(value: string): Promise<void> {
    this.emit("onChange", { value });
  }
  hello() {
    return "Hello world! 👋";
  }

  async poiSearchInCity(
    _options: PoiCitySearchOptions
  ): Promise<PoiSearchResult> {
    throw new Error("ExpoBaiduMap POI is not supported on web.");
  }

  async poiSearchNearby(
    _options: PoiNearbySearchOptions
  ): Promise<PoiSearchResult> {
    throw new Error("ExpoBaiduMap POI is not supported on web.");
  }

  async poiSearchInBounds(
    _options: PoiBoundsSearchOptions
  ): Promise<PoiSearchResult> {
    throw new Error("ExpoBaiduMap POI is not supported on web.");
  }

  async poiSearchDetail(
    _options: PoiDetailSearchOptions
  ): Promise<PoiDetailResult> {
    throw new Error("ExpoBaiduMap POI is not supported on web.");
  }

  async poiSuggestion(
    _options: PoiSuggestionOptions
  ): Promise<PoiSuggestionResult> {
    throw new Error("ExpoBaiduMap POI is not supported on web.");
  }
}

export default registerWebModule(ExpoBaiduMapModule, "ExpoBaiduMapModule");
