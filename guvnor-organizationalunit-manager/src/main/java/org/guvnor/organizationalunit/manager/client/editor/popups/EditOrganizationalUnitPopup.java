/*
 * Copyright 2013 JBoss Inc
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

package org.guvnor.organizationalunit.manager.client.editor.popups;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import org.guvnor.organizationalunit.manager.client.editor.OrganizationalUnitManagerPresenter;
import org.guvnor.organizationalunit.manager.client.resources.i18n.OrganizationalUnitManagerConstants;
import org.guvnor.structure.organizationalunit.OrganizationalUnit;
import org.uberfire.client.mvp.UberView;
import org.uberfire.ext.widgets.common.client.common.popups.BaseModal;
import org.uberfire.ext.widgets.common.client.common.popups.footers.ModalFooterOKCancelButtons;

public class EditOrganizationalUnitPopup extends BaseModal implements UberView<OrganizationalUnitManagerPresenter> {

    interface EditOrganizationalUnitPopupBinder
            extends
            UiBinder<Widget, EditOrganizationalUnitPopup> {

    }

    private static EditOrganizationalUnitPopupBinder uiBinder = GWT.create( EditOrganizationalUnitPopupBinder.class );

    @UiField
    TextBox nameTextBox;

    @UiField
    TextBox ownerTextBox;

    private OrganizationalUnit organizationalUnit;

    private OrganizationalUnitManagerPresenter presenter;

    private final Command okCommand = new Command() {
        @Override
        public void execute() {
            onOKButtonClick();
        }
    };

    private final Command cancelCommand = new Command() {
        @Override
        public void execute() {
            hide();
        }
    };

    private final ModalFooterOKCancelButtons footer = new ModalFooterOKCancelButtons( okCommand,
                                                                                      cancelCommand );

    public EditOrganizationalUnitPopup() {
        setTitle( OrganizationalUnitManagerConstants.INSTANCE.EditOrganizationalUnitPopupTitle() );

        add( uiBinder.createAndBindUi( this ) );
        add( footer );
    }

    @Override
    public void init( final OrganizationalUnitManagerPresenter presenter ) {
        this.presenter = presenter;
    }

    private void onOKButtonClick() {
        presenter.saveOrganizationalUnit( nameTextBox.getText(),
                                          ownerTextBox.getText() );
        hide();
    }

    public void setOrganizationalUnit( final OrganizationalUnit organizationalUnit ) {
        this.organizationalUnit = organizationalUnit;
    }

    @Override
    public void show() {
        if ( organizationalUnit == null ) {
            nameTextBox.setText( "" );
            ownerTextBox.setText( "" );
        } else {
            nameTextBox.setText( organizationalUnit.getName() );
            ownerTextBox.setText( organizationalUnit.getOwner() );
        }
        super.show();
    }

}
