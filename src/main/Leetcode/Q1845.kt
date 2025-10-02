package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class SeatManager(n: Int) {
    val treeSet = TreeSet<Int>()
    init {
        for (i in 1..n) {
            treeSet.add(i)
        }
    }

    fun reserve(): Int {
        val first = treeSet.first()
        treeSet.remove(first)
        return first
    }

    fun unreserve(seatNumber: Int) = treeSet.add(seatNumber)

}

/**
 * Your SeatManager object will be instantiated and called as such:
 * var obj = SeatManager(n)
 * var param_1 = obj.reserve()
 * obj.unreserve(seatNumber)
 */