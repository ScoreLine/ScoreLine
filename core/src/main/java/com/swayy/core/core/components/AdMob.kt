package com.swayy.core.core.components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.swayy.core.R

var mInterstitialAd: InterstitialAd? = null

// load the interstitial ad
fun loadInterstitial(context: Context) {
    InterstitialAd.load(
        context,
        context.getString(R.string.ad_id_interstitial),
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
                Log.d("MainActivity", adError.message)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                Log.d("MainActivity", "Ad was loaded.")
            }
        }
    )
}

// add the interstitial ad callbacks
fun addInterstitialCallbacks(context: Context) {
    mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            Log.d("MainActivity", "Ad failed to show.")
        }

        override fun onAdShowedFullScreenContent() {
            mInterstitialAd = null
            Log.d("MainActivity", "Ad showed fullscreen content.")

            loadInterstitial(context)
        }

        override fun onAdDismissedFullScreenContent() {
            Log.d("MainActivity", "Ad was dismissed.")
        }
    }
}

// show the interstitial ad
fun showInterstitial(context: Context) {
    val activity = context.findActivity()

    if (mInterstitialAd != null) {
        mInterstitialAd?.show(activity!!)
    } else {
        Log.d("MainActivity", "The interstitial ad wasn't ready yet.")
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
fun LargeAdView() {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            val adView = AdView(context)
            adView.setAdSize(AdSize.LARGE_BANNER)
            adView.adUnitId = "ca-app-pub-3376169146760040/5555105317"
            adView.loadAd(AdRequest.Builder().build())

            adView
        }
    )
}

@Composable
fun AdaptiveBanner() {
    val adWidth = LocalConfiguration.current.screenWidthDp - 32
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // shows an adaptive banner test ad
        AndroidView(
            factory = { context ->
                val adView = AdView(context)
                adView.setAdSize(
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        adWidth
                    )
                )
                adView.adUnitId = context.getString(R.string.ad_id_banner)
                adView.loadAd(AdRequest.Builder().build())

                adView
            }
        )

//        Text("Inline Adaptive Banner", modifier = Modifier.padding(16.dp))
//
//        // shows an inline adaptive banner test ad
//        AndroidView(
//            factory = { context ->
//                val adView = AdView(context)
//                adView.setAdSize(
//                    AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(
//                        context,
//                        adWidth
//                    )
//                )
//                adView.adUnitId = context.getString(R.string.ad_id_banner)
//                adView.loadAd(AdRequest.Builder().build())
//
//                adView
//
//            }
//        )
//
//        Text("Regular Banner", modifier = Modifier.padding(16.dp))
//
//        // shows a traditional banner test ad
//        AndroidView(
//            factory = { context ->
//
//                val adView = AdView(context)
//                adView.setAdSize(AdSize.BANNER)
//                adView.adUnitId = context.getString(R.string.ad_id_banner)
//                adView.loadAd(AdRequest.Builder().build())
//
//                adView
//
//            }
//        )

        // shows an interstitial ad on button click (on the first click only)
//        Button(
//            onClick = {
//                loadInterstitial(context)
//                showInterstitial(context)
//            },
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(text = "Show Interstitial")
//        }
//
//        Column(
//            Modifier.weight(1f),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = "Hello Ad Network!")
//            Text("More texts")
//        }
//
//        Text("And some more texts")
    }
}
