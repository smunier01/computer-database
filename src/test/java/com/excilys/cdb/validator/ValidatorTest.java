package com.excilys.cdb.validator;

import static org.junit.Assert.*;

import java.util.stream.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.validation.ValidatorUtil;

@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ValidatorTest {

    @Autowired
    private ValidatorUtil v;

    @Ignore
    @Test
    public void testValidateIntAllTrue() {
        Stream<String> s = Stream.of("1", "01", "15480", "0002", "0088471598154000");

        boolean a = s.allMatch(this.v::isIdValid);

        assertTrue(a);
    }

    @Ignore
    @Test
    public void testValidateIntAllFalse() {
        Stream<String> s = Stream.of("a", "0", "-1", "000", "1.2", "1,2", "15a45");

        boolean a = s.anyMatch(this.v::isIdValid);

        assertFalse(a);
    }

    @Test
    public void testValidateDateAllTrue() {
        Stream<String> s = Stream.of("1991-01-02", "2010-1-4", "1984-12-1");

        boolean a = s.allMatch(this.v::isDateValid);

        assertTrue(a);
    }

    @Test
    public void testValidateDateAllFalse() {
        Stream<String> s = Stream.of("1800-01-02", "1991/01/02", "2100-05-08", "1991-13-01", "1991-12-00",
                "1991-12-32");

        boolean a = s.anyMatch(this.v::isDateValid);

        assertFalse(a);
    }
}
