package Leetcode.kotlin

import java.math.BigInteger


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Q989 {
    class Solution {
        fun addToArrayForm(num: IntArray, k: Int): List<Int> {
            return (BigInteger(num.joinToString("")) + k.toBigInteger()).toString().map { it - '0' }
        }
    }

}