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
import com.t4zb.aws.databinding.FragmentUpdateBookBinding
import com.t4zb.aws.modellayer.helper.PicassoHelper
import com.t4zb.aws.modellayer.rest.service.event.BookModel
import com.t4zb.aws.modellayer.rest.service.repo.BookRepository
import com.t4zb.aws.modellayer.rest.service.repo.UpdateBookRepository
import com.t4zb.aws.ui.Contract.BaseContract
import com.t4zb.aws.ui.viewmodel.SharedViewModel
import com.t4zb.aws.utils.showLogDebug
import com.t4zb.aws.utils.showLogError
import com.t4zb.aws.utils.showSnack


class UpdateBookFragment : BaseFragment(R.layout.fragment_update_book), BaseContract.ViewMain {

    private lateinit var mBinding: FragmentUpdateBookBinding
    private lateinit var mContext: FragmentActivity
    private lateinit var mSharedViewModel: SharedViewModel
    private lateinit var imgUrlBOOK :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentUpdateBookBinding.bind(view)

        mSharedViewModel =
            ViewModelProvider(requireActivity(), defaultViewModelProviderFactory).get(
                SharedViewModel::class.java
            )

        mSharedViewModel.bookListDataRepo.bookListData.observe(viewLifecycleOwner, { data ->

            if (!data.isNullOrEmpty()) {


                var model = data.get(mSharedViewModel.bookIndex!!)

                imgUrlBOOK = model.imgUrl

                mBinding.bookNameTextField.setText(model.book_name)
                mBinding.bookAuthorTextField.setText(model.author)
                mBinding.bookPublisherEditorTextField.setText(model.publisher_editor)
                mBinding.bookIdeadTextField.setText(model.ideas)

                PicassoHelper().picassoOkhttp(mContext,model.imgUrl,mBinding.bookImg)
            }
        })

        mBinding.backBtn.setOnClickListener {
            var action = UpdateBookFragmentDirections.actionUpdateBookFragmentToBookDetailFragment()
            Navigation.findNavController(view).navigate(action)
        }

        mBinding.updateBookBtn.setOnClickListener {

            var editor = mBinding.bookPublisherEditorTextField.text
            var ideas = mBinding.bookIdeadTextField.text
            var name = mBinding.bookNameTextField.text
            var id = mSharedViewModel.bookID
            var imgUrl = imgUrlBOOK
            var author = mBinding.bookAuthorTextField.text

            if (editor.isNullOrEmpty() or ideas.isNullOrEmpty() or name.isNullOrEmpty() or author.isNullOrEmpty()){
                showSnack(view,"please fill in the blanks")
            }else{
                var mModel = BookModel(
                    publisher_editor = editor.toString(),
                    ideas = ideas.toString(),
                    book_name = name.toString(),
                    id = id,
                    imgUrl = imgUrl,
                    author = author.toString()
                )
             //   showLogError(TAG,mModel.toString())
                var dataRepo = activity?.let { it1 -> UpdateBookRepository(it1.application,mModel,id!!) }

                dataRepo?.updateData?.observe(viewLifecycleOwner,{data->


               //     showLogDebug(TAG,dataRepo?.updateData.toString())

                    if (data != null){
                        if (data.message.equals("updated")){
                            showSnack(view,data.message.toString())
                            var action = UpdateBookFragmentDirections.actionUpdateBookFragmentToHomeFragment()
                            Navigation.findNavController(view).navigate(action)
                        }
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
        return inflater.inflate(R.layout.fragment_update_book, container, false)
    }

    companion object {
        const val TAG = "UPDATE BOOK FRAGMENT"
    }

    override fun setupViewModel() {
    }

    override fun initializeViews() {
    }
}