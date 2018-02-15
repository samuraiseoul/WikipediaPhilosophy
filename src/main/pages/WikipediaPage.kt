package pages

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

const val WIKIPEDIA_BASE_PATH = "https://en.wikipedia.org"

class WikipediaPage constructor(private val pageUrl: String){
    var index : Int = 0
    private val pageDocument : Document = Jsoup.connect(this.pageUrl).get()
    val title: String = pageDocument.getElementById("firstHeading").text()
    val nextPage by lazy {
        findNextPage()
    }

    private fun findNextPage(): WikipediaPage {
        val useableLinkTags = pageDocument.select("#mw-content-text > div > p > a").filter {
            isWikipediaArticle(it) && notLanguage(it) && notWithinParenthesis(it) && noSiblingsWithLanguageElements(it)
        }
        if ((useableLinkTags.lastIndex + 1) == this.index) {
            throw NoSuchElementException("Infinite Loop, there are no more links to follow from this page that do not lead to infinite loops.")
        }
        return WikipediaPage(WIKIPEDIA_BASE_PATH + useableLinkTags[this.index].attr("href")) // go to the next link if page has been visited
    }

    private fun noSiblingsWithLanguageElements(element: Element): Boolean {
        return element.siblingElements().none {
            it.tagName() == "span" && !it.attr("lang").isNullOrEmpty()
        }
    }

    private fun isWikipediaArticle(element: Element) = element.attr("href").startsWith("/wiki")

    //ignore things in parenthesis
    private fun notWithinParenthesis(element: Element) = element.parent().html().replace(Regex("\\((.*?)\\)"), "").contains(element.html())

    //ignore language articles
    private fun notLanguage(element: Element) = !element.attr("href").contains("language", true) && !element.attr("title").contains("language", true)
}