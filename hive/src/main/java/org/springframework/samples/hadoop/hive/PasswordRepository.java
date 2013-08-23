package org.springframework.samples.hadoop.hive;


public interface PasswordRepository {

	Long count();
	
	void processPasswordFile(String inputFile);

}