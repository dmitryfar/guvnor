/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.guvnor.asset.management.client.editors.repository.wizard.pages;

import java.util.Collection;

import org.guvnor.structure.organizationalunit.OrganizationalUnit;
import org.uberfire.client.mvp.UberView;

public interface RepositoryInfoPageView
        extends
        UberView<RepositoryInfoPageView.Presenter> {

    static final String NOT_SELECTED = "NOT_SELECTED";

    interface Presenter {

        void stateChanged();

        String getName();

    }

    String getName();

    void setName( String name );

    void setValidName( final boolean isValid );

    void initOrganizationalUnits( Collection<OrganizationalUnit> organizationalUnits );

    String getOrganizationalUnitName();

    void setVisibleOU( boolean visible );

    void setValidOU( boolean ouValid );

    boolean isManagedRepository();

    void enabledManagedRepositoryCreation( boolean enabled );

}
