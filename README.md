# react-native-nitro-store-country

⚡️ Lightning-fast store country detection for React Native using [Nitro Modules](https://nitro.margelo.com)

Get the user's App Store (iOS) or Google Play (Android) country code with a single call. Built with Nitro for maximum performance.

## Features

- ⚡️ **Lightning-fast** - Built with Nitro Modules for maximum performance
- 🎯 **Accurate** - Gets store country from StoreKit (iOS) and Google Play Billing (Android)
- 💾 **Fetch once** - Queries store APIs once and saves the result
- 🔄 **Force refresh** - Option to refetch when needed
- 📱 **System country** - Access device locale country when store country is unavailable
- 🍎 **[iOS] Auto-fallback** - Automatically uses system country for non-App Store builds (TestFlight returns "USA")
- 🍎 **[iOS] Alpha-2 codes** - Converts StoreKit's alpha-3 codes to alpha-2 format
- ✨ **TypeScript** - Full type safety out of the box
- 🎨 **Simple API** - Just one method and one property

## Installation

```bash
npm install react-native-nitro-store-country react-native-nitro-modules
```

```bash
yarn add react-native-nitro-store-country react-native-nitro-modules
```

```bash
bun add react-native-nitro-store-country react-native-nitro-modules
```

### iOS

```bash
cd ios && pod install
```

### Android

No additional steps required. The library uses autolinking.

### Expo

Works with Expo Development Builds via EAS:

```bash
npx expo install react-native-nitro-store-country react-native-nitro-modules
eas build --profile development
```

> **Note:** Not compatible with Expo Go (requires native code).

## Usage

### Basic Usage

```typescript
import { StoreCountry } from 'react-native-nitro-store-country'

// Get store country (saves result after first call)
const storeCountry = await StoreCountry.getStoreCountry()
console.log(storeCountry) // "US", "GB", "DE", etc. or undefined

// Get device/system country (synchronous)
const systemCountry = StoreCountry.systemCountry
console.log(systemCountry) // "US", "GB", "DE", etc.
```

### Force Refresh

```typescript
// Force refetch when needed
const freshCountry = await StoreCountry.getStoreCountry(true)
```

### React Hook Example

```typescript
import { useEffect, useState } from 'react'
import { StoreCountry } from 'react-native-nitro-store-country'

const useStoreCountry = () => {
  const [country, setCountry] = useState(StoreCountry.systemCountry)

  useEffect(() => {
    StoreCountry.getStoreCountry().then(result => {
      // Update to store country if available, otherwise keep system country
      if (result !== undefined) {
        setCountry(result)
      }
    })
  }, [])

  return country
}

const App = () => {
  const country = useStoreCountry()

  return (
    <View>
      <Text>Country: {country ?? 'Unknown'}</Text>
    </View>
  )
}
```

## API Reference

### `getStoreCountry(force?: boolean): Promise<string | undefined>`

Gets the user's store country code (ISO 3166-1 alpha-2).

**Parameters:**

- `force` (optional): If `true`, forces a fresh fetch from the store API, ignoring any previously saved result. Default: `false`

**Returns:**

- `Promise<string | undefined>` - Two-letter country code (e.g., `"US"`, `"GB"`) or `undefined` if unavailable

**Behavior:**

- First call: Fetches from native store API (StoreKit/Google Play Billing)
- Subsequent calls: Returns saved result
- Saves `undefined` results to avoid repeated failed API calls
- Pass `force: true` to ignore saved result

### `systemCountry: string | undefined`

The device's system locale country (synchronous, readonly).

**Returns:**

- `string | undefined` - Two-letter country code from device locale settings

**Source:**

- **iOS:** `Locale.current.region?.identifier`
- **Android:** `Locale.getDefault().country`

## Platform-Specific Notes

### iOS

- **App Store builds:** Returns accurate store country from StoreKit
- **TestFlight/Debug/Simulator:** Automatically falls back to `systemCountry` (StoreKit returns "USA" in these environments, so the library uses system locale instead)
- **Alpha-3 to Alpha-2 conversion:** Converts StoreKit's 3-letter country codes (e.g., "USA") to 2-letter ISO 3166-1 alpha-2 codes (e.g., "US") to match `systemCountry` format
- **Automatic fallback:** Only happens on iOS for non-App Store builds. On App Store, returns StoreKit's value directly

### Android

- Uses Google Play Billing Library to fetch store country
- Returns `undefined` if:
  - Device doesn't have Google Play Services
  - User is not signed into Google Play
  - Billing client connection fails

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository.

## License

MIT

---

Made with ❤️ using [Nitro Modules](https://nitro.margelo.com)
