import org.sqlite.SQLiteException
import java.sql.Connection
import java.sql.DriverManager

class DAO {
    val connection: Connection
    init{
        Class.forName("org.sqlite.JDBC")
       connection= DriverManager.getConnection("jdbc:sqlite:identifier.sqlite")
    }



    fun addItem(word:String,prefix:Char,postfix:Char,url:String){
        val statement = connection.createStatement()
        try {
            statement.executeUpdate("INSERT INTO words (word,prefix,postfix,url) VALUES ('$word','$prefix','$postfix','$url')")
        }catch (e:SQLiteException){
            e.printStackTrace()
        }

    }
}