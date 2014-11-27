package org.guvnor.asset.management.client.editors.project.structure.widgets;

import com.google.gwt.user.client.ui.IsWidget;

public interface RepositoryStructureDataView extends IsWidget {

    enum ViewMode {
        CREATE_STRUCTURE,
        EDIT_SINGLE_MODULE_PROJECT,
        EDIT_MULTI_MODULE_PROJECT,
        EDIT_UNMANAGED_REPOSITORY
    }

    interface Presenter {

        void onProjectModeChange( );

        void onGroupIdChange( String groupId );

        void onArtifactIdChange( String artifactId );

        void onVersionChange( String version );

        void onInitRepositoryStructure();

        void onSaveRepositoryStructure();

        void onConvertToMultiModule();

        void onOpenSingleProject();
    }

    void setMode( ViewMode mode );

    void setPresenter( Presenter presenter );

    void setGroupId( String id );

    String getGroupId();

    void setArtifactId( String id );

    String getArtifactId();

    void setVersion( String version );

    String getVersionId();

    void clear();


}
