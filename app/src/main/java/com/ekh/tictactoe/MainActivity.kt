package com.ekh.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ekh.tictactoe.ui.theme.TicTacToeTheme

//draw means berabere
enum class whoWon{
    PlayerO,
    PlayerX,
    Draw
}

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
//eklenecekler: oyun bittiğinde mesaj verilecek, 3 arka arkaya olan yer kırmızıyla işaretlenecek!

//this will hold all the components
@Composable
fun actualGame() {
    //true: O turn, false: X turn, null: boş
    val playerOturn= remember {
        mutableStateOf(true)
    }
    // true: O turn, false: X turn
    val theMoves = remember {
        mutableStateListOf<Boolean?>(null,null,null,null,null,null,null,null,null)
    }
    val winner  = remember {
        mutableStateOf<whoWon?>(null)
    }
    var score1 =0
    var score2=0
    var drawCount=0
    val clickOfButton : () -> Unit ={
        playerOturn.value=true
        for(i in 0..8){
            theMoves[i]=null
        }
        winner.value=null
    }
    val onTap : (Offset)-> Unit ={
        if(winner.value==null) {
            if (playerOturn.value) {
                //which column i am at?
                val x = ((it).x / 333).toInt()
                val y = ((it).y / 333).toInt()
                //considering box as a 2d array
                val positionInMoves = y * 3 + x
                if (theMoves[positionInMoves] == null) {
                    theMoves[positionInMoves] = true
                    playerOturn.value = false
                    winner.value = isGameFinished(theMoves)
                }
            } else {
                //which column i am at?
                val x = ((it).x / 333).toInt()
                val y = ((it).y / 333).toInt()
                //considering box as a 2d array
                val positionInMoves = y * 3 + x
                if (theMoves[positionInMoves] == null) {
                    theMoves[positionInMoves] = false
                    playerOturn.value = true
                    winner.value = isGameFinished(theMoves)
                }
            }
        }
    }
    if(winner.value!=null){
        when(winner.value){
            whoWon.PlayerO-> score1++
            whoWon.Draw -> drawCount++
            whoWon.PlayerX -> score2++
            else -> {}
        }
    }

    Column (horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.background(Color.White))
    {
        val imageModifier = Modifier
            .size(150.dp)
            .background(Color.White)
        keepingPlayerScores(score1, score2, drawCount)
        Image(painter = painterResource(id = R.drawable.tictactoe),
            contentDescription = "tictactoe",
            modifier=imageModifier)
        xOxBoard(theMoves,onTap )
        whoseTurn(playerOturn,clickOfButton)
    }
}
@Composable
fun xOxBoard(theMoves: SnapshotStateList<Boolean?>, OnTap : (Offset)->Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(32.dp)
            .background(Color(0xffcaf0f8))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = OnTap
                )
            }
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
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight(1f)
                    .background(Color.Black)
            ) {

            }
            Column(
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight(1f)
                    .background(Color.Black)
            ) {

            }
        }

        Column (modifier = Modifier.fillMaxSize(1f)){
            for(i in 0..2){
                Row(modifier = Modifier.weight(1f)) {
                    for(j in 0..2){
                        Column(modifier = Modifier.weight(1f)) {
                            //putting x or o depending on the moves
                            decideonXorO(move = theMoves[i*3+j])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun whoseTurn(playerOturn: MutableState<Boolean>, clickOfButton : ()->Unit) {
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
        Button(onClick = {
            clickOfButton
        },
            modifier = Modifier.padding(8.dp)) {
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
            color= Color(0xff99582a),
            modifier = Modifier.padding(4.dp),
            fontSize = 15.sp)
        Text(text = "Draw: $roundCount",
            color= Color.Black,
            modifier = Modifier.padding(4.dp),
            fontSize = 15.sp)
        Text(text= playerX,
            color= Color.Blue,
            modifier = Modifier.padding(4.dp),
            fontSize = 15.sp)

    }
}
@Composable
fun decideonXorO(move: Boolean?){
    when(move){
        true -> Image(painter = painterResource(id = R.drawable.ic_o),
            contentDescription =null,
            modifier = Modifier.fillMaxSize(1f),
            colorFilter = ColorFilter.tint(Color(0xff99582a)))
        false -> Image(painter = painterResource(id = R.drawable.ic_x),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(1f),
            colorFilter = ColorFilter.tint(Color(0xff6f2dbd)))
        null -> Image(painter = painterResource(id = R.drawable.ic_null),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(1f))
    }
}
fun isGameFinished(moves: List<Boolean?>): whoWon? {
    var winner : whoWon? = null
    if(
        (moves[0]==true&& moves[1]==true&&moves[2]==true)||
        (moves[3]==true&& moves[4]==true&&moves[5]==true)||
        (moves[6]==true&& moves[7]==true&&moves[8]==true)||
        (moves[0]==true&& moves[3]==true&&moves[6]==true)||
        (moves[1]==true&& moves[4]==true&&moves[7]==true)||
        (moves[2]==true&& moves[5]==true&&moves[8]==true)||
        (moves[0]==true&& moves[4]==true&&moves[8]==true)||
        (moves[2]==true&& moves[4]==true&&moves[6]==true)
    ){
        winner=whoWon.PlayerO
    }
    if(
        (moves[0]==false&& moves[1]==false&&moves[2]==false)||
        (moves[3]==false&& moves[4]==false&&moves[5]==false)||
        (moves[6]==false&& moves[7]==false&&moves[8]==false)||
        (moves[0]==false&& moves[3]==false&&moves[6]==false)||
        (moves[1]==false&& moves[4]==false&&moves[7]==false)||
        (moves[2]==false&& moves[5]==false&&moves[8]==false)||
        (moves[0]==false&& moves[4]==false&&moves[8]==false)||
        (moves[2]==false&& moves[4]==false&&moves[6]==false)
    ){
        winner=whoWon.PlayerX
    }
    if(winner==null){
        var isGameFinished=true
        for(i in 0..8){
            if(moves[i]==null){
                isGameFinished=false
            }
        }
        if(isGameFinished){
            winner=whoWon.Draw
        }
    }
    return winner

}

