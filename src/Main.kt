/*
 Feito por : Tiago Águeda
 Website: https://tiagoagueda.pt
 */

val quandoDaErro = "Invalid response."
val esc: String = Character.toString(27)
val startBlue = "$esc[30;44m"
val startGrey = "$esc[30;47m"
val startWhite = "$esc[30;30m"
val end = "$esc[0m"
var count = 0

fun buildMenu(): String{
    val menuInicial = "1-> Start New Game;\n" +
            "2-> Exit Game.\n"
    return menuInicial
}

//Valida o Menu Inicial
fun validacaoMenuInicial() : Int?{
    var opcao: Int? = readLine()?.toIntOrNull()
    while (opcao !in 1..2 || opcao == null) {
        print(quandoDaErro)
        println(buildMenu())
        opcao = readLine()?.toIntOrNull()
    }
    return  opcao
}

// Pede primeiro nome
fun escolherNomeJogador1() : String {
    println("First player name?\n")
    var nome = readLine().toString()
    var nomeValidado = checkName(nome)
    while (nomeValidado == false) {
        println("$quandoDaErro\nFirst player name?\n")
        nome = readLine().toString()
        nomeValidado = checkName(nome)
    }
    return nome
}

// Pede segundo nome
fun escolherNomeJogador2() : String {
    var nomeValidado : Boolean
    println("Second player name?\n")
    var nome: String = readLine().toString()

    nomeValidado = checkName(nome)
    while (nomeValidado == false) {
        println("$quandoDaErro\nSecond player name?\n")
        nome = readLine().toString()
        nomeValidado = checkName(nome)
    }
    return nome
}

//Verifica nome
fun checkName(nome: String): Boolean {
    var tamanho = 0
    while (tamanho < nome.length) {
        if (nome[tamanho] in ('a'..'z') || nome[tamanho] in ('A'..'Z') || nome[tamanho] == ' ') {
            if (nome[tamanho] == ' ' && nome[tamanho + 1] in ('A'..'Z')) {
                return true
            }
        } else {
            return false
        }
        tamanho ++
    }
    return false
}


// Pede chess columns
fun funChessColumns() : String {
    println("How many chess columns?\n")
    var numero = readLine().toString()
    var numeroValidado : Boolean = checkIsNumber(numero)
    if(numero.toInt() < 4){
        numeroValidado = false
    }
    while (numeroValidado == false || numero.toInt() < 4) {
        println("$quandoDaErro\nHow many chess columns?\n")
        numero = readLine().toString()
        numeroValidado = checkIsNumber(numero)
    }
    return numero
}

// Pede chess Lines
fun funChessLines() : String {
    println("How many chess lines?\n")
    var numero = readLine().toString()
    var numeroValidado : Boolean = checkIsNumber(numero)
    if(numero.toInt() < 4){
        numeroValidado = false
    }
    while (numeroValidado == false || numero.toInt() < 4) {
        println("$quandoDaErro\nHow many chess lines?\n")
        numero = readLine().toString()
        numeroValidado = checkIsNumber(numero)
    }
    return numero
}


fun checkIsNumber(number: String): Boolean{
    if(number == ""){
        return false
    }else if(number[0].toInt() in (48..57)){
        return true
    }
    return false
}

// Show legend (y/n)
fun funShowLegend() : String {
    println("Show legend (y/n)?\n")
    var legend = readLine().toString()
    var legendValidado = showChessLegendOrPieces(legend)
    while (legendValidado == null) {
        println("$quandoDaErro\nShow legend (y/n)?\n")
        legend = readLine().toString()
        legendValidado = showChessLegendOrPieces(legend)
    }
    return legend
}

// Show Pieces (y/n)
fun funShowPieces() : String {
    var legendValidado : Boolean?
    var pieces = ""
    println("Show pieces (y/n)?\n")
    pieces = readLine().toString()
    legendValidado = showChessLegendOrPieces(pieces)
    while (legendValidado == null) {
        println("$quandoDaErro\nShow pieces (y/n)?\n")
        pieces = readLine().toString()
        legendValidado = showChessLegendOrPieces(pieces)
    }
    return pieces
}

