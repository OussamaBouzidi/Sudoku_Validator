import org.com.app.ExitCode;
import org.com.app.Validator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;



public class ValidatorTest {


    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();


    @Test
    public void test_checkLine_invalid()  {
        exit.expectSystemExitWithStatus(ExitCode.DUPLICATE_DIGIT.getValue());
        Validator.checkLine(2,"2,5,8,1,6,9,3,3,7","Vertically");
    }

    @Test
    public void test_non_existant_ext()  {
        exit.expectSystemExitWithStatus(ExitCode.INCORRECT_FILE_EXTENSION.getValue());
        Validator.main(new String[] { "src/test/resources/validSudoku.qwe" });
    }

    @Test
    public void test_non_existant_file()  {
        exit.expectSystemExitWithStatus(ExitCode.MISSING_FILE.getValue());
        Validator.main(new String[] { "src/test/resources/validSudoku" });
    }

    @Test
    public void test_wrong_file_format()  {
        exit.expectSystemExitWithStatus(ExitCode.INCORRECT_FILE_SIZE.getValue());
        Validator.main(new String[] { "src/test/resources/invalidSudoku8X9.txt" });
    }
    @Test
    public void test_uplicateDigit_vertically()  {
        exit.expectSystemExitWithStatus(ExitCode.DUPLICATE_DIGIT.getValue());
        Validator.main(new String[] { "src/test/resources/invalidSudokuVertically.txt" });
    }
    @Test
    public void test_uplicateDigit_horizantally()  {
        exit.expectSystemExitWithStatus(ExitCode.DUPLICATE_DIGIT.getValue());
        Validator.main(new String[] { "src/test/resources/invalidSudokuVertically.txt"});
    }
    @Test
    public void test_valid_sudoku()  {
        exit.expectSystemExitWithStatus(ExitCode.SUCCESS.getValue());
        Validator.main(new String[] { "src/test/resources/validSudoku.txt" });
    }
}
