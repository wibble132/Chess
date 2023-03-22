package piece

import Board

class King(board: Board, playForward: Boolean, position: Pair<Int, Int>) : Piece(board, playForward, position) {
    override fun toChar(): Char = 'k'

    override fun findPossibleMoves(currentBoardPositions: List<List<Int>>): List<Pair<Int, Int>> {
        val possibleMoves = mutableListOf<Pair<Int, Int>>()

        val possibleMovesX = listOf(1, 1, 1, 0, 0, -1, -1, -1)
        val possibleMovesY = listOf(1, 0, -1, 1, -1, 1, 0, -1)

        for (i in 0 until 8) {
            val x = position.first + possibleMovesX[i]
            val y = position.second + possibleMovesY[i]

            if (x in currentBoardPositions.indices && y in 0 until currentBoardPositions[0].size) {
                possibleMoves.add(Pair(x, y))
            }
        }

        // Castling
        if (!hasMoved && !board.isPositionAttacked(position)) {
            // Forward
            for (i in position.second + forwardDirection until currentBoardPositions[0].size step forwardDirection) {
                if (currentBoardPositions[i][position.second] == -1) {
                    if (board.isPositionAttacked(Pair(i, position.second))) {
                        break
                    }
                    continue
                }
                val piece = board.pieces[currentBoardPositions[i][position.second]]
                if (piece.toChar() == 'r' && !piece.hasMoved) {
                    possibleMoves.add(Pair(position.first, position.second + forwardDirection * 2))
                }
            }

            // Backward
            for (i in position.second - forwardDirection downTo 0 step forwardDirection) {
                if (currentBoardPositions[i][position.second] == -1) {
                    if (board.isPositionAttacked(Pair(i, position.second))) {
                        break
                    }
                    continue
                }
                val piece = board.pieces[currentBoardPositions[i][position.second]]
                if (piece.toChar() == 'r' && !piece.hasMoved) {
                    possibleMoves.add(Pair(position.first, position.second - forwardDirection * 2))
                }
            }

            // Left
            for (i in position.first - 1 downTo 0) {
                if (currentBoardPositions[i][position.second] == -1) {
                    if (board.isPositionAttacked(Pair(i, position.second))) {
                        break
                    }
                    continue
                }
                val piece = board.pieces[currentBoardPositions[i][position.second]]
                if (piece.toChar() == 'r' && !piece.hasMoved) {
                    possibleMoves.add(Pair(position.first - 2, position.second))
                }
            }

            // Right
            for (i in position.first + 1 until currentBoardPositions.size) {
                if (currentBoardPositions[i][position.second] == -1) {
                    if (board.isPositionAttacked(Pair(i, position.second))) {
                        break
                    }
                    continue
                }
                val piece = board.pieces[currentBoardPositions[i][position.second]]
                if (piece.toChar() == 'r' && !piece.hasMoved) {
                    possibleMoves.add(Pair(position.first + 2, position.second))
                }
            }
        }

        return possibleMoves
    }
}