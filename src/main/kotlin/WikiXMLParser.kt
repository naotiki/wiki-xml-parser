import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.io.File
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class WikiXMLParser(val dao: DAO) : DefaultHandler() {
    var inner: String? = null
    var title: String? = null
    var url: String? = null
    private val needEscape = Regex("[\\Q*+.?{}()[]^$-|\\/\\E]")

    //ぼくのかんがえたさいきょうのせいきひょうげんをかえしてくれるかんすー
    fun superUnrealPerfectUltraBeautifulDeliciousFunnyExciteUsefulBigTallExplosionBestWideLongUltimateFinalPowerRegex(
        string: String
    ) = Regex("${needEscape.replace(string, "")}（([^、）]+)(、[^）]*)*）.*")


    override fun startDocument() {

        println("読み込み開始")
    }


    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        //println("Start:$qName")
        inner = qName
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        when (inner) {
            "title" -> {
                title = ch?.let { String(it, start, length).replace("Wikipedia: ", "") }

            }
            "url"->{
                url = ch?.let { String(it, start, length) }
            }

            "abstract" -> {
                //println("-> ${ch?.let { }}")
                print(title?.let {
                    return@let if (ch != null) {
                        var first = it.first().toHiraganaOrNull()
                        var last = it.last().toHiraganaOrNull()
                        if (first != null && last != null) {
                            dao.addItem(it, first, last, url!!)
                            return@let "$it->$first,$last"
                        }

                        val word = String(ch, start, length)
                        superUnrealPerfectUltraBeautifulDeliciousFunnyExciteUsefulBigTallExplosionBestWideLongUltimateFinalPowerRegex(
                            it
                        ).matchEntire(word)?.groupValues?.get(1)?.let { rubyIsh ->
                            first = rubyIsh.first().toHiraganaOrNull()
                            last = rubyIsh.removeSuffix("ー").last().toHiraganaOrNull();
                            if (first != null && last != null) {
                                dao.addItem(it, first!!, last!!, url!!)


                                "$it->$first,$last"
                            } else {
                                null
                            }
                        }

                    } else {
                        null
                    }

                }?.also {
                    print("\n")
                }.orEmpty())
            }
        }

    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        //println("End:$qName")
        when (qName) {

        }
        inner = null
    }

    override fun endDocument() {
        println("\n終了")
    }

    companion object {
        fun start(dao: DAO, path: String) {
            val saxParserFactory: SAXParserFactory = SAXParserFactory.newInstance() //[2]
            val saxParser: SAXParser = saxParserFactory.newSAXParser() //[3]
            println(File(path).absolutePath)
            saxParser.parse(File(path), WikiXMLParser(dao)) //[4]

        }
    }
}