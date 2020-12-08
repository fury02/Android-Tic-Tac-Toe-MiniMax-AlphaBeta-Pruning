package com.arbuz.tictactoe.minmax

public class Heuristics(dimension: Int, figureLenght: Int) {
    val FigureLenght: Int = figureLenght
    val Dimension: Int = dimension

    var combinationPairs : CombinationPairs = CombinationPairs(Dimension, FigureLenght)
    var Pairs: MutableList<MutableList<IntArray>>  = combinationPairs.GetCombination()
    val helpers: Helpers = Helpers()

    public fun GetPlayerWinner(array: Array<Array<Byte?>>): Player?{
        if(Pairs.count { it -> it.all { it2 -> array[it2[0]][it2[1]] == Players.instance.Cpu.NumericAttributePlayer } } > 0){
            return Players.instance.Cpu
        }
        else if(Pairs.count { it -> it.all { it2 -> array[it2[0]][it2[1]] == Players.instance.User.NumericAttributePlayer } } > 0){
            return Players.instance.User
        }

        return null
    }

    public fun Analysis(array: Array<Array<Byte?>>, player: Player, position: IntArray) : Double{
        var score: Double = 0.00

        var winPlayer: Player? = GetPlayerWinner(array)

        if (winPlayer != null){
            if(winPlayer.equals(Players.instance.Cpu)){
                return Double.POSITIVE_INFINITY
            }
            else{
                return Double.NEGATIVE_INFINITY
            }
        }
        else{
            score += OverallAnalysis(array, player, position)
            score += PositionAnalysis(array, player, position)

            if(CheckNextMoveIsWinner(array))
            {
                score = score / 5
            }
        }

        return score
    }

    public fun  OverallAnalysis(array: Array<Array<Byte?>>, player: Player, position: IntArray) : Double {
        var score: Double = 0.00

        Pairs.forEach {
            if (it.all { it2 -> array[it2[0]][it2[1]] == null }) {
                score += 0;
            } else if (2 == FigureLenght - it.count { it2 -> array[it2[0]][it2[1]] == null }) {
                if (player.equals(Players.instance.Cpu)) {
                    if (2 == it.count { it2 -> array[it2[0]][it2[1]] == Players.instance.Cpu.NumericAttributePlayer }) {
                        score += 4
                    }
                }
                if (player.equals(Players.instance.User)) {
                    if (2 == it.count { it2 -> array[it2[0]][it2[1]] == Players.instance.User.NumericAttributePlayer }) {
                        score += 4
                    }
                }
                if (2 == FigureLenght - it.count { it2 -> array[it2[0]][it2[1]] == Players.instance.Cpu.NumericAttributePlayer } &&
                    2 == FigureLenght - it.count { it2 -> array[it2[0]][it2[1]] == Players.instance.User.NumericAttributePlayer }) {
                    score += 2
                }
            } else if (1 == FigureLenght - it.count { it2 -> array[it2[0]][it2[1]] == null }) {
                if (player.equals(Players.instance.Cpu)) {
                    if (2 == FigureLenght - it.count { it2 -> array[it2[0]][it2[1]] == Players.instance.Cpu.NumericAttributePlayer }) {
                        score += 3
                    }
                }
                if (player.equals(Players.instance.User)) {
                    if (2 == FigureLenght - it.count { it2 -> array[it2[0]][it2[1]] == Players.instance.User.NumericAttributePlayer }) {
                        score += 3
                    }
                }
            } else if (0 == FigureLenght - it.count { it2 -> array[it2[0]][it2[1]] != null }) {
                if (player.equals(Players.instance.Cpu)) {
                    if (1 == FigureLenght - it.count { it2 -> array[it2[0]][it2[1]] == Players.instance.Cpu.NumericAttributePlayer }) {
                        score += 2
                    }
                }
                if (player.equals(Players.instance.User)) {
                    if (1 == FigureLenght - it.count { it2 -> array[it2[0]][it2[1]] == Players.instance.User.NumericAttributePlayer }) {
                        score += 2
                    }
                }
            }
        }

        return score
    }

