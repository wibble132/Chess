package piece

import Board

class Rook(board: Board, playForward: Boolean, position: Pair<Int, Int>) : Piece(board, playForward, position) {
    override fun toChar(): Char = 'r'

    override fun findPossibleMoves(currentBoardPositions: List<List<Int>>): List<Pair<Int, Int>> {
        val possibleMoves = mutableListOf<Pair<Int, Int>>()

        // Move forward
        for (i in position.second + forwardDirection until currentBoardPositions[0].size step forwardDirection) {
            possibleMoves.add(Pair(position.first, i))
            if (currentBoardPositions[position.first][i] != -1) {
                break
            }
        }

        // Move backward
        for (i in position.second - forwardDirection downTo 0 step forwardDirection) {
            possibleMoves.add(Pair(position.first, i))
            if (currentBoardPositions[position.first][i] != -1) {
                break
            }
        }

        // Move right
        for (i in position.first + 1 until currentBoardPositions.size) {
            possibleMoves.add(Pair(i, position.second))
            if (currentBoardPositions[i][position.second] != -1) {
                break
            }
        }

        // Move left
        for (i in position.first - 1 downTo 0) {
            possibleMoves.add(Pair(i, position.second))
            if (currentBoardPositions[i][position.second] != -1) {
                break
            }
        }

        return possibleMoves
    }

}