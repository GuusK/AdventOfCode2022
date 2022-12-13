package days.day13

data class Node(val type: Contents, val num: Int, val lst: List<Node>) : Comparable<Node> {

    override fun compareTo(other: Node): Int {
        if (this.type == Contents.Item && other.type == Contents.Item) {
            return this.num - other.num
        } else if (this.type == Contents.List && other.type == Contents.List) {
            var idx = 0

            // check content of list
            while (idx < this.lst.size && idx < other.lst.size) {
                val diff = this.lst[idx].compareTo(other.lst[idx])
                // if there is a difference, return else continue searching
                if (diff != 0) {
                    return diff
                } else {
                    idx++
                }
            }
            // unequal list length, so we can return the difference between the length
            return this.lst.size - other.lst.size
        } else if (this.type == Contents.Item) {
            return Node(Contents.List, this.num, listOf(this)).compareTo(other)
        } else {
            return this.compareTo(Node(Contents.List, this.num, listOf(other)))
        }
    }

    companion object {

        private fun readInt(input: String, loc: Int): Pair<Node, Int> {
            var numStr = ""
            var curIdx = loc
            var curChar = input[curIdx]
            do {
                numStr += curChar
                curIdx++
                curChar = input[curIdx]
            } while (curChar != ',' && curChar != ']')
            return Pair(
                Node(Contents.Item, numStr.toInt(), listOf()),
                curIdx
            )
        }

        private fun fromStringRec(input: String, loc: Int): Pair<Node, Int> {
            assert(input[loc] == '[')
            var curIdx = loc + 1
            var curChar: Char
            val parsed = mutableListOf<Node>()
            while (curIdx < input.length && input[curIdx] != ']') {
                curChar = input[curIdx]
                if (curChar == '[') {
                    // we should be parsing a list
                    val (nodes, lastLoc) = fromStringRec(input, curIdx)
                    parsed.add(nodes)
                    curIdx = lastLoc + 1
                } else if (curChar == ',') {
                    curIdx++
                } else {
                    val (node, lastLoc) = readInt(input, curIdx)
                    parsed.add(node)
                    curIdx = lastLoc + 1
                }
            }

            return Pair(
                Node(Contents.List, -1, parsed),
                curIdx
            )
        }

        fun fromString(input: String): Node {
            return fromStringRec(input, 0).first
        }
    }
}
