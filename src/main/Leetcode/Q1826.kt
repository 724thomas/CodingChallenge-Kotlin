package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Q1826 {
    class Solution {
        fun badSensor(sensor1: IntArray, sensor2: IntArray): Int {
            val n: Int = sensor1.size
            var i: Int = 0

            while (i < n && sensor1[i] == sensor2[i]) i++

            while ( i+1 < n && sensor1[i] == sensor2[i+1] && sensor1[i+1] == sensor2[i]) {
                i++
            }

            return if ( i>= n-1) {
                -1
            } else {
                if (sensor1[i] == sensor2[i+1]) 1 else 2
            }
        }
    }

}