package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun countOddLetters(n: Int): Int {
        val digitWords = mapOf(
            '0' to "zero", '1' to "one", '2' to "two", '3' to "three",
            '4' to "four", '5' to "five", '6' to "six", '7' to "seven",
            '8' to "eight", '9' to "nine"
        )

        val counter = n.toString()
            .mapNotNull { digitWords[it] }   // 숫자 → 단어
            .flatMap { it.toList() }         // 단어 → 글자 리스트
            .groupingBy { it }
            .eachCount()                     // 글자별 개수 집계

        return counter.values.count { it % 2 == 1 }
    }
}
