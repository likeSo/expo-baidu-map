import * as React from "react";

import { ExpoBaiduMapViewProps } from "./ExpoBaiduMap.types";

export default function ExpoBaiduMapView(props: ExpoBaiduMapViewProps) {
  // Web implementation of Baidu Map View with empty methods to avoid errors
  // All map style properties and overlays are accepted but not implemented
  return (
    <div
      style={{
        width: "100%",
        height: "100%",
        backgroundColor: "#f0f0f0",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        color: "#666",
        fontSize: "14px",
      }}
    >
      Baidu Map is not supported on web platform. This is a placeholder view.
    </div>
  );
}
