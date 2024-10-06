package tkd.datastructure.tree

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import tkd.datastructure.tree.builder.treeBuilder

class TreeNodeDoubleLinkedTest : WordSpec() {
    init {
        val tree = treeBuilder<String>()
            .edge("A", "B")
            /**/.edge("B", "C")
            /*  */.edge("C", "D")
            /*  */.edge("C", "E")
            /*  */.edge("C", "F")
            /**/.edge("B", "G")
            /*  */.edge("G", "H")
            /*    */.edge("H", "I")
            /*    */.edge("H", "J")
            .edge("A", "K")
            .edge("A", "L")
            /**/.edge("L", "M")
            /**/.edge("L", "N")
            .buildDoubleLinked()

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

        "getLowestCommonAncestor" When {
            "querying first node not part of the tree" should {
                "throw IllegalArgumentException" {
                    shouldThrow<IllegalArgumentException> {
                        tree.getLowestCommonAncestor(
                            nodeAPredicate = { it.value == "_NONE_" },
                            nodeBPredicate = { it.value == "A" },
                        )
                    }.message shouldBe "First provided node is not part of the tree."
                }
            }
            "querying second node not part of the tree" should {
                "throw IllegalArgumentException" {
                    shouldThrow<IllegalArgumentException> {
                        tree.getLowestCommonAncestor(
                            nodeAPredicate = { it.value == "A" },
                            nodeBPredicate = { it.value == "_NONE_" },
                        )
                    }.message shouldBe "Second provided node is not part of the tree."
                }
            }
            "querying root node on first and second" should {
                "return root node" {
                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "A" },
                        nodeBPredicate = { it.value == "A" },
                    ).shouldNotBeNull().value shouldBe "A"
                }
            }
            "querying root node on first" should {
                "return root node" {
                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "A" },
                        nodeBPredicate = { it.value == "D" },
                    ).shouldNotBeNull().value shouldBe "A"
                }
            }
            "querying root node on second" should {
                "return root node" {
                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "E" },
                        nodeBPredicate = { it.value == "A" },
                    ).shouldNotBeNull().value shouldBe "A"
                }
            }
            "querying nodes with same parent" should {
                "return parent node" {
                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "I" },
                        nodeBPredicate = { it.value == "J" },
                    ).shouldNotBeNull().value shouldBe "H"

                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "F" },
                        nodeBPredicate = { it.value == "D" },
                    ).shouldNotBeNull().value shouldBe "C"

                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "G" },
                        nodeBPredicate = { it.value == "C" },
                    ).shouldNotBeNull().value shouldBe "B"

                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "B" },
                        nodeBPredicate = { it.value == "K" },
                    ).shouldNotBeNull().value shouldBe "A"
                }
            }
            "querying nodes with different parents, but same generation level" should {
                "return common ancestor node" {
                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "D" },
                        nodeBPredicate = { it.value == "H" },
                    ).shouldNotBeNull().value shouldBe "B"

                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "G" },
                        nodeBPredicate = { it.value == "M" },
                    ).shouldNotBeNull().value shouldBe "A"
                }
            }
            "querying nodes with different parents, and different generation level" should {
                "return common ancestor node" {
                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "J" },
                        nodeBPredicate = { it.value == "C" },
                    ).shouldNotBeNull().value shouldBe "B"

                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "D" },
                        nodeBPredicate = { it.value == "K" },
                    ).shouldNotBeNull().value shouldBe "A"

                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "K" },
                        nodeBPredicate = { it.value == "M" },
                    ).shouldNotBeNull().value shouldBe "A"
                }
            }
            "querying ancestor and dependent nodes" should {
                "return the ancestor node" {
                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "B" },
                        nodeBPredicate = { it.value == "I" },
                    ).shouldNotBeNull().value shouldBe "B"

                    tree.getLowestCommonAncestor(
                        nodeAPredicate = { it.value == "M" },
                        nodeBPredicate = { it.value == "L" },
                    ).shouldNotBeNull().value shouldBe "L"
                }
            }
        }
    }
}
