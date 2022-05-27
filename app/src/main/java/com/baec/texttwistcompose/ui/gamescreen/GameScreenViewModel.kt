import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.baec.texttwistcompose.data.WordSolution
import com.baec.texttwistcompose.repository.WordRepository
import com.baec.texttwistcompose.ui.gamescreen.GameScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel() {
    val TAG = "DEBUG"

    var gameState by mutableStateOf(GameScreenState())
        private set

    private val solutions: List<WordSolution> = emptyList()
    private val availableLetters: MutableList<Char> = mutableListOf()
    private val guessedLetters: MutableList<Char> = mutableListOf()

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
        for(c in guessedLetters)
            sb.append(c)
        checkAnswer(sb.toString())
    }

    private fun checkAnswer(guessedWord: String) {
        for(wordSolution in solutions)
        {
            if(wordSolution.word == guessedWord)
            {
                wordSolution.isGuessed = true
                break
            }
        }
    }

    private fun revealAllAnswers(){
        for(wordSolution in solutions)
        {
            wordSolution.isGuessed = true
        }
    }

    private fun updateGameState() {
        gameState = gameState.copy(
            availableLetters = availableLetters.toList(),
            guessedLetters = guessedLetters.toList()
        )
    }
}
