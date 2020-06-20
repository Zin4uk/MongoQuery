package com.zin4uk.query;

import com.intellij.execution.filters.HyperlinkWithPopupMenuInfo;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.intellij.util.PlatformIcons;
import com.zin4uk.query.match.QueryLinkMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;

public class QueryHyperlinkInfo implements HyperlinkWithPopupMenuInfo {

    final private QueryLinkMatch query;

    public QueryHyperlinkInfo(QueryLinkMatch query) {
        this.query = query;
    }

    @Nullable
    @Override
    public ActionGroup getPopupMenuGroup(@NotNull MouseEvent mouseEvent) {
        final DefaultActionGroup group = new DefaultActionGroup();

        group.add(new AnAction(() -> "Copy formatted query", PlatformIcons.COPY_ICON) {
            public void actionPerformed(@NotNull AnActionEvent e) {
                CopyPasteManager.getInstance().setContents(new StringSelection(query.getFormattedQuery()));
            }
        });

        group.add(new AnAction(() -> "Copy raw query", PlatformIcons.COPY_ICON) {
            public void actionPerformed(@NotNull AnActionEvent e) {
                CopyPasteManager.getInstance().setContents(new StringSelection(query.getQuery()));
            }
        });

        return group;
    }

    @Override
    public void navigate(Project project) {
        CopyPasteManager.getInstance().setContents(new StringSelection(query.getFormattedQuery()));
    }
}
