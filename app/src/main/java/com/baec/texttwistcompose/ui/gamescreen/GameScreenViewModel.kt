import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baec.texttwistcompose.data.Word
import com.baec.texttwistcompose.data.WordSolution
import com.baec.texttwistcompose.repository.WordRepository
import com.baec.texttwistcompose.ui.gamescreen.GameScreenState
import com.baec.texttwistcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel() {
    val TAG = "DEBUG"

    var gameState by mutableStateOf(GameScreenState())
        private set

    private val solutions: MutableList<WordSolution> = mutableListOf()
    private val availableLetters: MutableList<Char> = mutableListOf()
    private val guessedLetters: MutableList<Char> = mutableListOf()

    fun clickStartGame(){
        loadRandomWordAndInitializeGame(6)
    }

    fun clickGuessedLetter(index: Int) {
        val letter = guessedLetters.removeAt(index)
        availableLetters.add(letter)
        updateGameState()
    }

    fun clickAvailableLetter(index: Int) {
        val letter = availableLetters.removeAt(index)
        guessedLetters.add(letter)
        updateGameState()
    }

    fun clickSubmit() {
        val sb = StringBuilder()
        for (c in guessedLetters)
            sb.append(c)
        checkAnswer(sb.toString())
    }

    private fun checkAnswer(guessedWord: String) {
        for (wordSolution in solutions) {
            if (wordSolution.word == guessedWord) {
                wordSolution.isGuessed = true
                break
            }
        }
    }

    private fun revealAllAnswers() {
        for (wordSolution in solutions) {
            wordSolution.isGuessed = true
        }
    }

    private fun loadRandomWordAndInitializeGame(wordLength: Int) {
        viewModelScope.launch {
            val randomWordResult = wordRepository.getRandomWord(wordLength)
            if (randomWordResult is Resource.Success) {
                initializeGame(randomWordResult.data!!)
            }
        }
    }

    private fun initializeGame(word: Word) {
        availableLetters.clear()
        guessedLetters.clear()
        solutions.clear()
        val shuffledWord = word.alphaWord.toCharArray()
        shuffledWord.shuffle()
        for (c in shuffledWord) {
            availableLetters.add(c)
        }
        for (solutionWord in word.solutions) {
            solutions.add(WordSolution(solutionWord, false))
        }
        updateGameState()
    }

    private fun updateGameState() {
        gameState = gameState.copy(
            availableLetters = availableLetters.toList(),
            guessedLetters = guessedLetters.toList(),
            solutions = solutions.toList()
        )
    }
}
