import { type HybridObject } from 'react-native-nitro-modules'

export interface StoreCountry extends HybridObject<{
  ios: 'swift'
  android: 'kotlin'
}> {
  getStoreCountry(force?: boolean): Promise<string | undefined>
  readonly systemCountry?: string
}
