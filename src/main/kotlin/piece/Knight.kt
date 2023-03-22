package piece

import Board

class Knight(board: Board, playForward: Boolean, position: Pair<Int, Int>) : Piece(board, playForward, position) {
    override fun toChar(): Char = 'n'

    override fun findPossibleMoves(currentBoardPositions: List<List<Int>>): List<Pair<Int, Int>> {
        val possibleMoves = mutableListOf<Pair<Int, Int>>()

        val possibleMovesX = listOf(1, 2, 2, 1, -1, -2, -2, -1)
        val possibleMovesY = listOf(2, 1, -1, -2, -2, -1, 1, 2)

        for (i in 0 until 8) {
            val x = position.first + possibleMovesX[i]
            val y = position.second + possibleMovesY[i]

            if (x in currentBoardPositions.indices && y in 0 until currentBoardPositions[0].size) {
                possibleMoves.add(Pair(x, y))
            }
        }

        return possibleMoves
    }
}