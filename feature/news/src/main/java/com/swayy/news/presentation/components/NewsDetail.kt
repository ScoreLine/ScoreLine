package com.swayy.news.presentation.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun NewsDetail(
    news: String,
    navigateBack: () -> Unit,
){
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
            }
        },
        update = { view ->
            view.loadUrl(news)
        },
        modifier = Modifier.fillMaxSize()
    )

}