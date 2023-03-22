import piece.*
import kotlin.math.abs

class Board(private val height: Int, width: Int, colour1: Colour, colour2: Colour) {

    val pieces: MutableList<Piece> = mutableListOf()

    var enPassant = null as Pair<Int, Int>?

    // index of piece in pieces
    // -1 for empty space
    // index of piece otherwise
    val positions = List(width) { MutableList(height) { -1 } }
    private fun isEmpty(pos: Pair<Int, Int>): Boolean = positions[pos.first][pos.second] == -1

    private fun isNotEmpty(pos: Pair<Int, Int>): Boolean = !isEmpty(pos)

    private val textColEscape = "\u001b"
    private val colString1 = "$textColEscape[38;2;${colour1.red};${colour1.green};${colour1.blue}m"
    private val colString2 = "$textColEscape[38;2;${colour2.red};${colour2.green};${colour2.blue}m"
    private val colBackground1 = "$textColEscape[48;2;210;180;118m"
    private val colBackground2 = "$textColEscape[48;2;153;116;72m"
    private val clearColString = "$textColEscape[0m"

    override fun toString(): String = toStringHighlightingPositions(emptyList())

    fun toStringWithMoves(pieceStr: String): String {
        val piece = pieces[positions[pieceStr[0] - 'a'][pieceStr[1] - '1']]
        val movesTo = piece.possibleMoves
        return toStringHighlightingPositions(movesTo)
    }

    private fun toStringHighlightingPositions(highlightPositions: List<Pair<Int, Int>>): String {
        var colIdx = 0
        return positions.joinToString(separator = "\n") { column ->
            colIdx++
            var rowIdx = 0
            column.joinToString(separator = "") { idx ->
                (if ((rowIdx++ + colIdx) % 2 == 0) colBackground1 else colBackground2) + (if (idx == -1) ' ' else if (idx == -2) ' ' else (if (pieces[idx].playForward) colString1 else colString2) + pieces[idx].toChar()) + (if (Pair(
                        colIdx - 1,
                        rowIdx - 1
                    ) in highlightPositions
                ) '●' else ' ') + clearColString
            }
        }
    }

    /**
     * Adds a piece to the board.
     * @return If the piece was added returns true, otherwise false.
     */
    fun addPiece(piece: Piece): Boolean {

        val position = piece.position

        if (position.first >= positions.size || position.second >= positions[0].size || isNotEmpty(position)) {
            return false
        }

        positions[position.first][position.second] = pieces.size
        pieces.add(piece)

        return true
    }

    fun addPieces(pieces: Iterable<Piece>) = pieces.forEach { piece -> addPiece(piece) }

    fun movePiece(posString1: String, posString2: String): Boolean {
        val pos1 = Pair(posString1[0] - 'a', posString1[1] - '1')
        val pos2 = Pair(posString2[0] - 'a', posString2[1] - '1')
        val pieceIdx = positions[pos1.first][pos1.second]
        val piece = pieces[pieceIdx]

        if (!piece.possibleMoves.contains(pos2)) {
            // Invalid move
            return false
        }

        if (positions[pos2.first][pos2.second] != -1) {
            pieces[positions[pos2.first][pos2.second]].isAlive = false
        }

        positions[pos1.first][pos1.second] = -1
        positions[pos2.first][pos2.second] = pieceIdx
        piece.move(pos2)

        // Castling, bring rook over
        if (piece is King) {
            // If king moved 2 spaces, it was a castle
            if (abs(pos1.first - pos2.first) == 2) {
                val rook = pieces[positions[pos1.first + if (pos1.first < pos2.first) 3 else -4][pos1.second]]
                rook.move(Pair(pos1.first + if (pos1.first < pos2.first) 1 else -1, pos1.second))
                positions[pos1.first + if (pos1.first < pos2.first) 3 else -4][pos1.second] = -1
                positions[pos1.first + if (pos1.first < pos2.first) 1 else -1][pos1.second] = pieces.indexOf(rook)
            }
        }

        // Update En Passant

        if (piece is Pawn) {
            enPassant = if (abs(pos1.second - pos2.second) == 2) pos2 else null
            if (pos1.first != pos2.first && enPassant != null && positions[pos2.first][pos2.second] == -1) {
                // Remove pawn taken by En Passant
                pieces[positions[pos2.first][pos2.second + if (piece.playForward) 1 else -1]].isAlive = false
                positions[pos2.first][pos2.second + if (piece.playForward) 1 else -1] = -1
            }

        }

        pieces.forEach { it.dirtyMoves = true }

        return true
    }

    fun isPositionAttacked(pos: Pair<Int, Int>) = pieces.any { it.possibleMoves.contains(pos) }


    companion object {
        fun defaultChessBoard(): Board {
            val b = Board(8, 8, Colour(0xffffff), Colour(0))
            b.addPieces(
                listOf(
                    Pawn(b, true, Pair(0, 1)),
                    Pawn(b, true, Pair(1, 1)),
                    Pawn(b, true, Pair(3, 1)),
                    Pawn(b, true, Pair(2, 1)),
                    Pawn(b, true, Pair(4, 1)),
                    Pawn(b, true, Pair(5, 1)),
                    Pawn(b, true, Pair(6, 1)),
                    Pawn(b, true, Pair(7, 1)),
                    Pawn(b, false, Pair(0, 6)),
                    Pawn(b, false, Pair(1, 6)),
                    Pawn(b, false, Pair(2, 6)),
                    Pawn(b, false, Pair(3, 6)),
                    Pawn(b, false, Pair(4, 6)),
                    Pawn(b, false, Pair(5, 6)),
                    Pawn(b, false, Pair(6, 6)),
                    Pawn(b, false, Pair(7, 6)),
                    Rook(b, true, Pair(0, 0)),
                    Rook(b, true, Pair(7, 0)),
                    Rook(b, false, Pair(0, 7)),
                    Rook(b, false, Pair(7, 7)),
                    Knight(b, true, Pair(1, 0)),
                    Knight(b, true, Pair(6, 0)),
                    Knight(b, false, Pair(1, 7)),
                    Knight(b, false, Pair(6, 7)),
                    Bishop(b, true, Pair(2, 0)),
                    Bishop(b, true, Pair(5, 0)),
                    Bishop(b, false, Pair(2, 7)),
                    Bishop(b, false, Pair(5, 7)),
                    Queen(b, true, Pair(3, 0)),
                    Queen(b, false, Pair(3, 7)),
                    King(b, true, Pair(4, 0)),
                    King(b, false, Pair(4, 7))
                )
            )
            return b
        }
    }

}