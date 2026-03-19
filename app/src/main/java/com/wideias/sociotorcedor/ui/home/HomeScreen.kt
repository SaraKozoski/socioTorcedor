package com.wideias.sociotorcedor.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.background

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeColors.Fundo)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        BetCard()
        Spacer(modifier = Modifier.height(24.dp))
        ResultsSection()
        Spacer(modifier = Modifier.height(24.dp))
        MembershipSection(navController = navController)
        Spacer(modifier = Modifier.height(24.dp))
    }
}