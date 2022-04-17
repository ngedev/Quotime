package com.ngedev.quotime.ui.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ngedev.quotime.BaseApplication
import com.ngedev.quotime.QuoteViewModel
import com.ngedev.quotime.QuoteViewmodelFactory
import com.ngedev.quotime.databinding.FragmentListQuotesBinding
import com.ngedev.quotime.R
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation


class ListQuotesFragment : Fragment() {

    private val viewModel: QuoteViewModel by activityViewModels() {
        QuoteViewmodelFactory((activity?.application as BaseApplication).database.quoteDao())
    }

    private var _binding: FragmentListQuotesBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val adapter = ListAdapterQuotes {
            viewModel.selectQuotes(it)
            findNavController().navigate(R.id.action_listQuotesFragment_to_viewQuotesFragment)
        }

        binding.apply {
            rvQuotes.adapter = adapter

            fabRandom.setOnClickListener {
                viewModel.selectQuotes(viewModel.getRandomQuote())
                findNavController().navigate(R.id.action_listQuotesFragment_to_viewQuotesFragment)
            }
        }

        viewModel.listQuote.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuAdd -> {
                viewModel.selectQuotes(null)
                findNavController().navigate(R.id.action_listQuotesFragment_to_addEditFragment)
            }

            R.id.menuSetting -> {
                findNavController().navigate(R.id.action_listQuotesFragment_to_settingFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}