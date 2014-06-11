package org.springframework.samples.hadoop.dataset;

import org.kitesdk.data.Formats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.hadoop.store.DataStoreWriter;
import org.springframework.data.hadoop.store.dataset.*;

import java.util.Arrays;

@Configuration
@ImportResource("hadoop-context.xml")
public class DatasetConfig {

	private @Autowired org.apache.hadoop.conf.Configuration hadoopConfiguration;

	@Bean
	public DatasetRepositoryFactory datasetRepositoryFactory() {
		DatasetRepositoryFactory datasetRepositoryFactory = new DatasetRepositoryFactory();
		datasetRepositoryFactory.setConf(hadoopConfiguration);
		datasetRepositoryFactory.setBasePath("/user/spring");
		return datasetRepositoryFactory;
	}

    @Bean
    public DataStoreWriter<FileInfo> dataStoreWriter() {
        return new AvroPojoDatasetStoreWriter<FileInfo>(FileInfo.class, datasetRepositoryFactory(), fileInfoDatasetDefinition());
    }

	@Bean
	public DatasetOperations datasetOperations() {
		DatasetTemplate datasetOperations = new DatasetTemplate();
		datasetOperations.setDatasetDefinitions(Arrays.asList(fileInfoDatasetDefinition()));
		datasetOperations.setDatasetRepositoryFactory(datasetRepositoryFactory());
 		return datasetOperations;
	}

    @Bean
    public DatasetDefinition fileInfoDatasetDefinition() {
        DatasetDefinition definition = new DatasetDefinition();
        definition.setFormat(Formats.AVRO.getName());
        definition.setTargetClass(FileInfo.class);
        definition.setAllowNullValues(false);
        return definition;
    }
}
