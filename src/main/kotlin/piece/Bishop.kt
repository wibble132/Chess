package piece

import Board

class Bishop(board: Board, playForward: Boolean, position: Pair<Int, Int>) : Piece(board, playForward, position) {
    override fun toChar(): Char = 'b'

    override fun findPossibleMoves(currentBoardPositions: List<List<Int>>): List<Pair<Int, Int>> {
        val possibleMoves = mutableListOf<Pair<Int, Int>>()

        // Move forward right
        for (i in 1 until currentBoardPositions.size) {

            if (!isValidMovePosition(
                    Pair(position.first + i, position.second + i * forwardDirection),
                    currentBoardPositions
                )
            ) {
                break
            }

            possibleMoves.add(Pair(position.first + i, position.second + i * forwardDirection))
            if (currentBoardPositions[position.first + i][position.second + i * forwardDirection] != -1) {
                break
            }
        }

        // Move forward left
        for (i in 1 until currentBoardPositions.size) {

            if (!isValidMovePosition(
                    Pair(position.first - i, position.second + i * forwardDirection),
                    currentBoardPositions
                )
            ) {
                break
            }

            possibleMoves.add(Pair(position.first - i, position.second + i * forwardDirection))
            if (currentBoardPositions[position.first - i][position.second + i * forwardDirection] != -1) {
                break
            }
        }

        // Move backward right
        for (i in 1 until currentBoardPositions.size) {

            if (!isValidMovePosition(
                    Pair(position.first + i, position.second - i * forwardDirection),
                    currentBoardPositions
                )
            ) {
                break
            }

            possibleMoves.add(Pair(position.first + i, position.second - i * forwardDirection))
            if (currentBoardPositions[position.first + i][position.second - i * forwardDirection] != -1) {
                break
            }
        }

        // Move backward left
        for (i in 1 until currentBoardPositions.size) {

            if (!isValidMovePosition(
                    Pair(position.first - i, position.second - i * forwardDirection),
                    currentBoardPositions
                )
            ) {
                break
            }

            possibleMoves.add(Pair(position.first - i, position.second - i * forwardDirection))
            if (currentBoardPositions[position.first - i][position.second - i * forwardDirection] != -1) {
                break
            }
        }

        return possibleMoves
    }
}