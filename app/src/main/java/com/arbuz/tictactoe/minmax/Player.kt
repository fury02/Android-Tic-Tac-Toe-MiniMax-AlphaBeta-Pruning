package com.arbuz.tictactoe.minmax

import android.graphics.Color

public class Player {
    public var NameUser: String = ""
        get() { return field }
        set(value) { field = value }

    public var NumericAttributePlayer: Byte = 0
        get() { return field }
        set(value) { field = value }

    public var UsedPlayerColor = Color.GRAY
        get() { return field }
        set(value) { field = value }

    public constructor( nameUser: String, numericAttributePlayer: Byte, color: Int)
    {
        this.NameUser = nameUser
        this.NumericAttributePlayer = numericAttributePlayer
        this.UsedPlayerColor = color
    }
}