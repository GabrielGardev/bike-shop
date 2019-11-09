package bikeshop.domain.models.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static bikeshop.common.Constants.*;

public class UserRegisterBindingModel {

    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String email;

    @NotNull(message = NULL_USERNAME_MESSAGE)
    @NotEmpty(message = EMPTY_USERNAME_MESSAGE)
    @Length(min = 3, max = 15, message = INVALID_USERNAME_LENGTH_MESSAGE)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = NULL_PASSWORD_MESSAGE)
    @NotEmpty(message = EMPTY_PASSWORD_MESSAGE)
    @Length(min = 4, max = 20, message = INVALID_PASSWORD_LENGTH_MESSAGE)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = NULL_PASSWORD_MESSAGE)
    @NotEmpty(message = EMPTY_PASSWORD_MESSAGE)
    @Length(min = 4, max = 20, message = INVALID_PASSWORD_LENGTH_MESSAGE)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull(message = NULL_FIRST_NAME_MESSAGE)
    @NotEmpty(message = EMPTY_FIRST_NAME_MESSAGE)
    @Length(min = 2, message = INVALID_FIRST_NAME_LENGTH_MESSAGE)
    @Pattern(regexp = "^[A-Z][a-zA-Z]+", message = INVALID_FIRST_NAME_CAPITAL_CASE_MESSAGE)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull(message = NULL_LAST_NAME_MESSAGE)
    @NotEmpty(message = EMPTY_LAST_NAME_MESSAGE)
    @Length(min = 2, message = INVALID_LAST_NAME_LENGTH_MESSAGE)
    @Pattern(regexp = "^[A-Z][a-zA-Z]+", message = INVALID_LAST_NAME_CAPITAL_CASE_MESSAGE)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull(message = NULL_EMAIL_MESSAGE)
    @NotEmpty(message = EMPTY_EMAIL_MESSAGE)
    @Pattern(regexp = EMAIL_PATTERN_STRING, message = INVALID_EMAIL_MESSAGE)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
