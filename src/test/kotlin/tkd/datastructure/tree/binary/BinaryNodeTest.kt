package tkd.datastructure.tree.binary

import io.kotest.core.spec.style.WordSpec

class BinaryNodeTest : WordSpec() {
    init {
        val tree = BinaryNode(
            "A",
            BinaryNode(
                "B",
                BinaryNode(
                    "C",
                    BinaryNode("D"),
                    BinaryNode("E"),
                ),
                BinaryNode(
                    "F",
                    BinaryNode(
                        "G",
                        BinaryNode("H"),
                        BinaryNode("I"),
                    ),
                ),
            ),
            BinaryNode("J"),
        )

        "toStringTree" should {
            "-" {
                println(tree.toStringTree())
            }
        }

        "preOrderTraverse" should {
            "-" {
                print("preOrderTraverse: ")
                tree.preOrderTraverse {
                    print(it.value)
                }
                println()
            }
        }


        "inOrderTraverse" should {
            "-" {
                print("inOrderTraverse: ")
                tree.inOrderTraverse {
                    print(it.value)
                }
                println()
            }
        }


        "postOrderTraverse" should {
            "-" {
                print("postOrderTraverse: ")
                tree.postOrderTraverse {
                    print(it.value)
                }
                println()
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
