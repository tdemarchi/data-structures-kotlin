package tkd.datastructure.tree

import java.util.Objects

data class TreeNode<T>(
    val value: T,
    val children: List<TreeNode<T>>,
) {
    constructor(value: T, vararg children: TreeNode<T>) : this(value, listOf(*children))

    override fun toString(): String = """TreeNode($value)"""

    override fun equals(other: Any?): Boolean =
        if (other is TreeNode<*>) {
            Objects.equals(value, other.value)
        } else {
            false
        }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    /**
     * Return a visual-string representation of the tree.
     */
    fun toStringTree() = toStringTreeCurrent("")

    private fun toStringTreeCurrent(childrenPrefix: String = ""): String {
        val sb = StringBuilder()
        sb.append(value)
        for (i in children.indices) {
            sb.append(toStringTreeChild(children[i], childrenPrefix, i < children.size - 1))
        }
        return sb.toString()
    }

    private fun toStringTreeChild(child: TreeNode<T>?, prefix: String, hasNext: Boolean) =
        if (child != null) {
            "\n$prefix${if (hasNext) "├──" else "└──"} ${child.toStringTreeCurrent("$prefix${if (hasNext) "│" else " "}   ")}"
        } else {
            ""
        }

    /**
     * Traverse the tree in depth, calling [block] for each node.
     * When [block] returns null, the traversing stops.
     */
    inline fun inDepthTraverseBreak(block: (TreeNode<T>) -> Boolean): Boolean {
        val array = ArrayDeque<TreeNode<T>>()
        array.add(this)

        while (!array.isEmpty()) {
            val currentNode = array.removeLast()
            if (block(currentNode)) return true
            array.addAll(currentNode.children.reversed())
        }
        return false
    }

    /**
     * Traverse the tree in depth, calling [block] for each node.
     *
     * Traversing the below tree in depth results in this visiting order: A B C D E F G H I J K L M N
     *
     *   A                    ( 1)
     *   ├── B                ( 2)
     *   │   ├── C            ( 3)
     *   │   │   ├── D        ( 4)
     *   │   │   ├── E        ( 5)
     *   │   │   └── F        ( 6)
     *   │   └── G            ( 7)
     *   │       └── H        ( 8)
     *   │           ├── I    ( 9)
     *   │           └── J    (10)
     *   ├── K                (11)
     *   └── L                (12)
     *       ├── M            (13)
     *       └── N            (14)
     */
    inline fun inDepthTraverse(block: (TreeNode<T>) -> Unit) {
        inDepthTraverseBreak {
            block(it)
            false
        }
    }

    /**
     * Traverse the tree in breadth, calling [block] for each node.
     * When [block] returns null, the traversing stops.
     */
    inline fun inBreadthTraverseBreak(block: (TreeNode<T>) -> Boolean): Boolean {
        val array = ArrayDeque<TreeNode<T>>()
        array.add(this)

        while (!array.isEmpty()) {
            val currentNode = array.removeFirst()
            if (block(currentNode)) return true
            array.addAll(currentNode.children)
        }
        return false
    }

    /**
     * Traverse the tree in breadth, calling [block] for each node.
     *
     * Traversing the below tree in breadth results in this visiting order: A B K L C G M N D E F H I J
     *
     *   A                    ( 1)
     *   ├── B                ( 2)
     *   │   ├── C            ( 5)
     *   │   │   ├── D        ( 9)
     *   │   │   ├── E        (10)
     *   │   │   └── F        (11)
     *   │   └── G            ( 6)
     *   │       └── H        (12)
     *   │           ├── I    (13)
     *   │           └── J    (14)
     *   ├── K                ( 3)
     *   └── L                ( 4)
     *       ├── M            ( 7)
     *       └── N            ( 8)
     */
    inline fun inBreadthTraverse(block: (TreeNode<T>) -> Unit) {
        inBreadthTraverseBreak {
            block(it)
            false
        }
    }
}
