import kotlin.system.exitProcess

const val VICTORY_TITLE = "Philosophy"
const val RANDOM_ARTICLE_URL = "/wiki/Special:Random"

fun main(args: Array<String>){
    val randomPage = pages.WikipediaPage(pages.WIKIPEDIA_BASE_PATH + RANDOM_ARTICLE_URL)
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
            exitProcess(0);
        }
    }
    println(currentPage.title)
}