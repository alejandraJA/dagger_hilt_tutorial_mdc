package com.example.gob_fact.ui.fragment.fact

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
            factId?.let {
                viewModel.factId = factId
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFactBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fact.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            binding.fact = it
        }
        viewModel.loadFact()
        setOnClickListeners()
        initRequestLocation()
    }

    private fun initRequestLocation() {
        val locationManager = requireContext()
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = LocationListener { location ->
            latitude = location.latitude
            longitude = location.longitude
        }
        val permissionStatus = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED

        if (permissionStatus) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            binding.buttonShareWhatsApp.visibility = View.GONE
            return
        }
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                latitude = it.latitude
                longitude = it.longitude
            }
        }.addOnFailureListener {
            Snackbar.make(
                binding.root,
                "Error getting location, please enable GPS. For now, use the location service.",
                Snackbar.LENGTH_LONG
            ).show()
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                5f,
                locationListener
            )
        }
    }

    private fun setOnClickListeners() {
        binding.buttonOpenUrl.setOnClickListener {
            viewModel.fact.value?.let {
                val url = it.url
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
        binding.buttonCopyUrl.setOnClickListener {
            viewModel.fact.value?.let {
                val url = it.url
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("URL", url)
                clipboard.setPrimaryClip(clip)
            }
        }
        binding.buttonShareWhatsApp.setOnClickListener {
            viewModel.fact.value?.let {
                val message =
                    """
                    ${it.fact}
                    _${it.dateInsert}_
                    
                    *Organization* ${it.organization}
                    *Resource* ${it.resource}
                    *Operations* ${it.operations}
                    *Columns* ${it.columns}
                   
                    🔗 ${it.url}
                    
                    _Latitude_ $latitude
                    _Longitude_ $longitude
                    """.trimIndent()
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, message)
                intent.setPackage("com.whatsapp")
                startActivity(intent)
            }
        }
    }

}