package com.swayy.news.presentation.components

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.swayy.core.R

@Composable
fun NewsDetail(
    news: String,
    navigateBack: () -> Unit,
) {
    val isWebViewLoading = remember { mutableStateOf(true) }

    fun convertToOriginalString(encodedString: String): String {
        val originalString = encodedString.replace("_SLASH_", "/")
        return originalString
    }

    val my_url = convertToOriginalString(news)

    fun addPrefixToString(input: String): String {
        val prefix = "goal.com"
        return prefix + input
    }

    val final = addPrefixToString(my_url)

    Column (modifier = Modifier
        .fillMaxSize()
        ){
        Surface (tonalElevation = 4.dp, shadowElevation = 4.dp, modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onPrimary){
            Row(modifier = Modifier.padding(start = 10.dp, end = 12.dp, top = 40.dp)) {

                IconButton(onClick = navigateBack) {
                    Icon(
                        painter = painterResource(R.drawable.ic_round_arrow_back_24),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = "News",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 12.dp, bottom = 12.dp)
                        .align(Alignment.CenterVertically),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold

                )

            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                isWebViewLoading.value = false
                            }
                        }
                    }
                },
                update = { view ->
                    view.loadUrl(convertToOriginalString(final))
                },
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
            )
            if (isWebViewLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }

    Log.e("test", final)

}