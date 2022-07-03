import java.sql.DriverManager

fun main(args: Array<String>) {
    println("Hello World!")
    var path=args.firstOrNull()?: readlnOrNull()
    while (path==null){
        println("Xmlのパスを入力してください")
        path= readlnOrNull()
    }
    val dao=DAO()


    WikiXMLParser.start(dao,path)
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}