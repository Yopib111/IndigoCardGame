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

class Table() {
    var cardsOnTheTable = mutableListOf<Card>()

    fun printStringAboutTableSituation(cardsOnTheTable: MutableList<Card>) {
        if (cardsOnTheTable.isEmpty()) println("No cards on the table")
        else {
            println()
            println(
                "${cardsOnTheTable.size} cards on the table, and the top card is " +
                        cardsOnTheTable[cardsOnTheTable.lastIndex].value +
                        cardsOnTheTable[cardsOnTheTable.lastIndex].suit
            )
        }
    }

    fun addCardsOnTable(cards: MutableList<Card>){
        for (i in 0..3) {
            cardsOnTheTable += cards[i]
        }

//        val subList = cards.subList(0,3)
//        cards.subList(0,3).clear()
//        cardsOnTheTable = subList
//        cards = _cards.subList(4,_cards.lastIndex)

    }
}

class Hand() {
    var cardsInHand = mutableListOf<Card>()
    var takenCards = mutableListOf<Card>()
    var handName = ""
    var score = 0
//    var cardsInComputerHand = mutableListOf<Card>()

    fun removeCardFromHandAndComputerHand(
        cardsOnTheTable: MutableList<Card>,
        cardsInHandOrComputerHand: MutableList<Card>,
        indexOfCard: Int
    ) {
        cardsOnTheTable.add(cardsInHandOrComputerHand[indexOfCard]) //-1
        cardsInHandOrComputerHand.removeAt(indexOfCard) //-1
    }

    fun addCards(cards: MutableList<Card>) {
        for (i in 0..5) {
            cardsInHand += cards[i]
        }

//        val subList = cards.subList(0,5)
//        cardsInHand = subList
    }

    fun printCardsInHand(cards: MutableList<Card>) {
        var returnString = "Cards in hand: "
        for (i in 0 until cards.size) {
            returnString += "${i+1}" + ")" + "${cards[i].value}${cards[i].suit} "
        }
        println(returnString)
    }

    fun getIndexOfComputerMoveLogic(cardsInComputerHand: MutableList<Card>, table: MutableList<Card>):Int {
//        make list with same suits and list with same value of cards in the hand and lists with candidate cards
        val sameSuit = mutableListOf<Card>()
        val sameValue = mutableListOf<Card>()
        val candidateSameSuitList = mutableListOf<Card>()
        val candidateSameValueList = mutableListOf<Card>()
        val candidateCardsList = mutableListOf<Card>()


        for (i in cardsInComputerHand) {
            val currentSameSuit = mutableListOf<Card>()
            val currentSameValue = mutableListOf<Card>()

            for (j in cardsInComputerHand) {
                if (i.suit == j.suit) currentSameSuit += j
                if (i.value == j.value) currentSameValue += j
            }
            if (sameSuit.isEmpty()) sameSuit.addAll(currentSameSuit)
            else if (sameSuit.size < currentSameSuit.size) {
                sameSuit.clear()
                sameSuit.addAll(currentSameSuit)
            }
            if (sameValue.isEmpty()) sameValue.addAll(currentSameValue)
            else if (sameValue.size < currentSameValue.size) {
                sameValue.clear()
                sameValue.addAll(currentSameValue)
            }

        }

        for (i in cardsInComputerHand) {
            if (table.isNotEmpty() && i.suit == table[table.lastIndex].suit) {
                candidateSameSuitList += i
                candidateCardsList += i
            }
            if (table.isNotEmpty() && i.value == table[table.lastIndex].value) {
                candidateSameValueList += i
                candidateCardsList += i
            }
        }


        cardsInHand.forEach { print("${it.value}${it.suit} ")}
        println()

//        print("Same Suit: ")
//        printCardsInHand(sameSuit)
//        print("Same Value: ")
//        printCardsInHand(sameValue)
//        print("Candidate Same Suit: ")
//        printCardsInHand(candidateSameSuitList)
//        print("Candidate Same Value:")
//        printCardsInHand(candidateSameValueList)
//        print("Candidate cards: ")
//        printCardsInHand(candidateCardsList)


        if (cardsInComputerHand.size == 1) return 0
        if (candidateCardsList.size == 1) return cardsInComputerHand.indexOf(candidateCardsList[0])

        if (table.isEmpty()) {
            if (sameSuit.size > 1) {
                val returnCard = sameSuit[Random.nextInt(0, sameSuit.size-1)]
                return cardsInComputerHand.indexOf(returnCard)
            } else if (sameValue.size > 1) {
                val returnCard = sameValue[Random.nextInt(0, sameValue.size-1)]
                return cardsInComputerHand.indexOf(returnCard)
            } else return Random.nextInt(0, cardsInComputerHand.size-1)
        } else if (candidateCardsList.isEmpty()) {
            if (sameSuit.size > 1) {
                val returnCard = sameSuit[Random.nextInt(0, sameSuit.size-1)]
                return cardsInComputerHand.indexOf(returnCard)
            } else if (sameValue.size > 1) {
                val returnCard = sameValue[Random.nextInt(0, sameValue.size-1)]
                return cardsInComputerHand.indexOf(returnCard)
            } else return Random.nextInt(0, cardsInComputerHand.size-1)

        } else if (candidateSameSuitList.size > 1) {
            val returnCard = candidateSameSuitList[Random.nextInt(0, candidateSameSuitList.size-1)]
            return cardsInComputerHand.indexOf(returnCard)
        } else if (candidateSameValueList.size > 1) {
            val returnCard = candidateSameValueList[Random.nextInt(0, candidateSameValueList.size-1)]
            return cardsInComputerHand.indexOf(returnCard)
        } else if (candidateSameSuitList.size == 1) {
            return cardsInComputerHand.indexOf(candidateSameSuitList[0])
        } else if (candidateSameValueList.size == 1) {
            return cardsInComputerHand.indexOf(candidateSameValueList[0])
        }
        else return Random.nextInt(0, cardsInComputerHand.size-1)
    }

}

