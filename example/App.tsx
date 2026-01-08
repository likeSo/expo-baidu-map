import { useEvent } from 'expo';
import ExpoBaiduMap, { BaiduMapView } from 'expo-baidu-map';
import { Button, SafeAreaView, ScrollView, Text, View } from 'react-native';

export default function App() {

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.container}>
        <Text style={styles.header}>Module API Example</Text>
        <Group name="Functions">
          <Button title='初始化引擎' onPress={() => ExpoBaiduMap.startEngine()} />
        </Group>
        <Group name="Async functions">
          <Button
            title="Set value"
            onPress={async () => {
              await ExpoBaiduMap.setValueAsync('Hello from JS!');
            }}
          />
        </Group>
        <Group name="Views">
          <BaiduMapView
            active={true}
            style={styles.view}
          />
        </Group>
      </ScrollView>
    </SafeAreaView>
  );
}

function Group(props: { name: string; children: React.ReactNode }) {
  return (
    <View style={styles.group}>
      <Text style={styles.groupHeader}>{props.name}</Text>
      {props.children}
    </View>
  );
}

const styles = {
  header: {
    fontSize: 30,
    margin: 20,
  },
  groupHeader: {
    fontSize: 20,
    marginBottom: 20,
  },
  group: {
    margin: 20,
    backgroundColor: '#fff',
    borderRadius: 10,
    padding: 20,
  },
  container: {
    flex: 1,
    backgroundColor: '#eee',
  },
  view: {
    flex: 1,
    height: 200,
  },
};
