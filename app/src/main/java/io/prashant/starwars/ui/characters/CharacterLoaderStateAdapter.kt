package io.prashant.starwars.ui.characters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import io.prashant.starwars.R
import java.net.UnknownHostException

class CharacterLoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<CharacterLoaderStateAdapter.LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {

        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.getInstance(parent, retry)
    }

    class LoaderViewHolder(view: View, retry: () -> Unit) : RecyclerView.ViewHolder(view) {

        companion object {
            fun getInstance(parent: ViewGroup, retry: () -> Unit): LoaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.item_characters_loading, parent, false)
                return LoaderViewHolder(view, retry)
            }
        }

        private val groupError: Group = view.findViewById(R.id.group_error)
        private val groupLoading: Group = view.findViewById(R.id.group_loading)
        private val tvError: TextView = view.findViewById(R.id.tv_error)

        init {
            view.findViewById<Button>(R.id.btn_try_again).setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            when (loadState) {
                is LoadState.Loading -> {
                    groupLoading.visibility = View.VISIBLE
                    groupError.visibility = View.GONE
                }
                is LoadState.Error -> {
                    groupLoading.visibility = View.GONE
                    groupError.visibility = View.VISIBLE
                    tvError.text = getErrorText(tvError.context, loadState.error)
                }
                else -> {
                    groupLoading.visibility = View.GONE
                    groupError.visibility = View.GONE
                }
            }
        }

        private fun getErrorText(context: Context, e: Throwable) =
            if (e is UnknownHostException) {
                context.getString(R.string.please_check_your_internet_connection)
            } else {
                context.getString(R.string.failed_to_load_characters)
            }
    }
}