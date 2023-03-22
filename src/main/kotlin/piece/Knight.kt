package piece

import Board

class Knight(board: Board, playForward: Boolean, position: Pair<Int, Int>) : Piece(board, playForward, position) {
    override fun toChar(): Char = 'n'

    override fun findPossibleMoves(currentBoardPositions: List<List<Int>>): List<Pair<Int, Int>> {
        val possibleMovesX = listOf(1, 2, 2, 1, -1, -2, -2, -1)
        val possibleMovesY = listOf(2, 1, -1, -2, -2, -1, 1, 2)

        return possibleMovesX.map { it + position.first }.zip(possibleMovesY.map { it + position.second })
            .filter { (x, y) -> isValidMovePosition(Pair(x, y), currentBoardPositions) }

    }
}