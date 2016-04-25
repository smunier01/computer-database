package com.excilys.cdb.company;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Company;

public class CompanyDAOTest {

    private final CompanyDAO companyDAO = CompanyDAO.getInstance();

    @Test
    public void testFindShouldHaveValidId() throws DAOException {

        Company result = companyDAO.find(-1L);

        assertNull(result);

        result = companyDAO.find(0L);

        assertNull(result);

    }

    @Test
    public void testCreateFindDelete() throws DAOException {

        // create a company

        final Company comp = new Company(null, "MyCompanyName");

        assertNotNull(comp);
        final Company a = companyDAO.create(comp);
        assertNotNull(a);
        final Long id = a.getId();
        assertTrue(id > 0L);

        // find the computer by its id

        final Company b = companyDAO.find(id);

        assertNotNull(b);
        assertEquals(comp.getName(), b.getName());
        assertEquals(comp, b);

        // delete the computer

        companyDAO.delete(b);

        // try to find it again

        final Company c = companyDAO.find(id);

        assertNull(c);
    }

    @Test
    public void testUpdate() throws DAOException {

        // create a company

        final Company comp = new Company(null, "MyCompanyName");

        assertNotNull(comp);
        final Company a = companyDAO.create(comp);
        assertNotNull(a);
        final Long id = a.getId();
        assertTrue(id > 0L);

        // modify the object

        a.setName("NewCompanyName");

        // update the db

        final Company b = companyDAO.update(a);

        assertEquals(b, a);
        assertEquals(id, b.getId());

        // retrieve from db

        final Company d = companyDAO.find(id);

        assertNotNull(d);
        assertEquals("NewCompanyName", d.getName());

        // delete clean up

        companyDAO.delete(d);

        // test the delete

        final Company e = companyDAO.find(id);

        assertNull(e);
    }

    @Test
    public void testFindAll() throws DAOException {
        final List<Company> companies = companyDAO.findAll();

        assertNotNull(companies);
        assertTrue(companies.size() > 0);
    }

    @Test
    public void testFindAllWithCreate() throws DAOException {

        // count the number of computers with findAll

        final List<Company> companies1 = companyDAO.findAll();

        assertNotNull(companies1);
        assertTrue(companies1.size() > 0);

        final long sizeA = companies1.size();

        // create a computer

        final Company company = new Company(null, "MyCompanyName");

        assertNotNull(company);
        final Company a = companyDAO.create(company);
        assertNotNull(a);
        final Long id = a.getId();
        assertTrue(id > 0L);

        // count again, and compare the sizes

        final List<Company> companies2 = companyDAO.findAll();

        assertNotNull(companies2);
        assertTrue(companies2.size() > 0);

        final long sizeB = companies2.size();

        assertEquals(sizeA + 1L, sizeB);

        // delete clean up

        companyDAO.delete(company);

        // count and compare sizes

        final List<Company> companies3 = companyDAO.findAll();

        assertNotNull(companies3);
        assertTrue(companies3.size() > 0);

        final long sizeC = companies3.size();

        assertEquals(sizeA, sizeC);
    }

    @Test
    public void testFindAllWithLimit1() throws DAOException {
        final List<Company> companies1 = companyDAO.findAll(0, 20);
        final List<Company> companies2 = companyDAO.findAll();

        assertNotNull(companies1);
        assertNotNull(companies2);

        if (companies2.size() > 20) {
            assertTrue(companies1.size() == 20);
        } else {
            assertTrue(companies1.size() == companies2.size());
        }
    }

    @Test
    public void testFindAllWithLimit2() throws DAOException {
        final List<Company> companies1 = companyDAO.findAll(20, 7);
        final List<Company> companies2 = companyDAO.findAll();

        assertNotNull(companies1);
        assertNotNull(companies2);

        if (companies2.size() > 27) {
            assertTrue(companies1.size() == 7);
        } else {
            assertTrue(companies1.size() == (companies2.size() - 20));
        }
    }

}
