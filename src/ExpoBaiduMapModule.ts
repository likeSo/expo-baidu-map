import { NativeModule, requireNativeModule } from 'expo';

import { CoordinateType, ExpoBaiduMapModuleEvents } from './ExpoBaiduMap.types';

declare class ExpoBaiduMapModule extends NativeModule<ExpoBaiduMapModuleEvents> {
  startEngine(): Promise<void>;
  agreePrivacy(): Promise<void>;
  setCoordinateType(type: CoordinateType): Promise<void>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ExpoBaiduMapModule>('ExpoBaiduMap');
