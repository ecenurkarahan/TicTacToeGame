package com.ekh.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ekh.tictactoe.ui.theme.TicTacToeTheme

//draw means berabere
enum class WhoWon{
    PlayerO,
    PlayerX,
    Draw
}
class HoldScoresViewModel : ViewModel() {
    var playerOScore = 0
    var playerXScore = 0
    var drawScore =0
    fun updateO(){
        playerOScore++
    }
    fun updateX(){
        playerXScore++
    }
    fun updateDraw(){
        drawScore++
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scoresViewModel = viewModel<HoldScoresViewModel>()
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ActualGame(scoresViewModel)
                }
            }
        }
    }
}

//this will hold all the components
@Composable
fun ActualGame(scoresViewModel:HoldScoresViewModel) {

    var winnerString = "Game has not finished yet."
    //true: O turn, false: X turn, null: boş
    val playerOturn= remember {
        mutableStateOf(true)
    }
    //this holds the line that is to be colored with red
    var winningLine = remember{mutableStateListOf<Int?>(null,null,null)}

    // true: O turn, false: X turn, null: empty
    val theMoves = remember {
        mutableStateListOf<Boolean?>(null,null,null,null,null,null,null,null,null)
    }
    val winner  = remember {
        mutableStateOf<WhoWon?>(null)
    }
    //when we click play again, this lambda will be used
    val clickOfButton : () -> Unit ={
        playerOturn.value=true
        for(i in 0..8){
            theMoves[i]=null
        }
        winner.value=null
        winningLine[0]=null
        winningLine[1]=null
        winningLine[2]=null
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
            }
            else {
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
    //after the ontap operations,if there is a winner
    if(winner.value!=null){
        //i change the message to notify who won the game
        when(winner.value){
            WhoWon.PlayerO-> {
                scoresViewModel.updateO()
            winnerString= "Winner is Player 'O', Congrats!"}
            WhoWon.Draw -> {
                scoresViewModel.updateDraw()
                winnerString= "Game ended with a draw!"
            }
            WhoWon.PlayerX -> {
                scoresViewModel.updateX()
                winnerString= "Winner is Player 'X', Congrats!"
            }
            else -> {}
        }
        //here, when there is a winner we idenfity the indices to paint
        if((theMoves[0]==true&& theMoves[1]==true&&theMoves[2]==true)||
            (theMoves[0]==false&& theMoves[1]==false&&theMoves[2]==false)){
            winningLine[0]= 0
            winningLine[1]=1
            winningLine[2]=2
        }
        else if ((theMoves[3]==true&& theMoves[4]==true&&theMoves[5]==true)||
            (theMoves[3]==false&& theMoves[4]==false&&theMoves[5]==false)){
            winningLine[0]= 3
            winningLine[1]=4
            winningLine[2]=5
        }
        else if((theMoves[6]==true&& theMoves[7]==true&&theMoves[8]==true)||
            (theMoves[6]==false&& theMoves[7]==false&&theMoves[8]==false)){
            winningLine[0]= 6
            winningLine[1]=7
            winningLine[2]=8
        }

           else if((theMoves[0]==true&& theMoves[3]==true&&theMoves[6]==true)||
            (theMoves[0]==false&& theMoves[3]==false&&theMoves[6]==false)){
            winningLine[0]= 0
            winningLine[1]=3
            winningLine[2]=6
        }

           else if((theMoves[1]==true&& theMoves[4]==true&&theMoves[7]==true)||
            (theMoves[1]==false&& theMoves[4]==false&&theMoves[7]==false)){
            winningLine[0]= 1
            winningLine[1]=4
            winningLine[2]=7
        }

           else if((theMoves[2]==true&& theMoves[5]==true&&theMoves[8]==true)||
            (theMoves[2]==false&& theMoves[5]==false&&theMoves[8]==false)){
            winningLine[0]= 2
            winningLine[1]=5
            winningLine[2]=8

        }

           else if((theMoves[0]==true&& theMoves[4]==true&&theMoves[8]==true)||
            (theMoves[0]==false&& theMoves[4]==false&&theMoves[8]==false)){
            winningLine[0]= 0
            winningLine[1]=4
            winningLine[2]=8
        }
           else if((theMoves[2]==true&& theMoves[4]==true&&theMoves[6]==true)||
            (theMoves[2]==false&& theMoves[4]==false&&theMoves[6]==false)){
            winningLine[0]= 2
            winningLine[1]=4
            winningLine[2]=6
            }
    }

    Column (horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.background(Color.White))
    {
        val imageModifier = Modifier
            .size(150.dp)
            .background(Color.White)
        KeepingPlayerScores(scoresViewModel.playerOScore, scoresViewModel.playerXScore, scoresViewModel.drawScore)
        Text(text = winnerString, modifier = Modifier.padding(8.dp),
            color = Color.Black)
        Image(painter = painterResource(id = R.drawable.tictactoe),
            contentDescription = "tictactoe",
            modifier=imageModifier)
        XOxBoard(theMoves,onTap, winningLine )
        WhoseTurn(playerOturn,clickOfButton)
    }
}

@Composable
fun XOxBoard(
    theMoves: SnapshotStateList<Boolean?>, onTapofButton: (Offset)->Unit,
    winningIndices: SnapshotStateList<Int?>
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(32.dp)
            .background(Color(0xffcaf0f8))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = onTapofButton
                )
            }
    ) {
        Column (modifier = Modifier
            .fillMaxSize(1f)
            .background(Color.Transparent)){
            for(i in 0..2){
                Row(modifier = Modifier
                    .weight(1f)
                    .background(Color.Transparent)) {
                    for(j in 0..2){
                        val cellIndex = i * 3 + j
                        //if there is a winning situation, we paint the grids to red
                        val backgroundColor = if (winningIndices.contains(cellIndex)) {
                            Color.Red
                        } else {
                            Color(0xffcaf0f8)
                        }
                        //here i put columns as the grids and to differentiate the grids
                        //i used border
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(backgroundColor)
                                .border(shape = RectangleShape, width = 1.dp, color = Color.Black)
                        ) {
                            DecideonXorO(move = theMoves[cellIndex])
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun WhoseTurn(playerOturn: MutableState<Boolean>, clickOfButton : ()->Unit) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (playerOturn.value) {
            Text(text = "Player 'O' turn.",modifier = Modifier.padding(8.dp)
            , color = Color.Black)
        } else {
            Text(text = "Player 'X' turn.",modifier = Modifier.padding(8.dp),
                color= Color.Black)
        }
        Button(onClick = clickOfButton,
            modifier = Modifier.padding(8.dp)) {
            Text(text = "Play Again")
        }
    }
}

@Composable
fun KeepingPlayerScores(score1: Int, score2: Int, roundCount: Int){
    val playerO = "Player 'O': ${(score1/2).toInt().toString()}"
    val playerX  = "Player 'X': ${(score2/2).toInt().toString()}"
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
fun DecideonXorO(move: Boolean?){
    //var backgroundColor = Color(0xffcaf0f8)
        when (move) {
            true -> Image(
                painter = painterResource(id = R.drawable.ic_o),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(1f),
                colorFilter = ColorFilter.tint(Color(0xff99582a))
            )

            false -> Image(
                painter = painterResource(id = R.drawable.ic_x),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(1f),
                colorFilter = ColorFilter.tint(Color(0xff6f2dbd))
            )

            null -> Image(
                painter = painterResource(id = R.drawable.ic_null),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(1f)
            )
        }

}
fun isGameFinished(moves: List<Boolean?>): WhoWon? {
    var winner : WhoWon? = null
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
        winner=WhoWon.PlayerO
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
        winner=WhoWon.PlayerX
    }
    if(winner==null){
        var isGameFinished=true
        for(i in 0..8){
            if(moves[i]==null){
                isGameFinished=false
            }
        }
        if(isGameFinished){
            winner=WhoWon.Draw
        }
    }
    return winner

}

