package ba.etf.rma23.projekat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {
    private final var gameTitle = MutableLiveData<String>()

    fun setData(title :String){
        gameTitle.value = title
    }

    fun getSelectedData(): LiveData<String>{
        return gameTitle
    }
}