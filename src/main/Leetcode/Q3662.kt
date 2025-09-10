package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Main {
    class Solution {
        fun filterCharacters(s: String, k: Int): String {
            val counter = HashMap<Char, Int>()
            for (c in s) {
                counter[c] = (counter[c] ?: 0) + 1
            }

            val sb = StringBuilder()
            for (c in s) {
                if ((counter[c] ?: 0) >= k) continue
                sb.append(c);
            }

            return sb.toString()
        }
    }
}