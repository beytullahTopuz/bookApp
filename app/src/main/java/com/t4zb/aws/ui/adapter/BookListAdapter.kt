package com.t4zb.aws.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.t4zb.aws.R
import com.t4zb.aws.modellayer.helper.PicassoHelper
import com.t4zb.aws.modellayer.rest.service.event.BookModel
import com.t4zb.aws.ui.fragment.HomeFragmentDirections
import com.t4zb.aws.ui.viewmodel.SharedViewModel

class BookListAdapter(context: Context, bookList: List<BookModel>, viewModel: SharedViewModel) :
    RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    private var mContext = context
    private var mBookList = bookList
    private var mviewModel = viewModel

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mbook_img: ImageView = itemView.findViewById(R.id.mbook_img)
        var book_name_text: TextView = itemView.findViewById(R.id.book_name_text)
        var author_text_view: TextView = itemView.findViewById(R.id.author_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListAdapter.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view = layoutInflater.inflate(R.layout.item_book_card,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookListAdapter.ViewHolder, position: Int) {

        var cuurentBook = mBookList.get(position)

        if(!cuurentBook.imgUrl.isNullOrEmpty()){
            PicassoHelper().picassoOkhttp(mContext,cuurentBook.imgUrl,holder.mbook_img)
        }


        holder.author_text_view.text = cuurentBook.author
        holder.book_name_text.text = cuurentBook.book_name

        holder.itemView.setOnClickListener {
            mviewModel.bookIndex = position
            mviewModel.userID = cuurentBook.id!!
            var action = HomeFragmentDirections.actionHomeFragmentToBookDetailFragment()
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return mBookList.size
    }
}