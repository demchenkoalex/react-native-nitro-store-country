/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import { StatusBar, StyleSheet, Text, useColorScheme, View } from 'react-native';
import {
  SafeAreaProvider,
} from 'react-native-safe-area-context';
import { StoreCountry } from 'react-native-nitro-store-country';
import { useEffect, useState } from 'react';

function App() {
  const isDarkMode = useColorScheme() === 'dark';

  return (
    <SafeAreaProvider>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <AppContent />
    </SafeAreaProvider>
  );
}

function AppContent() {
  const [storeCountryResult, setStoreCountryResult] = useState<string | undefined>();

  useEffect(() => {
    StoreCountry.getStoreCountry().then(result => {
      setStoreCountryResult(result);
    })
  }, [])

  return (
    <View style={styles.container}>
      <Text>Store country: {storeCountryResult}</Text>
      <Text>System country: {StoreCountry.systemCountry}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

export default App;
