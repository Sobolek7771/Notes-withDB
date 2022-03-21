package com.example.notes_withdb.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes_withdb.R
import com.example.notes_withdb.databinding.FragmentUpdateBinding
import com.example.notes_withdb.model.Note
import com.example.notes_withdb.viewmodel.NoteViewModel

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mNoteViewModel : NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container,false)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.updateNoteNameEt.setText(args.currentNote.noteName)
        binding.updateNoteTextEt.setText(args.currentNote.noteText)
        binding.updateNoteGpsEt.setText(args.currentNote.noteGps)

        binding.updateBtn.setOnClickListener{
            updateItem()
        }
        //Add menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun updateItem(){
        val noteName = binding.updateNoteNameEt.text.toString()
        val noteText = binding.updateNoteTextEt.text.toString()
        val noteGps = binding.updateNoteGpsEt.text.toString()

        if (inputCheck(noteName, noteText, binding.updateNoteGpsEt.text)){
            //Create object
            val updatedNote = Note(args.currentNote.id, noteName, noteText, noteGps)
            Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_SHORT).show()
            //Update Current Note
            mNoteViewModel.updateNote(updatedNote)
            //Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }


    private fun inputCheck(noteName: String, noteText: String, note: Editable):Boolean{
        return !(TextUtils.isEmpty(noteName) && TextUtils.isEmpty(noteText) && note.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setNegativeButton("No"){_,_ ->}
        builder.setPositiveButton("Yes"){_,_ ->
            mNoteViewModel.deleteNote(args.currentNote)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            Toast.makeText(requireContext(), "${args.currentNote.noteName} removed", Toast.LENGTH_SHORT).show()
        }
        builder.setTitle("Delete ${args.currentNote.noteName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentNote.noteName}?")
        builder.create().show()
    }
}