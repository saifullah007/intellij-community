/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.plugins.gradle.execution.test.runner.events;

import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.gradle.execution.test.runner.GradleSMTestProxy;
import org.jetbrains.plugins.gradle.execution.test.runner.GradleTestsExecutionConsole;

/**
 * @author Vladislav.Soroka
 * @since 2/28/14
 */
public class BeforeSuiteEvent extends AbstractTestEvent {
  public BeforeSuiteEvent(GradleTestsExecutionConsole executionConsole) {
    super(executionConsole);
  }

  @Override
  public void process(@NotNull final TestEventXmlView eventXml) throws TestEventXmlView.XmlParserException {
    final String testId = eventXml.getTestId();
    final String parentTestId = eventXml.getParentTestId();
    final String name = eventXml.getTestName();
    final String fqClassName = eventXml.getTestClassName();

    if (StringUtil.isEmpty(parentTestId)) {
      registerTestProxy(testId, getResultsViewer().getTestsRootNode());
    }
    else {
      String locationUrl = findLocationUrl(null, fqClassName);
      final GradleSMTestProxy testProxy = new GradleSMTestProxy(name, true, locationUrl, null);
      testProxy.setLocator(getExecutionConsole().getUrlProvider());
      testProxy.setParentId(parentTestId);
      testProxy.setStarted();
      registerTestProxy(testId, testProxy);
    }
  }
}
