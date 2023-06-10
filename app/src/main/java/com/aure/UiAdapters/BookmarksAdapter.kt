package com.aure.UiAdapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aure.*
import com.aure.UiModels.BookmarksModel
import com.aure.fragments.PostFullView
import com.aure.fragments.viewTypeImage
import com.aure.fragments.viewTypeVideo

private const val TYPE_IMAGE = 1
private const val TYPE_VIDEO = 2
private const val TYPE_PROFILE = 3

class BookmarksAdapter(private val bookmarkList: ArrayList<BookmarksModel>, var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_IMAGE -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.image_media_item, parent, false)
                return BookMarkItemImageViewHolder(view)
            }
            TYPE_VIDEO -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.video_media_item, parent, false)
                return BookMarkItemVideoViewHolder(view)
            }
            TYPE_PROFILE -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.profile_media_item, parent, false)
                return BookMarkItemProfileViewHolder(view)
            }
            else -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.image_media_item, parent, false)
                return BookMarkItemImageViewHolder(view)
            }
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }


    override fun getItemViewType(position: Int): Int {
        return when (bookmarkList[position].type) {
            TYPE_IMAGE -> {
                TYPE_IMAGE
            }
            TYPE_VIDEO -> {
                TYPE_VIDEO
            }
            TYPE_PROFILE -> {
                TYPE_PROFILE
            }
            else -> {
                TYPE_IMAGE
            }
        }
    }


    inner class BookMarkItemVideoViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            ItemView.setOnClickListener {
                context.startActivity(Intent(context,PostFullView::class.java).putExtra("type",
                    viewTypeVideo
                ))
            }
        }
    }

    inner class BookMarkItemImageViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            ItemView.setOnClickListener {
                context.startActivity(Intent(context, PostFullView::class.java).putExtra("type",
                    viewTypeImage
                ))
            }
        }
    }

    inner class BookMarkItemProfileViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        init {
            ItemView.setOnClickListener {
                context.startActivity(Intent(context,DatingProfileFullView::class.java))
            }
        }
    }

}


