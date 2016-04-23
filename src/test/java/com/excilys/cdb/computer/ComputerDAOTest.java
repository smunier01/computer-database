package com.excilys.cdb.computer;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DAOException;
import com.excilys.cdb.model.Computer;

public class ComputerDAOTest {

	private ComputerDAO computerDAO = ComputerDAO.getInstance();
	
	@Test
	public void testFindShouldHaveValidId() throws DAOException {
		
		Computer result = computerDAO.find(-1L);
		
		assertNull(result);
		
		result = computerDAO.find(0L);
		
		assertNull(result);
	
	}
	
	@Test
	public void testCreateFindDelete() throws DAOException {
		
		// create a computer
		
		Computer comp = new Computer.ComputerBuilder()
				.name("MyComputerName")
				.discontinued(LocalDate.now())
				.build();
		
		assertNotNull(comp);
		Computer a = computerDAO.create(comp);
		assertNotNull(a);
		Long id = a.getId();
		assertTrue(id > 0L);
		
		// find the computer by its id
		
		Computer b = computerDAO.find(id);
		
		assertNotNull(b);
		assertEquals(comp.getName(), b.getName());
		assertEquals(comp, b);
		
		// delete the computer
		
		computerDAO.delete(b);
		
		// try to find it again
		
		Computer c = computerDAO.find(id);
		
		assertNull(c);
	}
	
	@Test
	public void testUpdate() throws DAOException {
		
		// create a computer
		
		LocalDate d1 = LocalDate.now();
		
		Computer comp = new Computer.ComputerBuilder()
				.name("MyComputerName")
				.discontinued(d1)
				.build();
		
		assertNotNull(comp);
		Computer a = computerDAO.create(comp);
		assertNotNull(a);
		Long id = a.getId();
		assertTrue(id > 0L);
		
		// modify the object
		
		LocalDate d2 = LocalDate.now().plusYears(1);
		
		a.setName("NewComputerName");
		a.setIntroduced(d2);
		a.setDiscontinued(null);
		
		// update the db
		
		Computer b = computerDAO.update(a);
		
		assertEquals(b, a);
		assertEquals(id, b.getId());
		
		// retrieve from db
		
		Computer d = computerDAO.find(id);
		
		assertNotNull(d);
		assertEquals("NewComputerName", d.getName());
		assertNull(d.getDiscontinued());
		assertEquals(d2, d.getIntroduced());
		
		// delete clean up
		
		computerDAO.delete(d);
		
		// test the delete
		
		Computer e = computerDAO.find(id);
		
		assertNull(e);
	}
	
	@Test
	public void testFindAll() throws DAOException {
		List<Computer> computers = computerDAO.findAll();
		
		assertNotNull(computers);
		assertTrue(computers.size() > 0);
	}
	
	@Test
	public void testFindAllWithCreate() throws DAOException {
		
		// count the number of computers with findAll
		
		List<Computer> computers1 = computerDAO.findAll();
		
		assertNotNull(computers1);
		assertTrue(computers1.size() > 0);
		
		long sizeA = computers1.size();
		
		// create a computer
		
		LocalDate d1 = LocalDate.now();
		
		Computer comp = new Computer.ComputerBuilder()
				.name("MyComputerName")
				.discontinued(d1)
				.build();
		
		assertNotNull(comp);
		Computer a = computerDAO.create(comp);
		assertNotNull(a);
		Long id = a.getId();
		assertTrue(id > 0L);
		
		// count again, and compare the sizes
		
		List<Computer> computers2 = computerDAO.findAll();
		
		assertNotNull(computers2);
		assertTrue(computers2.size() > 0);
		
		long sizeB = computers2.size();
		
		assertEquals(sizeA + 1L, sizeB);
		
		// delete clean up
		
		computerDAO.delete(comp);
		
		// count and compare sizes
		
		List<Computer> computers3 = computerDAO.findAll();
		
		assertNotNull(computers3);
		assertTrue(computers3.size() > 0);
		
		long sizeC = computers3.size();
		
		assertEquals(sizeA, sizeC);
	}
	
	@Test
	public void testFindAllWithLimit1() throws DAOException {
		List<Computer> computers1 = computerDAO.findAll(0, 20);
		List<Computer> computers2 = computerDAO.findAll();
		
		assertNotNull(computers1);
		assertNotNull(computers2);
		
		if (computers2.size() > 20) {
			assertTrue(computers1.size() == 20);
		} else {
			assertTrue(computers1.size() == computers2.size());
		}
	}
	
	@Test
	public void testFindAllWithLimit2() throws DAOException {
		List<Computer> computers1 = computerDAO.findAll(20, 7);
		List<Computer> computers2 = computerDAO.findAll();
		
		assertNotNull(computers1);
		assertNotNull(computers2);
		
		if (computers2.size() > 27) {
			assertTrue(computers1.size() == 7);
		} else {
			assertTrue(computers1.size() == (computers2.size() - 20));
		}
	}
}
