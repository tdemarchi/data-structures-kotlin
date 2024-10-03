package tkd.datastructure.tree.binary

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

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
            "return the visual three" {
                val expected = """
                    A
                    ├── B
                    │   ├── C
                    │   │   ├── D
                    │   │   └── E
                    │   └── F
                    │       └── G
                    │           ├── H
                    │           └── I
                    └── J
                """.trimIndent()

                val obtained = tree.toStringTree()
                println(obtained)

                obtained shouldBe expected
            }
        }

        "preOrderTraverse" should {
            "traverse nodes in pre-order" {
                val obtainedStringBuilder = StringBuilder()

                print("preOrderTraverse: ")
                tree.preOrderTraverse {
                    print(it.value)
                    obtainedStringBuilder.append(it.value)
                }
                println()

                obtainedStringBuilder.toString() shouldBe "ABCDEFGHIJ"
            }
        }

        "preOrderTraverseBreak" should {
            "traverse nodes in pre-order, breaking when asked" {
                val obtainedStringBuilder = StringBuilder()

                print("preOrderTraverseBreak: ")
                tree.preOrderTraverseBreak {
                    print(it.value)
                    obtainedStringBuilder.append(it.value)
                    (it.value == "D") // Break when "D" is visited
                }
                println()

                obtainedStringBuilder.toString() shouldBe "ABCD"
            }
        }

        "inOrderTraverse" should {
            "traverse nodes in in-order" {
                val obtainedStringBuilder = StringBuilder()

                print("inOrderTraverse: ")
                tree.inOrderTraverse {
                    print(it.value)
                    obtainedStringBuilder.append(it.value)
                }
                println()

                obtainedStringBuilder.toString() shouldBe "DCEBHGIFAJ"
            }
        }

        "inOrderTraverseBreak" should {
            "traverse nodes in in-order, breaking when asked" {
                val obtainedStringBuilder = StringBuilder()

                print("inOrderTraverseBreak: ")
                tree.inOrderTraverseBreak {
                    print(it.value)
                    obtainedStringBuilder.append(it.value)
                    (it.value == "H") // Break when "H" is visited
                }
                println()

                obtainedStringBuilder.toString() shouldBe "DCEBH"
            }
        }

        "postOrderTraverse" should {
            "traverse nodes in post-order" {
                val obtainedStringBuilder = StringBuilder()

                print("postOrderTraverse: ")
                tree.postOrderTraverse {
                    print(it.value)
                    obtainedStringBuilder.append(it.value)
                }
                println()

                obtainedStringBuilder.toString() shouldBe "DECHIGFBJA"
            }
        }

        "postOrderTraverseBreak" should {
            "traverse nodes in post-order, breaking when asked" {
                val obtainedStringBuilder = StringBuilder()

                print("postOrderTraverseBreak: ")
                tree.postOrderTraverseBreak {
                    print(it.value)
                    obtainedStringBuilder.append(it.value)
                    (it.value == "H") // Break when "H" is visited
                }
                println()

                obtainedStringBuilder.toString() shouldBe "DECH"
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

                obtainedStringBuilder.toString() shouldBe "ABCDEFGHIJ"
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

                obtainedStringBuilder.toString() shouldBe "ABJCFDEGHI"
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

                obtainedStringBuilder.toString() shouldBe "ABJCFD"
            }
        }
    }
}
