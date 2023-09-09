package com.swayy.more

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swayy.core.R
import com.swayy.more.settings.components.PreferenceRow

@Composable
fun MoreScreen(
    navigateSettings: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primary)
                .statusBarsPadding(),
        ) {
            androidx.compose.material3.Text(
                text = "Settings",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(top = 20.dp, start = 12.dp, end = 12.dp)
            )
            Spacer(modifier = Modifier.weight(2f))

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {


//        Divider(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 12.dp)
//        )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                Row(modifier = Modifier.padding(start = 14.dp, top = 14.dp, bottom = 14.dp)) {
                    androidx.compose.material3.Text(
                        text = "Premium Settings",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Divider(
                        thickness = 0.8.dp, color = Color.LightGray, modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically))
                }

            }

            PreferenceRow(
                title = "Your premium features",
                painter = painterResource(com.swayy.more.R.drawable.one),
                onClick = {}
            )

            PreferenceRow(
                title = "Ad-settings",
                painter = painterResource(com.swayy.more.R.drawable.plugin),
                onClick = {}
            )
            PreferenceRow(
                title = "App colours",
                painter = painterResource(com.swayy.more.R.drawable.mol),
                onClick = navigateSettings
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                Row(modifier = Modifier.padding(start = 14.dp, top = 14.dp, bottom = 14.dp)) {
                    androidx.compose.material3.Text(
                        text = "Notifications",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Divider(
                        thickness = 0.8.dp, color = Color.LightGray, modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically))
                }

            }

            PreferenceRow(
                title = "Push Notifications",
                painter = painterResource(com.swayy.more.R.drawable.two),
                onClick = {}
            )
            PreferenceRow(
                title = "Enable teams",
                painter = painterResource(com.swayy.more.R.drawable.three),
                onClick = {}
            )
            PreferenceRow(
                title = "Notification types",
                painter = painterResource(com.swayy.more.R.drawable.four),
                onClick = {}
            )
            PreferenceRow(
                title = "Notification sounds",
                painter = painterResource(com.swayy.more.R.drawable.fivve),
                onClick = {}
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                Row(modifier = Modifier.padding(start = 14.dp, top = 14.dp, bottom = 14.dp)) {
                    androidx.compose.material3.Text(
                        text = "Help",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Divider(
                        thickness = 0.8.dp, color = Color.LightGray, modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically))
                }

            }

            PreferenceRow(
                title = "Support",
                painter = painterResource(com.swayy.more.R.drawable.six),
                onClick = {}
            )
            PreferenceRow(
                title = "FAQ",
                painter = painterResource(com.swayy.more.R.drawable.seven),
                onClick = {}
            )
            PreferenceRow(
                title = "Instagram",
                painter = painterResource(com.swayy.more.R.drawable.eight),
                onClick = {}
            )
            PreferenceRow(
                title = "Facebook",
                painter = painterResource(com.swayy.more.R.drawable.nine),
                onClick = {}
            )
            PreferenceRow(
                title = "Twitter(X)",
                painter = painterResource(com.swayy.more.R.drawable.ten),
                onClick = {}
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                Row(modifier = Modifier.padding(start = 14.dp, top = 14.dp, bottom = 14.dp)) {
                    androidx.compose.material3.Text(
                        text = "More",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Divider(
                        thickness = 0.8.dp, color = Color.LightGray, modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically))
                }

            }

            PreferenceRow(
                title = "Rate app",
                painter = painterResource(com.swayy.more.R.drawable.star),
                onClick = {}
            )
            PreferenceRow(
                title = "Legal notice",
                painter = painterResource(com.swayy.more.R.drawable.warning),
                onClick = {}
            )
            PreferenceRow(
                title = "Privacy",
                painter = painterResource(com.swayy.more.R.drawable.insurance),
                onClick = {}
            )
            Spacer(modifier = Modifier.weight(3f))
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                val context = LocalContext.current


                Text(
                    text = "Made with ❤️ in Kenya 🇰🇪",
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp
                )

                Text(
                    text = "App Version: ${getAppVersionName(context)}",
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp
                )
            }

        }
    }

}

fun getAppVersionName(context: Context): String {
    var versionName = ""
    try {
        val info = context.packageManager?.getPackageInfo(context.packageName, 0)
        versionName = info?.versionName ?: ""
    } catch (e: PackageManager.NameNotFoundException) {
        e.message?.let { Log.e("", it) }
    }
    return versionName
}