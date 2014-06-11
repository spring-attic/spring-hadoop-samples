package org.springframework.samples.hadoop.dataset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.hadoop.store.DataStoreWriter;
import org.springframework.data.hadoop.store.StoreException;
import org.springframework.data.hadoop.store.dataset.DatasetOperations;
import org.springframework.data.hadoop.store.dataset.RecordCallback;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@ComponentScan
@EnableAutoConfiguration
public class MyFilesApp implements CommandLineRunner {

    private DatasetOperations datasetOperations;

	private DataStoreWriter<FileInfo> writer;

	private long count;

    @Autowired
    public void setDatasetOperations(DatasetOperations datasetOperations) {
        this.datasetOperations = datasetOperations;
    }

    @Autowired
    public void setDataStoreWriter(DataStoreWriter dataStoreWriter) {
        this.writer = dataStoreWriter;
    }

    public static void main(String[] args) {
        SpringApplication.run(MyFilesApp.class, args);
    }

    @Override
    public void run(String... strings) {
        String fileDir = System.getProperty("user.home");
        System.out.println("Processing " + fileDir + " ...");
		File f = new File(fileDir);
        try {
            processFile(f);
        } catch (IOException e) {
            throw new StoreException("Error writing FileInfo", e);
        } finally {
            close();
        }
        countFileInfoEntries();
        System.out.println("Done!");
    }

	private void processFile(File file) throws IOException {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                processFile(f);
            }
        } else {
            if (++count % 10000 == 0) {
                System.out.println("Writing " + count + " ...");
            }
            FileInfo fileInfo = new FileInfo(file.getName(), file.getParent(), (int)file.length(), file.lastModified());
            writer.write(fileInfo);
        }
    }

	private void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new StoreException("Error closing FileInfo", e);
        }
    }

    private void countFileInfoEntries() {
        final AtomicLong count = new AtomicLong();
        datasetOperations.read(FileInfo.class, new RecordCallback<FileInfo>() {
            @Override
            public void doInRecord(FileInfo record) {
                count.getAndIncrement();
            }
        });
        System.out.println("File count: " + count.get());
    }
}
