# Mockito App Learning Project

## Overview

This project is dedicated to learning and implementing Mockito, a popular Java mocking framework. It covers various techniques for creating mock objects, verifying method calls, and handling different behaviors in unit tests.

## Technologies Used

- **Java** (Latest version supported by Mockito)
- **Mockito** (Mocking framework for unit testing)
- **JUnit 5** (For running tests)
- **Maven** (Build tool)

## Project Structure

```
/ src
  / main
    / java
      / com.ewch.mockito.app
  / test
    / java
      / com.ewch.mockito.app
```

## Mockito Features and Best Practices Covered

### 1. Setting Up Mocks
- Using `mock(Class<T> classToMock)`: Creates a mock instance of a given class.
- Injecting mock dependencies into service classes for better isolation.

### 2. Stubbing Behavior
- Using `when().thenReturn()`: Defines return values for method calls.
- Using `when().thenThrow()`: Simulates exceptions in methods.
- Handling method parameters dynamically using `any()`, `anyInt()`, `anyLong()`, etc.

### 3. Verifying Interactions
- Using `verify(mock).method()`: Ensures a method was called on a mock object.
- Using `verify(mock, times(n))`: Confirms a method was called a specific number of times.
- Using `verifyNoMoreInteractions(mock)`: Ensures no unexpected method calls.

### 4. Argument Matchers
- Using `eq()`, `any()`, `argThat()`: Validating method arguments during verification.

### 5. Mocking Void Methods
- Using `doNothing().when(mock).voidMethod()`: Prevents actual execution.
- Using `doThrow().when(mock).voidMethod()`: Forces exceptions.

### 6. Spying on Real Objects
- Using `spy(Class<T> classToSpyOn)`: Wraps real objects to partially mock behavior.
- Overriding specific method calls while retaining real method execution.

### 7. Resetting Mocks
- Using `reset(mock)`: Clears all previous interactions.
- Using `clearInvocations(mock)`: Resets only invocation history.

### 8. Exception Handling
- Mocking and verifying exception flows in service methods.

## Running the Tests

To execute the tests, use the following Maven command:

```sh
mvn test
```
