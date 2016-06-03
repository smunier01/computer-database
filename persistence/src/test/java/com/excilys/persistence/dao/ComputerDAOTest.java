package com.excilys.persistence.dao;

import com.excilys.core.model.Computer;
import com.excilys.core.model.PageParameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

@ContextConfiguration("classpath:applicationContext.xml")
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
    public void testFindAllWithCreate() throws DAOException {

        // count the number of computers with findAll

        long sizeA = this.computerDAO.count();

        Assert.assertTrue(sizeA > 0);

        // create a computer

        final LocalDate d1 = LocalDate.now();

        final Computer comp = new Computer.ComputerBuilder().name("MyComputerName").discontinued(d1).build();

        Assert.assertNotNull(comp);
        final Computer a = this.computerDAO.create(comp);
        Assert.assertNotNull(a);
        final Long id = a.getId();
        Assert.assertTrue(id > 0L);

        // count again, and compare the sizes

        long sizeB = this.computerDAO.count();

        Assert.assertTrue(sizeB > 0);

        Assert.assertEquals(sizeA + 1L, sizeB);

        // delete clean up

        this.computerDAO.delete(comp);

        // count and compare sizes

        long sizeC = this.computerDAO.count();

        Assert.assertTrue(sizeC > 0);

        Assert.assertEquals(sizeA, sizeC);
    }

    @Test
    public void testFindAllWithLimit1() throws DAOException {
        final PageParameters page = new PageParameters.Builder().size(20).pageNumber(0).build();

        final List<Computer> computers1 = this.computerDAO.findAll(page);
        long res = this.computerDAO.count();

        Assert.assertNotNull(computers1);

        if (res > 20) {
            Assert.assertTrue(computers1.size() == 20);
        } else {
            Assert.assertTrue(computers1.size() == res);
        }
    }

    @Test
    public void testFindAllWithLimit2() throws DAOException {
        final PageParameters page = new PageParameters.Builder().size(7).pageNumber(3).build();

        final List<Computer> computers1 = this.computerDAO.findAll(page);
        long res = this.computerDAO.count();

        Assert.assertNotNull(computers1);

        if (res > (3 * 7)) {
            Assert.assertTrue(computers1.size() == 7);
        } else {
            Assert.assertTrue(computers1.size() == (res - (2 * 7)));
        }
    }
}