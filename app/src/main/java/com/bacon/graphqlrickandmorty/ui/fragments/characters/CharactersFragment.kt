package com.bacon.graphqlrickandmorty.ui.fragments.characters

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bacon.graphqlrickandmorty.R
import com.bacon.graphqlrickandmorty.common.base.BaseFragment
import com.bacon.graphqlrickandmorty.databinding.FragmentCharactersBinding
import com.bacon.graphqlrickandmorty.ui.adapters.CharactersAdapter
import com.bacon.graphqlrickandmorty.ui.state.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : BaseFragment<FragmentCharactersBinding, CharactersViewModel>(
    R.layout.fragment_characters
) {
    override val binding by viewBinding(FragmentCharactersBinding::bind)
    override val viewModel: CharactersViewModel by viewModels()
    private val adapter =
        CharactersAdapter(this::setOnItemLongClickListener, this::setOnItemClickListener)

    override fun initialize() {
        binding.rvCharacter.adapter = adapter
    }

    override fun setupObserves() {
        viewModel.queryCharactersList()
        viewModel.charactersList.subscribe {
            when (it) {
                is UIState.Error -> {
                    Log.e("anime", "setupObserves:${it.error} ")
                }
                is UIState.Loading -> {
                    Log.e("anime", "setupObserves:${it} ")
                }
                is UIState.Success -> {
                    Log.e("anime", "setupObserves:${it.data} ")
                    adapter.submitList(it.data.data?.characters?.results)
                }
            }
        }
    }


    private fun setOnItemClickListener(id: String) {
        findNavController().navigate(
            CharactersFragmentDirections
                .actionCharactersFragmentToCharacterDetailFragment(id))
    }

    private fun setOnItemLongClickListener(image: String) {
        findNavController().navigate(
            CharactersFragmentDirections
                .actionCharactersFragmentToCharacterDialogFragment(
                    image))
    }
}