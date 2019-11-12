package com.k15t.pat;

import com.k15t.pat.model.User;
import org.junit.Before;
import org.junit.Test;

import javax.validation.*;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {
    private static final String PHONE_NUMBER_NOT_CORRECT = "Phone number not in the correct format! Ex: +491739341284";
    private static final String PASSWORD_SHOULD_BE_MIN_8_CHARACTERS = "Password should be min 8 characters!";
    private static final String NAME_SHOULD_CONTAINS_ONLY_LETTERS_NO_NUMBERS_OR_SPECIAL_CHARACTERS = "Name should contains only letters, no numbers or special characters!";
    private static final String NAME_LENGTH_RANGE = "length must be between 2 and 2147483647";
    private static final String WRONG_EMAIL = "Wrong email!";
    private static final String PLEASE_FILL_IN_THE_EMAIL = "Please fill in the email";
    private static final String PLEASE_FILL_IN_THE_ADDRESS = "Please fill in the address";
    private static final String PLEASE_FILL_IN_A_PASSWORD = "Please fill in a password";
    private static final String PLEASE_FILL_IN_THE_NAME = "Please fill in the name";
    private static Validator validator;

    @Before
    public void setup() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void createNewUser_thenNoErrors() {
        User user = new User();
        user.setName("Name");
        user.setEmail("user@test.com");
        user.setAddress("address");
        user.setPassword("12345678");

        Optional<ConstraintViolation<User>> violation = validator.validate(user).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }

    @Test
    public void whenCreatingWithNameTooShort_thenValidationErrorsAreThrown() {
        User user = new User();
        user.setName("a");
        user.setEmail("user@test.com");
        user.setAddress("address");
        user.setPassword("12345678");

        Optional<ConstraintViolation<User>> violation = validator.validate(user).stream().findFirst();
        assertTrue(violation.isPresent());
        assertEquals(NAME_LENGTH_RANGE, violation.get().getMessage());
    }

    @Test
    public void whenCreatingWithWrongEmail_thenValidationErrorsAreThrown() {
        User user = new User();
        user.setName("Name");
        user.setEmail("user@test");
        user.setAddress("address");
        user.setPassword("12345678");

        Optional<ConstraintViolation<User>> violation = validator.validate(user).stream().findFirst();
        assertTrue(violation.isPresent());
        assertEquals(WRONG_EMAIL, violation.get().getMessage());
    }

    @Test
    public void whenCreatingWitoutEmail_thenValidationErrorsAreThrown() {
        User user = new User();
        user.setName("Name");
        user.setAddress("address");
        user.setPassword("12345678");

        Optional<ConstraintViolation<User>> violation = validator.validate(user).stream().findFirst();
        assertTrue(violation.isPresent());
        assertEquals(PLEASE_FILL_IN_THE_EMAIL, violation.get().getMessage());

    }

    @Test
    public void whenCreatingWitoutAddress_thenValidationErrorsAreThrown() {
        User user = new User();
        user.setName("Name");
        user.setEmail("user@test.com");
        user.setPassword("12345678");

        Optional<ConstraintViolation<User>> violation = validator.validate(user).stream().findFirst();
        assertTrue(violation.isPresent());
        assertEquals(PLEASE_FILL_IN_THE_ADDRESS, violation.get().getMessage());

    }

    @Test
    public void whenCreatingWitoutPassword_thenValidationErrorsAreThrown() {
        User user = new User();
        user.setName("Name");
        user.setEmail("user@test.com");
        user.setAddress("address");

        Optional<ConstraintViolation<User>> violation = validator.validate(user).stream().findFirst();
        assertTrue(violation.isPresent());
        assertEquals(PLEASE_FILL_IN_A_PASSWORD, violation.get().getMessage());

    }

    @Test
    public void whenCreatingWitoutName_thenValidationErrorsAreThrown() {
        User user = new User();
        user.setPassword("12345678");
        user.setEmail("user@test.com");
        user.setAddress("address");

        Optional<ConstraintViolation<User>> violation = validator.validate(user).stream().findFirst();
        assertTrue(violation.isPresent());
        assertEquals(PLEASE_FILL_IN_THE_NAME, violation.get().getMessage());
    }

    @Test
    public void whenCreatingNameWithNumbers_thenValidationErrorsAreThrown() {
        User user = new User();
        user.setName("123name");
        user.setPassword("12345678");
        user.setEmail("user@test.com");
        user.setAddress("address");

        Optional<ConstraintViolation<User>> violation = validator.validate(user).stream().findFirst();
        assertTrue(violation.isPresent());
        assertEquals(NAME_SHOULD_CONTAINS_ONLY_LETTERS_NO_NUMBERS_OR_SPECIAL_CHARACTERS, violation.get().getMessage());
    }

    @Test
    public void whenCreatingPasswordLengthSmallerThan8_thenValidationErrorsAreThrown() {
        User user = new User();
        user.setName("name");
        user.setPassword("1234567");
        user.setEmail("user@test.com");
        user.setAddress("address");

        Optional<ConstraintViolation<User>> violation = validator.validate(user).stream().findFirst();
        assertTrue(violation.isPresent());
        assertEquals(PASSWORD_SHOULD_BE_MIN_8_CHARACTERS, violation.get().getMessage());
    }

    @Test
    public void whenCreatingPhoneNotCorrect_thenValidationErrorsAreThrown() {
        User user = new User();
        user.setName("name");
        user.setPassword("12345678");
        user.setEmail("user@test.com");
        user.setAddress("address");
        user.setPhone("012456777888");

        Optional<ConstraintViolation<User>> violation = validator.validate(user).stream().findFirst();
        assertTrue(violation.isPresent());
        assertEquals(PHONE_NUMBER_NOT_CORRECT, violation.get().getMessage());
    }

    @Test
    public void whenCreatingPhoneCorrect_thenNoValidationErrorsAreThrown() {
        User user = new User();
        user.setName("name");
        user.setPassword("12345678");
        user.setEmail("user@test.com");
        user.setAddress("address");
        user.setPhone("+491739341284");

        Optional<ConstraintViolation<User>> violation = validator.validate(user).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }
}
