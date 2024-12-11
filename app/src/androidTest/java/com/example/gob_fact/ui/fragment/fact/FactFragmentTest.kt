//package com.example.gob_fact.ui.fragment.fact
//
//import android.content.ClipboardManager
//import android.content.Context
//import android.content.Intent
//import android.location.Location
//import android.location.LocationManager
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.fragment.app.testing.FragmentScenario
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.tasks.OnFailureListener
//import com.google.android.gms.tasks.Task
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.Mockito.*
//import org.mockito.MockitoAnnotations
//import org.robolectric.Shadows.shadowOf
//
//@HiltAndroidTest
//@RunWith(AndroidJUnit4::class)
//class FactFragmentTest {
//
//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Mock
//    private lateinit var factViewModel: FactViewModel
//
//    @Mock
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//
//    @Mock
//    private lateinit var locationManager: LocationManager
//
//    @Mock
//    private lateinit var locationTask: Task<Location>
//
//    @Mock
//    private lateinit var location: Location
//
//    private lateinit var fragment: FactFragment
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.openMocks(this)
//    }
//
//    @Test
//    fun fragmentShouldObserveFactFromViewModel() {
//        // Arrange
//        val scenario = FragmentScenario.launchInContainer(FactFragment::class.java)
//        scenario.onFragment { fragment ->
////            val mockFact = mock(Fact::class.java)
////            `when`(mockFact.url).thenReturn("https://example.com")
////            `when`(mockFact.fact).thenReturn("Test Fact")
//
//            fragment.viewModel = factViewModel
////            `when`(factViewModel.fact.value).thenReturn(mockFact)
//
//            // Act & Assert
//            verify(factViewModel).loadFact()
//        }
//    }
//
//    @Test
//    fun openUrlButtonShouldLaunchBrowserIntent() {
//        // Arrange
//        val scenario = FragmentScenario.launchInContainer(FactFragment::class.java)
//        scenario.onFragment { fragment ->
////            val mockFact = mock(Fact::class.java)
////            `when`(mockFact.url).thenReturn("https://example.com")
//
//            fragment.viewModel = factViewModel
////            `when`(factViewModel.fact.value).thenReturn(mockFact)
//
//            // Act
//            fragment.binding.buttonOpenUrl.performClick()
//
//            // Assert
//            val shadowActivity = shadowOf(fragment.requireActivity())
//            val startedIntent = shadowActivity.nextStartedActivity
//            assert(startedIntent.action == Intent.ACTION_VIEW)
//            assert(startedIntent.data.toString() == "https://example.com")
//        }
//    }
//
//    @Test
//    fun copyUrlButtonShouldCopyToClipboard() {
//        // Arrange
//        val scenario = FragmentScenario.launchInContainer(FactFragment::class.java)
//        scenario.onFragment { fragment ->
////            val mockFact = mock(Fact::class.java)
////            `when`(mockFact.url).thenReturn("https://example.com")
//
//            fragment.viewModel = factViewModel
////            `when`(factViewModel.fact.value).thenReturn(mockFact)
//
//            val context = fragment.requireContext()
//            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//
//            // Act
//            fragment.binding.buttonCopyUrl.performClick()
//
//            // Assert
//            val shadowClipboardManager = shadowOf(clipboardManager)
////            val clipData = shadowClipboardManager.primaryClip
////            assert(clipData?.getItemAt(0)?.text.toString() == "https://example.com")
//        }
//    }
//
//    @Test
//    fun locationRequestFails_shouldShowSnackbar() {
//        // Arrange
//        val scenario = FragmentScenario.launchInContainer(FactFragment::class.java)
//        scenario.onFragment { fragment ->
//            // Simulate location fetch failure
//            `when`(fusedLocationClient.lastLocation).thenReturn(locationTask)
//            `when`(locationTask.addOnFailureListener(any())).thenAnswer { invocation ->
//                val listener = invocation.arguments[0] as OnFailureListener
//                listener.onFailure(Exception("Location error"))
//                locationTask
//            }
//
//            // Act & Assert
//            // Note: Precise Snackbar verification can be tricky in unit tests
//            verify(locationTask).addOnFailureListener(any())
//        }
//    }
//
//    @Test
//    fun shareWhatsAppButtonShouldCreateShareIntent() {
//        // Arrange
//        val scenario = FragmentScenario.launchInContainer(FactFragment::class.java)
//        scenario.onFragment { fragment ->
////            val mockFact = mock(Fact::class.java)
////            `when`(mockFact.url).thenReturn("https://example.com")
////            `when`(mockFact.fact).thenReturn("Test Fact")
////            `when`(mockFact.dateInsert).thenReturn("2023-01-01")
////            `when`(mockFact.organization).thenReturn("Test Org")
////            `when`(mockFact.resource).thenReturn("Test Resource")
////            `when`(mockFact.operations).thenReturn("Test Operations")
////            `when`(mockFact.columns).thenReturn("Test Columns")
//
//            fragment.viewModel = factViewModel
////            `when`(factViewModel.fact.value).thenReturn(mockFact)
//
//            // Act
//            fragment.binding.buttonShareWhatsApp.performClick()
//
//            // Assert
//            val shadowActivity = shadowOf(fragment.requireActivity())
//            val startedIntent = shadowActivity.nextStartedActivity
//            assert(startedIntent.type == "text/plain")
//            assert(startedIntent.getPackage() == "com.whatsapp")
//        }
//    }
//}