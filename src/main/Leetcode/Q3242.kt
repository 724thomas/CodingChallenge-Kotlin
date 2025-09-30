package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class NeighborSum(
    private val grid: Array<IntArray>
) {
    val map = mutableMapOf<Int, Pair<Int, Int>>()
    val size = grid.size
    val adDir = listOf(
        0 to 1,
        0 to -1,
        1 to 0,
        -1 to 0,
    )
    val diDir = listOf(
        1 to 1,
        1 to -1,
        -1 to 1,
        -1 to -1,
    )
    init {
        for (i in 0..size-1) {
            for (j in 0..size-1) {
                map[grid[i][j]] = i to j
            }
        }
    }

    fun adjacentSum(value: Int): Int {
        val pos = map[value] ?: return 0
        val (row, col) = pos

        var sum = 0
        for ((dx, dy) in adDir) {
            val nx = row + dx
            val ny = col + dy
            if (nx < 0 || ny < 0 || nx >= size || ny >= size) continue
            sum += grid[nx][ny]
        }

        return sum
    }

    fun diagonalSum(value: Int): Int {
        val pos = map[value] ?: return 0
        val (row, col) = pos

        var sum = 0
        for ((dx, dy) in diDir) {
            val nx = row + dx
            val ny = col + dy
            if (nx < 0 || ny < 0 || nx >= size || ny >= size) continue
            sum += grid[nx][ny]
        }

        return sum
    }

}

/**
 * Your NeighborSum object will be instantiated and called as such:
 * var obj = NeighborSum(grid)
 * var param_1 = obj.adjacentSum(value)
 * var param_2 = obj.diagonalSum(value)
 */