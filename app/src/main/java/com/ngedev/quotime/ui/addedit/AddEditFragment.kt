package com.ngedev.quotime.ui.addedit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ngedev.quotime.*
import com.ngedev.quotime.databinding.FragmentAddEditBinding

class AddEditFragment : Fragment() {

    val viewModel:QuoteViewModel by activityViewModels(){
        QuoteViewmodelFactory((activity?.application as BaseApplication).database.quoteDao())
    }

    private var _binding : FragmentAddEditBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedQuote.observe(viewLifecycleOwner){
            binding.apply {
                etQuotes.setText(it?.quotes)
                etAuthor.setText(it?.author)
                if(it==null){
                    (requireActivity() as AppCompatActivity).supportActionBar?.title = "Add Quotes"
                    binding.bDelete.visibility = View.GONE
                } else {
                    (requireActivity() as AppCompatActivity).supportActionBar?.title = "Edit Quotes"
                }
            }
        }

        binding.bSave.setOnClickListener {
            val quote = binding.etQuotes.text.toString()
            val author = binding.etAuthor.text.toString()
            val dataQuote = Quote(0, quote, author)
            viewModel.insertQuote(dataQuote)
            findNavController().navigate(R.id.action_addEditFragment_to_listQuotesFragment)
        }

        binding.bDelete.setOnClickListener {
            viewModel.deleteQuote()
            findNavController().navigate(R.id.action_addEditFragment_to_listQuotesFragment)
        }
    }

}