import FilesystemStructure.Directory
import FilesystemStructure.File

fun main() {
    val input = readInputAsText("day07_input")
    val regex = Regex("""\$\s+(\w+)\s+([\s\S]+?)(?=\s+\$|\Z)""")
    val commands = regex.findAll(input)
        .map { it.groupValues }
        .map { (_, command, io) ->
            when (command) {
                "cd" -> Command.Cd(io)
                "ls" -> Command.Ls(io)
                else -> error("Unsupported command: $command")
            }
        }

    val filesystem = Filesystem(commands)
    val directories = filesystem.rootNode.flatten().filter { it.data is Directory }

    val result1 = directories.sumOf { if (it.size <= 100_000) it.size else 0 }
    println(result1)

    val missingSpace = 30_000_000 - (70_000_000 - filesystem.rootNode.size)
    val result2 = directories.mapNotNull { if (it.size >= missingSpace) it.size else null }.min()
    println(result2)
}

class Filesystem(commands: Sequence<Command>) {
    val rootNode = FilesystemNode(Directory("/"))
    private var currentNode = rootNode

    init {
        commands.forEach { command ->
            when (command) {
                is Command.Cd ->
                    currentNode = when (command.input) {
                        "/" -> rootNode

                        ".." -> currentNode.parent ?: rootNode

                        else -> currentNode.children.firstOrNull { it.data is Directory && it.data.name == command.input }
                            ?: error("Specified directory: ${command.input} doesn't exist.")
                    }

                is Command.Ls ->
                    command.structures
                        .map { FilesystemNode(it, parent = currentNode) }
                        .let(currentNode.children::addAll)
            }
        }
    }
}

class FilesystemNode(
    val data: FilesystemStructure,
    val parent: FilesystemNode? = null,
    val children: MutableList<FilesystemNode> = mutableListOf(),
) {
    val size: Int
        get() = when (data) {
            is File -> data.size
            is Directory -> children.sumOf { it.size }
        }

    fun flatten(): List<FilesystemNode> = listOf(this) + children.flatMap { it.flatten() }
}

sealed interface FilesystemStructure {
    data class Directory(val name: String) : FilesystemStructure
    data class File(val name: String, val size: Int) : FilesystemStructure
}

sealed interface Command {
    @JvmInline
    value class Cd(val input: String) : Command

    @JvmInline
    value class Ls(val output: String) : Command {
        val structures: List<FilesystemStructure>
            get() = output.lineSequence()
                .map { it.split(' ') }
                .map { (size, name) ->
                    when (size) {
                        "dir" -> Directory(name)
                        else -> File(name, size.toInt())
                    }
                }.toList()
    }
}