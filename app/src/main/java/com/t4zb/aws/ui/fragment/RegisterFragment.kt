package com.t4zb.aws.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.t4zb.aws.R
import com.t4zb.aws.databinding.FragmentRegisterBinding
import com.t4zb.aws.modellayer.rest.service.event.RegisterUserModel
import com.t4zb.aws.modellayer.rest.service.repo.RegisterRepository
import com.t4zb.aws.ui.Contract.BaseContract
import com.t4zb.aws.ui.viewmodel.SharedViewModel
import com.t4zb.aws.utils.showLogDebug
import com.t4zb.aws.utils.showSnack


class RegisterFragment : BaseFragment(R.layout.fragment_register),BaseContract.ViewMain {

    private lateinit var mBinding: FragmentRegisterBinding
    private lateinit var mContext: FragmentActivity
    private lateinit var mSharedViewModel: SharedViewModel

    override fun setupViewModel() {

        mSharedViewModel =
            ViewModelProvider(requireActivity(), defaultViewModelProviderFactory).get(
                SharedViewModel::class.java
            )
    }

    override fun initializeViews() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentRegisterBinding.bind(view)

        mBinding.loginBtn.setOnClickListener {
            var action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(view).navigate(action)
        }

        mBinding.registerBtn.setOnClickListener {

            var name = mBinding.registerNameTextField.text
            var surname = mBinding.registerSurNameTextField.text
            var email = mBinding.registerEmailTextField.text
            var password = mBinding.registerPasswordTextField.text

            var mlist = listOf<String>()

            if(name.isNullOrEmpty() or surname.isNullOrEmpty() or email.isNullOrEmpty() or password.isNullOrEmpty()){
                showSnack(view,"please fill in the blanks")
            }else{
                var registerUserModel = RegisterUserModel(
                    user_name = name.toString(),
                    user_lastname = surname.toString(),
                    user_email = email.toString(),
                    user_password = password.toString(),
                    user_book_list = mlist
                )

                var dataRepo = activity?.let { it1 -> RegisterRepository(it1.application,registerUserModel) }

                dataRepo?.registerData?.observe(viewLifecycleOwner,{data->
                    showLogDebug(TAG,data.toString())
                    if (data.message.equals("register successful")){
                          showSnack(view,"User Created, Please Login")
                        var action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                        Navigation.findNavController(view).navigate(action)
                    }else{
                        showSnack(view,"Something went wrong")
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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    companion object {
       const val TAG = "REGISTER FRAGMENT"
    }
}