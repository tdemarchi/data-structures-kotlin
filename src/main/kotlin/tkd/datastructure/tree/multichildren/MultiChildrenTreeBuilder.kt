package tkd.datastructure.tree.multichildren

class MultiChildrenTreeBuilder<T> {
    private data class NodeData<T>(
        val value: T,
    ) {
        var parent: T? = null
        val children = mutableListOf<T>()
    }

    private val nodeDataMap: MutableMap<T, NodeData<T>> = LinkedHashMap()

    fun edge(nodeAValue: T, nodeBValue: T): MultiChildrenTreeBuilder<T> {
        nodeDataMap.computeIfAbsent(nodeAValue) { _ ->
            NodeData(nodeAValue)
        }.children.add(nodeBValue)

        nodeDataMap.compute(nodeBValue) { _, currentNode ->
            currentNode
                ?.also { node ->
                    if (node.parent != null) {
                        throw IllegalArgumentException("""The edge ($nodeAValue, $nodeBValue) declares a child node $nodeBValue that is already child of ${node.parent}.""")
                    }
                }
                ?: NodeData(nodeBValue)
        }?.parent = nodeAValue

        return this
    }

    fun edge(values: String, delimiter: String = " ", mappingFunction: (String) -> T): MultiChildrenTreeBuilder<T> {
        val arr = values.trim().also {
            if (it.isEmpty()) throw IllegalArgumentException("""The edge string is empty.""")
        }.split(delimiter)
        if (arr.size != 2) throw IllegalArgumentException("""The edge string ("$values") contains ${arr.size} elements but should contain exactly 2 elements.""")

        val valueA: T = try {
            mappingFunction(arr[0])
        } catch (e: Exception) {
            throw IllegalArgumentException(
                """The first element ("${arr[0]}") on the edge string ("$values") could not be transformed: ${e.message}""",
                e
            )
        }

        val valueB: T = try {
            mappingFunction(arr[1])
        } catch (e: Exception) {
            throw IllegalArgumentException(
                """The second element ("${arr[1]}") on the edge string ("$values") could not be transformed: ${e.message}""",
                e
            )
        }

        return edge(valueA, valueB)
    }

    fun edgesFromLines(
        input: String,
        delimiter: String = " ",
        mappingFunction: (String) -> T
    ): MultiChildrenTreeBuilder<T> {
        input.lines().forEach { line ->
            edge(line, delimiter, mappingFunction)
        }
        return this
    }

    private fun buildNode(value: T): MultiChildrenNode<T> {
        val nodeData = nodeDataMap[value]!!
        return MultiChildrenNode(
            value = nodeData.value,
            children = nodeData.children.map { buildNode(it) }
        )
    }

    fun build(): MultiChildrenNode<T> =
        nodeDataMap.values
            .filter { it.parent == null }
            .let { rootList ->
                if (rootList.isEmpty()) throw IllegalArgumentException("Problem building tree, there is no root node.")
                if (rootList.size > 1) throw IllegalArgumentException("Problem building tree, there are more than one root node ${rootList.map { it.value }}.")
                buildNode(rootList.first().value)
            }
}

fun <T> multiChildrenTreeBuilder() = MultiChildrenTreeBuilder<T>()
