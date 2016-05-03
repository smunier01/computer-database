package com.excilys.cdb.computer;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.service.impl.ComputerService;
import com.excilys.cdb.util.PageParameters;

public class ComputerServiceTest {

    private final ComputerService service = ComputerService.getInstance();

    @Test
    public void testSingleton() {
        final ComputerService cs1 = ComputerService.getInstance();

        Assert.assertTrue(cs1 == this.service);
    }

    @Test
    public void testCreateComputer() throws ServiceException {

        final Computer c = this.service.createComputer(new Computer.ComputerBuilder().name("DefaultName").build());

        Assert.assertNotNull(c);

        Assert.assertTrue(c.getId() > 0);

        // clean up

        this.service.deleteComputer(c.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateComputer2() throws ServiceException {
        final Computer c = this.service.createComputer(new Computer.ComputerBuilder().name("").build());

        // if it somehow did work... do some clean up
        if ((c != null) && (c.getId() > 0)) {
            this.service.deleteComputer(c.getId());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputerError1() throws ServiceException {
        this.service.getComputer(-1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputerError2() throws ServiceException {
        this.service.getComputer(0L);
    }

    @Test
    public void testGetComputerNull() throws ServiceException {
        this.service.deleteComputer(1L);
        final Computer c = this.service.getComputer(1L);
        Assert.assertNull(c);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputersError1() throws ServiceException {
        final PageParameters p = Mockito.mock(PageParameters.class);
        Mockito.when(p.getPageNumber()).thenReturn(0L);
        Mockito.when(p.getSize()).thenReturn(0L);

        this.service.getComputers(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputersError2() throws ServiceException {

        final PageParameters p = Mockito.mock(PageParameters.class);
        Mockito.when(p.getPageNumber()).thenReturn(10L);
        Mockito.when(p.getSize()).thenReturn(-10L);

        this.service.getComputers(p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputersError3() throws ServiceException {

        final PageParameters p = Mockito.mock(PageParameters.class);
        Mockito.when(p.getPageNumber()).thenReturn(0L);
        Mockito.when(p.getSize()).thenReturn(-10L);

        this.service.getComputers(p);
    }

    @Test
    public void testCount() throws ServiceException {

        final PageParameters p = Mockito.mock(PageParameters.class);
        Mockito.when(p.getPageNumber()).thenReturn(0L);
        Mockito.when(p.getSize()).thenReturn(10L);

        final long a = this.service.countComputers(p);

        Assert.assertTrue(a > 0);

        // create a computer

        final Computer c = this.service.createComputer(new Computer.ComputerBuilder().name("DefaultName").build());

        Assert.assertNotNull(c);

        Assert.assertTrue(c.getId() > 0);

        // count again

        final long b = this.service.countComputers(p);

        Assert.assertEquals(a, b - 1);

        // clean up

        this.service.deleteComputer(c.getId());
    }

    @Test
    public void testGetComputers1() throws ServiceException {

        final PageParameters p = Mockito.mock(PageParameters.class);
        Mockito.when(p.getPageNumber()).thenReturn(0L);
        Mockito.when(p.getSize()).thenReturn(10L);

        final List<Computer> computers = this.service.getComputers(p);

        Assert.assertEquals(10, computers.size());

    }

    @Test
    public void testGetComputers2() throws ServiceException {

        final PageParameters p = Mockito.mock(PageParameters.class);
        Mockito.when(p.getPageNumber()).thenReturn(1L);
        Mockito.when(p.getSize()).thenReturn(10L);

        final List<Computer> computers = this.service.getComputers(p);

        Assert.assertEquals(10, computers.size());

    }
}
