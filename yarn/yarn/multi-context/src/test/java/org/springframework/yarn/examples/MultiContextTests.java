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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.Timed;
import org.springframework.yarn.test.junit.AbstractYarnClusterTests;
import org.springframework.yarn.test.junit.ApplicationInfo;
import org.springframework.yarn.test.support.ContainerLogUtils;

/**
 * Base test for multi context example.
 * Tests for multi context example. We're checking that
 * application status is ok and log files looks
 * what is expected.
 *
 * @author Janne Valkealahti
 *
 */
public abstract class MultiContextTests extends AbstractYarnClusterTests {

	@Test
	@Timed(millis=120000)
	public void testAppSubmission() throws Exception {
		ApplicationInfo info = submitApplicationAndWait();
		assertNotNull(info.getYarnApplicationState());
		assertThat(info.getYarnApplicationState(), is(YarnApplicationState.FINISHED));

		List<Resource> resources = ContainerLogUtils.queryContainerLogs(getYarnCluster(), info.getApplicationId());

		// appmaster and 4 containers should
		// make it 10 log files
		assertThat(resources, notNullValue());
		assertThat(resources.size(), is(10));

		for (Resource res : resources) {
			File file = res.getFile();
			if (file.getName().endsWith("stdout")) {
				// there has to be some content in stdout file
				assertThat(file.length(), greaterThan(0l));
				if (file.getName().equals("Container.stdout")) {
					Scanner scanner = new Scanner(file);
					String content = scanner.useDelimiter("\\A").next();
					scanner.close();
					// this is what container will log in stdout
					assertThat(content, containsString("Hello from MultiContextContainer"));
				}
			} else if (file.getName().endsWith("stderr")) {
				// can't have anything in stderr files
				assertThat(file.length(), is(0l));
			}
		}
	}

}
