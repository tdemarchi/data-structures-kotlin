package tkd.datastructure.tree.multichildren

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

class MultiChildrenTreeBuilderTest : WordSpec() {
    init {
        "build" When {
            "is usual tree" should {
                "build tree" {
                    val tree = multiChildrenTreeBuilder<String>()
                        .edge("A", "B")
                        .edge("A", "C")
                        .build()

                    tree shouldNotBe null

                    println(tree.toStringTree())
                }
            }
            "no edges provided" should {
                "throw exception" {
                    shouldThrow<IllegalArgumentException> {
                        multiChildrenTreeBuilder<String>()
                            .build()
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
                        multiChildrenTreeBuilder<String>()
                            .edgesFromLines(input) { it }
                            .build()
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
                        multiChildrenTreeBuilder<String>()
                            .edgesFromLines(input) { it }
                            .build()
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
                        multiChildrenTreeBuilder<String>()
                            .edgesFromLines(input) { it }
                            .build()
                    }.message shouldBe "The edge (C, E) declares a child node E that is already child of F."
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

                    val tree = multiChildrenTreeBuilder<String>()
                        .edgesFromLines(input) { it }
                        .build()

                    tree shouldNotBe null

                    println(tree.toStringTree())
                }
            }
            "no edges input is provided" should {
                "throw exception" {
                    val input = """
                        """.trimIndent()

                    shouldThrow<IllegalArgumentException> {
                        multiChildrenTreeBuilder<String>()
                            .edgesFromLines(input) { it }
                            .build()
                    }.message shouldBe "The edge string is empty."
                }
            }
        }
    }
}