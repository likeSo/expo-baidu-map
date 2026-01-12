// Reexport the native module. On web, it will be resolved to ExpoBaiduMapModule.web.ts
// and on native platforms to ExpoBaiduMapModule.ts
export { default } from "./ExpoBaiduMapModule";
export { default as BaiduMapView } from "./ExpoBaiduMapView";
export * from "./ExpoBaiduMap.types";
