package com.example.gob_fact.ui.main.fact

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.gob_fact.R
import com.example.gob_fact.data.datasource.database.entities.FactEntity
import com.example.gob_fact.databinding.FragmentFactBinding
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FactFragment : Fragment() {

    lateinit var viewModel: FactViewModel
    lateinit var binding: FragmentFactBinding
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FactViewModel::class.java]
        arguments?.let {
            val factId = it.getString("fact_id")
            factId?.let { id ->
                viewModel.factId = id
            }
        }
        requireActivity().window.statusBarColor = requireActivity().getColor(R.color.white)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setOnClickListeners()
        initRequestLocation()
    }

    private fun observeViewModel() {
        viewModel.fact.observe(viewLifecycleOwner) {
            it?.let { fact ->
                binding.fact = fact
            }
        }
        viewModel.loadFact()
    }

    private fun initRequestLocation() {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = LocationListener { location ->
            latitude = location.latitude
            longitude = location.longitude
        }
        if (checkLocationPermissions()) {
            requestLocationUpdates(locationManager, locationListener)
        } else {
            requestLocationPermissions()
        }
    }

    private fun checkLocationPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
        binding.buttonShareWhatsApp.visibility = View.GONE
    }

    private fun requestLocationUpdates(locationManager: LocationManager, locationListener: LocationListener) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                latitude = it.latitude
                longitude = it.longitude
            }
        }.addOnFailureListener {
            showSnackBar("Error getting location, please enable GPS. For now, use the location service.")
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_UPDATE_INTERVAL,
                LOCATION_UPDATE_DISTANCE,
                locationListener
            )
        }
    }

    private fun setOnClickListeners() {
        binding.buttonOpenUrl.setOnClickListener {
            viewModel.fact.value?.let { fact ->
                openUrl(fact.url)
            }
        }
        binding.buttonCopyUrl.setOnClickListener {
            viewModel.fact.value?.let { fact ->
                copyToClipboard(fact.url)
            }
        }
        binding.buttonShareWhatsApp.setOnClickListener {
            viewModel.fact.value?.let { fact ->
                shareOnWhatsApp(fact)
            }
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun copyToClipboard(url: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("URL", url)
        clipboard.setPrimaryClip(clip)
    }

    private fun shareOnWhatsApp(fact: FactEntity) {
        val message = """
            ${fact.fact}
            _${fact.dateInsert}_

            *Organization* ${fact.organization}
            *Resource* ${fact.resource}
            *Operations* ${fact.operations}
            *Columns* ${fact.columns}

            🔗 ${fact.url}

            _Latitude_ $latitude
            _Longitude_ $longitude
        """.trimIndent()
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.setPackage("com.whatsapp")
        startActivity(intent)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val LOCATION_UPDATE_INTERVAL = 5000L
        private const val LOCATION_UPDATE_DISTANCE = 5f
    }
}