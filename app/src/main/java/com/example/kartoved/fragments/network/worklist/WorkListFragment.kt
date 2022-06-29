package com.example.kartoved.fragments.network.worklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kartoved.databinding.FragmentWorkListBinding
import com.example.kartoved.viewmodel.UserViewModel
import com.example.kartoved.viewmodel.WorkViewModel

class WorkListFragment : Fragment() {

    private var _binding: FragmentWorkListBinding? = null
    private val binding get() = _binding!!

    private lateinit var workViewModel: WorkViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
         _binding = FragmentWorkListBinding.inflate(inflater, container, false)

        //RecyclerView
        val adapter = WorkListAdapter()
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        workViewModel = ViewModelProvider(this).get(WorkViewModel::class.java)
        //userViewModel создаем для того чтоб взять куки
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        //Запрос на сервер
        getAllWork()
        //Отображенние данных
        workViewModel.workResponse.observe(viewLifecycleOwner, Observer { response ->
            if(response.isSuccessful){
                adapter.setData(response.body())
            }
        })
        return binding.root
    }

    private fun getAllWork() {
        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            val cookies = user.cookies
            if (cookies != null) {
                workViewModel.getWork(cookies)
            }
        })

    }
}