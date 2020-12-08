package com.arbuz.tictactoe.minmax

import android.graphics.Color

class Players private  constructor() {

    private object  holder{
        val _instance = Players()
    }

    companion object
    {
        val instance: Players by lazy { holder._instance }
    }

    public  final val Cpu: Player
        get()
        {
            return  field
        }

    public  final val User: Player
        get()
        {
            return field
        }

    init {
        Cpu   = Player("Cpu", Byte.MAX_VALUE, Color.BLACK)
        User  = Player("User", Byte.MIN_VALUE, Color.YELLOW)
    }
}