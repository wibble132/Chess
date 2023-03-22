package piece

import Board

class Queen(board: Board, playForward: Boolean, position: Pair<Int, Int>) : Piece(board, playForward, position) {
    override fun toChar(): Char = 'q'

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

        // Move forward right
        for (i in 1 until currentBoardPositions.size) {
            if (position.first + i in currentBoardPositions.indices && position.second + i * forwardDirection in 0 until currentBoardPositions[0].size) {
                possibleMoves.add(Pair(position.first + i, position.second + i * forwardDirection))
                if (currentBoardPositions[position.first + i][position.second + i * forwardDirection] != -1) {
                    break
                }
            } else {
                break
            }
        }

        // Move forward left
        for (i in 1 until currentBoardPositions.size) {
            if (position.first - i in currentBoardPositions.indices && position.second + i * forwardDirection in 0 until currentBoardPositions[0].size) {
                possibleMoves.add(Pair(position.first - i, position.second + i * forwardDirection))
                if (currentBoardPositions[position.first - i][position.second + i * forwardDirection] != -1) {
                    break
                }
            } else {
                break
            }
        }

        // Move backward right
        for (i in 1 until currentBoardPositions.size) {
            if (position.first + i in currentBoardPositions.indices && position.second - i * forwardDirection in 0 until currentBoardPositions[0].size) {
                possibleMoves.add(Pair(position.first + i, position.second - i * forwardDirection))
                if (currentBoardPositions[position.first + i][position.second - i * forwardDirection] != -1) {
                    break
                }
            } else {
                break
            }
        }

        // Move backward left
        for (i in 1 until currentBoardPositions.size) {
            if (position.first - i in currentBoardPositions.indices && position.second - i * forwardDirection in 0 until currentBoardPositions[0].size) {
                possibleMoves.add(Pair(position.first - i, position.second - i * forwardDirection))
                if (currentBoardPositions[position.first - i][position.second - i * forwardDirection] != -1) {
                    break
                }
            } else {
                break
            }
        }

        return possibleMoves
    }
}