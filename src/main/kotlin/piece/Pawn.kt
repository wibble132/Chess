package piece

import Board

class Pawn(board: Board, playForward: Boolean, position: Pair<Int, Int>) :
    Piece(board, playForward, position) {
    override fun toChar(): Char = 'p'

    override fun findPossibleMoves(currentBoardPositions: List<List<Int>>): List<Pair<Int, Int>> {
        val possibleMoves = mutableListOf<Pair<Int, Int>>()

        if (position.second + forwardDirection in 0 until currentBoardPositions[0].size) {

            // Move forward
            if (currentBoardPositions[position.first][position.second + forwardDirection] == -1) {
                possibleMoves.add(Pair(position.first, position.second + forwardDirection))

                // Double move forward
                if (
                    position.second == (if (playForward) 1 else currentBoardPositions[0].size - 2) &&
                    currentBoardPositions[position.first][position.second + 2 * forwardDirection] == -1
                ) {
                    possibleMoves.add(Pair(position.first, position.second + 2 * forwardDirection))
                }
            }

            // taking pieces diagonally infront of the pawn
            // Forward Right
            if (position.first + 1 in currentBoardPositions.indices) {
                if (currentBoardPositions[position.first + 1][position.second + forwardDirection] != -1) {
                    possibleMoves.add(Pair(position.first + 1, position.second + forwardDirection))
                }
            }

            // Forward Left
            if (position.first - 1 in currentBoardPositions.indices) {
                if (currentBoardPositions[position.first - 1][position.second + forwardDirection] != -1) {
                    possibleMoves.add(Pair(position.first - 1, position.second + forwardDirection))
                }
            }

            // En Passant
            if (board.enPassant != null) {
                if (board.enPassant!!.second == position.second + forwardDirection) {
                    if (board.enPassant!!.first == position.first + 1) {
                        possibleMoves.add(Pair(position.first + 1, position.second + forwardDirection))
                    } else if (board.enPassant!!.first == position.first - 1) {
                        possibleMoves.add(Pair(position.first - 1, position.second + forwardDirection))
                    }
                }
            }
        }

        return possibleMoves
    }

}