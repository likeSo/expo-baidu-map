import type { StyleProp, ViewStyle } from 'react-native';

export type OnLoadEventPayload = {
  url: string;
};

export type ExpoBaiduMapModuleEvents = {
  onChange: (params: ChangeEventPayload) => void;
};

export type ChangeEventPayload = {
  value: string;
};

export type ExpoBaiduMapViewProps = {
  url: string;
  onLoad: (event: { nativeEvent: OnLoadEventPayload }) => void;
  style?: StyleProp<ViewStyle>;
};


export type MapType = 'normal' | 'satellite' | 'standard';

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