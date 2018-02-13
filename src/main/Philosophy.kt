package main

import main.pages.WikipediaPage
import kotlin.system.exitProcess

const val VICTORY_TITLE = "Philosophy"
const val WIKIPEDIA_BASE_PATH = "https://en.wikipedia.org"
const val RANDOM_ARTICLE_URL = "/wiki/Special:Random"

fun main(args: Array<String>){
    val randomPage = WikipediaPage(WIKIPEDIA_BASE_PATH + RANDOM_ARTICLE_URL)
    println("Starting article: ${randomPage.title}")
    var currentPage = randomPage.nextPage
    val titles = mutableMapOf<String, Int>()
    while(currentPage.title != VICTORY_TITLE) {
        val title = currentPage.title
        if(titles.contains(title)){
            titles[title] = titles.getOrDefault(title, 0) + 1
        } else {
            titles.put(title, 0)
        }
        currentPage.index = titles.getOrDefault(title, 0)
        println(title)
        try {
            currentPage = currentPage.nextPage
        } catch (exception: NoSuchElementException) {
            println(exception.message)
        }
    }
    println(currentPage.title)
}