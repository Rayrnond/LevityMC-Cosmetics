package com.reflexian.levitycosmetics;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

public class CosmeticsPluginLoader implements PluginLoader {

    @Override
    public void classloader(@NotNull PluginClasspathBuilder pluginClasspathBuilder) {
        MavenLibraryResolver rapi = new MavenLibraryResolver();
        rapi.addRepository(new RemoteRepository.Builder("jitpack", "default", "https://jitpack.io").build());
        rapi.addDependency(new Dependency(new DefaultArtifact("com.github.Rayrnond:RAPI:b85ffda6b6"), null));

//        MavenLibraryResolver hibernate = new MavenLibraryResolver();
//        hibernate.addDependency(new  Dependency(new DefaultArtifact("org.hibernate:hibernate-core:6.2.3.Final"), null));

        MavenLibraryResolver mysql = new MavenLibraryResolver();
        mysql.addDependency(new Dependency(new DefaultArtifact("mysql:mysql-connector-java:8.0.28"), null));

        pluginClasspathBuilder.addLibrary(rapi);
//        pluginClasspathBuilder.addLibrary(hibernate);
        pluginClasspathBuilder.addLibrary(mysql);
    }

}
