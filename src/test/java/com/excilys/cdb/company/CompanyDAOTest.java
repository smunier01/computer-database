package com.excilys.cdb.company;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.util.PageParameters;

public class CompanyDAOTest {

    private final CompanyDAO companyDAO = CompanyDAO.getInstance();

    @Test
    public void testFindShouldHaveValidId() throws DAOException {

        Company result = this.companyDAO.find(-1L);

        Assert.assertNull(result);

        result = this.companyDAO.find(0L);

        Assert.assertNull(result);

    }

    @Test
    public void testCreateFindDelete() throws DAOException {

        // create a company

        final Company comp = new Company(null, "MyCompanyName");

        Assert.assertNotNull(comp);
        final Company a = this.companyDAO.create(comp);
        Assert.assertNotNull(a);
        final Long id = a.getId();
        Assert.assertTrue(id > 0L);

        // find the computer by its id

        final Company b = this.companyDAO.find(id);

        Assert.assertNotNull(b);
        Assert.assertEquals(comp.getName(), b.getName());
        Assert.assertEquals(comp, b);

        // delete the computer

        this.companyDAO.delete(b);

        // try to find it again

        final Company c = this.companyDAO.find(id);

        Assert.assertNull(c);
    }

    @Test
    public void testUpdate() throws DAOException {

        // create a company

        final Company comp = new Company(null, "MyCompanyName");

        Assert.assertNotNull(comp);
        final Company a = this.companyDAO.create(comp);
        Assert.assertNotNull(a);
        final Long id = a.getId();
        Assert.assertTrue(id > 0L);

        // modify the object

        a.setName("NewCompanyName");

        // update the db

        final Company b = this.companyDAO.update(a);

        Assert.assertEquals(b, a);
        Assert.assertEquals(id, b.getId());

        // retrieve from db

        final Company d = this.companyDAO.find(id);

        Assert.assertNotNull(d);
        Assert.assertEquals("NewCompanyName", d.getName());

        // delete clean up

        this.companyDAO.delete(d);

        // test the delete

        final Company e = this.companyDAO.find(id);

        Assert.assertNull(e);
    }

    @Test
    public void testFindAll() throws DAOException {
        final List<Company> companies = this.companyDAO.findAll();

        Assert.assertNotNull(companies);
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

        final PageParameters page = new PageParameters(20, 0);

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

        final PageParameters page = new PageParameters(7, 3);

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

}
