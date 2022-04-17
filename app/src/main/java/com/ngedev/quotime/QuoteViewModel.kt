package com.ngedev.quotime

import android.util.Log
import androidx.lifecycle.*
import com.ngedev.quotime.database.QuoteDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class QuoteViewModel(private val quoteDao: QuoteDao): ViewModel() {
    val listQuote: LiveData<List<Quote>> = quoteDao.getQutes().asLiveData()

    private val _selectedQuote = MutableLiveData<Quote?>()
    val selectedQuote: LiveData<Quote?> = _selectedQuote

    fun getRandomQuote() = quoteDao.getRandomQuote()

    fun selectQuotes(quote: Quote?){
        Log.d("selectQuotes", "$quote")
        _selectedQuote.value = quote
    }

    fun insertQuote(quote: Quote){
        if(selectedQuote.value == null){
            viewModelScope.launch {
                quoteDao.insert(quote)
            }
        } else {
            val toUpdateQuote = selectedQuote.value
            toUpdateQuote?.quotes = quote.quotes
            toUpdateQuote?.author = quote.author
            if (toUpdateQuote != null) {
                updateQuote(toUpdateQuote)
            }
        }
    }

    fun updateQuote(quote: Quote){
        viewModelScope.launch {
            quoteDao.update(quote)
        }
    }

    fun deleteQuote(){
        viewModelScope.launch {
            selectedQuote.value?.let { quoteDao.delete(it) }
        }
    }
}

class QuoteViewmodelFactory(private val quoteDao: QuoteDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(QuoteViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return QuoteViewModel(quoteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}