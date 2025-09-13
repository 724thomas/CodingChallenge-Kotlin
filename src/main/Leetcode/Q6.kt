package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    var reversed = false
    var numRows = 0

    fun getNextRow(currRow: Int): Int {
        var next = currRow + if (reversed) -1 else 1
        if (next == 0 || next == numRows - 1) {
            reversed = !reversed
        }
        return next
    }

    fun convert(s: String, numRows: Int): String {
        if (numRows == 1 || s.length <= numRows) return s
        this.numRows = numRows

        val rows = MutableList(numRows) { StringBuilder() }
        var currRow = 0

        for (c in s) {
            rows[currRow].append(c)
            currRow = getNextRow(currRow)
        }

        return rows.joinToString(separator = "") { it.toString() }
    }
}
