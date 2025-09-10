package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Q2427 {
    class Solution {
        fun getFactors(num: Int): MutableSet<Int> {
            val set = mutableSetOf<Int>()
            for (i in 1..kotlin.math.sqrt(num.toDouble()).toInt()) {
                if (num % i == 0) {
                    set.add(i)
                    set.add(num/i)
                }
            }
            return set
        }
        fun commonFactors(a: Int, b: Int): Int {
            return (getFactors(a) intersect getFactors(b)).size
        }
    }

}