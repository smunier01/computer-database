package com.excilys.cdb.computer;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.PageParameters;
import com.excilys.cdb.service.impl.ComputerService;

@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ComputerDAOTest {

    @Autowired
    private ComputerDAO computerDAO;

    @Test
    public void testFindShouldHaveValidId() throws DAOException {

        Computer result = this.computerDAO.find(-1L);

        Assert.assertNull(result);

        result = this.computerDAO.find(0L);

        Assert.assertNull(result);

    }

    @Test
    @Ignore
    public void testCreateFindDelete() throws DAOException {

        // create a computer

        final Computer comp = new Computer.ComputerBuilder().name("MyComputerName").discontinued(LocalDate.now())
                .build();

        Assert.assertNotNull(comp);
        final Computer a = this.computerDAO.create(comp);
        Assert.assertNotNull(a);
        final Long id = a.getId();
        Assert.assertTrue(id > 0L);

        // find the computer by its id

        final Computer b = this.computerDAO.find(id);

        Assert.assertNotNull(b);
        Assert.assertEquals(comp.getName(), b.getName());
        Assert.assertEquals(comp, b);

        // delete the computer

        this.computerDAO.delete(b);

        // try to find it again

        final Computer c = this.computerDAO.find(id);

        Assert.assertNull(c);
    }

    @Test
    public void testUpdate() throws DAOException {

        // create a computer

        final LocalDate d1 = LocalDate.now();

        final Computer comp = new Computer.ComputerBuilder().name("MyComputerName").discontinued(d1).build();

        Assert.assertNotNull(comp);
        final Computer a = this.computerDAO.create(comp);
        Assert.assertNotNull(a);
        final Long id = a.getId();
        Assert.assertTrue(id > 0L);

        // modify the object

        final LocalDate d2 = LocalDate.now().plusYears(1);

        a.setName("NewComputerName");
        a.setIntroduced(d2);
        a.setDiscontinued(null);

        // update the db

        final Computer b = this.computerDAO.update(a);

        Assert.assertEquals(b, a);
        Assert.assertEquals(id, b.getId());

        // retrieve from db

        final Computer d = this.computerDAO.find(id);

        Assert.assertNotNull(d);
        Assert.assertEquals("NewComputerName", d.getName());
        Assert.assertNull(d.getDiscontinued());
        Assert.assertEquals(d2, d.getIntroduced());

        // delete clean up

        this.computerDAO.delete(d);

        // test the delete

        final Computer e = this.computerDAO.find(id);

        Assert.assertNull(e);
    }

    @Test
    public void testFindAll() throws DAOException {
        final List<Computer> computers = this.computerDAO.findAll();

        Assert.assertNotNull(computers);
        Assert.assertTrue(computers.size() > 0);
    }

    @Test
    public void testFindAllWithCreate() throws DAOException {

        // count the number of computers with findAll

        final List<Computer> computers1 = this.computerDAO.findAll();

        Assert.assertNotNull(computers1);
        Assert.assertTrue(computers1.size() > 0);

        final long sizeA = computers1.size();

        // create a computer

        final LocalDate d1 = LocalDate.now();

        final Computer comp = new Computer.ComputerBuilder().name("MyComputerName").discontinued(d1).build();

        Assert.assertNotNull(comp);
        final Computer a = this.computerDAO.create(comp);
        Assert.assertNotNull(a);
        final Long id = a.getId();
        Assert.assertTrue(id > 0L);

        // count again, and compare the sizes

        final List<Computer> computers2 = this.computerDAO.findAll();

        Assert.assertNotNull(computers2);
        Assert.assertTrue(computers2.size() > 0);

        final long sizeB = computers2.size();

        Assert.assertEquals(sizeA + 1L, sizeB);

        // delete clean up

        this.computerDAO.delete(comp);

        // count and compare sizes

        final List<Computer> computers3 = this.computerDAO.findAll();

        Assert.assertNotNull(computers3);
        Assert.assertTrue(computers3.size() > 0);

        final long sizeC = computers3.size();

        Assert.assertEquals(sizeA, sizeC);
    }

    @Test
    public void testFindAllWithLimit1() throws DAOException {
        final PageParameters page = new PageParameters.Builder().size(20).pageNumber(0).build();

        final List<Computer> computers1 = this.computerDAO.findAll(page);
        final List<Computer> computers2 = this.computerDAO.findAll();

        Assert.assertNotNull(computers1);
        Assert.assertNotNull(computers2);

        if (computers2.size() > 20) {
            Assert.assertTrue(computers1.size() == 20);
        } else {
            Assert.assertTrue(computers1.size() == computers2.size());
        }
    }

    @Test
    public void testFindAllWithLimit2() throws DAOException {
        final PageParameters page = new PageParameters.Builder().size(7).pageNumber(3).build();

        final List<Computer> computers1 = this.computerDAO.findAll(page);
        final List<Computer> computers2 = this.computerDAO.findAll();

        Assert.assertNotNull(computers1);
        Assert.assertNotNull(computers2);

        if (computers2.size() > (3 * 7)) {
            Assert.assertTrue(computers1.size() == 7);
        } else {
            Assert.assertTrue(computers1.size() == (computers2.size() - (2 * 7)));
        }
    }
}
