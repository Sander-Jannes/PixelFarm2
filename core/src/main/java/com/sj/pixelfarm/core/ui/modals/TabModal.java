package com.sj.pixelfarm.core.ui.modals;


public class TabModal<T> extends Modal {

    private final TabBar<T> tabBar;

    public TabModal(String image, T initialTab) {
        super(image);
        this.tabBar = new TabBar<>(initialTab);
        tabBar.space(20);

        table.add(tabBar).left().padLeft(40).top().padTop(50);
        table.row().expand();
    }

    public void addTab(String text, T tab, Runnable action) {
        tabBar.addButton(text, tab, action);
    }

    public T getActiveTab() {
        return tabBar.getActiveTab();
    }

    public void updateBar() {
        tabBar.update();
    }
}
