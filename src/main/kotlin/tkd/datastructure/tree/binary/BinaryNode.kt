package tkd.datastructure.tree.binary

import java.util.Objects

data class BinaryNode<T>(
    val value: T,
    val leftChild: BinaryNode<T>? = null,
    val rightChild: BinaryNode<T>? = null,
) {
    override fun toString(): String = """BinaryNode($value)"""

    override fun equals(other: Any?): Boolean =
        if (other is BinaryNode<*>) {
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

    private fun toStringTreeCurrent(childrenPrefix: String = ""): String =
        "$value${toStringTreeChild(leftChild, childrenPrefix, rightChild != null)}${toStringTreeChild(rightChild, childrenPrefix, false)}"

    private fun toStringTreeChild(child: BinaryNode<T>?, prefix: String, hasNext: Boolean) =
        if (child != null) {
            "\n$prefix${if (hasNext) "├──" else "└──"} ${child.toStringTreeCurrent("$prefix${if (hasNext) "│" else " "}   ")}"
        } else {
            ""
        }

    /**
     * Traverse the tree in pre-order (depth parent-left-right), calling [block] for each node.
     * When [block] returns null, the traversing stops.
     */
    fun preOrderTraverseBreak(block: (BinaryNode<T>) -> Boolean): Boolean {
        if (block(this)) return true
        if (leftChild?.preOrderTraverseBreak(block) == true) return true
        if (rightChild?.preOrderTraverseBreak(block) == true) return true
        return false
    }

    /**
     * Traverse the tree in pre-order (depth parent-left-right), calling [block] for each node.
     *
     * Traversing the below tree in pre-order results in this visiting order: A B C D E F G H I J K L M N
     *
     *   A                    ( 1)
     *   ├── B                ( 2)
     *   │   ├── C            ( 3)
     *   │   │   ├── D        ( 4)
     *   │   │   └── E        ( 5)
     *   │   └── F            ( 6)
     *   │       └── G        ( 7)
     *   │           ├── H    ( 8)
     *   │           └── I    ( 9)
     *   └── J                (10)
     *
     * Note: this is results in the same as traversing in depth,
     * but this uses recursion and in depth uses a queue structure.
     */
    inline fun preOrderTraverse(crossinline block: (BinaryNode<T>) -> Unit) {
        preOrderTraverseBreak {
            block(it)
            false
        }
    }

    /**
     * Traverse the tree in in-order (depth left-parent-right), calling [block] for each node.
     * When [block] returns null, the traversing stops.
     */
    fun inOrderTraverseBreak(block: (BinaryNode<T>) -> Boolean): Boolean {
        if (leftChild?.inOrderTraverseBreak(block) == true) return true
        if (block(this)) return true
        if (rightChild?.inOrderTraverseBreak(block) == true) return true
        return false
    }

    /**
     * Traverse the tree in in-order (depth left-parent-right), calling [block] for each node.
     *
     * Traversing the below tree in in-order results in this visiting order: D C E B H G I F A J
     *
     *   A                    ( 9)
     *   ├── B                ( 4)
     *   │   ├── C            ( 2)
     *   │   │   ├── D        ( 1)
     *   │   │   └── E        ( 3)
     *   │   └── F            ( 8)
     *   │       └── G        ( 6)
     *   │           ├── H    ( 5)
     *   │           └── I    ( 7)
     *   └── J                (10)
     */
    inline fun inOrderTraverse(crossinline block: (BinaryNode<T>) -> Unit) {
        inOrderTraverseBreak {
            block(it)
            false
        }
    }

    /**
     * Traverse the tree in post-order (depth left-right-parent), calling [block] for each node.
     * When [block] returns null, the traversing stops.
     */
    fun postOrderTraverseBreak(block: (BinaryNode<T>) -> Boolean): Boolean {
        if (leftChild?.postOrderTraverseBreak(block) == true) return true
        if (rightChild?.postOrderTraverseBreak(block) == true) return true
        if (block(this)) return true
        return false
    }

    /**
     * Traverse the tree in post-order (depth left-right-parent), calling [block] for each node.
     *
     * Traversing the below tree in post-order results in this visiting order: D E C H I G F B J A
     *
     *   A                    (10)
     *   ├── B                ( 8)
     *   │   ├── C            ( 3)
     *   │   │   ├── D        ( 1)
     *   │   │   └── E        ( 2)
     *   │   └── F            ( 7)
     *   │       └── G        ( 6)
     *   │           ├── H    ( 4)
     *   │           └── I    ( 5)
     *   └── J                ( 9)
     */
    inline fun postOrderTraverse(crossinline block: (BinaryNode<T>) -> Unit) {
        postOrderTraverseBreak {
            block(it)
            false
        }
    }

    /**
     * Traverse the tree in depth, calling [block] for each node.
     * When [block] returns null, the traversing stops.
     */
    inline fun inDepthTraverseBreak(block: (BinaryNode<T>) -> Boolean): Boolean {
        val stack = ArrayDeque<BinaryNode<T>>()
        stack.addLast(this)

        while (!stack.isEmpty()) {
            val currentNode = stack.removeLast()
            if (block(currentNode)) return true
            currentNode.rightChild?.let { stack.addLast(it) }
            currentNode.leftChild?.let { stack.addLast(it) }
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
     *   │   │   └── E        ( 5)
     *   │   └── F            ( 6)
     *   │       └── G        ( 7)
     *   │           ├── H    ( 8)
     *   │           └── I    ( 9)
     *   └── J                (10)
     */
    inline fun inDepthTraverse(block: (BinaryNode<T>) -> Unit) {
        inDepthTraverseBreak {
            block(it)
            false
        }
    }

    /**
     * Traverse the tree in breadth, calling [block] for each node.
     * When [block] returns null, the traversing stops.
     */
    inline fun inBreadthTraverseBreak(block: (BinaryNode<T>) -> Boolean): Boolean {
        val queue = ArrayDeque<BinaryNode<T>>()
        queue.addLast(this)

        while (!queue.isEmpty()) {
            val currentNode = queue.removeFirst()
            if (block(currentNode)) return true
            currentNode.leftChild?.let { queue.addLast(it) }
            currentNode.rightChild?.let { queue.addLast(it) }
        }
        return false
    }

    /**
     * Traverse the tree in breadth, calling [block] for each node.
     *
     * Traversing the below tree in breadth results in this visiting order: A B J C F D E G H I
     *
     *   A                    ( 1)
     *   ├── B                ( 2)
     *   │   ├── C            ( 4)
     *   │   │   ├── D        ( 6)
     *   │   │   └── E        ( 7)
     *   │   └── F            ( 5)
     *   │       └── G        ( 8)
     *   │           ├── H    ( 9)
     *   │           └── I    (10)
     *   └── J                ( 3)
     *
     * Note: this is results in the same as traversing in pre-order,
     * but this uses a queue structure and in pre-order uses recursion.
     */
    inline fun inBreadthTraverse(block: (BinaryNode<T>) -> Unit) {
        inBreadthTraverseBreak {
            block(it)
            false
        }
    }
}
