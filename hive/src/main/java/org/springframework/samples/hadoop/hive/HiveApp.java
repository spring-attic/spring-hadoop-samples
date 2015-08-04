/*
 * Copyright 2011-2012 the original author or authors.
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
package org.springframework.samples.hadoop.hive;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hive.HiveTemplate;

public class HiveApp {

	private static final Log log = LogFactory.getLog(HiveApp.class);

	public static void main(String[] args) throws Exception {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"/META-INF/spring/hive-context.xml", HiveApp.class);
		log.info("Hive Application Running");
		context.registerShutdownHook();

		HiveTemplate template = context.getBean(HiveTemplate.class);
		template.query("show tables;");

		PasswordRepository repository = context.getBean(HiveTemplatePasswordRepository.class);
		repository.processPasswordFile("/user/hive/input/passwd");
		log.info("Count of password entries = " + repository.count());
        context.close();
		log.info("Hive Application Completed");
	}
}
