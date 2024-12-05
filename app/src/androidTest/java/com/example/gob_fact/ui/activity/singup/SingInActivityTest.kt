package com.example.gob_fact.ui.activity.singup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.biometric.BiometricPrompt
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.gob_fact.ui.activity.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.Shadows.shadowOf

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SingInActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var singInViewModel: SingInViewModel

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    @Mock
    private lateinit var firebaseUser: FirebaseUser

    @Mock
    private lateinit var googleSignInClient: GoogleSignInClient

    @Mock
    private lateinit var googleSignInAccount: GoogleSignInAccount

    @Mock
    private lateinit var googleSignInTask: Task<GoogleSignInAccount>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun sing_in_with_email_and_password_without_biometric_should_navigate_to_main_activity() {
        // Arrange
        val scenario = ActivityScenario.launch(SingInActivity::class.java)
        scenario.onActivity { activity ->
            val binding = activity.binding
            binding.inputEmail.setText("test@example.com")
            binding.inputPassword.setText("password123")
            binding.checkboxEnableBiometric.isChecked = false

            `when`(
                singInViewModel.singIn(
                    userName = "test@example.com",
                    password = "password123"
                )
            ).thenReturn(Unit)

            // Act
            binding.buttonSingIn.performClick()

            // Assert
            val shadowActivity = shadowOf(activity)
            val startedIntent = shadowActivity.nextStartedActivity
            assert(startedIntent.component?.className == MainActivity::class.java.name)
        }
    }

    @Test
    fun sign_in_with_email_and_password_with_biometric_should_trigger_biometric_authentication() {
        // Arrange
        val scenario = ActivityScenario.launch(SingInActivity::class.java)
        scenario.onActivity { activity ->
            val binding = activity.binding
            binding.inputEmail.setText("test@example.com")
            binding.inputPassword.setText("password123")
            binding.checkboxEnableBiometric.isChecked = true

            // Act
            binding.buttonSingIn.performClick()

            // Assert
            // Note: Fully testing BiometricPrompt requires more complex setup
            // This test verifies the basic flow of triggering biometric authentication
            verify(singInViewModel, never()).singIn(any(), any())
        }
    }

    @Test
    fun google_sign_in_should_authenticate_and_navigate_to_main_activity() {
        // Arrange
        val scenario = ActivityScenario.launch(SingInActivity::class.java)
        scenario.onActivity { activity ->
            `when`(googleSignInTask.getResult(any<Class<Exception>>())).thenReturn(
                googleSignInAccount
            )
            `when`(googleSignInAccount.idToken).thenReturn("mockIdToken")
            `when`(googleSignInAccount.email).thenReturn("test@example.com")
            `when`(googleSignInAccount.displayName).thenReturn("Test User")

            // Act
            activity.signIn()

            // Assert
            val shadowActivity = shadowOf(activity)
            val startedIntent = shadowActivity.nextStartedActivity
            assert(startedIntent.component?.className == MainActivity::class.java.name)
        }
    }

    @Test
    fun biometric_authentication_failure_should_fall_back_to_password_sign_in() {
        // Arrange
        val scenario = ActivityScenario.launch(SingInActivity::class.java)
        scenario.onActivity { activity ->
            val binding = activity.binding
            binding.inputEmail.setText("test@example.com")
            binding.inputPassword.setText("password123")
            binding.checkboxEnableBiometric.isChecked = true

            // Simulate authentication error
            val authCallback = mock(BiometricPrompt.AuthenticationCallback::class.java)
            authCallback.onAuthenticationError(
                BiometricPrompt.ERROR_NEGATIVE_BUTTON,
                "Use account password"
            )

            // Assert
            verify(singInViewModel).singIn(
                userName = "test@example.com",
                password = "password123"
            )
        }
    }

}