fun showChessLegendOrPieces(message: String): Boolean?{//Boolean?
    when{
        (message == "y" || message == "Y") -> return true
        (message == "n" || message == "N") -> return false
        else -> return null
    }
}
fun buildBoard(numColumns: Int, numLines: Int, showLegend: Boolean= false,showPieces: Boolean= false, pieces: Array<Pair<String,String>?>): String {
    var tentativa = 1
    var letra = ' '
    var piece = ""
    var color = ""
    var piecePair = ""
    var pecas = ""
    var peca = ""
    var board = ""
    var lineBlueSquares = ""
    var count = 0
    while (count < numColumns * numLines) {
        val pecaIndividual = pieces[count]
        if (pecaIndividual == null){
            pecas += " "
        }else{
            piece = pecaIndividual.first
            color = pecaIndividual.second
            piecePair = convertStringToUnicode(piece, color)

            pecas += "$piecePair"
        }
        count++
    }
    peca = pecas[tentativa - 1].toString()
    if(showLegend != true){
        if (showPieces != true) {
            board = showpiecesFalse(numLines, numColumns, showLegend)
        } else {
            board = showpiecesTrue(numLines,numColumns, showLegend, peca, pecas)
        }
    }else{
        tentativa = 0
        val alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var juntar = "$startBlue   $end"
        do{
            letra = alfabeto[tentativa]
            juntar +="$startBlue $letra $end"
            tentativa++
        }while ( tentativa < numColumns)
        board = "${juntar}$startBlue   $end\n"
        tentativa = 1
        if (showPieces != true) {
            board += showpiecesFalse(numLines, numColumns, showLegend)
        } else {
            board += showpiecesTrue(numLines,numColumns, showLegend, peca, pecas)
        }
        count = 0
        while (count <= numColumns){
            lineBlueSquares += "$startBlue   $end"
            count++
        }
        board += "$lineBlueSquares$startBlue   $end\n"
    }
    return board
}

fun showpiecesTrue(numLines: Int,numColumns: Int, showLegend: Boolean, peca: String, pecas :String): String{
    var boardTrue = ""
    var tentativaTrue = 1
    var count = 1
    var contador = 1
    var peao = peca
    if (showLegend != true){
        tentativaTrue = 1
        count = 1
        while(tentativaTrue <= numLines) {
            while (count <= numColumns) {
                peao = pecas[contador -1].toString()
                contador++
                if ((count % 2 == 0 && tentativaTrue % 2 == 0) || (count % 2 == 1 && tentativaTrue % 2 == 1)) {
                    boardTrue += "$startWhite $peao $end"
                } else if((count % 2 == 1 && tentativaTrue % 2 == 0) || (count % 2 == 0 && tentativaTrue % 2 == 1)) {
                    boardTrue += "$startGrey $peao $end"
                }
                count++
            }
            boardTrue += ("\n")
            count= 1
            tentativaTrue++
        }
         }else{
        while(tentativaTrue <= numLines) {
            boardTrue += "$startBlue $tentativaTrue $end"
            while (count <= numColumns) {
                peao = pecas[contador -1].toString()
                contador++
                if ((count % 2 == 0 && tentativaTrue % 2 == 0) || (count % 2 == 1 && tentativaTrue % 2 == 1)) {
                    boardTrue += "$startWhite $peao $end"
                } else if((count % 2 == 1 && tentativaTrue % 2 == 0) || (count % 2 == 0 && tentativaTrue % 2 == 1)) {
                    boardTrue += "$startGrey $peao $end"
                }
                count++
            }
            boardTrue += ("$startBlue   $end\n")
            count= 1
            tentativaTrue++

        }
    }
    return boardTrue
}

