import { NativeModule, requireNativeModule } from 'expo';

import { ExpoBaiduMapModuleEvents } from './ExpoBaiduMap.types';

declare class ExpoBaiduMapModule extends NativeModule<ExpoBaiduMapModuleEvents> {
  PI: number;
  hello(): string;
  setValueAsync(value: string): Promise<void>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ExpoBaiduMapModule>('ExpoBaiduMap');
