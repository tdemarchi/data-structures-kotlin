package tkd.datastructure.tree

class TreeNodeDoubleLinked<T>(
    value: T,
    children: List<TreeNode<T>>,
    val generationLevel: Int,
) : TreeNode<T>(value, children) {
    private var _initializing: Boolean = true
    private var _parent: TreeNodeDoubleLinked<T>? = null

    val parent: TreeNodeDoubleLinked<T>?
        get() = _parent

    internal fun setParent(node: TreeNodeDoubleLinked<T>) {
        if (!_initializing) throw IllegalStateException("Node was already initialized.")
        _parent = node
    }

    /**
     * Return the first node from the tree that corresponds to the provided predicate.
     * Throw [IllegalArgumentException] if the provided node is not part of the tree.
     */
    internal inline fun getNode(crossinline nodePredicate: (TreeNodeDoubleLinked<T>) -> Boolean): TreeNodeDoubleLinked<T> =
        super.getNode { it: TreeNode<T> -> nodePredicate(it as TreeNodeDoubleLinked<T>) } as TreeNodeDoubleLinked<T>

    /**
     * Return an array with the first nodes from the tree that corresponds to the provided predicates.
     * Return null in any element of the array that is not part of the tree.
     */
    fun findNodes(vararg nodePredicate: (TreeNodeDoubleLinked<T>) -> Boolean): Array<TreeNodeDoubleLinked<T>?> {
        val nodePredicateAdapted = nodePredicate.map { predicate ->
            { it: TreeNode<T> -> predicate(it as TreeNodeDoubleLinked<T>) }
        }.toTypedArray()
        return super.findNodes(*nodePredicateAdapted).map { it as TreeNodeDoubleLinked<T>? }.toTypedArray()
    }

    /**
     * Return the lowest common ancestor node between two provided nodes.
     * Throw [IllegalArgumentException] if at least one of the provided nodes is not part of the tree.
     */
    fun getLowestCommonAncestor(
        nodeA: TreeNodeDoubleLinked<T>,
        nodeB: TreeNodeDoubleLinked<T>,
    ): TreeNodeDoubleLinked<T> {
        var nodeALeveledParent = nodeA
        var nodeBLeveledParent = nodeB
        while (nodeALeveledParent.generationLevel > nodeBLeveledParent.generationLevel) {
            nodeALeveledParent = nodeALeveledParent.parent!!
        }
        while (nodeBLeveledParent.generationLevel > nodeALeveledParent.generationLevel) {
            nodeBLeveledParent = nodeBLeveledParent.parent!!
        }

        while (true) {
            if (nodeALeveledParent == nodeBLeveledParent) {
                return nodeALeveledParent
            }

            nodeALeveledParent = nodeALeveledParent.parent ?: break
            nodeBLeveledParent = nodeBLeveledParent.parent ?: break
        }

        throw IllegalStateException("No common ancestor found.")
    }

    /**
     * Return the lowest common ancestor node between two provided nodes.
     * Throw [IllegalArgumentException] if at least one of the provided nodes is not part of the tree.
     */
    fun getLowestCommonAncestor(
        nodeAPredicate: (TreeNodeDoubleLinked<T>) -> Boolean,
        nodeBPredicate: (TreeNodeDoubleLinked<T>) -> Boolean,
    ): TreeNodeDoubleLinked<T> {
        val nodes = findNodes(nodeAPredicate, nodeBPredicate)
        return getLowestCommonAncestor(
            nodes[0] ?: throw IllegalArgumentException("First provided node is not part of the tree."),
            nodes[1] ?: throw IllegalArgumentException("Second provided node is not part of the tree."),
        )
    }
}
