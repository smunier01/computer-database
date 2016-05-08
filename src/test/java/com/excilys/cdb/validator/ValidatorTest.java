package com.excilys.cdb.validator;

import static org.junit.Assert.*;

import java.util.stream.*;

import org.junit.Test;

import com.excilys.cdb.validation.Validator;

public class ValidatorTest {

    private final Validator v = Validator.getInstance();

    @Test
    public void testValidateIntAllTrue() {
        final Stream<String> s = Stream.of("1", "01", "15480", "0002", "0088471598154000");

        final boolean a = s.allMatch(this.v::isIdValid);

        assertTrue(a);
    }

    @Test
    public void testValidateIntAllFalse() {
        final Stream<String> s = Stream.of("a", "0", "-1", "000", "1.2", "1,2", "15a45");

        final boolean a = s.anyMatch(this.v::isIdValid);

        assertFalse(a);
    }

    @Test
    public void testValidateDateAllTrue() {
        final Stream<String> s = Stream.of("1991-01-02", "2010-1-4", "1984-12-1");

        final boolean a = s.allMatch(this.v::isDateValid);

        assertTrue(a);
    }

    @Test
    public void testValidateDateAllFalse() {
        final Stream<String> s = Stream.of("1800-01-02", "1991/01/02", "2100-05-08", "1991-13-01", "1991-12-00",
                "1991-12-32");

        final boolean a = s.anyMatch(this.v::isDateValid);

        assertFalse(a);
    }
}
