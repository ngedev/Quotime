package com.ngedev.quotime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ngedev.quotime.database.QuoteDatabase
import com.ngedev.quotime.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding?=null
    private val binding get()=_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val quote = QuoteDatabase.getDatabase(applicationContext).quoteDao().getRandomQuote()

        if(quote != null){
            binding.tvRandomQuotes.text = quote.quotes
            if(quote.author.isNotEmpty()){
                binding.tvAuthor.text = "- ${quote.author}"
            }
        }

        Handler(mainLooper).postDelayed({
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }, 5000)


    }
}