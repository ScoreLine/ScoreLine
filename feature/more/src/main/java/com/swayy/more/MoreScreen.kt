package com.swayy.more

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swayy.core.R
import com.swayy.more.settings.components.PreferenceRow

@Composable
fun MoreScreen(
    navigateSettings: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ScoreLine",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

//        Divider(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 12.dp)
//        )

        PreferenceRow(
            title = "Settings",
            painter = painterResource(R.drawable.ic_settings_24),
            onClick = navigateSettings
        )
        PreferenceRow(
            title = "Folders",
            painter = rememberVectorPainter(Icons.Outlined.Folder),
            onClick = navigateSettings
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
                style = MaterialTheme.typography.titleSmall,
                fontSize = 12.sp
            )

            Text(
                text = "App Version: ${getAppVersionName(context)}",
                modifier = Modifier,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 11.sp
            )
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