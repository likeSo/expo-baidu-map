import { registerWebModule, NativeModule } from "expo";

import {
  ExpoBaiduMapModuleEvents,
  PoiBoundsSearchOptions,
  PoiCitySearchOptions,
  PoiDetailSearchOptions,
  PoiNearbySearchOptions,
  PoiSuggestionOptions,
  GeoCoderOptions,
  ReGeoCoderOptions,
} from "./ExpoBaiduMap.types";

class ExpoBaiduMapModule extends NativeModule<ExpoBaiduMapModuleEvents> {
  PI = Math.PI;
  async setValueAsync(value: string): Promise<void> {
    this.emit("onChange", { value });
  }
  hello() {
    return "Hello world! 👋";
  }

  async poiSearchInCity(_options: PoiCitySearchOptions): Promise<boolean> {
    throw new Error("ExpoBaiduMap POI is not supported on web.");
  }

  async poiSearchNearby(_options: PoiNearbySearchOptions): Promise<boolean> {
    throw new Error("ExpoBaiduMap POI is not supported on web.");
  }

  async poiSearchInBounds(_options: PoiBoundsSearchOptions): Promise<boolean> {
    throw new Error("ExpoBaiduMap POI is not supported on web.");
  }

  async poiSearchDetail(_options: PoiDetailSearchOptions): Promise<boolean> {
    throw new Error("ExpoBaiduMap POI is not supported on web.");
  }

  async poiSuggestion(_options: PoiSuggestionOptions): Promise<boolean> {
    throw new Error("ExpoBaiduMap POI is not supported on web.");
  }

  async geoCode(_options: GeoCoderOptions): Promise<boolean> {
    throw new Error("ExpoBaiduMap GeoCoder is not supported on web.");
  }

  async reGeoCode(_options: ReGeoCoderOptions): Promise<boolean> {
    throw new Error("ExpoBaiduMap ReGeoCoder is not supported on web.");
  }
}

export default registerWebModule(ExpoBaiduMapModule, "ExpoBaiduMapModule");
