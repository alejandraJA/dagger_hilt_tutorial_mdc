//package com.example.gob_fact.ui.fragment.main
//
//import android.view.View
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.fragment.app.testing.FragmentScenario
//import androidx.navigation.NavController
//import androidx.navigation.Navigation
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.gob_fact.R
//import com.example.gob_fact.data.datasource.database.entities.FactEntity
//import com.example.gob_fact.ui.fragment.main.adapter.FactAdapter
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
//class MainFragmentTest {
//
//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Mock
//    private lateinit var mainViewModel: MainViewModel
//
//    @Mock
//    private lateinit var navController: NavController
//
//    @Mock
//    private lateinit var factAdapter: FactAdapter
//
//    private lateinit var fragment: MainFragment
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.openMocks(this)
//    }
//
//    @Test
//    fun fragmentShouldObserveFactsFromViewModel() {
//        // Arrange
//        val scenario = FragmentScenario.launchInContainer(MainFragment::class.java)
//        scenario.onFragment { fragment ->
//            fragment.viewModel = mainViewModel
//
//            val mockFacts = listOf(
//                mock(FactEntity::class.java),
//                mock(FactEntity::class.java)
//            )
//            `when`(mainViewModel.facts.value).thenReturn(mockFacts)
//
//            // Act & Assert
//            verify(mainViewModel).loadMoreFacts(null)
//        }
//    }
//
//    @Test
//    fun recyclerViewScrollShouldTriggerLoadMoreFacts() {
//        // Arrange
//        val scenario = FragmentScenario.launchInContainer(MainFragment::class.java)
//        scenario.onFragment { fragment ->
//            fragment.viewModel = mainViewModel
//
//            val recyclerView = fragment.binding.recyclerFact
//            val layoutManager = mock(LinearLayoutManager::class.java)
//            recyclerView.layoutManager = layoutManager
//
//            // Simulate scrolling near the end of the list
//            `when`(layoutManager.findLastVisibleItemPosition()).thenReturn(95)
//            `when`(layoutManager.itemCount).thenReturn(100)
//
//            // Create a mock scroll event
//            val event = mock(RecyclerView.OnScrollListener::class.java)
//            recyclerView.dispatchNestedScroll(0, 100, 0, 0, null)
//
//            // Assert
//            verify(mainViewModel, atLeastOnce()).loadMoreFacts(null)
//        }
//    }
//
//    @Test
//    fun factItemClickShouldNavigateToFactFragment() {
//        // Arrange
//        val scenario = FragmentScenario.launchInContainer(MainFragment::class.java)
//        scenario.onFragment { fragment ->
//            // Set up navigation
//            Navigation.setViewNavController(fragment.requireView(), navController)
//
//            fragment.viewModel = mainViewModel
//
//            // Create a mock fact with an ID
//            val mockFact = mock(FactEntity::class.java)
//            `when`(mockFact.id).thenReturn("test-fact-id")
//
//            // Simulate clicking on a fact item
//            fragment.adapter.factInterface.invoke(0)
//
//            // Assert navigation
//            verify(navController).navigate(
//                eq(R.id.action_mainFragment_to_fragment_fact),
//                argThat { bundle ->
//                    bundle?.getString("fact_id") == "test-fact-id"
//                }
//            )
//        }
//    }
//
//    @Test
//    fun searchViewShouldTriggerFactSearch() {
//        // Arrange
//        val scenario = FragmentScenario.launchInContainer(MainFragment::class.java)
//        scenario.onFragment { fragment ->
//            fragment.viewModel = mainViewModel
//
//            // Simulate search query
//            val searchView = fragment.binding.searchFactsView
//            val searchQuery = "test query"
//
//            // Trigger search
//            searchView.setQuery(searchQuery, true)
//
//            // Assert
//            verify(mainViewModel).searchFacts(searchQuery)
//        }
//    }
//
//    @Test
//    fun factsListUpdateShouldUpdateAdapter() {
//        // Arrange
//        val scenario = FragmentScenario.launchInContainer(MainFragment::class.java)
//        scenario.onFragment { fragment ->
//            fragment.viewModel = mainViewModel
//
//            val mockFacts = listOf(
//                mock(FactEntity::class.java),
//                mock(FactEntity::class.java)
//            )
//            `when`(mainViewModel.facts.value).thenReturn(mockFacts)
//
//            // Act
//            fragment.viewModel.facts.value = mockFacts
//
//            // Assert
//            assert(fragment.adapter.itemCount == mockFacts.size)
//        }
//    }
//}