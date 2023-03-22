package piece

import Board

class Queen(board: Board, playForward: Boolean, position: Pair<Int, Int>) : Piece(board, playForward, position) {
    override fun toChar(): Char = 'q'

    override fun findPossibleMoves(currentBoardPositions: List<List<Int>>): List<Pair<Int, Int>> {
        val possibleMoves = mutableListOf<Pair<Int, Int>>()

        // Move forward
        for (i in position.second + forwardDirection until currentBoardPositions[0].size step forwardDirection) {
            if (!isValidMovePosition(Pair(position.first, i), currentBoardPositions)) {
                break
            }

            possibleMoves.add(Pair(position.first, i))
            if (currentBoardPositions[position.first][i] != -1) {
                break
            }
        }

        // Move backward
        for (i in position.second - forwardDirection downTo 0 step forwardDirection) {
            if (!isValidMovePosition(Pair(position.first, i), currentBoardPositions)) {
                break
            }

            possibleMoves.add(Pair(position.first, i))
            if (currentBoardPositions[position.first][i] != -1) {
                break
            }
        }

        // Move right
        for (i in position.first + 1 until currentBoardPositions.size) {
            if (!isValidMovePosition(Pair(i, position.second), currentBoardPositions)) {
                break
            }

            possibleMoves.add(Pair(i, position.second))
            if (currentBoardPositions[i][position.second] != -1) {
                break
            }
        }

        // Move left
        for (i in position.first - 1 downTo 0) {
            if (!isValidMovePosition(Pair(i, position.second), currentBoardPositions)) {
                break
            }

            possibleMoves.add(Pair(i, position.second))
            if (currentBoardPositions[i][position.second] != -1) {
                break
            }
        }

        // Move diagonally forward right
        for (i in 1 until currentBoardPositions.size) {
            val newPosition = Pair(position.first + i, position.second + i * forwardDirection)
            if (!isValidMovePosition(newPosition, currentBoardPositions)) {
                break
            }

            possibleMoves.add(newPosition)
            if (currentBoardPositions[newPosition.first][newPosition.second] != -1) {
                break
            }
        }

        // Move diagonally forward left
        for (i in 1 until currentBoardPositions.size) {
            val newPosition = Pair(position.first - i, position.second + i * forwardDirection)
            if (!isValidMovePosition(newPosition, currentBoardPositions)) {
                break
            }

            possibleMoves.add(newPosition)
            if (currentBoardPositions[newPosition.first][newPosition.second] != -1) {
                break
            }
        }

        // Move diagonally backward right
        for (i in 1 until currentBoardPositions.size) {
            val newPosition = Pair(position.first + i, position.second - i * forwardDirection)
            if (!isValidMovePosition(newPosition, currentBoardPositions)) {
                break
            }

            possibleMoves.add(newPosition)
            if (currentBoardPositions[newPosition.first][newPosition.second] != -1) {
                break
            }
        }

        // Move diagonally backward left
        for (i in 1 until currentBoardPositions.size) {
            val newPosition = Pair(position.first - i, position.second - i * forwardDirection)
            if (!isValidMovePosition(newPosition, currentBoardPositions)) {
                break
            }

            possibleMoves.add(newPosition)
            if (currentBoardPositions[newPosition.first][newPosition.second] != -1) {
                break
            }
        }

        return possibleMoves
    }
}