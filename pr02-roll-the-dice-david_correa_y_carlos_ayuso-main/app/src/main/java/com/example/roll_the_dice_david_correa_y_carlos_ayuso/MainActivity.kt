package com.example.roll_the_dice_david_correa_y_carlos_ayuso

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.roll_the_dice_david_correa_y_carlos_ayuso.ui.theme.Roll_the_Dice_David_Correa_y_Carlos_AyusoTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Roll_the_Dice_David_Correa_y_Carlos_AyusoTheme {
                RollDice()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RollDice() {
    val context = LocalContext.current // Obtener el contexto para el Toast
    var creditos = remember { mutableStateOf(10) }
    var numDado1 = remember { mutableStateOf(1) }
    var numDado2 = remember { mutableStateOf(1) }


    //IMAGEN DE FONDO
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.tapestry),
            contentDescription = "Tapete de fondo",
            alpha = 0.9f,
            modifier = Modifier
                .fillMaxSize()
                .matchParentSize()
        )
    }

    //COLUMNA PRINCIPAL
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // CONTADOR CREDITOS
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.ShoppingCart,
                contentDescription = "Créditos",
                tint = Color.White,
                modifier = Modifier.size(30.dp),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Creditos: ${creditos.value}",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        }
        //LOGO LASALLE
        Image(
            painter = painterResource(id = R.drawable.lsg_removebg_preview),
            contentDescription = "Logo LaSalleGracia",
            modifier = Modifier.size(250.dp)
        )

        //BOTON PARA TIRAR LOS DOS DADOS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = {
                    numDado1.value = Random.nextInt(1, 7)
                    numDado2.value = Random.nextInt(1, 7)
                    creditos.value -= 2
                    creditos.value += comprobarCoincidencia(numDado1.value, numDado2.value, context)
                }, enabled = creditos.value > 1
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dicerollericon_removebg_preview),
                    contentDescription = "Dado que gira los dos dados",
                    modifier = Modifier.size(150.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        //COLUMNA DADOS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {//DADO 1
            TextButton(
                onClick = {
                    numDado1.value = Random.nextInt(1, 7)
                    creditos.value -= 1
                    creditos.value += comprobarCoincidencia(numDado1.value, numDado2.value, context)


                },
                enabled = creditos.value > 0
            ) {
                Image(
                    painter = painterResource(id = devolverDado(numDado1.value)),
                    contentDescription = "Dado 1",
                    modifier = Modifier.size(170.dp)
                )
            }//DADO 2
            TextButton(
                onClick = {
                    numDado2.value = Random.nextInt(1, 7)
                    creditos.value -= 1
                    creditos.value += comprobarCoincidencia(numDado1.value, numDado2.value, context)


                },
                enabled = creditos.value > 0
            ) {
                Image(
                    painter = painterResource(id = devolverDado(numDado2.value)),
                    contentDescription = "Dado 2",
                    modifier = Modifier.size(170.dp)
                )
            }
        }

        // BOTON REINICIO
        Button(
            onClick = {
                creditos.value = 10
                numDado1.value = 1
                numDado2.value = 1
            }
        ) {
            Text("Reiniciar")
        }
    }
}

//funcion para combrobar coindicencias y mostrar las notificaciones TOAST
fun comprobarCoincidencia(dado1: Int, dado2: Int, context: android.content.Context): Int {
    if (dado1 == dado2) {
        val mensaje = when (dado1) {
            6 -> "Has sacado doble 6. Ganas 10 créditos."
            else -> "Has sacado doble $dado1. Ganas 5 créditos."
        }
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()

        return if (dado1 == 6) 10 else 5
    }
    return 0
}

//Funcion para imprimir una imagen de dado en base a su valor
fun devolverDado(num: Int): Int {
    return when (num) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_1
    }
}

