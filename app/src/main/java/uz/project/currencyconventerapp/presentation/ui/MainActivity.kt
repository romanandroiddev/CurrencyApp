package uz.project.currencyconventerapp.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import uz.project.currencyconventerapp.R
import uz.project.currencyconventerapp.databinding.ActivityMainBinding
import uz.project.currencyconventerapp.presentation.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val currency_codes = resources.getStringArray(R.array.currency_codes)

        val mArrayAdapter = ArrayAdapter<Any?>(this, R.layout.spinner_item, currency_codes)
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        binding.fromCurrencyTxt.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (binding.amountOfCurrency.text.toString().isNotEmpty()) {
                        convertCurrency()
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        binding.toCurrencyTxt.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (binding.amountOfCurrency.text.toString().isNotEmpty()) {
                        convertCurrency()
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        binding.toCurrencyTxt.adapter = mArrayAdapter
        binding.fromCurrencyTxt.adapter = mArrayAdapter


        binding.amountOfCurrency.addTextChangedListener {
            if (binding.amountOfCurrency.text.toString()
                    .isNotEmpty() && binding.amountOfCurrency.text.toString() != "0"
            ) {
                convertCurrency()
            }
        }




        binding.btnSwap.setOnClickListener {
            val fromCurrencyId = binding.fromCurrencyTxt.selectedItemId
            val toCurrencyId = binding.toCurrencyTxt.selectedItemId
            binding.fromCurrencyTxt.setSelection(toCurrencyId.toInt())
            binding.toCurrencyTxt.setSelection(fromCurrencyId.toInt())
            if (binding.amountOfCurrency.text.toString().isNotEmpty()) convertCurrency()

        }


        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when (event) {
                    is MainViewModel.CurrencyEvent.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tVResult.text = event.resultText
                        binding.onlyItemTv.text =
                            viewModel.conversion_only_item.value
                    }
                    is MainViewModel.CurrencyEvent.Failure -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.tVResult.text = event.errorText
                    }
                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun convertCurrency() {
        viewModel.convert(
            binding.amountOfCurrency.text.toString(),
            binding.fromCurrencyTxt.selectedItem.toString(),
            binding.toCurrencyTxt.selectedItem.toString(),
        )
    }

}

