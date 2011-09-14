/*
 * Copyright 2011 JBoss Inc
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

package org.drools.guvnor.server.repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;
import javax.inject.Named;
import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.drools.repository.AssetItem;
import org.drools.repository.JCRRepositoryConfigurator;
import org.drools.repository.RulesRepository;
import org.drools.repository.RulesRepositoryAdministrator;
import org.drools.repository.RulesRepositoryConfigurator;
import org.drools.repository.RulesRepositoryException;
import org.drools.repository.events.CheckinEvent;
import org.drools.repository.events.StorageEventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This startup class manages the JCR repository, sets it up if necessary.
 */
@ApplicationScoped
@Alternative // @Specializes
public class TestRepositoryStartupService extends RepositoryStartupService {

    @Override
    public Repository getRepositoryInstance() {
        System.out.println("blub");
        System.out.println("blub");
        System.out.println("blub");
        System.out.println("blub");
        System.out.println("blub");
        return super.getRepositoryInstance();
    }

}
