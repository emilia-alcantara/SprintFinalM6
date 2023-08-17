package cl.individual.sprintfinalm6.views.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import cl.individual.sprintfinalm6.R
import cl.individual.sprintfinalm6.databinding.FragmentDetailsBinding
import cl.individual.sprintfinalm6.views.PhoneViewModel
import coil.load


private const val ARG_PARAM1 = "id"


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val phoneViewModel: PhoneViewModel by activityViewModels()

    private var selectedPhoneId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedPhoneId = it.getInt(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        phoneViewModel.getPhoneDetails(selectedPhoneId)
        initDetailsView()
        return binding.root
    }

    private fun initDetailsView() {
        phoneViewModel.phoneDetailsLiveData(selectedPhoneId).observe(viewLifecycleOwner) {
            if (it != null) {
                binding.txtNameDt.text = it.name
                binding.txtPriceDt.text = "Ahora $ ${it.price}"
                binding.txtLastPriceDt.text = "Antes $ ${it.lastPrice}"
                binding.txtDescriptionDt.text = it.description
                binding.imgPhoneDt.load(it.image)

                val creditValue: String = if (it.credit) {
                    getString(R.string.credit_true)
                } else {
                    getString(R.string.credit_false)
                }

                binding.txtCreditDt.text = creditValue
            }
        }
    }
}