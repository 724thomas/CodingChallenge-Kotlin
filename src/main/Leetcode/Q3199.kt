package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Main {
    class Solution {
        fun tripletCount(a: IntArray, b: IntArray, c: IntArray): Int {
            var ans = 0
            for (x in a) for (y in b) for (z in c) {
                val xorVal = x xor y xor z
                if (xorVal.countOneBits() % 2 == 0) {
                    ans++
                }
            }

            return ans
        }
    }

}