    public fun PositionAnalysis(array: Array<Array<Byte?>>, player: Player, position: IntArray): Double{
        var score: Double = 0.00

        if (player.equals(Players.instance.Cpu)) {
            if(IsOverlap(array, player, position)){
                score += 100
            }
            if(IsPlug(array, player, position)){
                score += 200
            }
        }
        if (player.equals(Players.instance.User)) {
            if(IsOverlap(array, player, position)){
                score += 100
            }
            if(IsPlug(array, player, position)){
                score += 200
            }
        }
        return score
    }

    public fun IsOverlap(array: Array<Array<Byte?>>, player: Player, position: IntArray): Boolean{

        var arrayCurrent: Array<Array<Byte?>> = helpers.CopyAndCreateArray(array)
        arrayCurrent[position[0]][position[1]] = null

        Pairs.forEach {
            var isInboxPairs: Boolean = it.any {it2->position[0] == it2[0] && position[1] == it2[1] }

            if(isInboxPairs){
                if(2 == FigureLenght - it.count { it2 -> arrayCurrent[it2[0]][it2[1]] == null}){
                    if (player.equals(Players.instance.Cpu)) {
                        if(1 == FigureLenght - it.count { it2 -> arrayCurrent[it2[0]][it2[1]] == Players.instance.User.NumericAttributePlayer}){
                            return true
                        }
                    }
                    if (player.equals(Players.instance.User)) {
                        if(1 == FigureLenght - it.count { it2 -> arrayCurrent[it2[0]][it2[1]] == Players.instance.Cpu.NumericAttributePlayer}){
                            return true
                        }
                    }
                }
            }
        }

        return false
    }

    public fun IsPlug(array: Array<Array<Byte?>>, player: Player, position: IntArray): Boolean{
        var plugCPU: Int = 0
        var plugUser: Int = 0

        Pairs.forEach {
            var isInboxPairs: Boolean = it.any {it2->position[0] == it2[0] && position[1] == it2[1] }

            if(isInboxPairs) {
                if(2 == FigureLenght - it.count {it2 -> array[it2[0]][it2[1]] == null}){
                    if (player.equals(Players.instance.Cpu)) {
                        if(1 == FigureLenght - it.count { it2 -> array[it2[0]][it2[1]] == Players.instance.Cpu.NumericAttributePlayer}){
                            plugCPU++
                        }
                    }
                    if (player.equals(Players.instance.User)) {
                        if(1 == FigureLenght - it.count { it2 -> array[it2[0]][it2[1]] == Players.instance.User.NumericAttributePlayer}){
                            plugUser++
                        }
                    }
                }
            }
        }

        if (plugUser > 1 || plugUser > 1){
            return true
        }

        return false
    }

    public fun CheckNextMoveIsWinner(array: Array<Array<Byte?>>) : Boolean{
        var arrayNext: Array<Array<Byte?>> = helpers.CopyAndCreateArray(array)
        var playerNext: Player = helpers.SwithcPlayer(arrayNext)
        var collection: MutableList<IntArray> = GetAllPosition(array)

        collection.forEach {
            arrayNext = helpers.CopyAndCreateArray(array)
            arrayNext [it[0]][it[1]] = playerNext.NumericAttributePlayer
            if(GetPlayerWinner(arrayNext) != null){
                return true }
        }

        return false
    }

    fun GetAllPosition(array: Array<Array<Byte?>>) : MutableList<IntArray>{
        var list: MutableList<IntArray>  = mutableListOf()

        for(i:Int in 0..Dimension-1)
        {
            for(j:Int in 0..Dimension-1)
            {
                if(array[i][j] == null)
                {
                    list.add(intArrayOf(i,j))
                }
            }
        }

        return list
    }
}