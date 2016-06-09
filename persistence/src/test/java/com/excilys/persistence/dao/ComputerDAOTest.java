package com.excilys.persistence.dao;

import com.excilys.core.model.Computer;
import com.excilys.core.model.PageParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ComputerDAOTest {

    @Autowired
    private ComputerDAO computerDAO;

    // -------------------------------------- Find tests ---------------------------------------------------------------
    @Test
    @Transactional
    public void testFindShouldHaveValidId() {


        Computer result = this.computerDAO.find(-1L);
        assertNull(result);

        result = this.computerDAO.find(0L);
        assertNull(result);

    }

    @Test
    public void findTest() {
        Computer comp = computerDAO.find(2L);
        assertEquals(comp.getName(), "CM-2a");
    }

    // -------------------------------------- Create tests -------------------------------------------------------------
    @Test
    @Transactional
    public void createTest() {
        Computer comp = new Computer.ComputerBuilder().name("createComputerTestDAO").build();
        Computer created = computerDAO.create(comp);
        assertNotNull(created);
        assertNotNull(created.getId());

        computerDAO.delete(created);
    }

    @Test
    @Transactional
    public void createEqualTest() {
        Computer comp = new Computer.ComputerBuilder().name("createEqualsComputerTestDAO").build();
        Computer created = computerDAO.create(comp);
        comp.setId(created.getId());
        assertEquals(comp,created);

        computerDAO.delete(created);
    }


    // -------------------------------------- Update tests -------------------------------------------------------------

    @Test
    @Transactional
    public void updateTest() {
        Computer computer = new Computer.ComputerBuilder().name("updateTestDAO").build();
        computer = computerDAO.create(computer);
        computer.setName("updatedTestDAO");
        computer = computerDAO.update(computer);
        assertEquals("updatedTestDAO",computer.getName());

        computerDAO.delete(computer);
    }


    // -------------------------------------- Remove tests -------------------------------------------------------------

/*
    @Test
    @Transactional
    public void removeTest() {
        Computer computer = new Company(null,"removedTestDAO");
        Company created = computerDAO.create(company);
        Long id = created.getId();
        computerDAO.delete(created);
        Company retrieve = computerDAO.find(id);
        assertNull(retrieve);
    }


    // -------------------------------------- Count tests --------------------------------------------------------------

    @Test
    @Transactional
    public void CountTest() {
        long countInitial = companyDAO.count();
        Company comp = new Company(null,"countTestDAO");
        companyDAO.create(comp);
        long count = companyDAO.count();
        assertEquals(countInitial+1,count);

        companyDAO.delete(comp);
    }

    @Test @Ignore
    @Transactional
    public void CountTestFound() {
        PageParameters pageParameters = new PageParameters.Builder().search("count").build();
        long countInitial = companyDAO.count(pageParameters);
        Company comp = new Company(null,"countTest1DAO");
        companyDAO.create(comp);
        long count = companyDAO.count(pageParameters);
        assertEquals(countInitial+1,count);

        companyDAO.delete(comp);
    }

    @Test @Ignore
    @Transactional
    public void CountTestNotFound() {
        PageParameters pageParameters = new PageParameters.Builder().search("hunt").build();
        long countInitial = companyDAO.count(pageParameters);
        Company comp = new Company(null,"countTest2DAO");
        companyDAO.create(comp);
        long count = companyDAO.count(pageParameters);
        assertEquals(countInitial,count);

        companyDAO.delete(comp);
    }*/

    // -------------------------------------- Find all tests -----------------------------------------------------------

    @Test
    @Transactional
    public void findAllTest() {
        PageParameters pageParameters = new PageParameters.Builder().search("apple").direction(PageParameters.Direction.ASC).size(5).build();
        List<Computer> companies = computerDAO.findAll(pageParameters);
        for (int i =1; i < companies.size(); i++) {
            Computer comp = companies.get(i);
            Computer before = companies.get(i-1);
            System.err.println("dkljdjkf->"+comp.getName());
            boolean searchName = comp.getName().toLowerCase().contains("apple");
            assertTrue(searchName);
            int nameOrder = comp.getName().compareTo(before.getName());
            assertTrue(nameOrder >= 0);
        }
    }


    /*

    @Test
    public void testFindAll() throws DAOException {
        final List<Company> companies = this.companyDAO.findAll();

        assertNotNull(companies);
        Assert.assertTrue(companies.size() > 0);
    }

    @Test
    public void testFindAllWithCreate() throws DAOException {

        // count the number of computers with findAll

        final List<Company> companies1 = this.companyDAO.findAll();

        Assert.assertNotNull(companies1);
        Assert.assertTrue(companies1.size() > 0);

        final long sizeA = companies1.size();

        // create a computer

        final Company company = new Company(null, "MyCompanyName");

        Assert.assertNotNull(company);
        final Company a = this.companyDAO.create(company);
        Assert.assertNotNull(a);
        final Long id = a.getId();
        Assert.assertTrue(id > 0L);

        // count again, and compare the sizes

        final List<Company> companies2 = this.companyDAO.findAll();

        Assert.assertNotNull(companies2);
        Assert.assertTrue(companies2.size() > 0);

        final long sizeB = companies2.size();

        Assert.assertEquals(sizeA + 1L, sizeB);

        // delete clean up

        this.companyDAO.delete(company);

        // count and compare sizes

        final List<Company> companies3 = this.companyDAO.findAll();

        Assert.assertNotNull(companies3);
        Assert.assertTrue(companies3.size() > 0);

        final long sizeC = companies3.size();

        Assert.assertEquals(sizeA, sizeC);
    }

    @Test
    public void testFindAllWithLimit1() throws DAOException {

        final PageParameters page = new PageParameters.Builder().size(20).pageNumber(0).build();

        final List<Company> companies1 = this.companyDAO.findAll(page);
        final List<Company> companies2 = this.companyDAO.findAll();

        Assert.assertNotNull(companies1);
        Assert.assertNotNull(companies2);

        if (companies2.size() > 20) {
            Assert.assertEquals(companies1.size(), 20);
        } else {
            Assert.assertTrue(companies1.size() == companies2.size());
        }
    }

    @Test
    public void testFindAllWithLimit2() throws DAOException {

        final PageParameters page = new PageParameters.Builder().size(7).pageNumber(3).build();

        final List<Company> companies1 = this.companyDAO.findAll(page);
        final List<Company> companies2 = this.companyDAO.findAll();

        Assert.assertNotNull(companies1);
        Assert.assertNotNull(companies2);

        if (companies2.size() > (3 * 7)) {
            Assert.assertTrue(companies1.size() == 7);
        } else {
            Assert.assertTrue(companies1.size() == (companies2.size() - (2 * 7)));
        }
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
    }*/
}