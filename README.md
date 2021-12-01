## Spring Validation Example
### See: MethodConstraintControllerTest

This project shows how to use a method constraint validator with Spring.


The `ConformingMethodConstraintController` uses a parameter object that contains a method named `isValidDateRange`, which conforms to the JavaBean spec.

The `NonConformingMethodConstraintController` uses a parameter object that contains a method named `shouldBeValidDateRange`, which does not conform to the JavaBean spec.
