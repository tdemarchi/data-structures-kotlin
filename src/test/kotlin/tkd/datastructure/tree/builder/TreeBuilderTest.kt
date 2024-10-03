package tkd.datastructure.tree.builder

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

class TreeBuilderTest : WordSpec() {
    init {
        "buildMultiChildren" When {
            "is usual tree" should {
                "build tree" {
                    val tree = treeBuilder<String>()
                        .edge("A", "B")
                        .edge("A", "C")
                        .buildMultiChildren()

                    tree shouldNotBe null

                    println(tree.toStringTree())
                }
            }
            "no edges provided" should {
                "throw exception" {
                    shouldThrow<IllegalArgumentException> {
                        treeBuilder<String>()
                            .buildMultiChildren()
                    }.message shouldBe "Problem building tree, there is no root node."
                }
            }
            "tree has two roots" should {
                "throw exception" {
                    val input = """
                        A B
                        A C
                        A D
                        C E
                        C F
                        C G
                        X Y
                        """.trimIndent()

                    shouldThrow<IllegalArgumentException> {
                        treeBuilder<String>()
                            .edgesFromLines(input) { it }
                            .buildMultiChildren()
                    }.message shouldStartWith "Problem building tree, there are more than one root node"
                }
            }
            "has no root" should {
                "throw exception" {
                    val input = """
                        A B
                        A C
                        A D
                        C E
                        C F
                        C G
                        G A
                        """.trimIndent()

                    shouldThrow<IllegalArgumentException> {
                        treeBuilder<String>()
                            .edgesFromLines(input) { it }
                            .buildMultiChildren()
                    }.message shouldBe "Problem building tree, there is no root node."
                }
            }
            "a node has more than one parent" should {
                "throw exception" {
                    val input = """
                        A B
                        A C
                        A D
                        F E
                        C E
                        C F
                        C G
                        """.trimIndent()

                    shouldThrow<IllegalArgumentException> {
                        treeBuilder<String>()
                            .edgesFromLines(input) { it }
                            .buildMultiChildren()
                    }.message shouldBe "The edge (C, E) declares a child node E that is already child of F."
                }
            }
        }

        "buildBinary" When {
            "is usual tree" should {
                "build tree" {
                    val tree = treeBuilder<String>()
                        .edge("A", "B")
                        .edge("A", "C")
                        .buildBinary()

                    tree shouldNotBe null

                    println(tree.toStringTree())
                }
            }

            "not binary" should {
                "throw exception" {
                    val input = """
                        A B
                        A C
                        A D
                        """.trimIndent()

                    shouldThrow<IllegalArgumentException> {
                        treeBuilder<String>()
                            .edgesFromLines(input) { it }
                            .buildBinary()
                    }.message shouldBe "Node A has more than 2 children [B, C, D]."
                }
            }
        }

        "edgesFromLines" When {
            "is usual tree" should {
                "build tree" {
                    val input = """
                        A B
                        A C
                        A D
                        C E
                        C F
                        C G
                        """.trimIndent()

                    val tree = treeBuilder<String>()
                        .edgesFromLines(input) { it }
                        .buildMultiChildren()

                    tree shouldNotBe null

                    println(tree.toStringTree())
                }
            }
            "no edges input is provided" should {
                "throw exception" {
                    val input = """
                        """.trimIndent()

                    shouldThrow<IllegalArgumentException> {
                        treeBuilder<String>()
                            .edgesFromLines(input) { it }
                            .buildMultiChildren()
                    }.message shouldBe "The edge string is empty."
                }
            }
        }
    }
}