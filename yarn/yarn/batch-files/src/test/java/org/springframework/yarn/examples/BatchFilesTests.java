/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.yarn.examples;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.yarn.test.context.MiniYarnCluster;
import org.springframework.yarn.test.context.YarnDelegatingSmartContextLoader;
import org.springframework.yarn.test.junit.AbstractYarnClusterTests;
import org.springframework.yarn.test.junit.ApplicationInfo;
import org.springframework.yarn.test.support.ContainerLogUtils;

/**
 * Tests for batch files example.
 *
 * @author Janne Valkealahti
 *
 */
@ContextConfiguration(loader=YarnDelegatingSmartContextLoader.class)
@MiniYarnCluster
public class BatchFilesTests extends AbstractYarnClusterTests {

	@Test
	@Timed(millis=70000)
	public void testAppSubmission() throws Exception {
		createTestData();

		ApplicationInfo info = submitApplicationAndWait();
		assertThat(info, notNullValue());
		assertThat(info.getYarnApplicationState(), notNullValue());
		assertThat(info.getApplicationId(), notNullValue());
		assertThat(info.getYarnApplicationState(), is(YarnApplicationState.FINISHED));

		List<Resource> resources = ContainerLogUtils.queryContainerLogs(getYarnCluster(), info.getApplicationId());

		assertThat(resources, notNullValue());
		assertThat(resources.size(), is(8));

		int linesFound = 0;
		HashSet<String> linesUnique = new HashSet<String>(300);

		for (Resource res : resources) {
			File file = res.getFile();
			if (file.getName().endsWith("stdout")) {
				// there has to be some content in stdout file
				assertThat(file.length(), greaterThan(0l));
				if (file.getName().equals("Container.stdout")) {
					Scanner scanner = new Scanner(file);
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						if (line.contains("writing:")) {
							String[] split = line.split("\\s+");
							linesUnique.add(split[split.length-1]);
							linesFound++;
						}
					}
					scanner.close();
				}
			} else if (file.getName().endsWith("stderr")) {
				String content = "";
				if (file.length() > 0) {
					Scanner scanner = new Scanner(file);
					content = scanner.useDelimiter("\\A").next();
					scanner.close();
				}
				// can't have anything in stderr files
				assertThat("stderr file is not empty: " + content, file.length(), is(0l));
			}
		}

		assertThat(linesFound, is(300));
		assertThat(linesUnique.size(), is(300));
	}

	private void createTestData() throws IOException {
		FileSystem fs = FileSystem.get(getYarnCluster().getConfiguration());
		Path path = new Path("/syarn-tmp/batch-files/set1/data.txt");
		FSDataOutputStream out = fs.create(path);
		for (int i = 0; i<300; i++) {
			out.writeBytes("line" + i + "\n");
		}
		out.close();
		assertTrue(fs.exists(path));
		assertThat(fs.getFileStatus(path).getLen(), greaterThan(0l));
	}

}
