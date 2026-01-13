import type { ColorValue, StyleProp, ViewStyle } from "react-native";

export type OnLoadEventPayload = {
  url: string;
};

export type ExpoBaiduMapModuleEvents = {
  onChange: (params: ChangeEventPayload) => void;
  onGetPoiCitySearchResult: (result: PoiSearchResult | { errorCode: number }) => void;
  onGetPoiNearbySearchResult: (result: PoiSearchResult | { errorCode: number }) => void;
  onGetPoiBoundsSearchResult: (result: PoiSearchResult | { errorCode: number }) => void;
  onGetPoiDetailSearchResult: (result: PoiDetailResult | { errorCode: number }) => void;
  onGetSuggestionResult: (result: PoiSuggestionResult | { errorCode: number }) => void;
  onGetGeoCodeResult: (result: GeoCodeResult | { errorCode: number }) => void;
  onGetReGeoCodeResult: (result: ReGeoCodeResult | { errorCode: number }) => void;
};

export type ChangeEventPayload = {
  value: string;
};

export type MapType = "standard" | "satellite" | "none";

export type UserTrackingMode = "heading" | "follow" | "followWithHeading";

export type CoordinateType = "gps" | "common" | "bd09ll";

export type ExpoBaiduMapViewProps = {
  style?: StyleProp<ViewStyle>;
  active: boolean;
  mapType?: MapType;
  language?: "english" | "chinese";
  backgroundColor?: ColorValue;
  backgroundImage?: string;
  region?: CoordinateRegion;
  // limitMapRegion
  compassPosition?: Point;
  centerCoordinate?: Coordinate2D;
  showsUserLocation?: boolean;
  inDoorMapEnabled?: boolean;
  userTrackingMode?: UserTrackingMode;
  textMarkers?: TextMarker[];
};

export type Coordinate2D = {
  latitude: number;
  longitude: number;
};

export type CoordinateSpan = {
  latitudeDelta: number;
  longitudeDelta: number;
};

export type CoordinateRegion = {
  center: Coordinate2D;
  span: CoordinateSpan;
};

export type Point = {
  x: number;
  y: number;
};

export type Size = {
  width: number;
  height: number;
};

/**
 * 多边形覆盖物。根据一堆坐标点生成一个多边形覆盖物。
 */
export type Polygon = {
  /**
   * 多边形的坐标点数组。
   */
  coordinates: Coordinate2D[];
  /**
   * 多边形的坐标点数组的长度。
   */
  count: number;
};

/**
 * 圆覆盖物。根据一个坐标点和半径生成一个圆覆盖物。
 */
export type Circle = {
  /**
   * 圆的中心坐标点。
   */
  center: Coordinate2D;
  /**
   * 圆的半径。
   */
  radius: number;
  /**
   * 圆的填充颜色。
   */
  fillColor: string;
  /**
   * 圆的边框颜色。
   */
  strokeColor: string;
  /**
   * 圆的边框宽度。
   */
  strokeWidth: number;
};

export type TextMarker = {
  /**
   * 文本标记的坐标点。
   */
  center: Coordinate2D;
  /**
   * 文本标记的文本内容。
   */
  text: string;
  /**
   * 文本标记的背景颜色。
   */
  backgroundColor: string;
  /**
   * 文本标记的文本颜色。
   */
  textColor: string;
  /**
   * 文本标记的文本大小。
   */
  textSize: number;

  // 其他自定义属性，可以增加id参数，用来区分大头针
  [key: string]: any;
};

export type Bounds = {
  northEast: Coordinate2D;
  southWest: Coordinate2D;
};

export type PoiSearchScope = "basic" | "detail";

export type PoiCitySearchOptions = {
  city: string;
  keyword: string;
  pageIndex?: number;
  pageSize?: number;
  scope?: PoiSearchScope;
  tag?: string;
  cityLimit?: boolean;
  returnAddress?: boolean;
};

export type PoiNearbySearchOptions = {
  keyword: string;
  location: Coordinate2D;
  radius?: number;
  pageIndex?: number;
  pageSize?: number;
  scope?: PoiSearchScope;
  tag?: string;
  radiusLimit?: boolean;
  returnAddress?: boolean;
};

export type PoiBoundsSearchOptions = {
  keyword: string;
  bounds: Bounds;
  pageIndex?: number;
  pageSize?: number;
  scope?: PoiSearchScope;
  tag?: string;
  returnAddress?: boolean;
};

export type PoiDetailSearchOptions = {
  uid: string;
};

export type PoiSuggestionOptions = {
  keyword: string;
  city?: string;
  cityLimit?: boolean;
  location?: Coordinate2D;
  hotWord?: boolean;
};

export type PoiSearchPagination = {
  pageIndex: number;
  pageSize: number;
  totalCount: number;
  totalPage: number;
};

export type PoiInfo = {
  uid: string;
  name: string;
  location?: Coordinate2D;
  address?: string;
  province?: string;
  city?: string;
  area?: string;
  phone?: string;
  tag?: string;
};

export type PoiSearchResult = {
  pagination: PoiSearchPagination;
  pois: PoiInfo[];
};

export type PoiDetailInfo = {
  uid: string;
  name: string;
  location?: Coordinate2D;
  address?: string;
  province?: string;
  city?: string;
  area?: string;
  phone?: string;
  tag?: string;
};

export type PoiDetailResult = {
  poi: PoiDetailInfo;
};

export type PoiSuggestion = {
  uid?: string;
  key?: string;
  city?: string;
  district?: string;
  location?: Coordinate2D;
};

export type PoiSuggestionResult = {
  suggestions: PoiSuggestion[];
};

export type GeoCoderOptions = {
  address: string;
  city?: string;
  output?: "json" | "xml";
  retCoordType?: CoordinateType;
};

export type ReGeoCoderOptions = {
  location: Coordinate2D;
  radius?: number;
  tags?: string[];
  extensionsRoad?: boolean;
  pageSize?: number;
  pageNum?: number;
  isLatestAdmin?: boolean;
};

export type GeoCodeResult = {
  location: Coordinate2D;
  precise: number;
  confidence: number;
  level: string;
};

export type ReGeoCodeAddressComponent = {
  country: string;
  countryCode: string;
  countryCodeISO: string;
  countryCodeISO2: string;
  province: string;
  city: string;
  cityLevel: number;
  district: string;
  town: string;
  townCode: string;
  street: string;
  streetNumber: string;
  direction: string;
  distance: string;
  adcode: string;
  adcodeFull: string;
  provinceCode: string;
  cityCode: string;
  districtCode: string;
};

export type ReGeoCodeResult = {
  formattedAddress: string;
  addressComponent: ReGeoCodeAddressComponent;
  business: string;
  pois: PoiInfo[];
  roads: any[];
  roadIntersections: any[];
  aois: any[];
};
