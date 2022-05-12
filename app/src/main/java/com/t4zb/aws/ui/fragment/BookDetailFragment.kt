package com.t4zb.aws.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.t4zb.aws.R
import com.t4zb.aws.databinding.FragmentBookDetailBinding
import com.t4zb.aws.modellayer.helper.PicassoHelper
import com.t4zb.aws.ui.Contract.BaseContract
import com.t4zb.aws.ui.viewmodel.SharedViewModel
import com.t4zb.aws.utils.showLogDebug
import com.t4zb.aws.utils.showLogError
import com.t4zb.aws.utils.showSnack
import kotlinx.android.synthetic.main.item_book_card.*


class BookDetailFragment : BaseFragment(R.layout.fragment_book_detail),BaseContract.ViewMain {

    private lateinit var mBinding: FragmentBookDetailBinding
    private lateinit var mContext: FragmentActivity
    private lateinit var mSharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentBookDetailBinding.bind(view)

        mSharedViewModel =
            ViewModelProvider(requireActivity(), defaultViewModelProviderFactory).get(
                SharedViewModel::class.java
            )

        mBinding.updateButton.setOnClickListener {
            var action = BookDetailFragmentDirections.actionBookDetailFragmentToUpdateBookFragment()
            Navigation.findNavController(view).navigate(action)
        }

        mBinding.backBtn.setOnClickListener {
            var action = BookDetailFragmentDirections.actionBookDetailFragmentToHomeFragment()
            Navigation.findNavController(view).navigate(action)
        }

        mBinding.deleteButton.setOnClickListener {
            var action = BookDetailFragmentDirections.actionBookDetailFragmentToHomeFragment()
            Navigation.findNavController(view).navigate(action)
        }


        mSharedViewModel.bookListDataRepo.bookListData.observe(viewLifecycleOwner, {data->

            showSnack(view,data.toString())




                    var model = data.get(mSharedViewModel.bookIndex!!)

                    mBinding.authrTextView.text = model.author
                    mBinding.bookNameTextView.text = model.book_name
                    mBinding.bookIdeasTextView.text = model.ideas

                    PicassoHelper().picassoOkhttp(mContext,model.imgUrl,mBinding.detailImage)


        })





    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    companion object {
     const val TAG = "BOOK DETAIL FRAGMENT"
    }

    override fun setupViewModel() {
    }

    override fun initializeViews() {
    }
}