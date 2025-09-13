package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class Solution {
    fun numberOfSpecialChars(word: String): Int {
        val exclude = BooleanArray(26)

        val lowerChar = mutableSetOf<Char>()
        val upperChar = mutableSetOf<Char>()

        for (c in word) {
            if (c.isLowerCase()) {
                lowerChar.add(c)
                if (upperChar.contains(c)) {
                    exclude[c-'a'] = true
                }
            } else {
                upperChar.add(c.lowercaseChar())
            }
        }

        var ans = 0
        for (i in 0..25) {
            val c = 'a' + i
            if (lowerChar.contains(c) && upperChar.contains(c) && !exclude[i]) {
                ans++
            }
        }

        return ans
    }
}