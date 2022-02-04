package com.bacon.graphqlrickandmorty.ui.fragments.characters.detail

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.bacon.graphqlrickandmorty.R
import com.bacon.graphqlrickandmorty.common.base.BaseFragment
import com.bacon.graphqlrickandmorty.databinding.FragmentCharacterDetailBinding
import com.bacon.graphqlrickandmorty.ui.state.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel>(
        R.layout.fragment_character_detail
    ) {
    override val binding by viewBinding(FragmentCharacterDetailBinding::bind)
    override val viewModel: CharacterDetailViewModel by viewModels()
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun setupRequests() {
        viewModel.queryCharacter(args.id)
    }

    override fun setupObserves() = with(binding) {
        viewModel.characterState.subscribe { it ->
            characterDetailsFetchProgress.isVisible = it is UIState.Loading
            characterDetailsNotFound.isVisible = it is UIState.Error
            when (it) {
                is UIState.Error -> {
                    Log.e("anime", "setupObserves Detail Character:${it.error} ")
                }
                is UIState.Loading -> {
                    Log.e("anime", "setupObserves Detail Character:${it} ")
                }
                is UIState.Success -> {
                    Log.e("anime", "setupObserves Detail Character:${it.data} ")
                    it.data.data?.character?.let {
                        characterName.text = it.name
                        characterGender.text = it.gender
                        characterSpecies.text = it.species
                        characterStatus.text = it.status
                        characterType.text = it.type
                        characterIv.load(it.image)
                        characterId.text = it.id
                    }
                }
            }
        }
    }

}