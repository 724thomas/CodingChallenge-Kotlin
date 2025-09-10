package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Main {
    class Solution {
        val dx = intArrayOf(0, 1, 1, 1, 0, -1, -1, -1)
        val dy = intArrayOf(1, 1, 0, -1, -1, -1, 0, 1)
        lateinit var board: Array<CharArray>
        var color: Char = 'B'
        var rMove: Int = 0
        var cMove: Int = 0

        fun check(index: Int): Boolean {
            val opp = if (color == 'B') 'W' else 'B'
            var x = rMove + dx[index]
            var y = cMove + dy[index]
            var foundOpp = false

            while (x in 0 until 8 && y in 0 until 8) {
                when (board[x][y]) {
                    opp -> {
                        foundOpp = true
                        x += dx[index]
                        y += dy[index]
                    }
                    color -> {
                        return foundOpp   // 상대 돌을 최소 1개 거쳤으면 true
                    }
                    else -> return false
                }
            }
            return false
        }

        fun checkMove(board: Array<CharArray>, rMove: Int, cMove: Int, color: Char): Boolean {
            this.board = board
            this.rMove = rMove
            this.cMove = cMove
            this.color = color

            for (i in 0 until 8) {
                if (check(i)) return true
            }
            return false
        }
    }


}