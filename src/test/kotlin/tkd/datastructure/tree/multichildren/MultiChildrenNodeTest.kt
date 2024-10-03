package tkd.datastructure.tree.multichildren

import io.kotest.core.spec.style.WordSpec

class MultiChildrenNodeTest : WordSpec() {
    init {
        val tree = MultiChildrenNode(
            "A",
            MultiChildrenNode(
                "B",
                MultiChildrenNode(
                    "C",
                    MultiChildrenNode("D"),
                    MultiChildrenNode("E"),
                    MultiChildrenNode("F"),
                ),
                MultiChildrenNode(
                    "G",
                    MultiChildrenNode(
                        "H",
                        MultiChildrenNode("I"),
                        MultiChildrenNode("J"),
                    ),
                ),
            ),
            MultiChildrenNode("K"),
            MultiChildrenNode(
                "L",
                MultiChildrenNode("M"),
                MultiChildrenNode("N"),
            )
        )

        "toStringTree" should {
            "-" {
                println(tree.toStringTree())
            }
        }


        "inDepthTraverse" should {
            "-" {
                print("inDepthTraverse: ")
                tree.inDepthTraverse {
                    print(it.value)
                }
                println()
            }
        }


        "inBreadthTraverse" should {
            "-" {
                print("inBreadthTraverse: ")
                tree.inBreadthTraverse {
                    print(it.value)
                }
                println()
            }
        }

    }
}
