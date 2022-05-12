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
import com.t4zb.aws.databinding.FragmentAddBookBinding
import com.t4zb.aws.modellayer.rest.service.event.BookModel
import com.t4zb.aws.modellayer.rest.service.event.BookModelInsert
import com.t4zb.aws.modellayer.rest.service.repo.InsertBookRepository
import com.t4zb.aws.modellayer.rest.service.repo.RegisterRepository
import com.t4zb.aws.ui.Contract.BaseContract
import com.t4zb.aws.ui.viewmodel.SharedViewModel
import com.t4zb.aws.utils.showLogDebug
import com.t4zb.aws.utils.showSnack
import kotlinx.android.synthetic.main.fragment_add_book.*
import kotlinx.android.synthetic.main.item_book_card.*


class AddBookFragment : BaseFragment(R.layout.fragment_add_book),BaseContract.ViewMain {

    private lateinit var mBinding : FragmentAddBookBinding
    private lateinit var mContext: FragmentActivity
    private lateinit var mSharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentAddBookBinding.bind(view)

        mSharedViewModel =
            ViewModelProvider(requireActivity(), defaultViewModelProviderFactory).get(
                SharedViewModel::class.java
            )

        mBinding.backBtn.setOnClickListener {
            var action =  AddBookFragmentDirections.actionAddBookFragmentToHomeFragment()
            Navigation.findNavController(view).navigate(action)
        }


            mBinding.addookBtn.setOnClickListener {
                var bookName = mBinding.bookNameTextField.text
                var bookAuthor = mBinding.bookAuthorTextField.text
                var book_editor = mBinding.bookPublisherEditorTextField.text
                var book_ideas = mBinding.bookIdeadTextField.text

                if (bookName.isNullOrEmpty() or bookAuthor.isNullOrEmpty() or book_editor.isNullOrEmpty() or book_ideas.isNullOrEmpty()){
                    showSnack(view,"please fill in the blanks")
                }else{
                    var bookModel = BookModelInsert(
                        publisher_editor = book_editor.toString(),
                        ideas = book_ideas.toString(),
                        book_name = bookName.toString(),
                        imgUrl = "",
                        author = bookAuthor.toString()
                    )

                    // inset Book

                    var dataRepo = activity?.let { it1 -> InsertBookRepository(it1.application,bookModel) }

                    dataRepo?.insertBookData?.observe(viewLifecycleOwner,{data->
                        showLogDebug(TAG,data.toString())
                        if(data.message.equals("insert book successful")){
                            showSnack(view,data.message.toString())

                            var action =  AddBookFragmentDirections.actionAddBookFragmentToHomeFragment()
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
        return inflater.inflate(R.layout.fragment_add_book, container, false)
    }

    companion object {
      const val TAG = "Add Book Fragment"
    }

    override fun setupViewModel() {

    }

    override fun initializeViews() {

    }
}