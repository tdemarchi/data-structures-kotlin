package tkd.datastructure.tree

import java.util.*
import java.util.function.Predicate
import kotlin.collections.ArrayDeque

data class TreeNode<T>(
    val value: T,
    val children: List<TreeNode<T>>,
) {
    companion object {
        private const val VISUAL_TREE_FIRST_CHILD_INDICATOR = "├──"
        private const val VISUAL_TREE_LAST_CHILD_INDICATOR = "└──"
        private const val VISUAL_TREE_FIRST_CHILD_GRANDCHILDREN_INDICATOR = "│   "
        private const val VISUAL_TREE_LAST_CHILD_GRANDCHILDREN_INDICATOR = "    "

        // ASCII version
        // private const val VISUAL_TREE_FIRST_CHILD_INDICATOR = "+--"
        // private const val VISUAL_TREE_LAST_CHILD_INDICATOR = "+--"
        // private const val VISUAL_TREE_FIRST_GRANDCHILD_INDICATOR = "|   "
        // private const val VISUAL_TREE_LAST_CHILD_GRANDCHILDREN_INDICATOR = "    "

        data class TreeNodeTraverseNodeWrapper<T, P>(
            val node: TreeNode<T>,
            val payload: P,
        )
    }

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
            "\n$prefix${if (hasNext) VISUAL_TREE_FIRST_CHILD_INDICATOR else VISUAL_TREE_LAST_CHILD_INDICATOR} ${
                child.toStringTreeCurrent(
                    "$prefix${if (hasNext) VISUAL_TREE_FIRST_CHILD_GRANDCHILDREN_INDICATOR else VISUAL_TREE_LAST_CHILD_GRANDCHILDREN_INDICATOR}"
                )
            }"
        } else {
            ""
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
        val stack = ArrayDeque<TreeNode<T>>()
        stack.add(this)

        while (!stack.isEmpty()) {
            val currentNode = stack.removeLast()
            block(currentNode)
            stack.addAll(currentNode.children.reversed())
        }
    }

    /**
     * Traverse the tree in breadth, calling [block] for each node.
     *
     * This algorithm carries a payload of type [Payload] with each to be visited.
     * The payload for each node should be provided by the [payloadProvider] function.
     */
    inline fun <Payload> inDepthTraverse(
        rootPayload: Payload,
        payloadProvider: (TreeNode<T>, TreeNodeTraverseNodeWrapper<T, Payload>) -> Payload, // (current node, parent's wrapper) -> the payload for the current node
        block: (TreeNodeTraverseNodeWrapper<T, Payload>) -> Unit
    ) {
        val stack = ArrayDeque<TreeNodeTraverseNodeWrapper<T, Payload>>()
        stack.add(TreeNodeTraverseNodeWrapper(this, rootPayload))

        while (!stack.isEmpty()) {
            val currentWrapper = stack.removeLast()
            block(currentWrapper)
            stack.addAll(
                currentWrapper.node.children.reversed()
                    .map { childNode ->
                        TreeNodeTraverseNodeWrapper(childNode, payloadProvider(childNode, currentWrapper))
                    }
            )
        }
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
        val queue = ArrayDeque<TreeNode<T>>()
        queue.add(this)

        while (!queue.isEmpty()) {
            val currentNode = queue.removeFirst()
            block(currentNode)
            queue.addAll(currentNode.children)
        }
    }

    /**
     * Return the generation of a descendant node.
     * Return null if the descendant is not found.
     */
    fun getDescendantGeneration(descendantNodePredicate: Predicate<TreeNode<T>>): Int? {
        inDepthTraverse<Int>(
            rootPayload = 0,
            payloadProvider = { _, parent ->
                parent.payload + 1
            }
        ) { currentWrapper ->
            if (descendantNodePredicate.test(currentWrapper.node)) {
                return@getDescendantGeneration currentWrapper.payload
            }
        }
        return null
    }
}
