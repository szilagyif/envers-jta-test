/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.envers.bugs;

import hu.transctrl.entity.Driver;
import hu.transctrl.repository.DriverRepository;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

/**
 * This template demonstrates how to develop a test case for Hibernate Envers, using
 * its built-in unit test framework.
 */
@RunWith(Arquillian.class)
public class EnversUnitTestCase extends AbstractEnversTestCase {

	@EJB
	DriverRepository driverRepository;

	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[] {
				Driver.class
		};
	}

	// If you use *.hbm.xml mappings, instead of annotations, add the mappings here.
	@Override
	protected String[] getMappings() {
		return new String[] {
//				"Foo.hbm.xml",
//				"Bar.hbm.xml"
		};
	}
	// If those mappings reside somewhere other than resources/org/hibernate/test, change this.
	@Override
	protected String getBaseForMappings() {
		return "org/hibernate/test/";
	}

	// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
	@Override
	protected void configure(Configuration configuration) {
		super.configure( configuration );

		configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
		//configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	@Deployment
	public static EnterpriseArchive createTestArchive(){

		JavaArchive arc =  ShrinkWrap.create(JavaArchive.class, "test.jar")
				.addClasses(Driver.class, DriverRepository.class)
				.addAsManifestResource("META-INF/test-persistence.xml", ArchivePaths.create("persistence.xml"));
				//.addAsResource("META-INF/t2.xml", ArchivePaths.create("t2.xml"));

		EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class, "test.ear")
				.addAsLibrary(arc);

		return ear;
	}

	@Test
	public void driverPersistTest() throws Exception {

		Driver driver = new Driver();
		driver.setId(1l);
		driver.setName("Name");

		driverRepository.storeAndFlush(driver);

		//AuditReader reader = getAuditReader();

	}
}
