package com.sj.pixelfarm.ui.elements.modals;


public class TabModal<T> extends Modal {

    private final TabBar<T> tabBar;

    public TabModal(String image, String name, T initialTab) {
        super(image, name);
        this.tabBar = new TabBar<>(initialTab);
        tabBar.space(20);

        modalTable.add(tabBar).left().padLeft(40).top().padTop(50);
        modalTable.row().expand();
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
