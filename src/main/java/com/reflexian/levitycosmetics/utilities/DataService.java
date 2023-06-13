package com.reflexian.levitycosmetics.utilities;

import com.reflexian.levitycosmetics.data.Database;

import java.util.concurrent.CompletableFuture;

public abstract class DataService {

    protected Database getDatabase() {
        return Database.shared;
    }

}
