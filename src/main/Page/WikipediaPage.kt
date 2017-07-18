package main.Page

import main.WIKIPEDIA_BASE_PATH
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class WikipediaPage constructor(val pageUrl: String){
    val pageDocument : Document = Jsoup.connect(this.pageUrl).get()
    val title: String = pageDocument.getElementById("firstHeading").text()
    val nextPage by lazy {
        val firstUseableLinkTag = pageDocument.select("#mw-content-text > div > p > a").filter{
            it.attr("href").startsWith("/wiki") && it.siblingElements().filter {
                it.tagName() == "span" && !it.attr("lang").isNullOrEmpty()
            }.isEmpty()
        }.first()
//        val firstParagraphHtml = pageDocument.select("#mw-content-text > div > p:has(a)").filter{it.children().first().tagName() == "a"}.first().html()
//        val openingParenthesisIndex = firstParagraphHtml.indexOf('(')
//        val closingParenthesisIndex = firstParagraphHtml.indexOf(')')
//        val parenthesisString = when {
//            (closingParenthesisIndex > 0 && openingParenthesisIndex > 0) -> firstParagraphHtml.substring(openingParenthesisIndex + 1, closingParenthesisIndex)
//            else -> ""
//        }
//        val htmlStrippedOfParenthesis = firstParagraphHtml.replace(if (parenthesisString.contains("href")) parenthesisString else "", "")
//        val firstParagraph = Jsoup.parse(htmlStrippedOfParenthesis)
//        val nextHref = firstParagraph.select("body > a")
//                .filter{it.attr("href").startsWith("/wiki")}
//                .first().attr("href")
        WikipediaPage(WIKIPEDIA_BASE_PATH + firstUseableLinkTag.attr("href"))
    }
}