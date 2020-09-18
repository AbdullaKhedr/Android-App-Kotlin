package cmps312.qatar2022

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.stadium_item.view.*

class Adapter(val list: List<Stadium>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        StadiumViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.stadium_item, parent, false)
        )

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is StadiumViewHolder) {
            viewHolder.bind(list[position])
        }
    }

    override fun getItemCount() = list.size



}
class StadiumViewHolder(itemVew: View) : RecyclerView.ViewHolder(itemVew) {
    fun bind(stadium: Stadium) {
        itemView.apply {

            nameTv.text = stadium.name
            cityTv.text = stadium.city
            statusTv.text = stadium.status
            // to get the images
            val image = resources.getIdentifier(
                stadium.image,
                "drawable",
                context.packageName
            )
            stadiumImage.setImageResource(image)
        }
    }
}