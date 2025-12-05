import { requireNativeView } from 'expo';
import * as React from 'react';

import { ExpoBaiduMapViewProps } from './ExpoBaiduMap.types';

const NativeView: React.ComponentType<ExpoBaiduMapViewProps> =
  requireNativeView('ExpoBaiduMap');

export default function ExpoBaiduMapView(props: ExpoBaiduMapViewProps) {
  return <NativeView {...props} />;
}
