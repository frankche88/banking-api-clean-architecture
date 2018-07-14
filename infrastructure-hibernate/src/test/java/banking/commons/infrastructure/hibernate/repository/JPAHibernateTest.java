package banking.commons.infrastructure.hibernate.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class JPAHibernateTest {

	protected static SessionFactory sessionFactory;

	protected Session session;

	@BeforeClass
	public static void init() throws FileNotFoundException, SQLException {

		Configuration configuration = new Configuration();
		
		configuration.addResource("hibernate/customer.hbm.xml");
		configuration.addResource("hibernate/bankAccount.hbm.xml");
		
		configuration.addResource("hibernate/Users.hbm.xml");
        configuration.addResource("hibernate/UserRoles.hbm.xml");

		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

		configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");

		configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;MODE=MYSQL;INIT=RUNSCRIPT FROM 'classpath:db/V1_1__create_customer.sql'\\;RUNSCRIPT FROM 'classpath:db/V1_2__create_bank_account.sql'");

		configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
		
		configuration.setProperty("hibernate.show_sql", "true");
		
		configuration.setProperty("org.hibernate.type", "true");
		
		
		configuration.setProperty("hibernate.current_session_context_class", "org.hibernate.context.internal.ThreadLocalSessionContext");

		sessionFactory = configuration.buildSessionFactory();
	}

	@Before
	public void initializeDatabase() {
		session = sessionFactory.getCurrentSession();
		
		session.beginTransaction();
		session.doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				try {
					File script = new File(getClass().getResource("/db/V1_3__insert_customer.sql").getFile());
					RunScript.execute(connection, new FileReader(script));

//					script = new File(getClass().getResource("/db/V1_4__insert_bank_accounts.sql").getFile());
//					RunScript.execute(connection, new FileReader(script));

				} catch (FileNotFoundException e) {
					throw new RuntimeException("could not initialize with script");
				}
			}
		});
		
		session.getTransaction().commit();
	}

	@After
	public void tearAfter() {
	    if( session != null)
		session.close();
	}

	@AfterClass
	public static void tearDown() {
	    if( sessionFactory != null)
		sessionFactory.close();
	}

}
