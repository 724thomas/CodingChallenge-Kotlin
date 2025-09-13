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
class Solution {
    var ans: Int = 0

    fun dfs(node: TreeNode?, par: Int) : Int {
        if (node == null) return 0

        val currVal = node.`val`
        val left = dfs(node.left, currVal)
        val right = dfs(node.right, currVal)
        ans = max(ans, left + right)

        return if (currVal == par) 1 + max(left, right) else 0
    }

    fun longestUnivaluePath(root: TreeNode?): Int {
        if (root == null) return 0
        dfs(root, root.`val`)
        return ans
    }
}