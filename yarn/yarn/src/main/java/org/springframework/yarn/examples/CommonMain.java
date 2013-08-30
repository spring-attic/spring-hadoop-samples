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

import org.springframework.yarn.YarnSystemConstants;
import org.springframework.yarn.client.CommandLineClientRunner;

/**
 * Main class for examples submitting application.
 *
 * @author Janne Valkealahti
 *
 */
public class CommonMain extends CommandLineClientRunner {

	public static void main(String args[]) {
		new CommonMain().doMain(new String[] {
				YarnSystemConstants.DEFAULT_CONTEXT_FILE_CLIENT,
				YarnSystemConstants.DEFAULT_ID_CLIENT,
				CommandLineClientRunner.OPT_SUBMIT
		});
	}

}
