package dev.mizoguche.bookmarkcompose.data

import dev.mizoguche.bookmarkcompose.domain.Article
import dev.mizoguche.bookmarkcompose.domain.Category
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object HatebuRssParser {
    private val namespace: String? = null

    fun parse(rssString: String): Category {
        val stringReader = StringReader(rssString)
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        var category: Category? = null
        val articles: MutableList<Article> = mutableListOf()
        stringReader.use {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(stringReader)

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.eventType != XmlPullParser.START_TAG) {
                    continue
                }
                if (parser.name == "channel") {
                    category = readCategory(parser)
                }
                if (parser.name == "item") {
                    articles.add(readArticle(parser))
                }
            }
        }

        return category?.copy(articles = articles)
            ?: throw IllegalStateException("no category found: require channel tag")
    }

    private fun readCategory(parser: XmlPullParser): Category {
        parser.require(XmlPullParser.START_TAG, namespace, "channel")
        var title = ""
        var description = ""
        var url = ""

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "title" -> title = readText(parser, "title")
                "description" -> description = readText(parser, "description")
                "link" -> url = readText(parser, "link")
                else -> skip(parser)
            }
        }

        return Category(title = title, url = url, description = description, articles = listOf())
    }

    private fun readArticle(parser: XmlPullParser): Article {
        parser.require(XmlPullParser.START_TAG, namespace, "item")
        var title = ""
        var description = ""
        var url = ""
        var imageUrl = ""
        var bookmarkCount = 0
        var date = ""

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "title" -> title = readText(parser, "title")
                "description" -> description = readText(parser, "description")
                "link" -> url = readText(parser, "link")
                "dc:date" -> date = readText(parser, "dc:date")
                "hatena:bookmarkcount" -> bookmarkCount =
                    readText(parser, "hatena:bookmarkcount").toInt()
                "hatena:imageurl" -> imageUrl = readText(parser, "hatena:imageurl")
                else -> skip(parser)
            }
        }

        return Article(
            title = title,
            url = url,
            description = description,
            imageUrl = imageUrl,
            bookmarkCount = bookmarkCount,
            date = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        )
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser, tag: String): String {
        parser.require(XmlPullParser.START_TAG, namespace, tag)
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, namespace, tag)
        return title
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readAttribute(
        parser: XmlPullParser,
        tag: String,
        attribute: String,
        filter: (parser: XmlPullParser) -> Boolean = { true }
    ): String {
        parser.require(XmlPullParser.START_TAG, namespace, tag)
        var attributeValue = ""
        parser.nextTag()
        if (filter(parser)) {
            attributeValue = parser.getAttributeValue(null, attribute)
        }
        return attributeValue
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}