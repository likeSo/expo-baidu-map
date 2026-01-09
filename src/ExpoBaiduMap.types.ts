import type { ColorValue, StyleProp, ViewStyle } from 'react-native';

export type OnLoadEventPayload = {
  url: string;
};

export type ExpoBaiduMapModuleEvents = {
  onChange: (params: ChangeEventPayload) => void;
};

export type ChangeEventPayload = {
  value: string;
};

export type MapType = 'standard' | 'satellite' | 'none'

export type UserTrackingMode = 'heading' | 'follow' | 'followWithHeading';

export type CoordinateType = 'gps' | 'common' | 'bd09ll';

export type ExpoBaiduMapViewProps = {
  style?: StyleProp<ViewStyle>;
  active: boolean;
  mapType?: MapType;
  language?: 'english' | 'chinese';
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
}

export type Size = {
  width: number;
  height: number;
}

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
}

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
}

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
}