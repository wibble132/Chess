fun main() {

    val board = Board.defaultChessBoard()


    var playerTurn = true

    while (true) {

        clearScreen()
        println(board)

        // Ask user for input
        val position1 = readPosition(
            "It's ${if (playerTurn) "white's" else "black's"} turn.\n\nChoose a piece to move: ",
            true,
            board,
            playerTurn
        )

        clearScreen()
        println(board.toStringWithMoves(position1))
        val possibleMoves = board.pieces[board.positions[position1[0] - 'a'][position1[1] - '1']].possibleMoves
        println(
            "The possible moves are ${
                possibleMoves.joinToString(", ") { (x, y) -> "${'a' + x}${y + 1}" }
            }\n")

        val position2 = readPosition("Choose a position to move to: ", validSpaces = possibleMoves)
        board.movePiece(position1, position2)

        if (board.winner != null) {
            clearScreen()
            println(board)
            println("Game over! ${if (board.winner!!) "White" else "Black"} wins!")
            break
        }

        playerTurn = !playerTurn
    }


    println("Press Enter to exit.")
    readln()

}

fun readPosition(
    prompt: String = "",
    pieceInPlace: Boolean = false,
    board: Board? = null,
    pieceTeam: Boolean? = null,
    validSpaces: List<Pair<Int, Int>>? = null,
): String {

    if (pieceInPlace && board == null) throw IllegalArgumentException("If pieceInPlace is true, board must not be null.")
    if (pieceTeam != null && board == null) throw IllegalArgumentException("If pieceTeam is not null, board must not be null.")

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

        if (pieceTeam != null && board!!.pieces[board.positions[input[0] - 'a'][input[1] - '1']].playForward != pieceTeam) {
            print("This piece is not on your team. Please enter a valid position: ")
            continue
        }

        if (validSpaces != null && Pair(input[0] - 'a', input[1] - '1') !in validSpaces) {
            print("This piece cannot move to this position. Please enter a valid position: ")
            continue
        }

        break
    }
    return input
}

fun clearScreen() {
    print("\u001b[H\u001b[2J")
}