import * as React from 'react';

import { ExpoBaiduMapViewProps } from './ExpoBaiduMap.types';

export default function ExpoBaiduMapView(props: ExpoBaiduMapViewProps) {
  return (
    <div>
      <iframe
        style={{ flex: 1 }}
        src={props.url}
        onLoad={() => props.onLoad({ nativeEvent: { url: props.url } })}
      />
    </div>
  );
}
