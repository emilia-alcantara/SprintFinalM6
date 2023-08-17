package cl.individual.sprintfinalm6.views.listing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import cl.individual.sprintfinalm6.databinding.FragmentListBinding
import cl.individual.sprintfinalm6.views.PhoneViewModel


class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val phoneViewModel: PhoneViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)
        phoneViewModel.getAllPhones()
        initRecycler()

        return binding.root
    }

    private fun initRecycler() {
        val phoneListAdapter = PhoneListAdapter()
        binding.recPhoneList.adapter = phoneListAdapter
        phoneViewModel.phonesLiveData().observe(viewLifecycleOwner) {
            phoneListAdapter.setData(it)
        }
    }


}