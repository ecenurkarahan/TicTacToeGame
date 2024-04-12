package com.ekh.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ekh.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                //player scores
                //logo yani image
                //9 butonun olduğu bir kare
                //bir row içinde kimin turn olduğunu söyleyen bir string ve oyunu baştan başlatma butonu
            }
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}
@Composable
//@Preview
fun keepingPlayerScores(score1: Int, score2: Int, roundCount : Int){
    var playerO : String = "Player 'O': $score1"
    var playerX : String = "Player 'X': $score2"
    Row (modifier = Modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top){

        Text(text = playerO,
            color= Color.Blue,
            modifier = Modifier.padding(4.dp),
            fontSize = 15.sp)
        Text(text = "Draw: $roundCount",
            color= Color.Black,
            modifier = Modifier.padding(4.dp),
            fontSize = 15.sp)
        Text(text= playerX,
            color= Color.Green,
            modifier = Modifier.padding(4.dp),
            fontSize = 15.sp)

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TicTacToeTheme {
        Greeting("Android")
    }
}