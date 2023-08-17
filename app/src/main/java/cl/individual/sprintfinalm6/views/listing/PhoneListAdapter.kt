package cl.individual.sprintfinalm6.views.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cl.individual.sprintfinalm6.R
import cl.individual.sprintfinalm6.data.local.PhoneEntity
import cl.individual.sprintfinalm6.databinding.ListItemLayoutBinding
import coil.load

class PhoneListAdapter : RecyclerView.Adapter<PhoneListAdapter.ListViewHolder>() {
    private lateinit var binding: ListItemLayoutBinding
    private val phoneList = mutableListOf<PhoneEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val phone = phoneList[position]
        holder.bind(phone)
    }

    override fun getItemCount(): Int = phoneList.size

    fun setData(phone: List<PhoneEntity>) {
        this.phoneList.clear()
        this.phoneList.addAll(phone)
        notifyDataSetChanged()
    }

    class ListViewHolder(val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(phone: PhoneEntity) {
            binding.imgPhone.load(phone.image)
            binding.txtName.text = phone.name
            binding.txtPrice.text = "$ ${phone.price}"
            binding.cardPhoneItem.setOnClickListener {
                val selectedItemId = Bundle()
                selectedItemId.putInt("id", phone.id)
                findNavController(binding.root).navigate(
                    R.id.action_listFragment_to_detailsFragment,
                    selectedItemId
                )
            }
        }

    }
}