fun showpiecesFalse(numLines: Int,numColumns: Int, showLegend: Boolean): String{
    var boardFalse = ""
    var tentativaFalse = 1
    var count = 1
    if (showLegend != true){
        while(tentativaFalse <= numLines) {
        while (count <= numColumns) {
            if ((count % 2 == 0 && tentativaFalse % 2 == 0) || (count % 2 == 1 && tentativaFalse % 2 == 1)) {
                boardFalse += "$startWhite   $end"
            } else if((count % 2 == 1 && tentativaFalse % 2 == 0) || (count % 2 == 0 && tentativaFalse % 2 == 1)) {
                boardFalse += "$startGrey   $end"
            }
            count++
        }
          boardFalse += ("\n")
            count= 1
            tentativaFalse++
        }
    }else{
        while(tentativaFalse <= numLines) {
            boardFalse += "$startBlue $tentativaFalse $end"
            while (count <= numColumns) {
                if ((count % 2 == 0 && tentativaFalse % 2 == 0) || (count % 2 == 1 && tentativaFalse % 2 == 1)) {
                    boardFalse += "$startWhite   $end"
                } else if((count % 2 == 1 && tentativaFalse % 2 == 0) || (count % 2 == 0 && tentativaFalse % 2 == 1)) {
                    boardFalse += "$startGrey   $end"
                }
                count++
            }
            boardFalse += ("$startBlue   $end\n")
            count= 1
            tentativaFalse++
        }
    }
    return boardFalse
}

fun createInitialBoard(numColumns: Int, numLines: Int): Array<Pair<String,String>?>{
    var count = 0
    var initialBoard : Array<Pair<String,String>?> = arrayOfNulls(numColumns * numLines)
    if (numColumns == 5){
        initialBoard = arrayOfNulls(0)
    }else{
        var peca = ""
        var cor = ""
        var board = ""
        when{
            (numColumns == 8) -> board = "THBQKBHTTHBKQBHT"
            (numColumns == 7) -> board = "THBKBHT"
            (numColumns == 6 && numLines == 7) -> board = "TBQKBHTBKQBH"
            (numColumns == 6) -> board = "HBQKBTHBKQBT"
            (numColumns == 5) -> board = "HBKBTHBKBT"
            (numColumns == 4) -> board = "  TBTQ  "
        }
        while(numColumns > count){
            peca = board[count].toString()
            cor = "b"
            when{
                peca == " " -> initialBoard[count] = null
                else -> initialBoard[count] = Pair(peca , cor)
            }
            if(numColumns != 4 && numLines != 4){
                peca = "P"
                initialBoard[numColumns + count] = Pair(peca , cor)
            }
            count++
        }
        count = 1
        while (numColumns >= count){
            peca = board[board.length - count].toString()
            cor = "w"
            when{
                peca == " " -> initialBoard[initialBoard.size - count] = null
                else -> initialBoard[initialBoard.size - count] = Pair(peca , cor)
            }
            if(numColumns != 4 && numLines != 4){
                peca = "P"
                initialBoard[initialBoard.size - numColumns - count] = Pair(peca , cor)
            }
            count++
        }
    }
    return initialBoard
}

fun createTotalPiecesAndTurn(numColumns: Int, numLines: Int): Array<Int?>{
    val turn = 0
    var totalPieces : Array<Int?> = arrayOf()
    when{
        (numLines == 4 && numColumns == 4) -> totalPieces = arrayOf(2,2,turn)
        (numColumns == 8 && numLines == 8) -> totalPieces = arrayOf(numColumns*2,numColumns*2,turn)
        (numColumns == 7 && numLines == 7) -> totalPieces = arrayOf(numColumns*2,numColumns*2,turn)
        (numColumns == 6 && numLines == 6) -> totalPieces = arrayOf(numColumns*2,numColumns*2,turn)
        (numColumns == 6 && numLines == 7) -> totalPieces = arrayOf(numColumns*2,numColumns*2,turn)
        else -> totalPieces = arrayOf()
    }

    return totalPieces
}

fun altera(numeros: Array<Int>) {
    if (numeros.size != 0) {
        when {
            (numeros[2] == 0) -> numeros[0] = numeros[0] - 1
            (numeros[2] == 1) -> numeros[1] = numeros[1] - 1
        }
    }
}


