package com.margelo.nitro.nitrostorecountry

import com.margelo.nitro.core.*
import com.margelo.nitro.NitroModules
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.GetBillingConfigParams
import com.android.billingclient.api.PendingPurchasesParams
import java.util.Locale

class HybridStoreCountry : HybridStoreCountrySpec() {
    override val systemCountry: String?
        get() {
            val country = Locale.getDefault().country
            return country.ifEmpty { null }
        }

    private val appContext: Context by lazy {
        val ctx = NitroModules.applicationContext
        requireNotNull(ctx) { "NitroModules.applicationContext is null" }
        ctx
    }

    private var cachedStoreCountry: String? = null
    private var hasFetched = false

    override fun getStoreCountry(force: Boolean?): Promise<String?> {
        val shouldForce = force ?: false

        if (shouldForce) {
            hasFetched = false
            cachedStoreCountry = null
        }

        if (hasFetched) {
            return Promise.resolved(cachedStoreCountry)
        }

        val promise = Promise<String?>()
        var isResolved = false

        fun resolveWithResult(storeCountry: String?) {
            if (isResolved) return
            cachedStoreCountry = storeCountry
            hasFetched = true
            isResolved = true
            promise.resolve(storeCountry)
        }

        val billingClient =
            BillingClient.newBuilder(appContext).setListener { _, _ -> }.enablePendingPurchases(
                PendingPurchasesParams.newBuilder().enableOneTimeProducts().build()
            ).build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (isResolved) return

                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    val params = GetBillingConfigParams.newBuilder().build()
                    billingClient.getBillingConfigAsync(params) { result, config ->
                        if (isResolved) return@getBillingConfigAsync

                        val storeCountry =
                            if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                                config?.countryCode
                            } else {
                                null
                            }
                        resolveWithResult(storeCountry)
                        billingClient.endConnection()
                    }
                } else {
                    resolveWithResult(null)
                    billingClient.endConnection()
                }
            }

            override fun onBillingServiceDisconnected() {
                resolveWithResult(null)
                billingClient.endConnection()
            }
        })

        return promise
    }
}
