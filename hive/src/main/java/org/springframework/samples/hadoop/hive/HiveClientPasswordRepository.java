package org.springframework.samples.hadoop.hive;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hive.HiveClient;
import org.springframework.data.hadoop.hive.HiveClientFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class HiveClientPasswordRepository implements PasswordRepository {

	private static final Log logger = LogFactory
			.getLog(HiveClientPasswordRepository.class);

	private HiveClientFactory hiveClientFactory;

	private @Value("${hive.table}")
	String tableName;

	@Autowired
	public HiveClientPasswordRepository(HiveClientFactory hiveClientFactory) {
		this.hiveClientFactory = hiveClientFactory;
	}
	
	@Override
	public Long count() {
		HiveClient hiveClient = createHiveClient();
		try {
			return Long.parseLong(hiveClient.executeAndfetchOne("select count(*) from " + tableName));
		} finally {
			try {
				hiveClient.shutdown();
			} catch (SQLException tex) {
				logger.debug(
						"Unexpected exception on shutting down HiveClient", tex);
			}
		}
	}
	
	@Override
	public void processPasswordFile(String inputFile) {
		//TODO
	}

	protected HiveClient createHiveClient() {
		return hiveClientFactory.getHiveClient();
	}

	private RuntimeException translateException(Exception ex) {
		return new RuntimeException(ex);
	}


}