fun convertStringToUnicode(piece: String, color: String): String{
    var peca = ""
    when{
        (piece == "P" && color == "b") -> peca = "♟"
        (piece == "P" && color == "w") -> peca = "♙"
        (piece == "H" && color == "b") -> peca = "♞"
        (piece == "H" && color == "w") -> peca = "♘"
        (piece == "K" && color == "b") -> peca = "♚"
        (piece == "K" && color == "w") -> peca = "♔"
        (piece == "T" && color == "b") -> peca = "♜"
        (piece == "T" && color == "w") -> peca = "♖"
        (piece == "B" && color == "b") -> peca = "♝"
        (piece == "B" && color == "w") -> peca = "♗"
        (piece == "Q" && color == "b") -> peca = "♛"
        (piece == "Q" && color == "w") -> peca = "♕"
    }
    return peca
}

fun getCoordinates (readText: String?):Pair<Int, Int>?{
    val letras : Array<String?> = arrayOfNulls(5)
    count = 0
    var tabuleiroUnidimensional : Pair<Int, Int>?
    if(readText != null){
        while (readText.length -1 >= count){
            letras[count] = readText[count].toString()
            count++
        }
    var lineChoosed = 0
    var columnChoosed = 0

    when{
        (letras[1] == "A" || letras[1] == "a") -> lineChoosed = 1
        (letras[1] == "B" || letras[1] == "b") -> lineChoosed = 2
        (letras[1] == "C" || letras[1] == "c") -> lineChoosed = 3
        (letras[1] == "D" || letras[1] == "d") -> lineChoosed = 4
        (letras[1] == "E" || letras[1] == "e") -> lineChoosed = 5
        (letras[1] == "F" || letras[1] == "f") -> lineChoosed = 6
        (letras[1] == "G" || letras[1] == "g") -> lineChoosed = 7
        (letras[1] == "H" || letras[1] == "h") -> lineChoosed = 8
        else -> lineChoosed = 10
    }

    when{
        (letras[0] == "1") -> columnChoosed = 1
        (letras[0] == "2") -> columnChoosed = 2
        (letras[0] == "3") -> columnChoosed = 3
        (letras[0] == "4") -> columnChoosed = 4
        (letras[0] == "5") -> columnChoosed = 5
        (letras[0] == "6") -> columnChoosed = 6
        (letras[0] == "7") -> columnChoosed = 7
        (letras[0] == "8") -> columnChoosed = 8
        else -> columnChoosed = 11
    }


    if (columnChoosed > 8 || lineChoosed > 8){
        tabuleiroUnidimensional = null
    }else{
        tabuleiroUnidimensional = Pair(columnChoosed,lineChoosed)
    }
    }else{
        tabuleiroUnidimensional = null
    }

    return tabuleiroUnidimensional
}

fun checkRightPieceSelected(pieceColor: String, turn: Int): Boolean{
    var checkPiece = true
    when{
        (turn == 0 && pieceColor== "w" || turn == 1 && pieceColor == "b") -> checkPiece = true
        else -> checkPiece = false
    }
    return checkPiece
}

fun isCoordinateInsideChess (coord: Pair<Int, Int>,numColumns: Int,numLines: Int):Boolean {
    val column = coord.first
    val line = coord.second
    var validaInsideChess = true
when{
    (column < 1 || line < 1 || coord == null) -> validaInsideChess = false
    ((column > 8 || line > 8) && numColumns == 8 && numLines == 8) -> validaInsideChess = false
    ((column > 7 || line > 7) && numColumns == 7 && numLines == 7) -> validaInsideChess = false
    ((column > 6 || line > 6) && numColumns == 6 && numLines == 6) -> validaInsideChess = false
    ((column > 6 || line > 7) && numColumns == 6 && numLines == 7) -> validaInsideChess = false
    ((column > 4 || line > 4) && numColumns == 4 && numLines == 4) -> validaInsideChess = false
    else -> validaInsideChess = true
}

    return validaInsideChess

}

