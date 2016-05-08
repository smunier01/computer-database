package com.excilys.cdb.computer;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.PageParameters;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.service.impl.ComputerService;

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

    @Test
    public void testCountCreateThreadSafe() throws ServiceException {

        PageParameters p = new PageParameters.Builder().size(10).pageNumber(0).build();

        List<Computer> created = new ArrayList<>();
        Computer tmp;

        // start by counting the number of computers

        long firstCount = this.service.countComputers(p);

        for (int i = 0; i < 100; i++) {
            tmp = new Computer.ComputerBuilder().name("test").build();
            created.add(tmp);
        }

        created.stream().parallel().forEach(this.service::createComputer);

        Assert.assertEquals(100, created.size());

        // should be 100 more computer inside now

        long secondCount = this.service.countComputers(p);

        Assert.assertEquals(firstCount + 100, secondCount);

        // delete all using parallel stream

        created.stream().parallel().forEach(c -> this.service.deleteComputer(c.getId()));

        // count should be equal to the first one ..

        long thirdCount = this.service.countComputers(p);

        Assert.assertEquals(firstCount, thirdCount);
    }

    @Test
    public void testCountDeleteThreadSafe() throws ServiceException {

        PageParameters p = new PageParameters.Builder().size(10).pageNumber(0).build();

        List<Long> created = new ArrayList<>();
        Computer tmp;

        // start by counting the number of computers

        long firstCount = this.service.countComputers(p);

        for (int i = 0; i < 100; i++) {
            tmp = new Computer.ComputerBuilder().name("test").build();
            this.service.createComputer(tmp);
            Assert.assertTrue(tmp.getId() > 0);
            created.add(tmp.getId());
        }

        Assert.assertEquals(100, created.size());

        // should be 100 more computer inside now

        long secondCount = this.service.countComputers(p);

        Assert.assertEquals(firstCount + 100, secondCount);

        // delete all using parallel stream

        created.stream().parallel().forEach(this.service::deleteComputer);

        // count should be equal to the first one ..

        long thirdCount = this.service.countComputers(p);

        Assert.assertEquals(firstCount, thirdCount);
    }
}
