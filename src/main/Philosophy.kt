package main

import main.Page.WikipediaPage
import kotlin.system.exitProcess

val VICTORY_TITLE = "Philosophy"
val WIKIPEDIA_BASE_PATH = "https://en.wikipedia.org"
val RANDOM_ARTICLE_URL = "/wiki/Special:Random"

fun main(args: Array<String>){
    val randomPage = WikipediaPage(WIKIPEDIA_BASE_PATH + RANDOM_ARTICLE_URL)
    println("Starting article: ${randomPage.title}")
    var currentPage = randomPage.nextPage
    val titles = mutableSetOf<String>()
    while(currentPage.title != VICTORY_TITLE) {
        if(titles.contains(currentPage.title)){
            println("Infinite loop! Try again!")
            exitProcess(0)
        }
        println(currentPage.title)
        titles.add(currentPage.title)
        val nextPage = currentPage.nextPage
        currentPage = nextPage
    }
    println(currentPage.title)
}