package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun clumsy(n: Int): Int {
        val stack = ArrayDeque<Int>()
        stack.addLast(n)
        var op = 0  // 0=*, 1=/, 2=+, 3=-

        for (i in n - 1 downTo 1) {
            when (op) {
                0 -> {
                    val prev = stack.removeLast()
                    stack.addLast(prev * i)
                }
                1 -> {
                    val prev = stack.removeLast()
                    stack.addLast(prev / i)  // Int 나눗셈 → floor division
                }
                2 -> stack.addLast(i)
                3 -> stack.addLast(-i)
            }
            op = (op + 1) % 4
        }

        return stack.sum()
    }
}
