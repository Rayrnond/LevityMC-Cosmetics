package com.reflexian.levitycosmetics.utilities.uncategorizied;

import com.reflexian.levitycosmetics.data.Database;

public abstract class DataService {

    protected Database getDatabase() {
        return Database.shared;
    }

}
