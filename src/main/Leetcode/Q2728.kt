package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Main {
/*
/**
 * Definition for a street.
 * class Street(doors: IntArray) {
 *     fun openDoor()
 *     fun closeDoor()
 *     fun isDoorOpen(): Boolean
 *     fun moveRight()
 *     fun moveLeft()
 * }
 */
class Solution {
    fun houseCount(street: Street, k: Int): Int {
        repeat(k) {
            street.closeDoor()
            street.moveRight()
        }

        var ans = 0
        repeat(k) {
            if (street.isDoorOpen()) return ans
            street.openDoor()
            street.moveRight()
            ans++
        }
        return ans
    }
}
 */

}