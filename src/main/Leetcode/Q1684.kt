package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Q1684 {
    class Solution {
        fun countConsistentStrings(allowed: String, words: Array<String>): Int {
            val exist = BooleanArray(26)
            for (c in allowed) {
                exist[c-'a']=true
            }

            var ans: Int = 0
            for (word in words) {
                var exists: Boolean = true
                for (c in word) {
                    if (!exist[c-'a']) {
                        exists = false
                        break
                    }
                }
                if (exists) ans++
            }
            return ans
        }
    }

}