fun isValidTargetPiece(currentSelectedPiece : Pair<String, String>,currentCoord: Pair<Int, Int>,
                       targetCoord : Pair<Int, Int>, pieces : Array<Pair<String, String>?>, numColumns: Int, numLines: Int):Boolean{
    val peao = currentSelectedPiece.first
    var validacaoMove = true
    val currentColumn = currentCoord.first
    val currentLine = currentCoord.second
    val targetColumn = targetCoord.first
    val targetLine = targetCoord.second
    val current = pieces[((currentColumn -1)*numLines) + currentLine - 1]
    val target = pieces[((targetColumn -1)*numLines) + targetLine - 1]
    if (current == null){
        validacaoMove = false
    }else{
        when {
            peao == "P" -> validacaoMove = isKnightValid(currentCoord, targetCoord, pieces, numColumns, numLines)
            peao == "H" -> validacaoMove = isHorseValid(currentCoord, targetCoord, pieces, numColumns, numLines)
            peao == "K" -> validacaoMove = isKingValid(currentCoord, targetCoord, pieces, numColumns, numLines)
            peao == "T" -> validacaoMove = isTowerValid(currentCoord, targetCoord, pieces, numColumns, numLines)
            peao == "B" -> validacaoMove = isBishopValid(currentCoord, targetCoord, pieces, numColumns, numLines)
            peao == "Q" -> validacaoMove = isQueenValid(currentCoord, targetCoord, pieces, numColumns, numLines)
        }
    }



    return validacaoMove
}

fun movePiece( pieces : Array<Pair<String, String>?>, numColumns: Int, numLines: Int, currentCoord: Pair<Int, Int>,
               targetCoord: Pair<Int, Int>, totalPiecesAndTurn : Array<Int>): Boolean{
    var validacaoMove = true
    val currentColumn = currentCoord.first
    val currentLine = currentCoord.second
    val targetColumn = targetCoord.first
    val targetLine = targetCoord.second
    val tipoPeca = pieces[((currentColumn -1)*numLines) + currentLine - 1]
    val tipoPecaSecond = pieces[((targetColumn -1)*numLines) + targetLine - 1]
    val currentSelectedPiece = pieces[((currentColumn -1)*numLines) + currentLine - 1]
    if (currentSelectedPiece == null) {
        println(quandoDaErro)
    }else{
        validacaoMove = isValidTargetPiece(currentSelectedPiece,currentCoord, targetCoord, pieces, numColumns, numLines)
        if (validacaoMove == true){
            when{
                totalPiecesAndTurn[2] == 0 -> totalPiecesAndTurn[2] = 1
                totalPiecesAndTurn[2] == 1 -> totalPiecesAndTurn[2] = 0
            }
            if (tipoPeca != null && tipoPecaSecond != null){
                if(tipoPecaSecond.second != tipoPeca.second){
                    altera(totalPiecesAndTurn)
                }
            }
            pieces[((targetColumn -1)*numLines) + targetLine - 1] = tipoPeca
            pieces[((currentColumn -1)*numLines) + currentLine - 1] = null
        }
    }
    return validacaoMove
}

fun startNewGame (whitePlayer: String, blackPlayer: String, pieces : Array<Pair<String, String>?>,
                  totalPiecesAndTurn : Array<Int?>, numColumns: Int,numLines: Int, showLegend: Boolean= false, showPieces: Boolean = false){
    var jogador = ""
    if (totalPiecesAndTurn.size != 0) {
        when {
            (totalPiecesAndTurn[2] == 0) -> jogador = whitePlayer
            (totalPiecesAndTurn[2] == 1) -> jogador = blackPlayer
        }
    }
    val choose = "$jogador, choose a piece (e.g 2D).\nMenu-> m;\n"
    var validaMovimento = true
    do{
        println(buildBoard(numColumns, numLines, showLegend,showPieces, pieces))
            println(choose)
            val coordFirst = readLine()!!
        var currentLine = 0
        var currentColumn = 0
        var currentPeca :  Pair<String, String>? = Pair("p","w")
        var currentCoord : Pair<Int, Int> = Pair(1,1)
        if (coordFirst != "m"){
            if (coordFirst !in("a".."z")){
            currentCoord = getCoordinates (coordFirst) as Pair<Int, Int> //Mudar nome
             currentColumn = currentCoord.first
             currentLine = currentCoord.second
            currentPeca = pieces[((currentColumn -1)*numLines) + currentLine - 1]
            }else{
                currentPeca = null
            }
        while(currentPeca == null){
            println(quandoDaErro)
            println(choose)
            val coordFirst = readLine()!!
            val currentCoord = getCoordinates (coordFirst) as Pair<Int, Int> //Mudar nome
        }

    println("$jogador, choose a target piece (e.g 2D).\n" +
            "Menu-> m;\n")
    val coordSecond = readLine()!!
            if (coordFirst !in("a".."z")){
    val currentCoordSecond = getCoordinates (coordSecond) as Pair<Int, Int>//Mudar nome
    validaMovimento = movePiece( pieces, numColumns, numLines, currentCoord,currentCoordSecond, totalPiecesAndTurn as Array<Int>)
            }
    if (validaMovimento == false){
        println(quandoDaErro)
    }
    if (totalPiecesAndTurn.size != 0 && totalPiecesAndTurn[0] == 0 || totalPiecesAndTurn[1] == 0){
        println("Congrats! $jogador wins!")
    }
}else{
    totalPiecesAndTurn[1] = 0
}


    }while(validaMovimento == false && totalPiecesAndTurn[0] != 0 && totalPiecesAndTurn[1] != 0)
    if (totalPiecesAndTurn.size != 0){
        when{
            (jogador == whitePlayer) -> totalPiecesAndTurn[2] = 1
            else -> totalPiecesAndTurn[2] = 0
        }
    }

}

