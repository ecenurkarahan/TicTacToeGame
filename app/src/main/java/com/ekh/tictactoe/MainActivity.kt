package com.ekh.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
                    actualGame()
                }
            }
        }
    }
}


//this will hold all the components
@Composable
fun actualGame() {
    //true: O turn, false: X turn
    val playerOturn= remember {
        mutableStateOf(true)
    }
    // true: O turn, false: X turn, null: no move
    val theMoves = remember {
        mutableStateListOf<Boolean?>(true,null,false,true,null,null,true,false,true)
    }
    var score1 =0
    var score2=0
    var roundCount=0
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.background(Color.White))
    {
        val imageModifier = Modifier
            .size(150.dp)
            .background(Color.White)
        keepingPlayerScores(score1, score2, roundCount)
        Image(painter = painterResource(id = R.drawable.tictactoe),
            contentDescription = "tictactoe",
            modifier=imageModifier)
        xOxBoard(theMoves )
        //whose turnde sıkıntı var çok yakınlar
        //componentler dağıtılabilir
        whoseTurn(playerOturn)
    }
}
@Composable
fun xOxBoard(theMoves: SnapshotStateList<Boolean?>) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(32.dp)
            .background(Color.Cyan)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Row(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth(1f)
                    .background(Color.Black)
            ) {

            }
            Row(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth(1f)
                    .background(Color.Black)
            ) {

            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            Column(
                modifier = Modifier.width(2.dp)
                    .fillMaxHeight(1f)
                    .background(Color.Black)
            ) {

            }
            Column(
                modifier = Modifier.width(2.dp)
                    .fillMaxHeight(1f)
                    .background(Color.Black)
            ) {

            }
        }

    }
}

@Composable
fun whoseTurn(playerOturn: MutableState<Boolean>) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (playerOturn.value) {
            Text(text = "Player 'O' turn.",modifier = Modifier.padding(8.dp))
        } else {
            Text(text = "Player 'X' turn.",modifier = Modifier.padding(8.dp))
        }
        Button(onClick = { /*TODO*/ },modifier = Modifier.padding(8.dp)) {
            Text(text = "Play Again")
        }
    }
}

@Composable
fun keepingPlayerScores(score1: Int, score2: Int, roundCount : Int){
    val playerO : String = "Player 'O': $score1"
    val playerX : String = "Player 'X': $score2"
    Row (modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
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
fun isGameFinished(): Boolean{
    TODO()
}

