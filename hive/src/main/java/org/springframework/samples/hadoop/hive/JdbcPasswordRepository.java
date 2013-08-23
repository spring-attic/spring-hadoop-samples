package org.springframework.samples.hadoop.hive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPasswordRepository implements PasswordRepository, ResourceLoaderAware {

	private @Autowired JdbcOperations jdbcOperations;

	private @Value("${hive.table}")	String tableName;
	
	private ResourceLoader resourceLoader;

	@Override
	public Long count() {
		return jdbcOperations.queryForLong("select count(*) from " + tableName);
	}

	@Override
	public void processPasswordFile(String inputFile) {
		//TODO 
		/*
		SimpleJdbcTestUtils.executeSqlScript(new SimpleJdbcTemplate(jdbcOperations),
				resourceLoader.getResource(inputFile),
				true);
		*/

	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
