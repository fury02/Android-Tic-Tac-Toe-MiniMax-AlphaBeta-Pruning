package com.arbuz.tictactoe.minmax

class Helpers {

    fun CopyAndCreateArray(source: Array<Array<Byte?>>): Array<Array<Byte?>> {
        var Dimension: Int = source.size

        var dest: Array<Array<Byte?>> = Array(Dimension, { Array(Dimension, { null }) })

        for(i:Int in 0..Dimension-1)
        {
            for(j:Int in 0..Dimension-1)
            {
                dest[i][j] = source[i][j]
            }
        }

        return  dest
    }

    fun GetCountFreePosition(array: Array<Array<Byte?>>): Int{
        var Dimension: Int = array.size

        var count: Int = 0

        for(i:Int in 0..Dimension-1)
        {
            for(j:Int in 0..Dimension-1)
            {
                if(array[i][j] == null)
                {
                    count++
                }
            }
        }

        return  count
    }


    fun SwithcPlayer(array: Array<Array<Byte?>>): Player
    {
        var Dimension: Int = array.size

        var PlayerNext: Player
        var userMove: Int = 0
        var cpuMove: Int = 0

        for(i:Int in 0..Dimension-1)
        {
            for(j:Int in 0..Dimension-1)
            {
                if(array[i][j] == Players.instance.User.NumericAttributePlayer)
                {
                    userMove++
                }
                else if(array[i][j] == Players.instance.Cpu.NumericAttributePlayer)
                {
                    cpuMove++
                }
            }
        }

        if(userMove > cpuMove)
        {
            return  Players.instance.Cpu
        }
        else if(userMove < cpuMove)
        {
            return  Players.instance.User
        }
        else //Default
        {
            return  Players.instance.User
        }
    }
}
