package piece

import Board

abstract class Piece(protected val board: Board, val playForward: Boolean, var position: Pair<Int, Int>, var hasMoved: Boolean = false) {

    var isAlive = false

    protected val forwardDirection = if (playForward) 1 else -1

    /**
     * Representation of the piece as a character for printing the board
     */
    abstract fun toChar(): Char

    /**
     * @param currentBoardPositions A matrix representing the current board, with -1 for empty tiles and
     *                              >=0 for occupied tiles
     * @return a list of coordinates of possible moves that the piece can go to
     */
    protected abstract fun findPossibleMoves(currentBoardPositions: List<List<Int>>): List<Pair<Int, Int>>

    private var mPossibleMoves = mutableListOf<Pair<Int, Int>>()
    var dirtyMoves = true

    val possibleMoves: List<Pair<Int, Int>>
        get() {
            if (dirtyMoves) {
                mPossibleMoves = findPossibleMoves(board.positions).toMutableList()
                dirtyMoves = false
            }
            return mPossibleMoves
        }



    /**
     * Change the position of this piece.
     * NOTE: This doesn't update the board, only this piece. This should only be called by the board.
     */
    fun move(newPosition: Pair<Int, Int>) {
        position = newPosition
        hasMoved = true
    }
}