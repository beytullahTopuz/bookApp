package com.t4zb.aws.ui.fragment

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.t4zb.aws.R
import com.t4zb.aws.databinding.FragmentLoginBinding
import com.t4zb.aws.modellayer.rest.service.event.LoginModel
import com.t4zb.aws.modellayer.rest.service.repo.LoginRepository
import com.t4zb.aws.ui.Contract.BaseContract
import com.t4zb.aws.ui.viewmodel.SharedViewModel
import com.t4zb.aws.utils.showLogDebug
import com.t4zb.aws.utils.showSnack


class LoginFragment : BaseFragment(R.layout.fragment_login), BaseContract.ViewMain {

    private lateinit var mBinding: FragmentLoginBinding
    private lateinit var mContext:FragmentActivity
    private lateinit var mSharedViewModel: SharedViewModel

    override fun setupViewModel() {

        mSharedViewModel =
            ViewModelProvider(requireActivity(), defaultViewModelProviderFactory).get(
                SharedViewModel::class.java
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = requireActivity()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentLoginBinding.bind(view)

        mBinding.registerBtn.setOnClickListener {

            var action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(view).navigate(action)
        }



        mBinding.loginBtn.setOnClickListener{

            var email = mBinding.loginEmailTextField.text
            var password = mBinding.loginPasswordTextField.text
            if (email.isNullOrEmpty() or password.isNullOrEmpty()){
                showSnack(view,"please fill in the blanks")
            }else{

                var loginModel = LoginModel(
                    user_email ="$email",
                    user_password = "$password"
                )
                var dataRepo = activity?.let { it1 -> LoginRepository(it1.application,loginModel) }

                dataRepo?.loginData?.observe(viewLifecycleOwner,{ data->
                    showLogDebug(TAG,data.toString())
                    showSnack(view,data.message)
                    if (data.message.equals("login succes")){
                        var action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        Navigation.findNavController(view).navigate(action)
                    }
                })
            }




        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }
    override fun initializeViews() {

      /*  mSharedViewModel.loginDataRepo.loginData.observe(viewLifecycleOwner,{data->
            showLogDebug(TAG,data.toString())
        })

        */



    }

    companion object {

        const val TAG = "LOGIN FRAGMENT"
    }
}