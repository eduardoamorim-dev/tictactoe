package com.example.tictactoe

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // Matriz representando o tabuleiro (0 = vazio, 1 = X, 2 = O)
    private var board = Array(3) { IntArray(3) { 0 } }

    // Jogador atual (1 = X, 2 = O)
    private var currentPlayer = 1

    // Estado do jogo (true = jogo ativo, false = jogo terminado)
    private var isGameActive = true

    // Placar dos jogadores
    private var scoreX = 0
    private var scoreO = 0

    // Referencias dos componentes da UI
    private lateinit var tvStatus: TextView
    private var tvScoreX: TextView? = null
    private var tvScoreO: TextView? = null

    private lateinit var buttons: Array<Array<Button>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            initializeButtonsArray()
            initializeViews()
            setupGame()
        } catch (e: Exception) {
            e.printStackTrace()
            tvStatus = TextView(this)
            tvStatus.text = "Erro ao inicializar o jogo"
        }
    }

    private fun initializeButtonsArray() {
        buttons = Array(3) { Array(3) { Button(this) } }
    }

    private fun initializeViews() {
        tvStatus = findViewById(R.id.tvStatus)

        try {
            tvScoreX = findViewById(R.id.tvScoreX)
            tvScoreO = findViewById(R.id.tvScoreO)
        } catch (e: Exception) {
            // Se não existir, ignora
        }

        // Inicializar botões do tabuleiro
        buttons[0][0] = findViewById(R.id.btn00)
        buttons[0][1] = findViewById(R.id.btn01)
        buttons[0][2] = findViewById(R.id.btn02)
        buttons[1][0] = findViewById(R.id.btn10)
        buttons[1][1] = findViewById(R.id.btn11)
        buttons[1][2] = findViewById(R.id.btn12)
        buttons[2][0] = findViewById(R.id.btn20)
        buttons[2][1] = findViewById(R.id.btn21)
        buttons[2][2] = findViewById(R.id.btn22)
    }

    private fun setupGame() {
        updateStatusText()
        updateScoreDisplay()

        // Configurar cores para os símbolos
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].setOnClickListener { onCellClick(it) }
                // Adicionar tag para identificar posição
                buttons[i][j].tag = "$i,$j"
            }
        }
    }

    fun onCellClick(view: View) {
        if (!isGameActive) return

        val button = view as Button
        val tag = button.tag?.toString() ?: return

        try {
            val coordinates = tag.split(",")
            val row = coordinates[0].toInt()
            val col = coordinates[1].toInt()

            // Verificar se a célula já está ocupada
            if (board[row][col] != 0) {
                // Animação de shake para indicar movimento inválido
                shakeButton(button)
                return
            }

            // Fazer a jogada
            makeMove(row, col, button)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun makeMove(row: Int, col: Int, button: Button) {
        // Atualizar o tabuleiro
        board[row][col] = currentPlayer

        // Atualizar o botão com o símbolo e cor apropriados
        if (currentPlayer == 1) {
            button.text = "X"
            button.setTextColor(Color.RED)
        } else {
            button.text = "O"
            button.setTextColor(Color.BLUE)
        }

        // Animação de escala ao fazer jogada
        scaleButton(button)

        // Verificar se há um vencedor
        val winner = checkWinner()

        when (winner) {
            1 -> {
                tvStatus.text = "Jogador X Venceu!"
                tvStatus.setTextColor(Color.RED)
                scoreX++
                isGameActive = false
                highlightWinningLine()
                updateScoreDisplay()
            }
            2 -> {
                tvStatus.text = "Jogador O Venceu!"
                tvStatus.setTextColor(Color.BLUE)
                scoreO++
                isGameActive = false
                highlightWinningLine()
                updateScoreDisplay()
            }
            3 -> {
                tvStatus.text = "Empate!"
                tvStatus.setTextColor(Color.GRAY)
                isGameActive = false
            }
            else -> {
                // Alternar jogador
                currentPlayer = if (currentPlayer == 1) 2 else 1
                updateStatusText()
            }
        }
    }

    private fun checkWinner(): Int {
        // Verificar linhas
        for (i in 0..2) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0]
            }
        }

        // Verificar colunas
        for (j in 0..2) {
            if (board[0][j] != 0 && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return board[0][j]
            }
        }

        // Verificar diagonal principal
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0]
        }

        // Verificar diagonal secundária
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2]
        }

        // Verificar empate
        var emptyCells = 0
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == 0) emptyCells++
            }
        }

        return if (emptyCells == 0) 3 else 0 // 3 = empate, 0 = jogo continua
    }

    private fun highlightWinningLine() {
        try {
            val winnerBackground = ContextCompat.getDrawable(this, R.drawable.cell_winner_background)

            // Verificar linhas
            for (i in 0..2) {
                if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                    buttons[i][0].background = winnerBackground
                    buttons[i][1].background = winnerBackground
                    buttons[i][2].background = winnerBackground
                    return
                }
            }

            // Verificar colunas
            for (j in 0..2) {
                if (board[0][j] != 0 && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                    buttons[0][j].background = winnerBackground
                    buttons[1][j].background = winnerBackground
                    buttons[2][j].background = winnerBackground
                    return
                }
            }

            // Verificar diagonal principal
            if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
                buttons[0][0].background = winnerBackground
                buttons[1][1].background = winnerBackground
                buttons[2][2].background = winnerBackground
                return
            }

            // Verificar diagonal secundária
            if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
                buttons[0][2].background = winnerBackground
                buttons[1][1].background = winnerBackground
                buttons[2][0].background = winnerBackground
            }
        } catch (e: Exception) {
            // Se der erro no drawable, usar cor simples
            for (i in 0..2) {
                if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                    buttons[i][0].setBackgroundColor(Color.YELLOW)
                    buttons[i][1].setBackgroundColor(Color.YELLOW)
                    buttons[i][2].setBackgroundColor(Color.YELLOW)
                    return
                }
            }
        }
    }

    private fun updateStatusText() {
        val playerSymbol = if (currentPlayer == 1) "X" else "O"
        tvStatus.text = "Vez do Jogador $playerSymbol"

        val color = if (currentPlayer == 1) Color.RED else Color.BLUE
        tvStatus.setTextColor(color)
    }

    private fun updateScoreDisplay() {
        tvScoreX?.text = scoreX.toString()
        tvScoreO?.text = scoreO.toString()
    }

    fun onResetClick(view: View) {
        resetGame()
    }

    private fun resetGame() {
        // Limpar o tabuleiro
        board = Array(3) { IntArray(3) { 0 } }

        // Resetar estado do jogo
        currentPlayer = 1
        isGameActive = true

        // Limpar todos os botões
        try {
            val defaultBackground = ContextCompat.getDrawable(this, R.drawable.cell_background)
            for (i in 0..2) {
                for (j in 0..2) {
                    buttons[i][j].text = ""
                    buttons[i][j].background = defaultBackground
                    buttons[i][j].setTextColor(Color.BLACK)
                }
            }
        } catch (e: Exception) {
            // Se der erro no drawable, usar cor simples
            for (i in 0..2) {
                for (j in 0..2) {
                    buttons[i][j].text = ""
                    buttons[i][j].setBackgroundColor(Color.LTGRAY)
                    buttons[i][j].setTextColor(Color.BLACK)
                }
            }
        }

        // Atualizar status
        updateStatusText()

        // Animação de reset
        animateReset()
    }

    // Animações
    private fun scaleButton(button: Button) {
        try {
            val scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1.0f, 1.2f, 1.0f)
            val scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1.0f, 1.2f, 1.0f)

            scaleX.duration = 200
            scaleY.duration = 200

            scaleX.start()
            scaleY.start()
        } catch (e: Exception) {
            // Se der erro na animação, ignora
        }
    }

    private fun shakeButton(button: Button) {
        try {
            val shake = ObjectAnimator.ofFloat(button, "translationX", 0f, 10f, -10f, 10f, -10f, 0f)
            shake.duration = 300
            shake.start()
        } catch (e: Exception) {
            // Se der erro na animação, ignora
        }
    }

    private fun animateReset() {
        try {
            for (i in 0..2) {
                for (j in 0..2) {
                    val alpha = ObjectAnimator.ofFloat(buttons[i][j], "alpha", 0.3f, 1.0f)
                    alpha.duration = 300
                    alpha.startDelay = ((i * 3 + j) * 50).toLong()
                    alpha.start()
                }
            }
        } catch (e: Exception) {
            // Se der erro na animação, ignora
        }
    }
}