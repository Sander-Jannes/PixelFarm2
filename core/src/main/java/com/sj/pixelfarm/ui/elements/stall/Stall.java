package com.sj.pixelfarm.ui.elements.stall;


import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.sj.pixelfarm.Entities;
import com.sj.pixelfarm.core.ui.UIUtils;
import com.sj.pixelfarm.order.Order;
import com.sj.pixelfarm.order.OrderGenerator;
import com.sj.pixelfarm.ui.elements.modals.Modal;

import java.util.ArrayList;


public class Stall extends Modal {

    private static final String IMAGE = "shop/shop";

    public Stall() {
        super(IMAGE, Entities.STALL);

        ArrayList<Order> orders = new ArrayList<>(OrderGenerator.generateOrders(9));
        VerticalGroup orderList = new VerticalGroup().space(80);

        for (Order order : orders) {
            OrderCard card = new OrderCard(order);
            orderList.addActor(card);
        }

        modalTable.row().expandY();

        ScrollPane pane = UIUtils.createScrollPane(orderList, p -> {
            p.setFadeScrollBars(false);
            p.setForceScroll(false, true);
            p.setScrollingDisabled(true, false);
        });

        modalTable.add(pane).pad(10);
    }
}
