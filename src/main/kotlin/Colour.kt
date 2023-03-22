data class Colour(var colour: Int) {
    constructor(red: UByte, green: UByte, blue: UByte) :
            this((red.toInt() shl 16) + (green.toInt() shl 8) + blue.toInt())

    var red: UByte
        get() = (colour shr 16).toUByte()
        set(value) {
            colour = (colour and 0x00FFFF) + value.toInt() shl 16
        }

    var green: UByte
        get() = (colour shr 8 and 0xff).toUByte()
        set(value) {
            colour = (colour and 0xFF00FF) + value.toInt() shl 8
        }

    var blue: UByte
        get() = (colour and 0xff).toUByte()
        set(value) {
            colour = (colour and 0xFFFF00) + value.toInt()
        }

    override fun toString(): String = "Colour($red, $green, $blue)"
}