package com.ngedev.quotime.ui.viewquotes

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ngedev.quotime.*
import com.ngedev.quotime.databinding.FragmentAddEditBinding
import com.ngedev.quotime.databinding.FragmentViewQuotesBinding

class ViewQuotesFragment : Fragment() {
    val viewModel: QuoteViewModel by activityViewModels(){
        QuoteViewmodelFactory((activity?.application as BaseApplication).database.quoteDao())
    }

    private var _binding : FragmentViewQuotesBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.viewModel = viewModel

        viewModel.selectedQuote.observe(viewLifecycleOwner){
            binding.tvRandomQuotes.text = it?.quotes

            if(it?.author?.isNotEmpty() == true){
                binding.tvAuthor.text = "- ${it?.author}"
            }
        }

        binding.fabRandom.setOnClickListener {
            viewModel.selectQuotes(viewModel.getRandomQuote())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuEdit -> {
                findNavController().navigate(R.id.action_viewQuotesFragment_to_addEditFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}