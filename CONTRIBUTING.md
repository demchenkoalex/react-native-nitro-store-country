# Contributing

Thank you for your interest in contributing to `react-native-nitro-store-country`! This guide will help you get started with development.

## Development Setup

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/demchenkoalex/react-native-nitro-store-country.git
   cd react-native-nitro-store-country
   ```

2. **Install dependencies:**

   ```bash
   bun install
   ```

3. **Install example app dependencies:**
   ```bash
   cd example
   bun install
   cd ..
   ```

### Running the Example App

#### iOS

```bash
cd example/ios
pod install
cd ..
bun run ios
```

#### Android

```bash
cd example
bun run android
```

### Development Workflow

1. **Make changes to the Nitro spec** (`src/specs/StoreCountry.nitro.ts`)
2. **Generate Nitro code:**
   ```bash
   bunx nitrogen
   ```
   This updates the generated code in `nitrogen/` and native implementations in `ios/` and `android/`
3. **Update native implementations** if needed:
   - **iOS**: Modify files in `ios/` folder (Swift implementations)
   - **Android**: Modify files in `android/` folder (Kotlin implementations)
   - If you add new files to `ios/`, run `pod install` in the example app
4. **Update TypeScript exports** in `src/index.ts` if the API changes
5. **Build TypeScript:**
   ```bash
   bun run typescript
   ```
6. **Run the example app** to verify changes:
   - **iOS**: `cd example/ios && pod install && cd .. && bun run ios`
   - **Android**: `cd example && bun run android`
7. **Test your changes** in a real React Native app

### Pull Requests

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Make your changes
4. Update documentation if needed
5. Submit a pull request with a clear description

## Need Help?

- Check existing issues on GitHub

- Check existing issues on GitHub
- Create a new issue for bugs or feature requests
- Join the discussion in GitHub Discussions

Thank you for contributing! 🎉
