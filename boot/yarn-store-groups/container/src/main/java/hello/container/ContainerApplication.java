package hello.container;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.FutureTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.store.PartitionDataStoreWriter;
import org.springframework.data.hadoop.store.config.annotation.EnableDataStorePartitionTextWriter;
import org.springframework.data.hadoop.store.config.annotation.SpringDataStoreTextWriterConfigurerAdapter;
import org.springframework.data.hadoop.store.config.annotation.builders.DataStoreTextWriterConfigurer;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;
import org.springframework.yarn.annotation.OnContainerStart;
import org.springframework.yarn.annotation.YarnComponent;
import org.springframework.yarn.container.YarnContainerSupport;

@SpringBootApplication
public class ContainerApplication {

	private static final Log log = LogFactory.getLog(ContainerApplication.class);

	@YarnComponent
	static class HdfsStoreWriter extends YarnContainerSupport {

		@Autowired
		private PartitionDataStoreWriter<String, Map<String, Object>> writer;

		@OnContainerStart
		public ListenableFuture<?> writer() throws Exception {
			final MyFuture myFuture = new MyFuture();
			getTaskScheduler().schedule(new FutureTask<Void>(new Runnable() {
				@Override
				public void run() {
					try {
						while(!myFuture.interrupted) {
							for (int i = 0; i < 10; i++) {
								writer.write(Integer.toString(i));
							}
							Thread.sleep(1000);
						}
					} catch (Exception e) {
						myFuture.set(false);
						log.error("Error in writing",e);
					}
				}
			}, null), new Date());
			return myFuture;
		}

	}

	static class MyFuture extends SettableListenableFuture<Boolean> {

		boolean interrupted = false;

		@Override
		protected void interruptTask() {
			interrupted = true;
			log.info("interruptTask");
		}
	}

	@Configuration
	@EnableDataStorePartitionTextWriter
	static class Config extends SpringDataStoreTextWriterConfigurerAdapter {

		@Override
		public void configure(DataStoreTextWriterConfigurer config) throws Exception {
			config
				.basePath("/tmp/store")
				.idleTimeout(60000)
				.inWritingSuffix(".tmp")
				.withPartitionStrategy()
					.map("dateFormat('yyyy/MM/dd/HH/mm', timestamp)")
					.and()
				.withNamingStrategy()
					.name("data")
					.uuid()
					.rolling()
					.name("txt", ".")
					.and()
				.withRolloverStrategy()
					.size("1M");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ContainerApplication.class, args);
	}

}
