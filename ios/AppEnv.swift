import Foundation

enum AppEnvironment: String {
    case simulator
    case debug
    case testFlight
    case appStore
}

struct AppEnv {
    static var current: AppEnvironment {
#if targetEnvironment(simulator)
        return .simulator
#elseif DEBUG
        return .debug
#else
        return hasSandboxReceipt ? .testFlight : .appStore
#endif
    }

    static var isTestFlight: Bool {
        current == .testFlight
    }

    static var isAppStore: Bool {
        current == .appStore
    }

    private static var hasSandboxReceipt: Bool {
        guard let url = Bundle.main.appStoreReceiptURL else { return false }
        return url.lastPathComponent == "sandboxReceipt" || url.path.contains("sandboxReceipt")
    }
}
