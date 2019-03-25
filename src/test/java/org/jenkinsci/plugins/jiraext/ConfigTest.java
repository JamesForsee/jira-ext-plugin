/***************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 **************************************************************************/
package org.jenkinsci.plugins.jiraext;

import hudson.util.Secret;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import static org.junit.Assert.*;

/**
 * @author dalvizu
 */
public class ConfigTest
{

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    @Before
    public void setUp() throws Exception
    {

    }

    @After
    public void tearDown() throws Exception
    {

    }

    @Test
    public void testSaveConfig()
        throws Exception
    {
        Config.PluginDescriptor before = Config.getGlobalConfig();
        before.setPattern("FOO-");
        before.setUsername("username");
        before.setEncryptedPassword(Secret.fromString("password"));
        before.setVerboseLogging(true);
        jenkinsRule.configRoundtrip();
        Config.PluginDescriptor after = Config.getGlobalConfig();
        jenkinsRule.assertEqualBeans(before, after, "jiraBaseUrl,username,encryptedPassword,pattern,verboseLogging");
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testMigrationToEncryptedPassword()
            throws Exception
    {
        Config.PluginDescriptor config = Config.getGlobalConfig();
        config.setPattern("FOO-");
        config.setUsername("username");
        config.setPassword("password");
        config.setVerboseLogging(true);
        config.readResolve();
        assertNull(config.getPassword());
        assertEquals("password", config.getEncryptedPassword().getPlainText());

    }

}