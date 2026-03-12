package com.wideias.sociotorcedor.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wideias.sociotorcedor.R
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import androidx.compose.foundation.layout.requiredSize
import com.wideias.sociotorcedor.ui.home.HomeColors

@Composable
fun MembershipSection() {
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
                MembershipCard(plano = plano)
            }
        }
    }
}

@Composable
fun MembershipCard(plano: Plano) {
    Box(
        modifier = Modifier
            .width(220.dp)
            .height(320.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(plano.cor)
    ) {
        // Logo ao fundo com transparência
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
                onClick = { },
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
                    color = plano.cor.copy(alpha = 3f)  // ← usa a cor do plano atual
                )
            }
        }
    }
}