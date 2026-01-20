import type { ColorValue, StyleProp, ViewStyle } from "react-native";

export type OnLoadEventPayload = {
  url: string;
};

export type ExpoBaiduMapModuleEvents = {
  onChange: (params: ChangeEventPayload) => void;
  onGetPoiCitySearchResult: (
    result: PoiSearchResult | { errorCode: number },
  ) => void;
  onGetPoiNearbySearchResult: (
    result: PoiSearchResult | { errorCode: number },
  ) => void;
  onGetPoiBoundsSearchResult: (
    result: PoiSearchResult | { errorCode: number },
  ) => void;
  onGetPoiDetailSearchResult: (
    result: PoiDetailResult | { errorCode: number },
  ) => void;
  onGetSuggestionResult: (
    result: PoiSuggestionResult | { errorCode: number },
  ) => void;
  onGetGeoCodeResult: (result: GeoCodeResult | { errorCode: number }) => void;
  onGetReGeoCodeResult: (
    result: ReGeoCodeResult | { errorCode: number },
  ) => void;
};

export type ChangeEventPayload = {
  value: string;
};

export type MapType = "standard" | "satellite" | "none";

export type UserTrackingMode = "heading" | "follow" | "followWithHeading";

export type CoordinateType = "gps" | "common" | "bd09ll";

export type LogoPosition =
  | "leftBottom"
  | "leftTop"
  | "centerBottom"
  | "centerTop"
  | "rightBottom"
  | "rightTop";

export type ExpoBaiduMapViewProps = {
  style?: StyleProp<ViewStyle>;
  active: boolean;
  mapType?: MapType;
  language?: "english" | "chinese";
  backgroundColor?: ColorValue;
  backgroundImage?: string;
  region?: CoordinateRegion;
  limitMapRegion?: CoordinateRegion;
  compassPosition?: Point;
  centerCoordinate?: Coordinate2D;
  showsUserLocation?: boolean;
  inDoorMapEnabled?: boolean;
  userTrackingMode?: UserTrackingMode;
  // Map style properties
  showsScale?: boolean;
  showsZoomControls?: boolean;
  rotationEnabled?: boolean;
  doubleClickZoomEnabled?: boolean;
  showsCompass?: boolean;
  logoPosition?: LogoPosition;
  logoOffset?: Point;
  scrollEnabled?: boolean;
  zoomEnabled?: boolean;
  tiltEnabled?: boolean;
  maxZoomLevel?: number;
  minZoomLevel?: number;
  // Overlays
  markers?: Marker[];
  polylines?: Polyline[];
  arcs?: Arc[];
  polygons?: Polygon[];
  circles?: Circle[];
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
 * 点标记覆盖物。
 */
export type Marker = {
  /**
   * 点标记的坐标点。
   */
  coordinate: Coordinate2D;
  /**
   * 点标记的唯一标识符。必填，因为你需要区分多个点标记。
   */
  key: string;
  /**
   * 点标记的标题。
   */
  title?: string;
  /**
   * 点标记的副标题。
   */
  subtitle?: string;
  /**
   * 点标记的图标。
   */
  icon?: string;
  /**
   * 点标记的旋转角度。
   */
  rotation?: number;
  /**
   * 点标记的透明度。
   */
  alpha?: number;
  /**
   * 点标记是否可点击。
   */
  isClickable?: boolean;
  /**
   * 点标记的Z轴顺序。
   */
  zIndex?: number;
};

/**
 * 折线覆盖物。
 */
export type Polyline = {
  /**
   * 折线的坐标点数组。
   */
  coordinates: Coordinate2D[];
  /**
   * 折线的颜色。
   */
  strokeColor: string;
  /**
   * 折线的宽度。
   */
  strokeWidth: number;
  /**
   * 折线是否可点击。
   */
  isClickable?: boolean;
  /**
   * 折线的Z轴顺序。
   */
  zIndex?: number;
};

/**
 * 弧线覆盖物。
 */
export type Arc = {
  /**
   * 弧线的坐标点数组。
   */
  coordinates: Coordinate2D[];
  /**
   * 弧线的颜色。
   */
  strokeColor: string;
  /**
   * 弧线的宽度。
   */
  lineWidth: number;
  /**
   * 弧线的Z轴顺序。
   */
  zIndex?: number;
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
   * 多边形的填充颜色。
   */
  fillColor: string;
  /**
   * 多边形的边框颜色。
   */
  strokeColor: string;
  /**
   * 多边形的边框宽度。
   */
  strokeWidth: number;
  /**
   * 多边形是否可点击。
   */
  isClickable?: boolean;
  /**
   * 多边形的Z轴顺序。
   */
  zIndex?: number;
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
  /**
   * 圆是否可点击。
   */
  isClickable?: boolean;
  /**
   * 圆的Z轴顺序。
   */
  zIndex?: number;
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
