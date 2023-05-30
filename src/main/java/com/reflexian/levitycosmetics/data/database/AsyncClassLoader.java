package com.reflexian.levitycosmetics.data.database;

import com.reflexian.levitycosmetics.LevityCosmetics;

import java.io.File;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class AsyncClassLoader {

    public static final AsyncClassLoader shared = new AsyncClassLoader();

    public void loadLibrariesAsync(Callback callback) {
        Thread thread = new Thread(() -> {
            File libsFolder = new File(LevityCosmetics.getInstance().getDataFolder() + File.separator + "libs");
            int loadedLibraries = 0;
            if (!libsFolder.exists()) {
                libsFolder.mkdir();
            }

            for (File file : libsFolder.listFiles()) {
                try {
                    URL jarUrl = new URL("file://" + file.getPath());
                    URLClassLoader classLoader = (URLClassLoader) LevityCosmetics.getInstance().getPluginLoader();
                    Class<?> urlClass = URLClassLoader.class;

                    // Use reflection to access the addURL method of URLClassLoader
                    java.lang.reflect.Method method = urlClass.getDeclaredMethod("addURL", URL.class);
                    method.setAccessible(true);
                    method.invoke(classLoader, jarUrl);
                    callback.onSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onError(1);
                }
            }


        });

        thread.start();
    }
}
