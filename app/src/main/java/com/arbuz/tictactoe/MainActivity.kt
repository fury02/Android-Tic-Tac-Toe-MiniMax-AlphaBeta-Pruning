/*
package com.arbuz.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
*/

package com.arbuz.tictactoe
import com.arbuz.tictactoe.minmax.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.app.AlertDialog
import android.widget.Button
import android.widget.LinearLayout
import android.os.Bundle
import android.view.LayoutInflater
import com.arbuz.tictactoe.R

import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    var Dimension: Int = 3
    var Figure: Int = 3
    var GameArray : Array<Array<Byte?>> = Array(Dimension, {Array(Dimension, {null})} )
    var CpuPlayer = Players.instance.Cpu
    var UserPlayer = Players.instance.User
    var collectionLinearLayout: MutableMap<String, LinearLayout> = mutableMapOf()
    var helpers = Helpers()
    var heuristics = Heuristics(Dimension,Figure)
    var popupDialog: AlertDialog.Builder? = null
    var floatingButtonReset: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        floatingButtonReset = findViewById<FloatingActionButton>(R.id.floatingActionButtonReset)

        var linearLayoutvll_00 = findViewById<LinearLayout>(R.id.ll_00)
        var linearLayoutvll_01 = findViewById<LinearLayout>(R.id.ll_01)
        var linearLayoutvll_02 = findViewById<LinearLayout>(R.id.ll_02)
        var linearLayoutvll_10 = findViewById<LinearLayout>(R.id.ll_10)
        var linearLayoutvll_11 = findViewById<LinearLayout>(R.id.ll_11)
        var linearLayoutvll_12 = findViewById<LinearLayout>(R.id.ll_12)
        var linearLayoutvll_20 = findViewById<LinearLayout>(R.id.ll_20)
        var linearLayoutvll_21 = findViewById<LinearLayout>(R.id.ll_21)
        var linearLayoutvll_22 = findViewById<LinearLayout>(R.id.ll_22)

        collectionLinearLayout.put("00", linearLayoutvll_00)
        collectionLinearLayout.put("01", linearLayoutvll_01)
        collectionLinearLayout.put("02", linearLayoutvll_02)
        collectionLinearLayout.put("10", linearLayoutvll_10)
        collectionLinearLayout.put("11", linearLayoutvll_11)
        collectionLinearLayout.put("12", linearLayoutvll_12)
        collectionLinearLayout.put("20", linearLayoutvll_20)
        collectionLinearLayout.put("21", linearLayoutvll_21)
        collectionLinearLayout.put("22", linearLayoutvll_22)

        linearLayoutvll_00.setOnClickListener { HandlerLinearLayoutUserClick(linearLayoutvll_00,0,0) }
        linearLayoutvll_01.setOnClickListener { HandlerLinearLayoutUserClick(linearLayoutvll_01,0,1) }
        linearLayoutvll_02.setOnClickListener { HandlerLinearLayoutUserClick(linearLayoutvll_02,0,2) }
        linearLayoutvll_10.setOnClickListener { HandlerLinearLayoutUserClick(linearLayoutvll_10,1,0) }
        linearLayoutvll_11.setOnClickListener { HandlerLinearLayoutUserClick(linearLayoutvll_11,1,1) }
        linearLayoutvll_12.setOnClickListener { HandlerLinearLayoutUserClick(linearLayoutvll_12,1,2) }
        linearLayoutvll_20.setOnClickListener { HandlerLinearLayoutUserClick(linearLayoutvll_20,2,0) }
        linearLayoutvll_21.setOnClickListener { HandlerLinearLayoutUserClick(linearLayoutvll_21,2,1) }
        linearLayoutvll_22.setOnClickListener { HandlerLinearLayoutUserClick(linearLayoutvll_22,2,2) }

        floatingButtonReset?.hide()
        floatingButtonReset?.setOnClickListener { HandlerFoatingButtonUserClick() }
    }

    private fun HandlerLinearLayoutUserClick(linearLayout: LinearLayout, i:Int, j:Int) {
        if (helpers.SwithcPlayer(GameArray) == UserPlayer) {
            if(!CheckIsOverGame()){
                linearLayout.background.setTint(UserPlayer.UsedPlayerColor)
                linearLayout.isClickable = false
                GameArray[i][j] = UserPlayer.NumericAttributePlayer
                if(heuristics.GetAllPosition(GameArray).size > 0)
                    GoMiniMax()
                if(heuristics.GetAllPosition(GameArray).size == 0)
                    ShowPopup()
            }
        }
    }

    private fun HandlerOkUserClick(){
        GameArray = Array(Dimension, {Array(Dimension, {null})} )
        floatingButtonReset?.hide()
        this.recreate()
    }

    private fun HandlerFoatingButtonUserClick(){
        GameArray = Array(Dimension, {Array(Dimension, {null})} )
        floatingButtonReset?.hide()
        this.recreate()
    }

    private fun ShowPopup(){
        var viewPopup = LayoutInflater.from(this).inflate(R.layout.layout_popup, null)
        popupDialog = AlertDialog.Builder(this).setView(viewPopup)
        var btOkPopupDialog = viewPopup.findViewById<Button>(R.id.btokdialogpopup)

        btOkPopupDialog.setOnClickListener { HandlerOkUserClick() }
        floatingButtonReset?.show()
        popupDialog?.show()
    }

    private  fun CheckIsOverGame(): Boolean{
        if(heuristics.GetPlayerWinner(GameArray) != null || heuristics.GetAllPosition(GameArray).size == 0){
            ShowPopup()

            return true
        }

        return  false
    }

    private fun SetMoveCpu(estArray: IntArray){
        var i = estArray[0]
        var j = estArray[1]
        var strKey: String = i.toString() + j.toString()

        var linearLayout : LinearLayout =  collectionLinearLayout.getOrDefault(strKey, findViewById<LinearLayout>(R.id.ll_00))
        linearLayout.background.setTint(CpuPlayer.UsedPlayerColor)
        linearLayout.isClickable = false
        GameArray[i][j] = CpuPlayer.NumericAttributePlayer

        CheckIsOverGame()
    }

    private fun GoMiniMax() {
        var deth : Int = 1

        var operation = Operation(GameArray, Figure, deth)
        var treeNode: TreeNode = operation.GetEstimatedGraph()
        var maxEstimationBeta : Double? =  treeNode.Children.toTypedArray().maxByOrNull { it -> it.Beta!! }?.Beta

        var isWinner: Boolean = heuristics.CheckNextMoveIsWinner(GameArray)

        if(isWinner == true || maxEstimationBeta!! > 100.00) {
            var estArray: IntArray? = treeNode.Children.filter { it -> it.Beta ==  maxEstimationBeta }.first().NumberCell

            if(estArray != null) {
                SetMoveCpu(estArray)
            }
        }
        else  {
            var freePosition: Int = helpers.GetCountFreePosition(GameArray)

            if(freePosition == 8){
                deth = 7
            }
            if(freePosition == 6){
                deth = 3
            }
            if(freePosition == 4){
                deth = 3
            }
            if(freePosition == 2){
                deth = 1
            }

            var operation = Operation(GameArray, Figure, deth)
            var treeNode: TreeNode = operation.GetEstimatedGraph()

            if(treeNode.Children.size > 0) {

                var rnd = Random

                var maxEstimation : Double? =  treeNode.Children.toTypedArray().maxByOrNull { it -> it.Beta!! }?.Beta
                var lstArray = treeNode.Children.filter{ it -> it.Beta == maxEstimation }.toList()

                var estArray: IntArray? = intArrayOf(2)
                if(lstArray.size > 1) {
                    var startRndInt : Int = 0
                    var endRndInt : Int = lstArray.size - 1
                    var rndDigit: Int = rnd.nextInt(startRndInt, endRndInt)
                    estArray = lstArray[rndDigit].NumberCell
                }
                else {
                    estArray = treeNode.Children.filter{ it -> it.Beta == maxEstimation }.first().NumberCell
                }

                if(estArray != null) {
                    SetMoveCpu(estArray)
                }
            }
        }
    }
}
