package com.reflexian.levitycosmetics.data.database;

public interface Callback {
    void onSuccess();

    void onError(int var1);

    void onExist();
}