//class ComputerHand (var cardsInComputerHand: MutableList<Card>) : Hand(cardsInComputerHand) {
//    override fun removeCardFromHandAndComputerHand(
//        cardsOnTheTable: MutableList<Card>,
//        cardsInHandOrComputerHand: MutableList<Card>,
//        indexOfCard: Int
//    ) {
//        cardsOnTheTable.add(cardsInComputerHand[indexOfCard])
//        cardsInComputerHand.removeAt(indexOfCard)
//    }



fun main() {
    val regex = Regex("yes|no|exit")
    var readAnswer = ""
    var lastWon = 1

    println("Indigo Card Game")

    while (!readAnswer.matches(regex)) {
        println("Play first?")
        readAnswer = readln().lowercase()
    }
    if (readAnswer == "exit") finish()

//    generate and mix cards and put 4 cards on the table and remove it from the card deck
    var cards = generateCards()
    cards.shuffle()
    val table = Table()
    table.addCardsOnTable(cards)

    cards = cards.subList(4,cards.lastIndex+1)

//    generate hand cards and remove it from card deck
    val hand = Hand()
    hand.addCards(cards)
    hand.handName = "Player"
    cards = deleteSixCards(cards)

//    generate computer hand cards and remove it from card deck
    val computerHand = Hand()
    computerHand.addCards(cards)
    computerHand.handName = "Computer"
    cards = deleteSixCards(cards)

// print start cards on the table position
    print("Initial cards on the table: ")
    table.cardsOnTheTable.forEach { print("${it.value}${it.suit} ") }
    println()

    while (hand.cardsInHand.size != 0 && computerHand.cardsInHand.size != 0) {

        if (readAnswer == "no") {
            lastWon = 2
            computerMove(computerHand, table)
            if (checkingWinAndScores(computerHand,table, false)) {
                printScores(hand, computerHand)
                lastWon = 2
            }
            playerMove(hand, table)
            if (checkingWinAndScores(hand, table, false)) {
                printScores(hand, computerHand)
                lastWon = 1
            }
        } else if (readAnswer == "yes"){
            playerMove(hand, table)
            if (checkingWinAndScores(hand, table, false)) {
                printScores(hand, computerHand)
                lastWon = 1
            }
            computerMove(computerHand, table)
            if (checkingWinAndScores(computerHand,table, false)) {
                printScores(hand, computerHand)
                lastWon = 2
            }
        }

        if (hand.cardsInHand.isEmpty() && cards.isNotEmpty()) {
            hand.addCards(cards)
            cards = deleteSixCards(cards)
        }

        if (computerHand.cardsInHand.isEmpty() && cards.isNotEmpty()) {
            computerHand.addCards(cards)
            cards = deleteSixCards(cards)
        }

        if (hand.cardsInHand.isEmpty() && computerHand.cardsInHand.isEmpty() && cards.isEmpty()) {
            table.printStringAboutTableSituation(table.cardsOnTheTable)

            if (lastWon == 1) {
                checkingWinAndScores(hand, table, true)
            } else
                checkingWinAndScores(computerHand, table, true)

            if (hand.takenCards.size > computerHand.takenCards.size) hand.score += 3
            else if (hand.takenCards.size < computerHand.takenCards.size) computerHand.score += 3
            else {
                if (lastWon == 1) hand.score += 3 else computerHand.score += 3
            }

            printScores(hand, computerHand)

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

fun computerMove(computerHand: Hand, table: Table){
    table.printStringAboutTableSituation(table.cardsOnTheTable)
    computerHand.removeCardFromHandAndComputerHand(table.cardsOnTheTable,
        computerHand.cardsInHand,
        computerHand.getIndexOfComputerMoveLogic(computerHand.cardsInHand, table.cardsOnTheTable)
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
        } else {
            hand.removeCardFromHandAndComputerHand(
                table.cardsOnTheTable,
                hand.cardsInHand,
                chooseIndexOfCard.toInt() - 1
            )

        }
    }

}

fun checkingWinAndScores(hand: Hand, table: Table, lastCard: Boolean): Boolean {
    val cardsWithPoints = "10JQKA"
    //        checking matches and move cards to taken player or computer if its matches
    if (!lastCard && table.cardsOnTheTable.size > 1 &&
        (table.cardsOnTheTable[table.cardsOnTheTable.lastIndex].value == table.cardsOnTheTable[table.cardsOnTheTable.lastIndex-1].value ||
                table.cardsOnTheTable[table.cardsOnTheTable.lastIndex].suit == table.cardsOnTheTable[table.cardsOnTheTable.lastIndex-1].suit)) {
        hand.takenCards.addAll(table.cardsOnTheTable)

        for (element in table.cardsOnTheTable) {
            if (element.value in cardsWithPoints) hand.score++
        }
        println("${hand.handName} wins cards")
        table.cardsOnTheTable.clear()
        return true

    } else if (lastCard) {
        hand.takenCards.addAll(table.cardsOnTheTable)

        for (element in table.cardsOnTheTable) {
            if (element.value in cardsWithPoints) hand.score++
        }
        table.cardsOnTheTable.clear()
        return true
    }
    return false
}

fun printScores(player: Hand, computer: Hand) {
    println("Score: Player ${player.score} - Computer ${computer.score}\n" +
            "Cards: Player ${player.takenCards.size} - Computer ${computer.takenCards.size}")
    println()

}


