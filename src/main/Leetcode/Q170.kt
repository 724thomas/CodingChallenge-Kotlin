package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */


class Q170 {
    class TwoSum() {
        val nums = mutableMapOf<Int, Int>()

        fun add(number: Int) {
            nums[number] = nums.getOrDefault(number, 0) + 1
        }

        fun find(value: Int): Boolean {
            for ((num, count) in nums) {
                val remain = value - num
                if (remain == num) {
                    if (count >= 2) return true
                } else {
                    if (nums.containsKey(remain)) return true
                }
            }
            return false
        }

    }

    /**
     * Your TwoSum object will be instantiated and called as such:
     * var obj = TwoSum()
     * obj.add(number)
     * var param_2 = obj.find(value)
     */

}