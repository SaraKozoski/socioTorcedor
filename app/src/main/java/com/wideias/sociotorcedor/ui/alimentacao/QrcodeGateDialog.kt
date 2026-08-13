package com.wideias.sociotorcedor.ui.alimentacao

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.wideias.sociotorcedor.ui.home.HomeColors
import com.wideias.sociotorcedor.ui.theme.BebasNeue
import java.util.UUID

data class ResgateInfo(
    val codigo: String,
    val nomeProduto: String,
    val pontos: Int
)

fun gerarCodigoResgate(): String {
    return UUID.randomUUID().toString().uppercase().replace("-", "").take(10)
}

fun gerarQrCodeBitmap(conteudo: String, tamanho: Int = 512): Bitmap {
    val hints = mapOf(EncodeHintType.MARGIN to 1)
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(conteudo, BarcodeFormat.QR_CODE, tamanho, tamanho, hints)
    val bitmap = Bitmap.createBitmap(tamanho, tamanho, Bitmap.Config.RGB_565)
    for (x in 0 until tamanho) {
        for (y in 0 until tamanho) {
            bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
        }
    }
    return bitmap
}

@Composable
fun QrCodeResgateDialog(
    resgate: ResgateInfo,
    onDismiss: () -> Unit
) {
    val qrBitmap = remember(resgate.codigo) {
        gerarQrCodeBitmap(resgate.codigo)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = HomeColors.CardEscuro),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "RESGATE CONFIRMADO",
                    fontFamily = BebasNeue,
                    fontSize = 20.sp,
                    color = HomeColors.Cards1,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = resgate.nomeProduto,
                    fontFamily = BebasNeue,
                    fontSize = 16.sp,
                    color = HomeColors.TextoBranco,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // QR Code
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(androidx.compose.ui.graphics.Color.White)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        bitmap = qrBitmap.asImageBitmap(),
                        contentDescription = "QR Code de resgate",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Código legível
                Text(
                    text = "CÓDIGO",
                    fontFamily = BebasNeue,
                    fontSize = 12.sp,
                    color = HomeColors.TextoCinza
                )
                Text(
                    text = resgate.codigo,
                    fontFamily = BebasNeue,
                    fontSize = 20.sp,
                    color = HomeColors.TextoBranco,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Apresente este QR Code na retirada",
                    fontFamily = BebasNeue,
                    fontSize = 12.sp,
                    color = HomeColors.TextoCinza,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Destaque dos pontos debitados
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    color = HomeColors.DetalhesCard1
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PONTOS UTILIZADOS",
                            fontFamily = BebasNeue,
                            fontSize = 13.sp,
                            color = HomeColors.TextoCinza
                        )
                        Text(
                            text = "- ${resgate.pontos} pts",
                            fontFamily = BebasNeue,
                            fontSize = 15.sp,
                            color = HomeColors.Cards1,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HomeColors.Cards1)
                ) {
                    Text(
                        text = "FECHAR",
                        fontFamily = BebasNeue,
                        fontSize = 15.sp,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                }
            }
        }
    }
}