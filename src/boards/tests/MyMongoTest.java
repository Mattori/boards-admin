package boards.tests;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boards.database.DBOAdapter;
import boards.database.MyMongo;
import boards.models.Developer;
import boards.models.Project;

public class MyMongoTest {
	
	private MyMongo myMongo;
	@Before
	public void setUp() throws Exception {
		myMongo=new MyMongo();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDb() {
		fail("Not yet implemented");
	}

	@Test
	public void testConnectString() {
		try {
			myMongo.connect("testsMyMongo");
			assertTrue(myMongo.isConnected());
		} catch (UnknownHostException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testConnectStringStringInt() {
		try {
			myMongo.connect("testsMyMongo", "127.0.0.1", 27017);
			assertTrue(myMongo.isConnected());
		} catch (UnknownHostException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetCollection() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertStringObject() throws UnknownHostException {
		myMongo.connect("testsMyMongo");
		int nbBefore = myMongo.load(Developer.class).size();
		Developer dev = new Developer();
		myMongo.insert("Marc Zuckerberg", dev);
		assertEquals(1, myMongo.load(Developer.class).size() - nbBefore);
	}

	@Test
	public void testInsertObject() throws UnknownHostException {
		myMongo.connect("testsMyMongo");
		int nbBefore = myMongo.load(Developer.class).size();
		Developer dev = new Developer();
		dev.setIdentity("Linus Torvalds");
		myMongo.insert(dev);
		assertEquals(1, myMongo.load(Developer.class).size() - nbBefore);
	}

	@Test
	public void testFindOne() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOneString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOneBasicDBObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOneStringBasicDBObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFind() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertStringListOfQextendsModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindStringBasicDBObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadCursorClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadBasicDBObjectClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindBasicDBObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCollection() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDbNames() {
		fail("Not yet implemented");
	}

	@Test
	public void testClose() {
		fail("Not yet implemented");
	}

	@Test
	public void testDropCollectionString() {
		fail("Not yet implemented");
	}

	@Test
	public void testDropCollectionClassOfQextendsModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testDropCollections() {
		fail("Not yet implemented");
	}

}
