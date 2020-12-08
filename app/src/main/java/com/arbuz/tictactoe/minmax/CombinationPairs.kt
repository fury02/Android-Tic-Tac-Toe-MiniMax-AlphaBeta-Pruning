package com.arbuz.tictactoe.minmax

public class CombinationPairs(dimension: Int, figureLenght: Int) {

    val FigureLenght: Int = figureLenght
    val Dimension: Int = dimension
    var Pairs: MutableList<MutableList<IntArray>>  = mutableListOf()

    fun GetCombination(): MutableList<MutableList<IntArray>>
    {
        if(Pairs.size == 0){
            PairsDiagonalRightLeft().forEach { it -> Pairs.add(it) }
            PairsDiagonalLeftRight().forEach { it -> Pairs.add(it) }
            PairsHorizontal().forEach { it -> Pairs.add(it) }
            PairsVertical().forEach { it -> Pairs.add(it) }
        }

        return Pairs
    }

    fun PairsDiagonalRightLeft(): MutableList<MutableList<IntArray>>{
        var pairs: MutableList<MutableList<IntArray>>  = mutableListOf()
        var comb: MutableList<IntArray>  = mutableListOf()

        for (i:Int in 0..Dimension-1) {
            for (j:Int in 0..Dimension-1) {
                var di:Int = i; var dj:Int = j;
                comb = mutableListOf()
                do {
                    comb.add(intArrayOf(di,dj))

                    if(comb.size == FigureLenght){
                        val isPredicate: (MutableList<IntArray>) -> Boolean = { it[0][0] == comb[0][0] && it[0][1] == comb[0][1] }
                        var contains: Boolean = pairs.any{isPredicate(it)}

                        if(contains){
                            comb = mutableListOf()
                        }
                        else
                        {
                            pairs.add(comb)
                            comb = mutableListOf()
                        }
                    }

                    di++;dj--;
                }
                while (di < Dimension && dj >= 0)
            }
        }

        return pairs
    }

    fun PairsDiagonalLeftRight(): MutableList<MutableList<IntArray>>{
        var pairs: MutableList<MutableList<IntArray>>  = mutableListOf()
        var comb: MutableList<IntArray>  = mutableListOf()

        for (i:Int in 0..Dimension-1) {
            for (j:Int in 0..Dimension-1) {
                var di:Int = i; var dj:Int = j;
                comb = mutableListOf()
                do {
                    comb.add(intArrayOf(di,dj))

                    if(comb.size == FigureLenght){
                        val isPredicate: (MutableList<IntArray>) -> Boolean = { it[0][0] == comb[0][0] && it[0][1] == comb[0][1] }
                        var contains: Boolean = pairs.any{isPredicate(it)}

                        if(contains){
                            comb = mutableListOf()
                        }
                        else
                        {
                            pairs.add(comb)
                            comb = mutableListOf()
                        }
                    }

                    di++;dj++;
                }
                while (di < Dimension && dj < Dimension )
            }
        }

        return pairs
    }

    fun PairsVertical(): MutableList<MutableList<IntArray>> {
        var pairs: MutableList<MutableList<IntArray>> = mutableListOf()
        var comb: MutableList<IntArray> = mutableListOf()

        for (j:Int in 0..Dimension-1) {
            for (p:Int in 0..Dimension-1) {
                comb = mutableListOf()
                var i: Int = p
                do {
                    comb.add(intArrayOf(i,j))
                    if(comb.size == FigureLenght){
                        pairs.add(comb)
                        comb = mutableListOf()
                    }
                    i++
                }
                while (i < Dimension )
            }
        }

        return pairs
    }

    fun PairsHorizontal(): MutableList<MutableList<IntArray>> {
        var pairs: MutableList<MutableList<IntArray>> = mutableListOf()
        var comb: MutableList<IntArray> = mutableListOf()

        for (i:Int in 0..Dimension-1) {
            for (p:Int in 0..Dimension-1) {
                comb = mutableListOf()
                var j: Int = p
                do {
                    comb.add(intArrayOf(i,j))
                    if(comb.size == FigureLenght){
                        pairs.add(comb)
                        comb = mutableListOf()
                    }
                    j++
                }
                while (j < Dimension )
            }
        }

        return pairs
    }
}