package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */
class Solution {
    fun insertionSortList(head: ListNode?): ListNode? {
        if (head?.next == null) return head

        var dummy = ListNode(0)
        var curr = head

        while (curr != null) {
            var prev: ListNode? = dummy
            while (prev?.next != null && prev.next!!.`val` < curr.`val`) {
                prev = prev.next
            }

            val next = curr.next
            curr.next = prev?.next
            prev?.next = curr
            curr = next
        }

        return dummy.next
    }
}
