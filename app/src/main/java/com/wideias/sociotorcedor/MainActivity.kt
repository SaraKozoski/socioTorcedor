package com.wideias.sociotorcedor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.wideias.sociotorcedor.navigation.AppNavigation
import com.wideias.sociotorcedor.ui.theme.SocioTorcedorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocioTorcedorTheme {
                AppNavigation()
            }
        }
    }
}