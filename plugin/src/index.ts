import { ConfigPlugin, withInfoPlist } from "expo/config-plugins";

const withExpoBaiduMap: ConfigPlugin<{
  iosApiKey: string;
  androidApiKey: string;
}> = (config, props) => {
  config = withInfoPlist(config, (config) => {
    const queriesSchemes = config.modResults.LSApplicationQueriesSchemes || [];
    if (!queriesSchemes.includes("baidumap")) {
      queriesSchemes.push("baidumap");
      config.modResults.LSApplicationQueriesSchemes = queriesSchemes;
    }

    if (props.iosApiKey) {
        config.modResults['BaiduMapApiKey'] = props.iosApiKey;
    }

    return config;
  });
  return config;
};

export default withExpoBaiduMap;
