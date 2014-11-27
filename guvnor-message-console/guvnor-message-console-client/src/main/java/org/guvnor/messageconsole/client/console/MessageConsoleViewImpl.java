/*
 * Copyright 2014 JBoss Inc
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.guvnor.messageconsole.client.console;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import org.guvnor.messageconsole.client.console.resources.MessageConsoleResources;
import org.guvnor.messageconsole.events.SystemMessage;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.ext.widgets.common.client.common.BusyPopup;
import org.uberfire.ext.widgets.common.client.tables.SimpleTable;

@ApplicationScoped
public class MessageConsoleViewImpl extends Composite implements MessageConsoleView {

    @Inject
    private PlaceManager placeManager;

    @Inject
    private MessageConsoleService consoleService;

    protected final SimpleTable<MessageConsoleServiceRow> dataGrid = new SimpleTable<MessageConsoleServiceRow>();

    public MessageConsoleViewImpl() {
        addLevelColumn();
        addTextColumn();
        addFileNameColumn();
        addColumnColumn();
        addLineColumn();

        initWidget( dataGrid );
    }

    @PostConstruct
    public void setupDataDisplay() {
        consoleService.addDataDisplay( dataGrid );
    }

    private void addLineColumn() {
        Column<MessageConsoleServiceRow, ?> lineColumn = new Column<MessageConsoleServiceRow, String>( new TextCell() ) {
            @Override
            public String getValue( MessageConsoleServiceRow row ) {
                return row != null ? Integer.toString( row.getMessageLine() ) : null;
            }
        };
        dataGrid.addColumn( lineColumn,
                MessageConsoleResources.CONSTANTS.Line() );
        dataGrid.setColumnWidth( lineColumn,
                60,
                Style.Unit.PCT );
    }

    private void addColumnColumn() {
        Column<MessageConsoleServiceRow, ?> column = new Column<MessageConsoleServiceRow, String>( new TextCell() ) {
            @Override
            public String getValue( MessageConsoleServiceRow row ) {
                return Integer.toString( row.getMessageColumn() );
            }
        };
        dataGrid.addColumn( column,
                MessageConsoleResources.CONSTANTS.Column() );
        dataGrid.setColumnWidth( column,
                60,
                Style.Unit.PCT );
    }

    private void addTextColumn() {
        Column<MessageConsoleServiceRow, ?> column = new Column<MessageConsoleServiceRow, String>( new TextCell() ) {
            @Override
            public String getValue( MessageConsoleServiceRow row ) {
                return row.getMessageText();
            }

            @Override
            public void render( Cell.Context context, MessageConsoleServiceRow row, SafeHtmlBuilder sb ) {
                String title = row.getMessageText();
                sb.append( createDivStart(title) );
                super.render( context, row, sb );
                sb.append( createDivEnd() );
            }

        };
        dataGrid.addColumn( column,
                MessageConsoleResources.CONSTANTS.Text() );
        dataGrid.setColumnWidth( column,
                60,
                Style.Unit.PCT );
    }

    private void addFileNameColumn() {
        Column<MessageConsoleServiceRow, HyperLinkCell.HyperLink> column = new Column<MessageConsoleServiceRow, HyperLinkCell.HyperLink>( new HyperLinkCell() ) {
            @Override
            public HyperLinkCell.HyperLink getValue( MessageConsoleServiceRow row ) {
                if ( row.getMessagePath() != null ) {
                    return HyperLinkCell.HyperLink.newLink( row.getMessagePath().getFileName() );
                } else {
                    return HyperLinkCell.HyperLink.newText( "-" );
                }
            }
        };
        column.setFieldUpdater( new FieldUpdater<MessageConsoleServiceRow, HyperLinkCell.HyperLink>() {
            @Override
            public void update( final int index,
                    final MessageConsoleServiceRow row,
                    final HyperLinkCell.HyperLink value ) {
                if ( row.getMessagePath() != null ) {
                    placeManager.goTo( row.getMessagePath() );
                }
            }
        } );
        dataGrid.addColumn( column,
                MessageConsoleResources.CONSTANTS.FileName() );
        dataGrid.setColumnWidth( column,
                60,
                Style.Unit.PCT );
    }

    private void addLevelColumn() {
        Column<MessageConsoleServiceRow, ?> column = new Column<MessageConsoleServiceRow, ImageResource>( new ImageResourceCell() ) {
            @Override
            public ImageResource getValue( MessageConsoleServiceRow row ) {
                switch ( row.getMessageLevel() ) {
                    case ERROR:
                        return MessageConsoleResources.INSTANCE.Error();
                    case WARNING:
                        return MessageConsoleResources.INSTANCE.Warning();
                    case INFO:
                    default:
                        return MessageConsoleResources.INSTANCE.Information();
                }
            }

            @Override
            public void render( Cell.Context context,
                    MessageConsoleServiceRow row,
                    SafeHtmlBuilder sb ) {
                String title = getLevelTitle( row.getMessageLevel() );
                sb.append( createDivStart( title ) );
                super.render( context, row, sb );
                sb.append( createDivEnd() );
            }
        };
        dataGrid.addColumn( column,
                MessageConsoleResources.CONSTANTS.Level() );
        dataGrid.setColumnWidth( column,
                60,
                Style.Unit.PCT );
    }

    @Override
    public void showBusyIndicator( final String message ) {
        BusyPopup.showMessage( message );
    }

    @Override
    public void hideBusyIndicator() {
        BusyPopup.close();
    }


    private String getLevelTitle( SystemMessage.Level level ) {
        switch ( level ) {
            case ERROR:
                return MessageConsoleResources.CONSTANTS.ErrorLevelTitle();
            case WARNING:
                return MessageConsoleResources.CONSTANTS.WarningLevelTitle();
            case INFO:
            default:
                return MessageConsoleResources.CONSTANTS.InfoLevelTitle();
        }
    }

    private SafeHtml createDivStart( String title ) {
        return createDivStart( title, "" );
    }

    private SafeHtml createDivStart(String title, String defaultValue) {
        if (title == null || "".equals(title)) title = defaultValue;
        return SafeHtmlUtils.fromTrustedString( "<div title=\"" + SafeHtmlUtils.htmlEscape( title.trim() ) + "\">" );
    }

    private SafeHtml createDivEnd() {
        return SafeHtmlUtils.fromTrustedString( "</div>" );
    }
}