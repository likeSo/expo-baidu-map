## ExpoBaiduMap 🗺️

一款专为 Expo 开发的，现代的，全 TypeScript 支持的，高性能的百度地图插件。
🚨：请注意，这款插件现在并没有正式发布，不建议您在生产环境中使用，目前还在开发过程中，还有很多功能没有实现。有什么想法或者问题都可以联系我。

## 安装 📦

```shell
npx expo install expo-baidu-map expo-build-properties
```

## 配置

安卓混淆规则

```txt
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-keep class com.baidu.vi.** {*;}
-dontwarn com.baidu.**
```

```js
expo: {
  plugins: [
    [
      "expo-baidu-map",
      {
        androidApiKey: "安卓app id",
        iosApiKey: " iOS app id",
      },
    ],
    [
      "expo-build-properties",
      {
        android: {
          extraProguardRules: "把上面的混淆规则放到这里",
        },
      },
    ],
  ];
}
```

## 例子

```ts
import ExpoBaiduMapModule, { ExpoBaiduMapView } from 'expo-baidu-map'


ExpoBaiduMapModule.startEngine()
ExpoBaiduMapModule.agreePolicy()
...
<ExpoBaiduMapView />
```