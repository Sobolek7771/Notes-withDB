package com.example.kartoved.fragments.note.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kartoved.R
import com.example.kartoved.databinding.FragmentNoteListBinding
import com.example.kartoved.viewmodel.NoteViewModel


class ListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() =_binding!!

    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)

        //RecyclerView
        val adapter = ListAdapter()
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //NoteViewModel
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mNoteViewModel.readAllData.observe(viewLifecycleOwner, Observer { note ->
            adapter.setData(note)
        })

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        binding.backToMapButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete){
            deleteAllNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setNegativeButton("Нет"){_,_ ->}
        builder.setPositiveButton("Да"){_,_ ->
            mNoteViewModel.deleteAllNotes()
            Toast.makeText(requireContext(), "Все заметки удалены", Toast.LENGTH_SHORT).show()
        }
        builder.setTitle("Удалить все?")
        builder.setMessage("Вы уверены что хотите удалить все заметки с телефона?")
        builder.create().show()
    }
}