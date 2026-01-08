import { NativeModule, requireNativeModule } from 'expo';

import { ExpoBaiduMapModuleEvents } from './ExpoBaiduMap.types';

declare class ExpoBaiduMapModule extends NativeModule<ExpoBaiduMapModuleEvents> {
  startEngine(): Promise<void>;
  agreePrivacy(): Promise<void>;
  setCoordinateType(type: string): Promise<void>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ExpoBaiduMapModule>('ExpoBaiduMap');
