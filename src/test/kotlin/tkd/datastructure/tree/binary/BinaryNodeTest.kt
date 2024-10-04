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

        "preOrderTraverse" When {
            "full traverse" should {
                "traverse all nodes in pre-order" {
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
        }

        "preOrderTraverseBreak" When {
            "full traverse" should {
                "traverse nodes in pre-order until break" {
                    val obtainedStringBuilder = StringBuilder()

                    print("preOrderTraverseBreak, breaking on D: ")
                    tree.preOrderTraverseBreak {
                        print(it.value)
                        obtainedStringBuilder.append(it.value)
                        (it.value == "D") // Break when "D" is visited
                    }
                    println()

                    obtainedStringBuilder.toString() shouldBe "ABCD"
                }
            }
        }

        "inOrderTraverse" When {
            "full traverse" should {
                "traverse all nodes in in-order" {
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
        }

        "inOrderTraverseBreak" When {
            "full traverse" should {
                "traverse nodes in in-order until break" {
                    val obtainedStringBuilder = StringBuilder()

                    print("inOrderTraverseBreak, breaking on H: ")
                    tree.inOrderTraverseBreak {
                        print(it.value)
                        obtainedStringBuilder.append(it.value)
                        (it.value == "H") // Break when "H" is visited
                    }
                    println()

                    obtainedStringBuilder.toString() shouldBe "DCEBH"
                }
            }
        }

        "postOrderTraverse" When {
            "full traverse" should {
                "traverse all nodes in post-order" {
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
        }

        "postOrderTraverseBreak" When {
            "full traverse" should {
                "traverse nodes in post-order until break" {
                    val obtainedStringBuilder = StringBuilder()

                    print("postOrderTraverseBreak, breaking on H: ")
                    tree.postOrderTraverseBreak {
                        print(it.value)
                        obtainedStringBuilder.append(it.value)
                        (it.value == "H") // Break when "H" is visited
                    }
                    println()

                    obtainedStringBuilder.toString() shouldBe "DECH"
                }
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

                    obtainedStringBuilder.toString() shouldBe "ABCDEFGHIJ"
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

                    obtainedStringBuilder.toString() shouldBe "ABJCFDEGHI"
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

                    obtainedStringBuilder.toString() shouldBe "ABJCFD"
                }
            }
        }
    }
}
