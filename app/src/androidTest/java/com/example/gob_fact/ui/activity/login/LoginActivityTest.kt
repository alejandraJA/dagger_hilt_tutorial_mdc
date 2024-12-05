package com.example.gob_fact.ui.activity.login

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gob_fact.ui.activity.main.MainActivity
import com.example.gob_fact.ui.activity.singup.SingInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.Shadows.shadowOf

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginViewModel: LoginViewModel

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    @Mock
    private lateinit var firebaseUser: FirebaseUser

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun when_biometrics_are_disabled_fingerprint_views_should_be_gone() {
        // Arrange
        `when`(loginViewModel.isBiometricDisabled).thenReturn(true)

        // Act
        val scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.onActivity { activity ->
            // Assert
            val binding = activity.binding
            assert(binding.imageFingerprint.visibility == View.GONE)
            assert(binding.textFingerprint.visibility == View.GONE)
            assert(binding.textOr.visibility == View.GONE)
        }
    }

    @Test
    fun when_user_is_not_logged_in_navigate_to_sign_in_activity() {
        // Arrange
        `when`(loginViewModel.userName.value).thenReturn(false)

        // Act
        val scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.onActivity { activity ->
            // Assert
            val shadowActivity = shadowOf(activity)
            val startedIntent = shadowActivity.nextStartedActivity
            assert(startedIntent.component?.className == SingInActivity::class.java.name)
        }
    }

    @Test
    fun when_current_user_exists_navigate_to_main_activity() {
        // Arrange
        `when`(firebaseAuth.currentUser).thenReturn(firebaseUser)

        // Act
        val scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.onActivity { activity ->
            // Assert
            val shadowActivity = shadowOf(activity)
            val startedIntent = shadowActivity.nextStartedActivity
            assert(startedIntent.component?.className == MainActivity::class.java.name)
        }
    }

    @Test
    fun login_with_valid_credentials_should_navigate_to_main_activity() {
        // Arrange
        val scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.onActivity { activity ->
            val binding = activity.binding
            binding.inputEmail.setText("test@example.com")
            binding.inputPassword.setText("password123")

            `when`(loginViewModel.login(
                userName = "test@example.com",
                password = "password123"
            )).thenReturn(true)

            // Act
            binding.buttonLogin.performClick()

            // Assert
            val shadowActivity = shadowOf(activity)
            val startedIntent = shadowActivity.nextStartedActivity
            assert(startedIntent.component?.className == MainActivity::class.java.name)
        }
    }

    @Test
    fun login_with_invalid_credentials_should_not_navigate() {
        // Arrange
        val scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.onActivity { activity ->
            val binding = activity.binding
            binding.inputEmail.setText("test@example.com")
            binding.inputPassword.setText("wrongpassword")

            `when`(loginViewModel.login(
                userName = "test@example.com",
                password = "wrongpassword"
            )).thenReturn(false)

            // Act
            binding.buttonLogin.performClick()

            // Assert
            val shadowActivity = shadowOf(activity)
            val startedIntent = shadowActivity.nextStartedActivity
            assert(startedIntent == null)
        }
    }

}