fun isHorseValid(currentCoord: Pair<Int, Int>,targetCoord : Pair<Int, Int>,pieces : Array<Pair<String, String>?>,
                 numColumns: Int, numLines: Int): Boolean{
    var validaHorse = false
    val currentColumn = currentCoord.first
    val targetColumn = targetCoord.first
    val currentLine = currentCoord.second
    val targetLine = targetCoord.second
    val current = pieces[((currentColumn -1)*numLines) + currentLine - 1]
    val target = pieces[((targetColumn -1)*numLines) + targetLine - 1]
        if(!(targetCoord.first > numColumns || targetCoord.second > numLines)){
            when{
                (currentCoord.first == targetCoord.first -2 && currentCoord.second == targetCoord.second +1) -> validaHorse = true
                (currentCoord.first == targetCoord.first -2 && currentCoord.second == targetCoord.second -1) -> validaHorse = true
                (currentCoord.first == targetCoord.first +2 && currentCoord.second == targetCoord.second +1) -> validaHorse = true
                (currentCoord.first == targetCoord.first +2 && currentCoord.second == targetCoord.second -1) -> validaHorse = true
                (currentCoord.second == targetCoord.second -2 && currentCoord.first == targetCoord.first +1) -> validaHorse = true
                (currentCoord.second == targetCoord.second -2 && currentCoord.first == targetCoord.first -1) -> validaHorse = true
                (currentCoord.second == targetCoord.second +2 && currentCoord.first == targetCoord.first +1) -> validaHorse = true
                (currentCoord.second == targetCoord.second +2 && currentCoord.first == targetCoord.first -1) -> validaHorse = true
            }
    }

    if(current != null && target != null){
        val currentColor = current.second
        val targetColor = target.second
        if (currentColor == targetColor){
            validaHorse = false
        }
    }
    return validaHorse
}

fun isKingValid(currentCoord: Pair<Int, Int>,targetCoord : Pair<Int, Int>,pieces: Array<Pair<String, String>?>,numColumns: Int,numLines: Int):Boolean{
    var validaKing = false
    val currentColumn = currentCoord.first
    val targetColumn = targetCoord.first
    val currentLine = currentCoord.second
    val targetLine = targetCoord.second
    val current = pieces[((currentColumn -1)*numLines) + currentLine - 1]
    val target = pieces[((targetColumn -1)*numLines) + targetLine - 1]
    count = 0
        if (!(targetCoord.first > numColumns || targetCoord.second > numLines)) {
            when {
                (currentCoord.first == targetCoord.first + 1 || currentCoord.first == targetCoord.first - 1) -> validaKing = true
                (currentCoord.second == targetCoord.second + 1 || currentCoord.second == targetCoord.second - 1) -> validaKing = true
                (currentCoord.first == targetCoord.first + 1 && currentCoord.second == targetCoord.second - 1) -> validaKing = true
                (currentCoord.first == targetCoord.first + 1 && currentCoord.second == targetCoord.second + 1) -> validaKing = true
                (currentCoord.first == targetCoord.first - 1 && currentCoord.second == targetCoord.second - 1) -> validaKing = true
                (currentCoord.first == targetCoord.first - 1 && currentCoord.second == targetCoord.second + 1) -> validaKing = true
            }
        }
    if(current != null && target != null){
        val currentColor = current.second
         val targetColor = target.second
        if (currentColor == targetColor){
            validaKing = false
        }
    }
    return validaKing
}

