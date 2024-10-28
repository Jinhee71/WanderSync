package com.example.sprintproject;
import static org.junit.Assert.*;
import com.example.sprintproject.viewmodel.LoginViewModel;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LoginViewModelTest {

    private LoginViewModel loginViewModel;
    private FirebaseAuth mockAuth;

    @Before
    public void setUp() {
        // Mock FirebaseAuth instance
        mockAuth = Mockito.mock(FirebaseAuth.class);

        // Inject mock FirebaseAuth instance
        loginViewModel = new LoginViewModel(mockAuth);
    }

    @Test
    public void testIsEmailValid() {
        // Test valid email formats
        assertTrue(loginViewModel.isEmailValid("test@example.com"));        // Simple valid email
        assertTrue(loginViewModel.isEmailValid("user.name+alias@domain.co")); // Complex but valid email

        // Test invalid email formats
        assertFalse(loginViewModel.isEmailValid("test@.com"));              // Missing domain name
        assertFalse(loginViewModel.isEmailValid("test@com."));              // Missing top-level domain
        assertFalse(loginViewModel.isEmailValid("test@com"));               // Incorrect format for domain
        assertFalse(loginViewModel.isEmailValid("test.com"));               // Missing '@' symbol
        assertFalse(loginViewModel.isEmailValid(""));                       // Empty string
        assertFalse(loginViewModel.isEmailValid(null));                     // Null input
    }
}
