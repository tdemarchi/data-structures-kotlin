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

        "getDescendantGeneration" When {
            "querying root node" should {
                "return generation 0" {
                    tree.getDescendantGeneration { it.value == "A" } shouldBe 0
                }
            }
            "querying child node" should {
                "return generation 1" {
                    tree.getDescendantGeneration("B") shouldBe 1
                    tree.getDescendantGeneration("K") shouldBe 1
                    tree.getDescendantGeneration("L") shouldBe 1
                    tree.getDescendantGeneration { it.value == "B" } shouldBe 1
                    tree.getDescendantGeneration { it.value == "K" } shouldBe 1
                    tree.getDescendantGeneration { it.value == "L" } shouldBe 1
                }
            }
            "querying grandchild node" should {
                "return generation 2" {
                    tree.getDescendantGeneration { it.value == "C" } shouldBe 2
                    tree.getDescendantGeneration { it.value == "G" } shouldBe 2
                    tree.getDescendantGeneration { it.value == "M" } shouldBe 2
                    tree.getDescendantGeneration { it.value == "N" } shouldBe 2
                }
            }
            "querying great-grandchild node" should {
                "return generation 3" {
                    tree.getDescendantGeneration { it.value == "D" } shouldBe 3
                    tree.getDescendantGeneration { it.value == "E" } shouldBe 3
                    tree.getDescendantGeneration { it.value == "F" } shouldBe 3
                    tree.getDescendantGeneration { it.value == "H" } shouldBe 3
                }
            }
            "querying great-great-grandchild node" should {
                "return generation 4" {
                    tree.getDescendantGeneration { it.value == "I" } shouldBe 4
                    tree.getDescendantGeneration { it.value == "J" } shouldBe 4
                }
            }
            "querying node not descendant" should {
                "return null" {
                    tree.getDescendantGeneration { it.value == "Z" } shouldBe null
                }
            }
        }
    }
}
