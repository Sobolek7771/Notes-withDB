package com.example.kartoved.fragments.network

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kartoved.R
import com.example.kartoved.databinding.FragmentNetworkBinding
import com.example.kartoved.viewmodel.UserViewModel
import com.example.kartoved.viewmodel.WorkViewModel

class NetworkFragment : Fragment() {

    private var _binding: FragmentNetworkBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel
    private lateinit var workViewModel: WorkViewModel

    private val args by navArgs<NetworkFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentNetworkBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        workViewModel = ViewModelProvider(this).get(WorkViewModel::class.java)

        //Если работа выбрана, то прячем возможность ее выбрать и отображаем выбранную работу
        if(args.currentWork == null){
            binding.mainSquare.visibility = View.GONE
            binding.showWork.visibility = View.VISIBLE
            binding.popOutMenuOuter.visibility = View.GONE
            binding.popOutMenu.visibility = View.GONE
            binding.missionDivider.visibility = View.GONE
        }else {
            binding.mainSquare.visibility = View.VISIBLE
            binding.showWork.visibility = View.GONE

            workViewModel.addWorkInDatabase(args.currentWork!!)
            Toast.makeText(requireContext(), "Миссия взята в работу", Toast.LENGTH_SHORT).show()

            //Надуваем поля работы
            workViewModel.readAllData.observe(viewLifecycleOwner, Observer { work ->
                if (work!==null){
                    binding.workNameView.text = work.name
                    binding.workDescriptionTextView.text = work.task

                    if (work.type_work == "polyline") {
                        binding.workIconView.setImageResource(R.drawable.ic_polyline)
                    } else if (work.type_work == "polygon") {
                        binding.workIconView.setImageResource(R.drawable.ic_area)
                    }
                }
            })

            //Expand  button
            binding.expandButton.setOnClickListener {
                binding.popOutMenu.visibility = if (binding.popOutMenu.visibility == View.GONE) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
                binding.expandButton.setImageResource(if (binding.popOutMenu.visibility == View.GONE) {
                    R.drawable.ic_expand_closed
                } else {
                    R.drawable.ic_expand_open
                })
            }

            binding.deleteWorkButton.setOnClickListener{
                removeWork()
            }
        }

        userViewModel.readAllData.observe(viewLifecycleOwner, Observer{ user ->
            if (user==null){
                Toast.makeText(requireContext(), "Для работы с сетью необходимо авторизоваться", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_networkFragment_to_authorizationFragment)
            }else{
                binding.firstName.setText(user.first_name)
                binding.secondName.setText(user.second_name)}
        })

        binding.logOutButton.setOnClickListener(){
            //отправка запроса на сервак
            removeUser()
        }

        binding.showWork.setOnClickListener(){
            findNavController().navigate(R.id.action_networkFragment_to_fragmentWorkList)
        }

        binding.backToMapButton.setOnClickListener{
            findNavController().navigate(R.id.action_networkFragment_to_mainFragment)
        }

        return binding.root
    }

    private fun removeWork() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setNegativeButton("Нет"){_,_ ->}
        builder.setPositiveButton("Да"){_,_ ->
            workViewModel.deleteAllWorkFromDatabase()
            Toast.makeText(requireContext(), "Миссия удалена", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_networkFragment_to_mainFragment)
        }
        builder.setTitle("Удалить миссию?")
        builder.setMessage("Вы уверены что хотите удалить миссию с телефона?")
        builder.create().show()
    }

    private fun removeUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setNegativeButton("Нет"){_,_ ->}
        builder.setPositiveButton("Да"){_,_ ->
            userViewModel.readAllData.observe(viewLifecycleOwner, Observer{ user ->
                val cookies = user.cookies
                if (cookies != null) {
                    userViewModel.signOut(cookies)
                }
            })
            userViewModel.userSignOutResponse.observe(viewLifecycleOwner, Observer{ response ->
                if((response.isSuccessful) && (response.code() == 200)){
                    userViewModel.deleteAllUsersFromDatabase()
                    workViewModel.deleteAllWorkFromDatabase()

                    Toast.makeText(requireContext(),"Пользователь удален", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_networkFragment_to_mainFragment)
                }else{
                    Toast.makeText(requireContext(), "ERROR ".plus(response.code().toString()), Toast.LENGTH_SHORT).show()
                }
            })
        }
        builder.setTitle("Выйти из пользователя?")
        builder.setMessage("Вы уверены что хотите из пользователя?")
        builder.create().show()
    }
}