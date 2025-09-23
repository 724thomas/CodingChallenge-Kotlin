package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun calPoints(operations: Array<String>): Int {
        val stack = mutableListOf<Int>()
        var ans = 0
        val n = operations.size
        for (op in operations) {
            when (op) {
                "C" -> stack.removeLast()
                "D" -> stack.add(stack.last() * 2)
                "+" -> stack.add(stack[stack.lastIndex] + stack[stack.lastIndex - 1])
                else -> stack.add(op.toInt())
            }
        }
        return stack.sum()
    }
}