import { NitroModules } from 'react-native-nitro-modules'
import type { StoreCountry as StoreCountrySpec } from './specs/StoreCountry.nitro'

export const StoreCountry =
  NitroModules.createHybridObject<StoreCountrySpec>('StoreCountry')
