package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Q2432 {
    class Solution {
        fun hardestWorker(n: Int, logs: Array<IntArray>): Int {
            var id = Int.MAX_VALUE
            var dur = 0

            var time = 0
            for ((cid, cend) in logs) {
                val cdur = cend - time
                when {
                    cdur > dur -> {
                        dur = cdur
                        id = cid
                    }
                    cdur == dur && cid < id -> {
                        id = cid
                    }
                }
                time = cend
            }

            return id
        }
    }

}