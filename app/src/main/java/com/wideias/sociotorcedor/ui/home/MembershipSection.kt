package com.wideias.sociotorcedor.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.wideias.sociotorcedor.R
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import androidx.compose.foundation.layout.requiredSize
import com.wideias.sociotorcedor.ui.home.HomeColors

@Composable
fun MembershipSection(navController: androidx.navigation.NavController) {
    Column {
        Text(
            text = "SEJA SÓCIO",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = BebasNeue,
            color = HomeColors.TextoBranco,
            modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(planosMock) { plano ->
                MembershipCard(plano = plano, navController = navController)
            }
        }
    }
}

@Composable
fun MembershipCard(plano: Plano, navController: androidx.navigation.NavController) {
    Box(
        modifier = Modifier
            .width(220.dp)
            .height(320.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(plano.cor)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_clube),
            contentDescription = null,
            modifier = Modifier
            .requiredSize(380.dp)
            .align(Alignment.Center),
            alpha = 0.5f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_clube),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = plano.nome,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = BebasNeue,
                    color = Color.White
                )
            }

            Text(
                text = plano.preco,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = BebasNeue,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            HorizontalDivider(
                color = Color.White.copy(alpha = 0.3f),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            plano.beneficios.forEach { beneficio ->
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(text = "• ", color = Color.White, fontSize = 11.sp, fontFamily = BebasNeue)
                    Text(text = beneficio, fontSize = 13.7.sp, fontFamily = BebasNeue, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.navigate("plano_detalhe/${plano.nome}") },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth().height(40.dp)
            ) {
                Text(
                    text = "Ver Benefícios",
                    fontSize = 14.sp,
                    fontFamily = BebasNeue,
                    color = plano.cor.copy(alpha = 3f) 
                )
            }
        }
    }
}