package org.com.app;

public enum ExitCode {

    SUCCESS(0),
    MISSING_FILE(1),
    INCORRECT_FILE_EXTENSION(2),
    INCORRECT_FILE_SIZE(3),
    DUPLICATE_DIGIT(4);

    private final int error_code;

    ExitCode(final int value) {
        error_code = value;
    }
    public int getValue() {
        return error_code;
    }

}
