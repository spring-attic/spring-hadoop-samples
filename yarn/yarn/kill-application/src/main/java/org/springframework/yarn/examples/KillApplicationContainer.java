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

import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.yarn.container.YarnContainer;

/**
 * Container which just sleeps.
 *
 * @author Janne Valkealahti
 *
 */
public class KillApplicationContainer implements YarnContainer {

	private static final Log log = LogFactory.getLog(KillApplicationContainer.class);

	@Override
	public void run() {
		log.info("Hello from KillApplicationContainer, gonna sleep next 2 minutes...");
		for(int i = 0; i<24; i++) {
			log.info("Waiting to get killed");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
		}
		log.info("Hello from KillApplicationContainer, it seems I wasn't killed.");
	}

	@Override
	public void setEnvironment(Map<String, String> environment) {
	}

	@Override
	public void setParameters(Properties parameters) {
	}

}
