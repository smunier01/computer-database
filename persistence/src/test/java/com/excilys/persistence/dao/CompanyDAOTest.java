package com.excilys.persistence.dao;

import com.excilys.core.model.Company;
import com.excilys.core.model.PageParameters;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration("classpath*:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyDAOTest {

    @Autowired
    private CompanyDAO companyDAO;

    // -------------------------------------- Find tests ---------------------------------------------------------------
    @Test
    @Transactional
    public void testFindShouldHaveValidId() {


        Company result = this.companyDAO.find(-1L);
        assertNull(result);

        result = this.companyDAO.find(0L);
        assertNull(result);

    }

    @Test
    public void findTest() {
        Company comp = companyDAO.find(3L);
        assertEquals(comp.getName(), "RCA");
    }

    // -------------------------------------- Create tests -------------------------------------------------------------
    @Test
    @Transactional
    public void createTest() {
        Company comp = new Company();
        comp.setName("createCompanyTestDAO");
        Company created = companyDAO.create(comp);
        assertNotNull(created);
        assertNotNull(created.getId());

        companyDAO.delete(created);
    }

    @Test
    @Transactional
    public void createEqualTest() {
        Company comp = new Company();
        comp.setName("createEqualsCompanyTestDAO");
        Company created = companyDAO.create(comp);
        comp.setId(created.getId());
        assertEquals(comp,created);

        companyDAO.delete(created);
    }


    // -------------------------------------- Update tests -------------------------------------------------------------

    @Test
    @Transactional
    public void updateTest() {
        Company company = new Company(null,"updateTestDAO");
        company = companyDAO.create(company);
        company.setName("updatedTestDAO");
        company = companyDAO.update(company);
        assertEquals("updatedTestDAO",company.getName());

        companyDAO.delete(company);
    }


    // -------------------------------------- Remove tests -------------------------------------------------------------


    @Test
    @Transactional
    public void removeTest() {
        Company company = new Company(null,"removedTestDAO");
        Company created = companyDAO.create(company);
        Long id = created.getId();
        companyDAO.delete(created);
        Company retrieve = companyDAO.find(id);
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

    @Test
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

    @Test
    @Transactional
    public void CountTestNotFound() {
        PageParameters pageParameters = new PageParameters.Builder().search("hunt").build();
        long countInitial = companyDAO.count(pageParameters);
        Company comp = new Company(null,"countTest2DAO");
        companyDAO.create(comp);
        long count = companyDAO.count(pageParameters);
        assertEquals(countInitial,count);

        companyDAO.delete(comp);
    }

    // -------------------------------------- Find all tests -----------------------------------------------------------

   @Test @Ignore
    @Transactional
    public void findAllTest() {
        PageParameters pageParameters = new PageParameters.Builder().search("apple").order(PageParameters.Order.NAME).direction(PageParameters.Direction.ASC).size(5).build();
        List<Company> companies = companyDAO.findAll(pageParameters);
        for (int i =1; i < companies.size(); i++) {
            Company comp = companies.get(i);
            Company before = companies.get(i-1);
            System.err.println("dkljdjkf->"+comp.getName());
            boolean searchName = comp.getName().toLowerCase().contains("apple");
            assertTrue(searchName);
            int nameOrder = comp.getName().compareTo(before.getName());
            assertTrue(nameOrder >= 0);
        }
    }

    /*@Test
    public void testUpdate() throws DAOException {

        // create a company

        final Company comp = new Company(null, "MyCompanyName");

        assertNotNull(comp);
        final Company a = this.companyDAO.create(comp);
        assertNotNull(a);
        final Long id = a.getId();
        //assertTrue(id > 0L);

        // modify the object

        a.setName("NewCompanyName");

        // update the db

        final Company b = this.companyDAO.update(a);

        assertEquals(b, a);
        assertEquals(id, b.getId());

        // retrieve from db

        final Company d = this.companyDAO.find(id);

        Assert.assertNotNull(d);
        Assert.assertEquals("NewCompanyName", d.getName());

        // delete clean up

        this.companyDAO.delete(d);

        // test the delete

        final Company e = this.companyDAO.find(id);

        assertNull(e);
    }

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
    }*/

}