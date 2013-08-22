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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.yarn.client.YarnClient;

/**
 * Main class for examples.
 *
 * @author Janne Valkealahti
 *
 */
public class CommonMain {

	private static final Log log = LogFactory.getLog(CommonMain.class);

	public static void main(String args[]) {

		ConfigurableApplicationContext context = null;

		try {
			context = new ClassPathXmlApplicationContext("application-context.xml");
			System.out.println("Submitting example");
			YarnClient client = (YarnClient) context.getBean("yarnClient");
			client.submitApplication();
			System.out.println("Submitted example");
		} catch (Throwable e) {
			log.error("Error in main method", e);
		} finally {
			if (context != null) {
				context.close();
			}
		}

	}

}
