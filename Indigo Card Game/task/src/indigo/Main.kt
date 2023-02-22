package indigo

import kotlin.random.Random
import kotlin.system.exitProcess

class Card (val value: String, val suit: String)

enum class CardName(val symbol:String) {
    KING("K"),
    QUEEN("Q"),
    JACK("J"),
    TEN("10"),
    NINE("9"),
    EIGHT("8"),
    SEVEN("7"),
    SIX("6"),
    FIVE("5"),
    FOUR("4"),
    THREE("3"),
    TWO("2"),
    ACE("A")
}

enum class CardSuit(val symbol: String) {
    CLUBS("♣"),
    DIAMONDS("♦"),
    HEARTS("♥"),
    SPADES("♠")
}

class Table(var cardsOnTheTable: MutableList<Card>) {
    fun printStringAboutTableSituation(cardsOnTheTable: MutableList<Card>) {
        println()
        println(
            "${cardsOnTheTable.size} cards on the table, and the top card is " +
                    cardsOnTheTable[cardsOnTheTable.lastIndex].value +
                    cardsOnTheTable[cardsOnTheTable.lastIndex].suit
        )
    }
}

open class Hand(var cardsInHand: MutableList<Card>) {
    open fun removeCardFromHandAndComputerHand(
        cardsOnTheTable: MutableList<Card>,
        cardsInHandOrComputerHand: MutableList<Card>,
        indexOfCard: Int
    ) {
        cardsOnTheTable.add(cardsInHandOrComputerHand[indexOfCard-1])
        cardsInHandOrComputerHand.removeAt(indexOfCard-1)
    }

    open fun addSixCards(cards: MutableList<Card>):MutableList<Card> {
        val resultList = mutableListOf<Card>()
        for (i in 0..5) {
            resultList.add(cards[i])
        }
        return resultList
    }

    fun printCardsInHand(cards: MutableList<Card>) {
        var returnString = "Cards in hand: "
        for (i in 0 until cards.size) {
            returnString += "${i+1}" + ")" + "${cards[i].value}${cards[i].suit} "
        }
        println(returnString)
    }
}

class ComputerHand (var cardsInComputerHand: MutableList<Card>) : Hand(cardsInComputerHand) {
    override fun removeCardFromHandAndComputerHand(
        cardsOnTheTable: MutableList<Card>,
        cardsInHandOrComputerHand: MutableList<Card>,
        indexOfCard: Int
    ) {
        cardsOnTheTable.add(cardsInComputerHand[indexOfCard])
        cardsInComputerHand.removeAt(indexOfCard)
    }

    fun randomIndexOfComputerMove(cardsInComputerHand: MutableList<Card>):Int {
        return if (cardsInComputerHand.size == 1) 0
        else Random.nextInt(0, cardsInComputerHand.size-1)

    }

}

fun main() {
    println("Indigo Card Game")
    val regex = Regex("yes|no|exit")

    var readAnswer = ""
    while (!readAnswer.matches(regex)) {
        println("Play first?")
        readAnswer = readln().lowercase()
    }
    if (readAnswer == "exit") finish()

//    generate and mix cards and put 4 cards on the table and remove it from the card deck
    var cards = generateCards()
    cards.shuffle()
    val table = Table(mutableListOf())
    for (i in 0..3) {
        table.cardsOnTheTable.add(cards[0])
        cards.removeAt(0)
    }

//    generate hand cards and remove it from card deck
    val hand = Hand(mutableListOf())
    hand.cardsInHand = hand.addSixCards(cards)
    cards = deleteSixCards(cards)

//    generate computer hand cards and remove it from card deck
    val computerHand = ComputerHand(mutableListOf())
    computerHand.cardsInComputerHand = computerHand.addSixCards(cards)
    cards = deleteSixCards(cards)

// print start cards on the table position
    print("Initial cards on the table: ")
    table.cardsOnTheTable.forEach { print("${it.value}${it.suit} ") }
    println()

    while (hand.cardsInHand.size != 0 && computerHand.cardsInComputerHand.size != 0) {

        if (readAnswer == "no") {
            computerMove(computerHand, table)
            playerMove(hand, table)
        } else if (readAnswer == "yes"){
            playerMove(hand, table)
            computerMove(computerHand, table)
        }

        if (hand.cardsInHand.isEmpty() && cards.isNotEmpty()) {
            hand.cardsInHand = hand.addSixCards(cards)
            cards = deleteSixCards(cards)
        }

        if (computerHand.cardsInComputerHand.isEmpty() && cards.isNotEmpty()) {
            computerHand.cardsInComputerHand = hand.addSixCards(cards)
            cards = deleteSixCards(cards)
        }

        if (hand.cardsInHand.isEmpty() && computerHand.cardsInComputerHand.isEmpty() && cards.isEmpty()) {
            table.printStringAboutTableSituation(table.cardsOnTheTable)
            println("Game Over")
        }


    }

}

fun generateCards(): MutableList<Card>{
    val list = mutableListOf<Card>()
    for (i in CardSuit.values()) {
        for (j in CardName.values()) {
            val card = Card(j.symbol,i.symbol)
            list.add(card)
        }
    }
    return list
}

fun finish() {
    println("Game Over")
    exitProcess(0)
}

fun deleteSixCards(cards: MutableList<Card>):MutableList<Card> {
    repeat(6) {cards.removeAt(0)    }
    return cards
}

fun computerMove(computerHand: ComputerHand, table: Table){
    table.printStringAboutTableSituation(table.cardsOnTheTable)
    computerHand.removeCardFromHandAndComputerHand(table.cardsOnTheTable,
        computerHand.cardsInComputerHand,
        computerHand.randomIndexOfComputerMove(computerHand.cardsInComputerHand)
    )
    println("Computer plays " + table.cardsOnTheTable[table.cardsOnTheTable.lastIndex].value +
            table.cardsOnTheTable[table.cardsOnTheTable.lastIndex].suit)
}

fun playerMove(hand: Hand, table: Table) {
    table.printStringAboutTableSituation(table.cardsOnTheTable)
    hand.printCardsInHand(hand.cardsInHand)
    val regexChoosingCard = Regex("[1-6]|exit")
    var chooseIndexOfCard = ""
    while (!chooseIndexOfCard.matches(regexChoosingCard)) {
        println("Choose a card to play (1-${hand.cardsInHand.size}):")
        chooseIndexOfCard = readln()
        if (chooseIndexOfCard == "exit") finish()
        try {
            chooseIndexOfCard.toInt()
        } catch (e: Exception) {
            continue
        }
        if (chooseIndexOfCard.toInt() > hand.cardsInHand.size || chooseIndexOfCard.toInt() < 1) {
            chooseIndexOfCard = ""
            continue
        } else
            hand.removeCardFromHandAndComputerHand(table.cardsOnTheTable, hand.cardsInHand, chooseIndexOfCard.toInt())
    }

}


