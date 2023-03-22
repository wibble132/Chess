fun main() {

    val board = Board.defaultChessBoard()

    // print initial board

    while (true) {
        println(board)

        // Ask user for input
        val position1 = readPosition("Choose a piece to move: ", true, board)

        println(board.toStringWithMoves(position1))
        println("The possible moves are ${
            board.pieces[board.positions[position1[0] - 'a'][position1[1] - '1']].possibleMoves
                .joinToString(", ") { (x, y) -> "${'a' + x}${y + 1}" }
        }\n")

        val position2 = readPosition("Choose a position to move to: ")
        board.movePiece(position1, position2)
    }

}

fun readPosition(prompt: String = "", pieceInPlace: Boolean = false, board: Board? = null): String {

    if (pieceInPlace && board == null)
        throw IllegalArgumentException("If pieceInPlace is true, board must not be null.")

    print(prompt)
    var input: String
    while (true) {
        input = readln()

        if (!(input.length == 2 && input[0] in 'a'..'g' && input[1] in '1'..'8')) {
            print("Invalid input. Please enter a valid position (e.g. a1): ")
            continue
        }

        if (pieceInPlace && board!!.positions[input[0] - 'a'][input[1] - '1'] == -1) {
            print("There is no piece at this position. Please enter a valid position: ")
            continue
        }

        break
    }
    return input
}