package main.pages

import main.WIKIPEDIA_BASE_PATH
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class WikipediaPage constructor(private val pageUrl: String){
    var index : Int = 0
    private val pageDocument : Document = Jsoup.connect(this.pageUrl).get()
    val title: String = pageDocument.getElementById("firstHeading").text()
    val nextPage by lazy {
        val useableLinkTags = pageDocument.select("#mw-content-text > div > p > a").filter {
            it.attr("href").startsWith("/wiki")
                && !it.attr("href").contains("language", true) //ignore language articles,
                && !it.attr("title").contains("language", true)
                && it.parent().html().replace(Regex("\\((.*?)\\)"), "").contains(it.html()) //ignore things in parenthesis
                && it.siblingElements().none {
                    it.tagName() == "span" && !it.attr("lang").isNullOrEmpty()
                }
        }
        if((useableLinkTags.lastIndex + 1) == this.index) {
            throw NoSuchElementException("Infinit Loop, there are no more links to follow that do not lead to infinite loops.")
        }
        WikipediaPage(WIKIPEDIA_BASE_PATH + useableLinkTags[this.index].attr("href")) // go to the next link if page has been visited
    }
}