package com.javalive.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import com.javalive.entity.Person;

/**
 * @author javalive.com P6Spy is a framework that enables us to log all sql
 *         statements and parameters for java application.
 * 
 *         By using P6Spy with Hibernate, you can log current execution time,
 *         total elapsed time, sql statement with bind variable, sql statement
 *         executed etc.
 *
 */
public class HibernateUtil {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

				Map<String, String> settings = new HashMap<>();
				settings.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");// IMP:- Major change
				settings.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/test1");// IMP:- Major change
				// settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				// settings.put(Environment.URL,"jdbc:mysql://localhost:3306/test1?useSSL=false");
				settings.put(Environment.USER, "root");
				settings.put(Environment.PASS, "root");
				// settings.put(Environment.HBM2DDL_AUTO, "update");

				registryBuilder.applySettings(settings);
				registry = registryBuilder.build();
				MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Person.class);
				Metadata metadata = sources.getMetadataBuilder().build();
				sessionFactory = metadata.getSessionFactoryBuilder().build();
			} catch (Exception e) {
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
