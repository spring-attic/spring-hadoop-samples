package org.springframework.samples.hadoop.hive;

import org.apache.hadoop.hive.service.HiveClient;
import org.springframework.dao.DataAccessException;

public interface HiveClientCallback<T> {

	T doInHive(HiveClient hiveClient) throws Exception, DataAccessException;
}
