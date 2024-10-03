package tkd.datastructure.tree

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class TreeNodeTest : WordSpec() {
    init {
        val tree = TreeNode(
            "A",
            TreeNode(
                "B",
                TreeNode(
                    "C",
                    TreeNode("D"),
                    TreeNode("E"),
                    TreeNode("F"),
                ),
                TreeNode(
                    "G",
                    TreeNode(
                        "H",
                        TreeNode("I"),
                        TreeNode("J"),
                    ),
                ),
            ),
            TreeNode("K"),
            TreeNode(
                "L",
                TreeNode("M"),
                TreeNode("N"),
            )
        )

        "toStringTree" should {
            "return the visual three" {
                val expected = """
                    A
                    ├── B
                    │   ├── C
                    │   │   ├── D
                    │   │   ├── E
                    │   │   └── F
                    │   └── G
                    │       └── H
                    │           ├── I
                    │           └── J
                    ├── K
                    └── L
                        ├── M
                        └── N
                """.trimIndent()

                val obtained = tree.toStringTree()
                println(obtained)

                obtained shouldBe expected
            }
        }

        "inDepthTraverse" should {
            "traverse nodes in depth" {
                val obtainedStringBuilder = StringBuilder()

                print("inDepthTraverse: ")
                tree.inDepthTraverse {
                    print(it.value)
                    obtainedStringBuilder.append(it.value)
                }
                println()

                obtainedStringBuilder.toString() shouldBe "ABCDEFGHIJKLMN"
            }
        }

        "inDepthTraverseBreak" should {
            "traverse nodes in depth, breaking when asked" {
                val obtainedStringBuilder = StringBuilder()

                print("inDepthTraverseBreak: ")
                tree.inDepthTraverseBreak {
                    print(it.value)
                    obtainedStringBuilder.append(it.value)
                    (it.value == "D") // Break when "D" is visited
                }
                println()

                obtainedStringBuilder.toString() shouldBe "ABCD"
            }
        }

        "inBreadthTraverse" should {
            "traverse nodes in breadth" {
                val obtainedStringBuilder = StringBuilder()

                print("inBreadthTraverse: ")
                tree.inBreadthTraverse {
                    print(it.value)
                    obtainedStringBuilder.append(it.value)
                }
                println()

                obtainedStringBuilder.toString() shouldBe "ABKLCGMNDEFHIJ"
            }
        }

        "inBreadthTraverseBreak" should {
            "traverse nodes in breadth, breaking when asked" {
                val obtainedStringBuilder = StringBuilder()

                print("inBreadthTraverseBreak: ")
                tree.inBreadthTraverseBreak {
                    print(it.value)
                    obtainedStringBuilder.append(it.value)
                    (it.value == "D") // Break when "D" is visited
                }
                println()

                obtainedStringBuilder.toString() shouldBe "ABKLCGMND"
            }
        }
    }
}
