package com.reflexian.levitycosmetics.data.objects.cosmetics;

import lombok.Getter;

public enum CosmeticType {


    CHAT_COLOR(0),
    TAB_COLOR(1),
    CROWN(2),
    GLOW(3),
    HAT(4),
    ASSIGNED_NICKNAME(5),
    NICKNAME_PAINT(6),
    TITLE(7),
    TITLE_PAINT(8),
    ASSIGNED_TITLE(9),
    ASSIGNED_HAT(10);


    @Getter
    private int identifier;

    private CosmeticType(int identifier) {
        this.identifier = identifier;
    }

    public static CosmeticType fromIdentifier(int identifier) {
        for (CosmeticType cosmeticType : values()) {
            if (cosmeticType.getIdentifier() == identifier) {
                return cosmeticType;
            }
        }
        return null;
    }

}
