package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
class RecentCounter() {
    val deque = ArrayDeque<Int>()

    fun ping(t: Int): Int {
        deque.addLast(t)

        while (deque.first() < t-3000) {
            deque.removeFirst()
        }
        return deque.size
    }

}

/**
 * Your RecentCounter object will be instantiated and called as such:
 * var obj = RecentCounter()
 * var param_1 = obj.ping(t)
 */