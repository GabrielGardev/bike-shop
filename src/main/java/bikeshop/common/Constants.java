package bikeshop.common;

public final class Constants {

    //roles
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_ROOT = "ROLE_ROOT";

    //error massages
    public static final String USERNAME_NOT_FOUND = "Username not found!";
    public static final String PASSWORDS_DONT_MATCH = "Old password dont match current password!";
    public static final String INCORRECT_ID = "Incorrect id!";
    public static final String INCORRECT_CATEGORY = "Incorrect category name!";
    public static final String INCORRECT_SIZE_NAME = "Incorrect bicycle size name!";
    public static final String INCORRECT_AUTHORITY = "Incorrect authority name!";

    public static final String DUPLICATE_USERNAME = "Username already exist!";
    public static final String DUPLICATE_EMAIL = "Email already exist!";
    public static final String DUPLICATE_BICYCLE = "Bicycle already exist!";
    public static final String DUPLICATE_CATEGORY = "Category already exist!";
    public static final String DUPLICATE_BICYCLE_SIZE = "Bicycle size already exist!";

    //LocalDateTime format pattern
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    //validation
    public static final String EMAIL_PATTERN_STRING ="^((\"[\\w-\\s]+\")|([\\w-]+(?:\\.[\\w-]+)*)|(\"[\\w-\\s]+\")([\\w-]+(?:\\.[\\w-]+)*))(@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)|(@\\[?((25[0-5]\\.|2[0-4][0-9]\\.|1[0-9]{2}\\.|[0-9]{1,2}\\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\]?$)";
    public static final String INVALID_EMAIL_MESSAGE = "Invalid email.";
    public static final String NULL_EMAIL_MESSAGE = "Email cannot be null.";
    public static final String EMPTY_EMAIL_MESSAGE = "Email cannot be empty.";

    public static final String INVALID_USERNAME_LENGTH_MESSAGE = "Username must be between 3 and 15 characters long.";
    public static final String NULL_USERNAME_MESSAGE = "Username cannot be null.";
    public static final String EMPTY_USERNAME_MESSAGE = "Username cannot be empty.";

    public static final String INVALID_PASSWORD_LENGTH_MESSAGE = "Password must be between 4 and 20 symbols long.";
    public static final String NULL_PASSWORD_MESSAGE = "Password cannot be null.";
    public static final String EMPTY_PASSWORD_MESSAGE = "Password cannot be empty.";

    public static final String INVALID_FIRST_NAME_LENGTH_MESSAGE = "First name must be at least 2 symbols long.";
    public static final String INVALID_FIRST_NAME_CAPITAL_CASE_MESSAGE = "First name must start with capital letter.";
    public static final String NULL_FIRST_NAME_MESSAGE = "First name cannot be null.";
    public static final String EMPTY_FIRST_NAME_MESSAGE = "First name cannot be empty.";

    public static final String INVALID_LAST_NAME_LENGTH_MESSAGE = "Last name must be at least 2 symbols long.";
    public static final String INVALID_LAST_NAME_CAPITAL_CASE_MESSAGE = "Last name must start with capital letter.";
    public static final String NULL_LAST_NAME_MESSAGE = "Last name cannot be null.";
    public static final String EMPTY_LAST_NAME_MESSAGE = "Last name cannot be empty.";

    public static final String INVALID_CATEGORY_LENGTH_MESSAGE = "Category must be between 2 and 15 symbols long.";
    public static final String INVALID_CATEGORY_CAPITAL_CASE_MESSAGE = "Category must be only with capital letters.";
    public static final String NULL_CATEGORY_MESSAGE = "Category cannot be null.";
    public static final String EMPTY_CATEGORY_MESSAGE = "Category cannot be empty.";

    public static final String INVALID_BICYCLE_SIZE_LENGTH_MESSAGE = "Bicycle size must be max 2 letters.";
    public static final String INVALID_BICYCLE_SIZE_CAPITAL_CASE_MESSAGE = "Bicycle size must be only with capital letters.";
    public static final String NULL_BICYCLE_SIZE_MESSAGE = "Bicycle size cannot be null.";
    public static final String EMPTY_BICYCLE_SIZE_MESSAGE = "Bicycle size cannot be empty.";
}
