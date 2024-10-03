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

    fun toStringTree() = toStringTreeCurrent("")

    private fun toStringTreeCurrent(childrenPrefix: String = ""): String =
        "$value${toStringTreeChild(leftChild, childrenPrefix, rightChild != null)}${toStringTreeChild(rightChild, childrenPrefix, false)}"

    private fun toStringTreeChild(child: BinaryNode<T>?, prefix: String, hasNext: Boolean) =
        if (child != null) {
            "\n$prefix${if (hasNext) "├──" else "└──"} ${child.toStringTreeCurrent("$prefix${if (hasNext) "│" else " "}   ")}"
        } else {
            ""
        }

    fun preOrderTraverseBreak(block: (BinaryNode<T>) -> Boolean): Boolean {
        if (block(this)) return true
        if (leftChild?.preOrderTraverseBreak(block) == true) return true
        if (rightChild?.preOrderTraverseBreak(block) == true) return true
        return false
    }

    fun preOrderTraverse(block: (BinaryNode<T>) -> Unit) {
        preOrderTraverseBreak {
            block(it)
            false
        }
    }

    fun inOrderTraverseBreak(block: (BinaryNode<T>) -> Boolean): Boolean {
        if (leftChild?.inOrderTraverseBreak(block) == true) return true
        if (block(this)) return true
        if (rightChild?.inOrderTraverseBreak(block) == true) return true
        return false
    }

    fun inOrderTraverse(block: (BinaryNode<T>) -> Unit) {
        inOrderTraverseBreak {
            block(it)
            false
        }
    }

    fun postOrderTraverseBreak(block: (BinaryNode<T>) -> Boolean): Boolean {
        if (leftChild?.postOrderTraverseBreak(block) == true) return true
        if (rightChild?.postOrderTraverseBreak(block) == true) return true
        if (block(this)) return true
        return false
    }

    fun postOrderTraverse(block: (BinaryNode<T>) -> Unit) {
        postOrderTraverseBreak {
            block(it)
            false
        }
    }

    fun inDepthTraverseBreak(block: (BinaryNode<T>) -> Boolean): Boolean {
        val array = ArrayDeque<BinaryNode<T>>()
        array.addLast(this)

        while (!array.isEmpty()) {
            val currentNode = array.removeLast()
            if (block(currentNode)) return true
            currentNode.rightChild?.let { array.addLast(it) }
            currentNode.leftChild?.let { array.addLast(it) }
        }
        return false
    }

    fun inDepthTraverse(block: (BinaryNode<T>) -> Unit) {
        inDepthTraverseBreak {
            block(it)
            false
        }
    }

    fun inBreadthTraverseBreak(block: (BinaryNode<T>) -> Boolean): Boolean {
        val array = ArrayDeque<BinaryNode<T>>()
        array.addLast(this)

        while (!array.isEmpty()) {
            val currentNode = array.removeFirst()
            if (block(currentNode)) return true
            currentNode.leftChild?.let { array.addLast(it) }
            currentNode.rightChild?.let { array.addLast(it) }
        }
        return false
    }

    fun inBreadthTraverse(block: (BinaryNode<T>) -> Unit) {
        inBreadthTraverseBreak {
            block(it)
            false
        }
    }
}
