package com.example.kartoved.fragments.network.authorization

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kartoved.R
import com.example.kartoved.databinding.FragmentAuthorizationBinding
import com.example.kartoved.model.LoginPassword
import com.example.kartoved.model.User
import com.example.kartoved.viewmodel.UserViewModel

class AuthorizationFragment: Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container,false)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.enterButton.setOnClickListener{
            authorizeUser()
        }

        binding.backToMapButton.setOnClickListener{
            findNavController().navigate(R.id.action_authorizationFragment_to_mainFragment)
        }

        return binding.root
    }

    private fun authorizeUser(){
        //val username = "+71040000000"
        val username = binding.editLogin.text.toString()
        //val password = "soso4ek123"
        val password = binding.editPassword.text.toString()

        if (inputCheck(username, password)){
            val loginPassword = LoginPassword(username, password)
            userViewModel.signIn(loginPassword)

            userViewModel.userResponse.observe(viewLifecycleOwner, Observer { response ->
                if(response.isSuccessful){
                    //на всякий случай удаляем всех юзеров
                    userViewModel.deleteAllUsersFromDatabase()

                    //#TODO работа с куки написана на костялях, надо будет переписать

                    val strCookie = response.headers().get("set-cookie")
                    val sessionid = strCookie?.substring(0, strCookie.indexOf(';'))

                    val user = User(response.body()?.id, response.body()?.username, response.body()?.first_name, response.body()?.second_name, response.body()?.phone, response.body()?.birthday,response.body()?.email,sessionid)
                        //response.headers().get("set-cookie"),response.headers().get("x-token"))

                    userViewModel.addUserInDatabase(user)

                    Toast.makeText(requireContext(), "Пользователь авторизован",Toast.LENGTH_SHORT).show()

                    //val action = AuthorizationFragmentDirections.actionAuthorizationFragmentToNetworkFragment(null)
                    //findNavController().navigate(action)
                    findNavController().navigate(R.id.action_authorizationFragment_to_networkFragment)
                }else{
                    Toast.makeText(requireContext(), "ERROR ".plus(response.code().toString()), Toast.LENGTH_SHORT).show()
                }
            })
        }else{
            Toast.makeText(requireContext(), "Необходимо заполнить все поля", Toast.LENGTH_SHORT).show()
        }
    }

    /*
    private fun readAllCookies(response: Response<User>?) {
        System.out.println(response.toString())
    }
     */

    private fun inputCheck(username: String, password: String):Boolean{
        return !(TextUtils.isEmpty(username) && TextUtils.isEmpty(password))
    }

}