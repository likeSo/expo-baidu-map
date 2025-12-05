import { registerWebModule, NativeModule } from 'expo';

import { ExpoBaiduMapModuleEvents } from './ExpoBaiduMap.types';

class ExpoBaiduMapModule extends NativeModule<ExpoBaiduMapModuleEvents> {
  PI = Math.PI;
  async setValueAsync(value: string): Promise<void> {
    this.emit('onChange', { value });
  }
  hello() {
    return 'Hello world! 👋';
  }
}

export default registerWebModule(ExpoBaiduMapModule, 'ExpoBaiduMapModule');
