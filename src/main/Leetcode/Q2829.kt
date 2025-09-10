package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Main {
    package Leetcode.kotlin


    /*
    1. 아이디어 :


    2. 시간복잡도 :
    O( )

    3. 자료구조/알고리즘 :

     */


    class Main {
        class Solution {
            fun minimumSum(n: Int, k: Int): Int {
                val nums = mutableSetOf<Int>()
                var curr = 1
                var ans = 0

                while (nums.size < n) {
                    val remain = k - curr
                    if (remain !in nums) {
                        nums.add(curr)
                        ans+= curr
                    }
                    curr++
                }

                return ans
            }
        }

    }

}