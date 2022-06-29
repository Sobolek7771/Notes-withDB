package com.example.kartoved.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kartoved.R
import com.example.kartoved.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    //TODO перенеси логику авторизован ли юзер в окно работы с сетью
    //private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        //(activity as AppCompatActivity?)!!.getSupportActionBar()!!.hide()
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        //userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.noteMenuButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_listFragment)
        }

        /*
        binding.networkMenuButton.setOnClickListener{
            userViewModel.readAllData.observe(viewLifecycleOwner, Observer{ user ->
                if (user==null){
                    findNavController().navigate(R.id.action_mainFragment_to_authorizationFragment)
                }else{
                    findNavController().navigate(R.id.action_mainFragment_to_networkFragment)
                }
            })
        }
         */

        binding.networkMenuButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_networkFragment)
        }

        return binding.root
    }
}