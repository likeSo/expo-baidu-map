import { NativeModule, requireNativeModule } from "expo";

import {
  CoordinateType,
  ExpoBaiduMapModuleEvents,
  PoiBoundsSearchOptions,
  PoiCitySearchOptions,
  PoiDetailResult,
  PoiDetailSearchOptions,
  PoiSearchResult,
  PoiNearbySearchOptions,
  PoiSuggestionOptions,
  PoiSuggestionResult,
} from "./ExpoBaiduMap.types";

declare class ExpoBaiduMapModule extends NativeModule<ExpoBaiduMapModuleEvents> {
  startEngine(): Promise<void>;
  agreePrivacy(): Promise<void>;
  setCoordinateType(type: CoordinateType): Promise<void>;

  poiSearchInCity(options: PoiCitySearchOptions): Promise<PoiSearchResult>;
  poiSearchNearby(options: PoiNearbySearchOptions): Promise<PoiSearchResult>;
  poiSearchInBounds(options: PoiBoundsSearchOptions): Promise<PoiSearchResult>;
  poiSearchDetail(options: PoiDetailSearchOptions): Promise<PoiDetailResult>;
  poiSuggestion(options: PoiSuggestionOptions): Promise<PoiSuggestionResult>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ExpoBaiduMapModule>("ExpoBaiduMap");
