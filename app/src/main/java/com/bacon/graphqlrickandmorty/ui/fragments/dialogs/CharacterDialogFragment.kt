package com.bacon.graphqlrickandmorty.ui.fragments.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.bacon.graphqlrickandmorty.databinding.FragmentCharacterDialogBinding


class CharacterDialogFragment : DialogFragment() {

    private val args: CharacterDialogFragmentArgs by navArgs()
    private lateinit var binding: FragmentCharacterDialogBinding


    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentCharacterDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setupImage()
        return builder
    }

    private fun setupImage() {
        binding.characterImage.load(args.image)
    }

}