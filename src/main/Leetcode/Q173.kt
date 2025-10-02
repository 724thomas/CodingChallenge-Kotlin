package Leetcode.kotlin


/*
1. 아이디어 :


2. 시간복잡도 :
O( )

3. 자료구조/알고리즘 :

 */
/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */
class BSTIterator(root: TreeNode?) {
    val nums = mutableListOf<Int>()
    var idx = -1
    var size = 0

    init {
        inorder(root)
        size = nums.size
    }

    fun inorder(node: TreeNode?) {
        if (node == null) return

        inorder(node.left)
        nums.add(node.`val`)
        inorder(node.right)
    }

    fun next(): Int = nums[++idx]

    fun hasNext(): Boolean = idx != size-1

}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * var obj = BSTIterator(root)
 * var param_1 = obj.next()
 * var param_2 = obj.hasNext()
 */