package ba.etf.rma23.projekat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {
    private final var gameId = MutableLiveData<Int>()

    fun setData(id: Int){
        gameId.value = id
    }

    fun getSelectedData(): LiveData<Int>{
        return gameId
    }
}