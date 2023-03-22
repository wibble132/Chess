data class Colour(var colour: Int) {
    constructor(red: Int, green: Int, blue: Int) :
            this(red shl 16 + green shl 8 + blue)

    var red: Int
        get() = colour shr 16
        set(value) { colour = value shl 16 + green shl 8 + blue }

    var green: Int
        get() = colour shr 8 and 0xff
        set(value) { colour = red shl 16 + value shl 8 + blue }

    var blue: Int
        get() = colour and 0xff
        set(value) { colour = red shl 16 + green shl 8 + value }
}