fun isTowerValid(currentCoord: Pair<Int, Int>,targetCoord: Pair<Int, Int>,pieces: Array<Pair<String, String>?>,numColumns: Int,numLines: Int):Boolean{
    var validaTower = false
    val currentColumn = currentCoord.first
    val targetColumn = targetCoord.first
    val currentLine = currentCoord.second
    val targetLine = targetCoord.second
    val current = pieces[((currentColumn -1)*numLines) + currentLine - 1]
    val target = pieces[((targetColumn -1)*numLines) + targetLine - 1]
    count = 0

        count = 0
        if(!(targetCoord.first > numColumns || targetCoord.second > numLines)){
            while(numColumns > count || numLines > count){
                when{
                    (currentCoord.second == targetCoord.second && currentCoord.first == targetCoord.first -count) -> validaTower = true
                    (currentCoord.first == targetCoord.first && currentCoord.second == targetCoord.second -count) -> validaTower = true
                    (currentCoord.second == targetCoord.second && currentCoord.first == targetCoord.first +count) -> validaTower = true
                    (currentCoord.first == targetCoord.first && currentCoord.second == targetCoord.second +count) -> validaTower = true
                }
                count++
            }
        }
    if(current != null && target != null){
        val currentColor = current.second
        val targetColor = target.second
        if (currentColor == targetColor){
            validaTower = false
        }
    }
    return validaTower
}

fun isBishopValid(currentCoord: Pair<Int, Int>,targetCoord: Pair<Int, Int>,pieces: Array<Pair<String, String>?>,
                  numColumns: Int,numLines: Int): Boolean{
    var validaBishop = false
    val currentColumn = currentCoord.first
    val targetColumn = targetCoord.first
    val currentLine = currentCoord.second
    val targetLine = targetCoord.second
    val current = pieces[((currentColumn -1)*numLines) + currentLine - 1]
    val target = pieces[((targetColumn -1)*numLines) + targetLine - 1]
    count = 0
        if(!(targetCoord.first > numColumns || targetCoord.second > numLines)){
            while(numColumns > count || numLines > count){
                when{
                    (currentCoord.first == targetCoord.first +count && currentCoord.second == targetCoord.second -count) -> validaBishop = true
                    (currentCoord.first == targetCoord.first +count && currentCoord.second == targetCoord.second +count) -> validaBishop = true
                    (currentCoord.first == targetCoord.first -count && currentCoord.second == targetCoord.second -count) -> validaBishop = true
                    (currentCoord.first == targetCoord.first -count && currentCoord.second == targetCoord.second +count) -> validaBishop = true
                }
                count++
            }
        }
    if(current != null && target != null){
        val currentColor = current.second
        val targetColor = target.second
        if (currentColor == targetColor){
            validaBishop = false
        }
    }
    return validaBishop
}

fun isQueenValid(currentCoord: Pair<Int, Int>,targetCoord: Pair<Int, Int>,pieces: Array<Pair<String, String>?>,numColumns: Int,numLines: Int):Boolean{
    var validaQueen = false
    val currentColumn = currentCoord.first
    val targetColumn = targetCoord.first
    val currentLine = currentCoord.second
    val targetLine = targetCoord.second
    val current = pieces[((currentColumn -1)*numLines) + currentLine - 1]
    val target = pieces[((targetColumn -1)*numLines) + targetLine - 1]
        count = 0
        if(!(targetCoord.first > numColumns || targetCoord.second > numLines)){
            while(numColumns > count || numLines > count){
                when{
                    (currentCoord.second == targetCoord.second && currentCoord.first == targetCoord.first -count) -> validaQueen = true
                    (currentCoord.first == targetCoord.first && currentCoord.second == targetCoord.second -count) -> validaQueen = true
                    (currentCoord.second == targetCoord.second && currentCoord.first == targetCoord.first +count) -> validaQueen = true
                    (currentCoord.first == targetCoord.first && currentCoord.second == targetCoord.second +count) -> validaQueen = true
                    (currentCoord.first == targetCoord.first +count && currentCoord.second == targetCoord.second -count) -> validaQueen = true
                    (currentCoord.first == targetCoord.first +count && currentCoord.second == targetCoord.second +count) -> validaQueen = true
                    (currentCoord.first == targetCoord.first -count && currentCoord.second == targetCoord.second -count) -> validaQueen = true
                    (currentCoord.first == targetCoord.first -count && currentCoord.second == targetCoord.second +count) -> validaQueen = true
                }
                count++
            }
        }
    if(current != null && target != null){
        val currentColor = current.second
        val targetColor = target.second
        if (currentColor == targetColor){
            validaQueen = false
        }
    }
    return validaQueen
}

