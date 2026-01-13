## ExpoBaiduMap 🗺️

一款专为 Expo 开发的，现代的，全 TypeScript 支持的，高性能的百度地图插件。

🚨：请注意，这款插件现在并没有正式发布，不建议您在生产环境中使用，目前还在开发过程中，还有很多功能没有实现。有什么想法或者问题都可以联系我。
🚨：本插件与传统地图插件的实现方式有差别，目前来说，自定义大头针内容还没有具体的实现方案。其他功能在API调用的方式手会有一些差异，功能不受影响。


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

## 初始化 🔄

在使用地图功能之前，你必须先同意隐私政策，然后调用 `startEngine` 方法来初始化地图引擎。

```ts
import ExpoBaiduMapModule from 'expo-baidu-map'

await ExpoBaiduMapModule.agreePrivacy(true)
await ExpoBaiduMapModule.startEngine()
```

`startEngine`之后，你才能开始使用其他地图功能。

## 例子

```ts
import ExpoBaiduMapModule, { ExpoBaiduMapView } from 'expo-baidu-map'


...
<ExpoBaiduMapView />
```