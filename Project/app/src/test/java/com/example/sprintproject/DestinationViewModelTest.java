package com.example.sprintproject;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.sprintproject.viewmodel.DestinationViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DestinationViewModelTest {

    private DestinationViewModel viewModel;
    private FirebaseAuth mockAuth;
    private FirebaseFirestore mockDb;

    @BeforeEach
    public void setUp() {
        mockAuth = mock(FirebaseAuth.class);
        mockDb = mock(FirebaseFirestore.class);

        // Create an instance of DestinationViewModel with mocked dependencies
        viewModel = new DestinationViewModel();
        viewModel.mAuth = mockAuth;  // Inject the mock FirebaseAuth instance
        viewModel.db = mockDb;       // Inject the mock FirebaseFirestore instance
    }

    @Test
    public void testAddDestination_withStartDateAfterEndDate_shouldReturnFalse() {
        // Arrange
        String location = "Paris";
        LocalDate startDate = LocalDate.of(2024, 12, 10);
        LocalDate endDate = LocalDate.of(2024, 12, 5); // End date is before start date

        // Act
        boolean result = viewModel.addDestination(location, startDate, endDate);

        // Assert
        assertFalse(result, "addDestination should return false if startDate is after endDate");
    }

    @Test
    public void testAddDestination_withValidDates_shouldReturnTrue() {
        // Arrange
        String location = "Paris";
        LocalDate startDate = LocalDate.of(2024, 12, 5);
        LocalDate endDate = LocalDate.of(2024, 12, 10); // End date is after start date

        // Act
        boolean result = viewModel.addDestination(location, startDate, endDate);

        // Assert
        assertTrue(result, "addDestination should return true for valid dates");
    }
}
