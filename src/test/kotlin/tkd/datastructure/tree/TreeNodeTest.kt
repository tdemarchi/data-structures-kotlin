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

        "inDepthTraverse" When {
            "full traverse" should {
                "traverse all nodes in depth" {
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
            "breaking when asked" should {
                "traverse nodes in depth until break" {
                    val obtainedStringBuilder = StringBuilder()

                    print("inDepthTraverse, breaking on D: ")
                    tree.run {
                        inDepthTraverse {
                            print(it.value)
                            obtainedStringBuilder.append(it.value)
                            if (it.value == "D") return@run // Break when "D" is visited
                        }
                    }
                    println()

                    obtainedStringBuilder.toString() shouldBe "ABCD"
                }
            }
        }

        "inBreadthTraverse" When {
            "full traverse" should {
                "traverse all nodes in breadth" {
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
            "breaking when asked" should {
                "traverse nodes in breadth until break" {
                    val obtainedStringBuilder = StringBuilder()

                    print("inBreadthTraverse, breaking on D: ")
                    tree.run {
                        inBreadthTraverse {
                            print(it.value)
                            obtainedStringBuilder.append(it.value)
                            if (it.value == "D") return@run // Break when "D" is visited
                        }
                    }
                    println()

                    obtainedStringBuilder.toString() shouldBe "ABKLCGMND"
                }
            }
        }
    }
}
