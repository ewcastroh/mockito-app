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
- Using `verifyNoInteractions(mock)`: Ensures no method calls were made. 
- Using `verifyNoMoreInteractions(mock)`: Ensures no unexpected method calls.
- Using `verifyZeroInteractions(mock)`: Ensures no method calls were made.
- Using `verify(mock, never()).method()`: Ensures a method was never called.
- Using `verify(mock, atLeast(n)).method()`: Ensures a method was called at least n times.
- Using `verify(mock, atLeastOnce()).method()`: Ensures a method was called at least once.
- Using `verify(mock, atMost(n)).method()`: Ensures a method was called at most n times.
- Using `verify(mock, atMostOnce(n)).method()`: Ensures a method was called at most once.
- Using `verify(mock, timeout(n)).method()`: Ensures a method was called within n milliseconds.
- Using `verify(mock, timeout(n).times(m)).method()`: Ensures a method was called within n milliseconds m times.
- Using `verify(mock, timeout(n).atLeast(m)).method()`: Ensures a method was called within n milliseconds at least m times.

### 4. Argument Matchers
- Using `eq()`, `any()`, `argThat()`: Validating method arguments during verification.
- Using `anyInt()`, `anyLong()`, `anyString()`: Matching specific argument types.
- Using `argThat()`: Customizing argument matching with a lambda expression.
- Using `rgumentMatcher<T>()`: Customizing argument matching with a custom matcher.

### 5. Mocking Void Methods
- Using `doNothing().when(mock).voidMethod()`: Prevents actual execution.
- Using `doThrow().when(mock).voidMethod()`: Forces exceptions.

### 6. Spying on Real Objects
- Using `spy(Class<T> classToSpyOn)`: Wraps real objects to partially mock behavior.
- Overriding specific method calls while retaining real method execution.
- Using `doReturn().when(spy).method()`: Customizing behavior for specific methods.
- Using `doCallRealMethod().when(spy).method()`: Invoking the real method from a spy object.
- Using `doNothing().when(spy).voidMethod()`: Preventing actual execution of void methods.
- Using `doThrow().when(spy).voidMethod()`: Forcing exceptions in void methods.
- Using `doAnswer().when(spy).method()`: Defining custom behavior for method calls.
- Using `@Spy`: Annotating a field to spy on real objects.
- Using `@Spy` with `@InjectMocks`: Spying on real objects and injecting mocks.

### 7. Resetting Mocks
- Using `reset(mock)`: Clears all previous interactions.
- Using `clearInvocations(mock)`: Resets only invocation history.

### 8. Exception Handling
- Mocking and verifying exception flows in service methods.
- Using `assertThrows()`: Validating exceptions in JUnit 5 tests.
- Using `doThrow().when(mock).method()`: Simulating exceptions in void methods.
- Using `when().thenThrow()`: Simulating exceptions in method calls.

### 9. Injecting Dependencies
- Allowing dependency injection by annotations into tested class
  1. Using `MockitoAnnotations.openMocks(this);` method in setUp method (`@BeforeEach`).
  2. Annotating Test class using `@ExtendWith(MockitoExtension.class)`
- Using `@Mock`: Annotation for every mocked dependency to be injected in tested class.
- Using `@InjectMocks`: Annotation for tested class that instantiates it and inject mocks.

### 11. Answering Behavior
- Using `Answer<T>`: Customizing behavior for method calls.
- Using `doAnswer()`: Defining custom behavior for void methods.
- Using `doCallRealMethod()`: Invoking the real method from a spy object.
- Using `thenAnswer()`: Defining custom behavior for method calls.
- Using `then()` with `thenReturn()`: Defining custom behavior for method calls.
- Using `InvocationOnMock`: Extracting arguments from method calls.
- Using `invocation.getArgument(index)`: Extracting specific arguments from method calls.
- Using `invocation.getArguments()`: Extracting all arguments from method calls.
- Using `invocation.getMock()`: Extracting the mock object from method calls.
- Using `invocation.getMethod()`: Extracting the method from method calls.
- Using `invocation.getMock().method()`: Invoking a method on a mock object.

### 11. Capturing Arguments
- Using `@Captor`: Annotating a field to capture arguments.
- Using `ArgumentCaptor<T>`: Captures arguments passed to a method.
- Using `verify(mock).method(captor.capture())`: Extracts captured arguments for validation.
- Using `captor.getAllValues()`: Retrieves all captured values.
- Using `captor.getValue()`: Retrieves the last captured value.
- Using `captor.getAllValues().get(index)`: Retrieves a specific captured value.
- Using `captor.capture()`: Captures arguments for verification.
- Using `captor.capture()` with `verify(mock).method(captor.capture())`: Captures arguments for verification.
- Using `captor.capture()` with `verify(mock, times(n)).method(captor.capture())`: Captures arguments for verification.

### 12. InOrder Verification
- Using `InOrder`: Verifying method calls in a specific order.
- Using `inOrder.verify(mock1).method1()`: Ensures method1 was called before method2.

## Running the Tests

To execute the tests, use the following Maven command:

```sh
mvn test
```
