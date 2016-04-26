package com.excilys.cdb.computer;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.model.Computer;

public class ComputerDAOTest {

	private final ComputerDAO computerDAO = ComputerDAO.getInstance();

	@Test
	public void testFindShouldHaveValidId() throws DAOException {

		Computer result = computerDAO.find(-1L);

		Assert.assertNull(result);

		result = computerDAO.find(0L);

		Assert.assertNull(result);

	}

	@Test
	public void testCreateFindDelete() throws DAOException {

		// create a computer

		final Computer comp = new Computer.ComputerBuilder().name("MyComputerName").discontinued(LocalDate.now())
				.build();

		Assert.assertNotNull(comp);
		final Computer a = computerDAO.create(comp);
		Assert.assertNotNull(a);
		final Long id = a.getId();
		Assert.assertTrue(id > 0L);

		// find the computer by its id

		final Computer b = computerDAO.find(id);

		Assert.assertNotNull(b);
		Assert.assertEquals(comp.getName(), b.getName());
		Assert.assertEquals(comp, b);

		// delete the computer

		computerDAO.delete(b);

		// try to find it again

		final Computer c = computerDAO.find(id);

		Assert.assertNull(c);
	}

	@Test
	public void testUpdate() throws DAOException {

		// create a computer

		final LocalDate d1 = LocalDate.now();

		final Computer comp = new Computer.ComputerBuilder().name("MyComputerName").discontinued(d1).build();

		Assert.assertNotNull(comp);
		final Computer a = computerDAO.create(comp);
		Assert.assertNotNull(a);
		final Long id = a.getId();
		Assert.assertTrue(id > 0L);

		// modify the object

		final LocalDate d2 = LocalDate.now().plusYears(1);

		a.setName("NewComputerName");
		a.setIntroduced(d2);
		a.setDiscontinued(null);

		// update the db

		final Computer b = computerDAO.update(a);

		Assert.assertEquals(b, a);
		Assert.assertEquals(id, b.getId());

		// retrieve from db

		final Computer d = computerDAO.find(id);

		Assert.assertNotNull(d);
		Assert.assertEquals("NewComputerName", d.getName());
		Assert.assertNull(d.getDiscontinued());
		Assert.assertEquals(d2, d.getIntroduced());

		// delete clean up

		computerDAO.delete(d);

		// test the delete

		final Computer e = computerDAO.find(id);

		Assert.assertNull(e);
	}

	@Test
	public void testFindAll() throws DAOException {
		final List<Computer> computers = computerDAO.findAll();

		Assert.assertNotNull(computers);
		Assert.assertTrue(computers.size() > 0);
	}

	@Test
	public void testFindAllWithCreate() throws DAOException {

		// count the number of computers with findAll

		final List<Computer> computers1 = computerDAO.findAll();

		Assert.assertNotNull(computers1);
		Assert.assertTrue(computers1.size() > 0);

		final long sizeA = computers1.size();

		// create a computer

		final LocalDate d1 = LocalDate.now();

		final Computer comp = new Computer.ComputerBuilder().name("MyComputerName").discontinued(d1).build();

		Assert.assertNotNull(comp);
		final Computer a = computerDAO.create(comp);
		Assert.assertNotNull(a);
		final Long id = a.getId();
		Assert.assertTrue(id > 0L);

		// count again, and compare the sizes

		final List<Computer> computers2 = computerDAO.findAll();

		Assert.assertNotNull(computers2);
		Assert.assertTrue(computers2.size() > 0);

		final long sizeB = computers2.size();

		Assert.assertEquals(sizeA + 1L, sizeB);

		// delete clean up

		computerDAO.delete(comp);

		// count and compare sizes

		final List<Computer> computers3 = computerDAO.findAll();

		Assert.assertNotNull(computers3);
		Assert.assertTrue(computers3.size() > 0);

		final long sizeC = computers3.size();

		Assert.assertEquals(sizeA, sizeC);
	}

	@Test
	public void testFindAllWithLimit1() throws DAOException {
		final List<Computer> computers1 = computerDAO.findAll(0, 20);
		final List<Computer> computers2 = computerDAO.findAll();

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
		final List<Computer> computers1 = computerDAO.findAll(20, 7);
		final List<Computer> computers2 = computerDAO.findAll();

		Assert.assertNotNull(computers1);
		Assert.assertNotNull(computers2);

		if (computers2.size() > 27) {
			Assert.assertTrue(computers1.size() == 7);
		} else {
			Assert.assertTrue(computers1.size() == (computers2.size() - 20));
		}
	}
}
