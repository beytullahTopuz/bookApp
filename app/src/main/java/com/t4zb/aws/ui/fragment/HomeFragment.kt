package com.t4zb.aws.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.t4zb.aws.R
import com.t4zb.aws.databinding.FragmentHomeBinding
import com.t4zb.aws.modellayer.rest.service.repo.BookRepository
import com.t4zb.aws.modellayer.rest.service.repo.LoginRepository
import com.t4zb.aws.ui.Contract.BaseContract
import com.t4zb.aws.ui.adapter.BookListAdapter
import com.t4zb.aws.ui.viewmodel.SharedViewModel
import com.t4zb.aws.utils.showLogDebug
import kotlinx.android.synthetic.main.item_book_card.*


class HomeFragment : BaseFragment(R.layout.fragment_home), BaseContract.ViewMain {

    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mContext: FragmentActivity
    private lateinit var mSharedViewModel: SharedViewModel

    override fun setupViewModel() {


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentHomeBinding.bind(view)
        mBinding.bookRecylerView.layoutManager = GridLayoutManager(context, 2)

        mSharedViewModel =
            ViewModelProvider(requireActivity(), defaultViewModelProviderFactory).get(
                SharedViewModel::class.java
            )


        var dataRepo = activity?.let { it1 -> BookRepository(it1.application,) }

        showLogDebug(TAG,"girdi")
        showLogDebug(TAG,dataRepo?.bookListData.toString())

        dataRepo?.bookListData?.observe(viewLifecycleOwner, { data ->
            showLogDebug(TAG, data.toString())
            showLogDebug(TAG, data.toString())

            if (data != null && data.isNotEmpty()) {

                var mAdapter = BookListAdapter(mContext, data, mSharedViewModel)
                mBinding.bookRecylerView.adapter = mAdapter

            }
        })

        mBinding.menuButton.setOnClickListener {
            var action = HomeFragmentDirections.actionHomeFragmentToAddBookFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    override fun initializeViews() {
        showLogDebug(TAG, "girdi initialize views")

        //   var dataRepo = activity?.let { it1 -> BookRepository(it1.application) }




    }

    fun adaptersetup(){

        showLogDebug(TAG,"girdi 41")
        mBinding.bookRecylerView.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

        mSharedViewModel.bookListDataRepo?.bookListData?.observe(viewLifecycleOwner, { data ->
            showLogDebug(TAG, data.toString())
            showLogDebug(TAG, data.toString())


            //    showLogDebug(TAG,data.toString())
            var mAdapter = BookListAdapter(mContext, data, mSharedViewModel)
            mBinding.bookRecylerView.adapter = mAdapter


        })
    }

    companion object {
        const val TAG = "Home Fragment"
    }
}