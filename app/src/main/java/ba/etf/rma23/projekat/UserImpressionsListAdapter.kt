package ba.etf.rma23.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.unsa.etf.rma.spirala1.R

class UserImpressionsListAdapter (
    private var userImpression: List<UserImpression>
) : RecyclerView.Adapter<UserImpressionsListAdapter.UserImpressionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserImpressionsViewHolder
    {
        return when(viewType) {
            0 ->
                UserRatingViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_ratings, parent, false))

            1 ->
                UserReviewViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_review, parent, false))

            else ->
                throw Exception("Error")
        }
    }

    override fun getItemCount(): Int = userImpression.size
    override fun onBindViewHolder(holder: UserImpressionsViewHolder, position: Int) {
        when(userImpression[position]){
            is UserRating -> {
                (holder as UserRatingViewHolder)
                holder.username.text = (userImpression[position] as UserRating).username
                holder.ratingBar.rating = (userImpression[position] as UserRating).rating.toFloat()
            }
            is UserReview -> {
                (holder as UserReviewViewHolder)
                holder.username.text = (userImpression[position] as UserReview).username
                holder.commentar.text = (userImpression[position] as UserReview).review
            }
        }
    }
    fun updateUserImpressions(userImpression: List<UserImpression>) {
        this.userImpression = userImpression
        notifyDataSetChanged()
    }
    open inner class UserImpressionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    inner class UserRatingViewHolder(itemView: View) : UserImpressionsViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username_textview)
        val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)
    }

    inner class UserReviewViewHolder(itemView: View) : UserImpressionsViewHolder(itemView){
        val username: TextView = itemView.findViewById(R.id.username_textview)
        val commentar: TextView = itemView.findViewById(R.id.review_textview)
    }

    override fun getItemViewType(position: Int): Int = when(userImpression[position]) {
        is UserRating -> {
            0
        }
        is UserReview -> {
            1
        }
        else -> {
            2
        }
    }
}