fun isKnightValid(currentCoord: Pair<Int, Int>,targetCoord: Pair<Int, Int>,pieces: Array<Pair<String, String>?>,
                  numColumns: Int,numLines: Int):Boolean{
    var validaKnight = false
    val currentColumn = currentCoord.first
    val targetColumn = targetCoord.first
    val currentLine = currentCoord.second
    val targetLine = targetCoord.second
    val current = pieces[((currentColumn -1)*numLines) + currentLine - 1]
    val target = pieces[((targetColumn -1)*numLines) + targetLine - 1]
    count = 0
        if(!(targetCoord.first > numColumns || targetCoord.second > numLines)){
            when{
                (currentCoord.first == targetCoord.first - 1 && currentCoord.second == targetCoord.second) -> validaKnight = true
                (currentCoord.first == targetCoord.first + 1 && currentCoord.second == targetCoord.second) -> validaKnight = true
                else -> validaKnight = false
            }
        }

if(current != null && target != null){
    val currentColor = current.second
    val targetColor = target.second
    if (currentColor == targetColor){
        validaKnight = false
    }
}
    return validaKnight
}



fun verificaTabuleiro(numeroColumns:Int, numeroLines:Int):Boolean{
    var tabuleiros = false
    when{
        (numeroColumns == 8 && numeroLines == 8) -> tabuleiros = true
        (numeroColumns == 7 && numeroLines == 7) -> tabuleiros = true
        (numeroColumns == 6 && numeroLines == 6) -> tabuleiros = true
        (numeroColumns == 6 && numeroLines == 7) -> tabuleiros = true
        (numeroColumns == 4 && numeroLines == 4) -> tabuleiros = true
        else -> tabuleiros = false
    }
    return tabuleiros
}


fun main() {
    println("Welcome to the Chess Board Game!")
    println(buildMenu())
    var opcao = validacaoMenuInicial()
    do{
        while(opcao == 1){
            val jogador1 = escolherNomeJogador1()
            val jogador2 =escolherNomeJogador2()
            var numeroColumns = funChessColumns().toInt()
            var numeroLines = funChessLines().toInt()
            var verificaTabuleirosDefinidos = verificaTabuleiro(numeroColumns, numeroLines)
            while (verificaTabuleirosDefinidos == false){
                println(quandoDaErro)
                numeroColumns = funChessColumns().toInt()
                numeroLines = funChessLines().toInt()
                verificaTabuleirosDefinidos = verificaTabuleiro(numeroColumns, numeroLines)
            }

            val legend = funShowLegend()
            val pieces =  funShowPieces()
            val legendValue = showChessLegendOrPieces(legend)  as Boolean
            val piecesValue = showChessLegendOrPieces(pieces)  as Boolean
            val totalPieces = createTotalPiecesAndTurn(numeroColumns, numeroLines)
            val initialBoard = createInitialBoard(numeroColumns, numeroLines)
                val numeros = totalPieces as Array<Int>

            if (totalPieces.size != 0){
            do {
                startNewGame(jogador1, jogador2, initialBoard ,totalPieces, numeroColumns,numeroLines, legendValue, piecesValue)
            }while (numeros[1] != 0 )
            }

            println(buildMenu())
            opcao = validacaoMenuInicial()
        }
    }while (opcao != 2 && opcao != 1)

}