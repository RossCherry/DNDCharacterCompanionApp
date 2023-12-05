package com.zybooks.DnDPlayerCompanion

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CharacterDialogFragment: DialogFragment() {

    interface OnSubjectEnteredListener {
        fun onSubjectEntered(characterText: String)
    }

    private lateinit var listener: OnSubjectEnteredListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val characterEditText = EditText(requireActivity())
        characterEditText.inputType = InputType.TYPE_CLASS_TEXT
        characterEditText.maxLines = 1
        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.character)
            .setView(characterEditText)
            .setPositiveButton(R.string.create) { dialog, whichButton ->
                // Notify listener
                val character = characterEditText.text.toString()
                listener.onSubjectEntered(character.trim())
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnSubjectEnteredListener
    }
}