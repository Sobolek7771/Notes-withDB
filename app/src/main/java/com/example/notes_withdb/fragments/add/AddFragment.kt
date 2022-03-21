package com.example.notes_withdb.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notes_withdb.R
import com.example.notes_withdb.databinding.FragmentAddBinding
import com.example.notes_withdb.model.Note
import com.example.notes_withdb.viewmodel.NoteViewModel


class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() =_binding!!

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.addBtn.setOnClickListener{
            insertDataToDatabase()
        }

        return binding.root
    }

    private fun insertDataToDatabase(){
        val noteName = binding.addNoteNameEt.text.toString()
        val noteText = binding.addNoteTextEt.text.toString()
        val noteGps = binding.addGpsEt.text.toString()

        if (inputCheck(noteName,noteText,noteGps)){
            //Create Note object
            val note = Note(0, noteName,noteText,noteGps)
            //Add data to database
            mNoteViewModel.addNote(note)
            Toast.makeText(requireContext(),"Note added",Toast.LENGTH_LONG).show()
            //Navigation back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"All fields must be filled",Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(noteName: String, noteText: String, noteGps: String):Boolean{
        return !(TextUtils.isEmpty(noteName) && TextUtils.isEmpty(noteText) && TextUtils.isEmpty(noteGps))
    }
}