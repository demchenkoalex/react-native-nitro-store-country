import Foundation
import NitroModules
import StoreKit

class HybridStoreCountry : HybridStoreCountrySpec {
    var systemCountry: String? {
        if #available(iOS 16, *) {
            return Locale.current.region?.identifier
        } else {
            return Locale.current.regionCode
        }
    }

    private var cachedStoreCountry: String?
    private var hasFetched = false

    func getStoreCountry(force: Bool?) -> Promise<String?> {
        let shouldForce = force ?? false

        if shouldForce {
            hasFetched = false
            cachedStoreCountry = nil
        }

        if hasFetched {
            return Promise.resolved(withResult: cachedStoreCountry)
        }

        return Promise.async { [weak self] in
            let storefront = await Storefront.current
            let storeCountry = storefront?.countryCode.toAlpha2

            // StoreKit returns USA for TestFlight/debug/simulator, fallback to system country in those cases
            let resolvedCountry = AppEnv.isAppStore ? storeCountry : self?.systemCountry

            self?.cachedStoreCountry = resolvedCountry
            self?.hasFetched = true

            return resolvedCountry
        }
    }
}
