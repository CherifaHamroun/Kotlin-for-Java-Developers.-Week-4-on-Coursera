package board

import board.Direction.*
open class SB(override val width: Int) : SquareBoard{
    var squareboard : MutableList<Cell> = mutableListOf()
    init {
        for (i in 1 until width+1) for (j in 1 until width+1) squareboard = (squareboard + Cell(i,j)).toMutableList()
    }
    override fun getCellOrNull(i: Int, j: Int): Cell? = if (i in 1 until width+1 && j in 1 until width+1) squareboard[((i-1)*width)+j-1] else null
    override fun getCell(i: Int, j: Int): Cell {
        require(i in 1 until width+1 || j in 1 until width+1){"incorrect values of i and j."}
        return squareboard[((i-1)*width)+j-1]
    }

    override fun getAllCells(): Collection<Cell> {
        return squareboard
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> = if (jRange.first<jRange.last)squareboard.filter { it.i  == i && it.j in jRange } else squareboard.filter { it.i  == i && it.j in jRange }.reversed()
    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> = if (iRange.first<iRange.last)squareboard.filter { it.j  == j && it.i in iRange } else squareboard.filter { it.j  == j && it.i in iRange }.reversed()

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when(direction){
            UP -> getCellOrNull(this.i-1,this.j)
            DOWN -> getCellOrNull(this.i+1,this.j)
            RIGHT -> getCellOrNull(this.i,this.j+1)
            LEFT -> getCellOrNull(this.i,this.j-1)
        }
    }
}
class GB<Any>(override val width: Int) : GameBoard<Any>,SB(width){
    var gameboard : Map<Cell,Any?>?
    val S = SB(width)
    init {
       gameboard = S.squareboard.map { it to null }.toMap()
    }
    override operator fun get(cell: Cell): Any? = gameboard!![cell]
    override operator fun set(cell: Cell, value: Any?) {
        gameboard = gameboard?.plus(Pair(cell,value))
    }
    override fun getCell(i:Int, j:Int):Cell{
        return S.getCell(i,j)
    }
    override fun filter(predicate: (Any?) -> Boolean): Collection<Cell> {
        return this.gameboard?.filter { predicate(it.value) }?.keys ?: listOf()
    }
    override fun find(predicate: (Any?) -> Boolean): Cell? {
        return this.gameboard?.filter { predicate(it.value) }?.keys?.first()
    }
    override fun any(predicate: (Any?) -> Boolean): Boolean {
        return this.gameboard?.any { predicate(it.value) } ?: false
    }
    override fun all(predicate: (Any?) -> Boolean): Boolean {
        return this.gameboard?.all { predicate(it.value) } ?: false
    }
}
fun createSquareBoard(width: Int): SquareBoard = SB(width)
fun <T> createGameBoard(width: Int): GameBoard<T>{
   return GB(width)
}


