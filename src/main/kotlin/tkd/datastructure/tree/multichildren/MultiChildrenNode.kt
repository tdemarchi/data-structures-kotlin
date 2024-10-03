package tkd.datastructure.tree.multichildren

import java.util.Objects

data class MultiChildrenNode<T>(
    val value: T,
    val children: List<MultiChildrenNode<T>>,
) {
    constructor(value: T, vararg children: MultiChildrenNode<T>) : this(value, listOf(*children))

    override fun toString(): String = """MultiChildrenNode($value)"""

    override fun equals(other: Any?): Boolean =
        if (other is MultiChildrenNode<*>) {
            Objects.equals(value, other.value)
        } else {
            false
        }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    fun toStringTree() = toStringTreeCurrent("")

    private fun toStringTreeCurrent(childrenPrefix: String = ""): String {
        val sb = StringBuilder()
        sb.append(value)
        for (i in children.indices) {
            sb.append(toStringTreeChild(children[i], childrenPrefix, i < children.size - 1))
        }
        return sb.toString()
    }

    private fun toStringTreeChild(child: MultiChildrenNode<T>?, prefix: String, hasNext: Boolean) =
        if (child != null) {
            "\n$prefix${if (hasNext) "├──" else "└──"} ${child.toStringTreeCurrent("$prefix${if (hasNext) "│" else " "}   ")}"
        } else {
            ""
        }

    fun inDepthTraverseBreak(block: (MultiChildrenNode<T>) -> Boolean): Boolean {
        val array = ArrayDeque<MultiChildrenNode<T>>()
        array.add(this)

        while (!array.isEmpty()) {
            val currentNode = array.removeLast()
            if (block(currentNode)) return true
            array.addAll(currentNode.children.reversed())
        }
        return false
    }

    fun inDepthTraverse(block: (MultiChildrenNode<T>) -> Unit) {
        inDepthTraverseBreak {
            block(it)
            false
        }
    }

    fun inBreadthTraverseBreak(block: (MultiChildrenNode<T>) -> Boolean): Boolean {
        val array = ArrayDeque<MultiChildrenNode<T>>()
        array.add(this)

        while (!array.isEmpty()) {
            val currentNode = array.removeFirst()
            if (block(currentNode)) return true
            array.addAll(currentNode.children)
        }
        return false
    }

    fun inBreadthTraverse(block: (MultiChildrenNode<T>) -> Unit) {
        inBreadthTraverseBreak {
            block(it)
            false
        }
    }
}
