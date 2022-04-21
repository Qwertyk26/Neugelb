package de.neugelb.presentation.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.neugelb.R
import de.neugelb.databinding.ItemMovieBinding
import de.neugelb.domain.entity.response.Result

class MoviesAdapter(var results: ArrayList<Result>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    var onItemClick: (Result) -> Unit = {}

    fun addList(newList: List<Result>) {
        val oldSize = results.size
        results.addAll(newList)
        notifyItemRangeInserted(oldSize, results.size - oldSize)
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) = with(binding) {
            Glide.with(this@ViewHolder.itemView.context)
                .load("${this@ViewHolder.itemView.context.getString(R.string.posters_base_url)}${result.posterPath}")
                .into(posterImage)
            movieTitle.text = result.originalTitle
            this@ViewHolder.itemView.setOnClickListener {
                onItemClick?.invoke(result)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }
}