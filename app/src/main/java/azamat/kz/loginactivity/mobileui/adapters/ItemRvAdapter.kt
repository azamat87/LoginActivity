package azamat.kz.loginactivity.mobileui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import azamat.kz.loginactivity.R
import azamat.kz.loginactivity.remote.model.TransactionModel
import kotlinx.android.synthetic.main.l_item.view.*

class ItemRvAdapter: RecyclerView.Adapter<ItemRvAdapter.ItemVH>() {

    private val items  = ArrayList<TransactionModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        return ItemVH(LayoutInflater.from(parent.context).inflate(R.layout.l_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        holder.tvTitle.text = items[position].x!!.hash
        val value = items[position].x!!.inputs[0].prev_out!!.value
        val format: Double = value!!.toDouble() / 100000000
        holder.tvCount.text = String.format("%.8f BTC", format)
    }

    fun addItems(item: TransactionModel) {
        items.add(item)
        notifyItemChanged(items.size)
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.tvHash
        val tvCount = itemView.tvValue
    }

}