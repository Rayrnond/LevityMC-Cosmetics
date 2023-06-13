package com.reflexian.levitycosmetics.utilities;

import fr.minuskube.inv.content.SlotPos;

public class InvUtils {

    public static SlotPos getPos(int slot) {
        int col=slot%9;
        int row=((slot-col)/9);
        return new SlotPos(row,col);
    }

}
