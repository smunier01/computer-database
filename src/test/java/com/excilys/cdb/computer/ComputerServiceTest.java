package com.excilys.cdb.computer;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.util.PageParameters;

public class ComputerServiceTest {

    private ComputerService service = ComputerService.getInstance();

    @Test
    public void testSingleton() {
        ComputerService cs1 = ComputerService.getInstance();

        assertTrue(cs1 == service);
    }

    @Test
    public void testCreateComputer() throws ServiceException {

        Computer c = service.createComputer("MyComputer", null, null, null);

        assertNotNull(c);

        assertTrue(c.getId() > 0);

        // clean up

        service.deleteComputer(c.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateComputer2() throws ServiceException {
        Computer c = service.createComputer("", null, null, null);

        // if it somehow did work... do some clean up
        if ((c != null) && (c.getId() > 0)) {
            service.deleteComputer(c.getId());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputerError1() throws ServiceException {
        service.getComputer(-1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputerError2() throws ServiceException {
        service.getComputer(0L);
    }

    @Test
    public void testGetComputerNull() throws ServiceException {
        service.deleteComputer(1L);
        Computer c = service.getComputer(1L);
        assertNull(c);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputersError1() throws ServiceException {
        PageParameters p = new PageParameters(0, 0);

        service.getComputers(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputersError2() throws ServiceException {
        PageParameters p = new PageParameters(10, -10);

        service.getComputers(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputersError3() throws ServiceException {
        PageParameters p = new PageParameters(-10, 0);

        service.getComputers(p);
    }

    @Test
    public void testCount() throws ServiceException {
        long a = service.countComputers();

        assertTrue(a > 0);

        // create a computer

        Computer c = service.createComputer("MyComputer", null, null, null);

        assertNotNull(c);

        assertTrue(c.getId() > 0);

        // count again

        long b = service.countComputers();

        assertEquals(a, b - 1);

        // clean up

        service.deleteComputer(c.getId());
    }

    @Test
    public void testGetComputers1() throws ServiceException {
        PageParameters p = new PageParameters(10, 0);

        List<Computer> computers = service.getComputers(p);

        assertEquals(10, computers.size());

    }

    @Test
    public void testGetComputers2() throws ServiceException {
        PageParameters p = new PageParameters(10, 1);

        List<Computer> computers = service.getComputers(p);

        assertEquals(10, computers.size());

    }
}
