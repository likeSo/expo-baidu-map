import { NativeModule, requireNativeModule } from "expo";

import {
  CoordinateType,
  ExpoBaiduMapModuleEvents,
  PoiBoundsSearchOptions,
  PoiCitySearchOptions,
  PoiDetailSearchOptions,
  PoiNearbySearchOptions,
  PoiSuggestionOptions,
  GeoCoderOptions,
  ReGeoCoderOptions,
} from "./ExpoBaiduMap.types";

declare class ExpoBaiduMapModule extends NativeModule<ExpoBaiduMapModuleEvents> {
  startEngine(): Promise<void>;
  agreePrivacy(agree: boolean): Promise<void>;
  setCoordinateType(type: CoordinateType): Promise<void>;

  poiSearchInCity(options: PoiCitySearchOptions): Promise<boolean>;
  poiSearchNearby(options: PoiNearbySearchOptions): Promise<boolean>;
  poiSearchInBounds(options: PoiBoundsSearchOptions): Promise<boolean>;
  poiSearchDetail(options: PoiDetailSearchOptions): Promise<boolean>;
  poiSuggestion(options: PoiSuggestionOptions): Promise<boolean>;

  geoCode(options: GeoCoderOptions): Promise<boolean>;
  reGeoCode(options: ReGeoCoderOptions): Promise<boolean>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ExpoBaiduMapModule>("ExpoBaiduMap");
