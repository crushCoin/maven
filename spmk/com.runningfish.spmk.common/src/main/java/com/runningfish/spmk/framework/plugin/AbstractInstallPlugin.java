package com.runningfish.spmk.framework.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public abstract class AbstractInstallPlugin implements InitializingBean {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static private String META_INF_DIRECTORY = "META-INF";
    static private String MANIFEST_NAME = "MANIFEST.MF";
    static private String MANIFEST_FILE_PATH = String.format("%s%s%s", META_INF_DIRECTORY, File.pathSeparator,
            MANIFEST_NAME);

    static private String SNAP_CONTEXTPATH = "Snap-ContextPath";

    @Resource
    private PluginsMananger pluginsMananger;

    /**
     * 获取当前JAR的文件路径
     * 
     * @return {@link String} JAR的文件路径
     */
    public String getPluginFile() {
        String pluginPath = null;
        String webappRoot = System.getProperty("webapp.root");
        if (null != webappRoot && webappRoot.trim().length() > 0) {
            // 获取plugin的文件名
            String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
            String pfn = jarPath.substring((jarPath.lastIndexOf(File.separator) + 1), jarPath.length());
            // 拼装plugin在中间件中真实的层放地址
            pluginPath = String.format("%s%s%s%s%s%s%s", webappRoot, File.separator, "WEB-INF", File.separator, "lib",
                    File.separator, pfn);
            if (!new File(pluginPath).exists()) {
                pluginPath = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
            }

        } else {
            pluginPath = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        }

        Assert.notNull(pluginPath, String.format("plugin file path=[{}] is not exist", pluginPath));

        if (log.isInfoEnabled()) {
            log.info("check plugin file path=[{}]", pluginPath);
        }

        return pluginPath;
    }

    /**
     * 获取插件定义的上下文，如获取不到上下文时，向外抛出IllegalAccessError
     * 
     * @return 插件上下文
     * @throws IllegalAccessError
     */
    public String getSnapContextPath4Plugin() throws IllegalAccessError {
        String snapContextPath = null;
        String pluginFilePath = getPluginFile();
        //log.info("getSnapContextPath4Plugin-->pluginFile:"+pluginFilePath);
        File pluginFile = new File(pluginFilePath);
        if (!pluginFile.canRead()) {
            throw new IllegalArgumentException(String.format("plugin File Path=[{}] not can read", pluginFilePath));
        }

        if (pluginFile.isFile()) {
            snapContextPath = getSnapContext4Jar(pluginFile);
        } else if (pluginFile.isDirectory()) {
            snapContextPath = getSnapContext4Directory(pluginFile);
        }
        if (snapContextPath == null || snapContextPath.trim().length() == 0) {
            if (log.isErrorEnabled()) {
                log.error("plugin file path=[{}] is not exist [{}] in [{}] file", new String[] { pluginFilePath, SNAP_CONTEXTPATH,
                        MANIFEST_FILE_PATH });
            }
        }

        return snapContextPath;
    }

    private String getSnapContext4Directory(File pluginFile) {
        Assert.notNull(pluginFile, String.format("plugin File Path=[{}] parameter is null", pluginFile.getPath()));

        String snapContext = null;
        File manifestFile = getManifestFile(pluginFile);
        Assert.notNull(manifestFile,
                String.format("[{}] is not exist in plugin File Path=[{}]", MANIFEST_NAME, pluginFile.getPath()));

        try {
            Manifest manifest = new Manifest(new FileInputStream(manifestFile));
            snapContext = manifest.getMainAttributes().getValue(SNAP_CONTEXTPATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return snapContext;
    }

    /**
     * 
     * @param pluginDirectory
     * @return
     */
    private File getManifestFile(File pluginDirectory) {
        for (File metaInfo : pluginDirectory.listFiles()) {
            if (metaInfo.getName().lastIndexOf(META_INF_DIRECTORY) != -1) {
                for (File manifest : metaInfo.listFiles()) {
                    if (manifest.getName().lastIndexOf(MANIFEST_NAME) != -1) {
                        return manifest;

                    }
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param pluginFile
     * @return
     */
    private String getSnapContext4Jar(File pluginFile) {
        Assert.notNull(pluginFile, String.format("plugin File Path=[{}] parameter is null", pluginFile.getPath()));

        JarInputStream jarin = null;
        String snapContext = null;
        try {
            jarin = new JarInputStream(new FileInputStream(pluginFile));
            snapContext = jarin.getManifest().getMainAttributes().getValue(SNAP_CONTEXTPATH);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("plugin context=[{}] this [{}] not exist or not read", pluginFile, MANIFEST_FILE_PATH);
            }
            throw new IllegalArgumentException(String.format("plugin context=[{}] this [{}] not exist or not read", pluginFile,
                    MANIFEST_FILE_PATH));
        } finally {
            if (null != jarin) {
                try {
                    jarin.close();
                } catch (IOException e) {
                }
            }
        }
        return snapContext;
        /*
         * JarEntry jarEntry = null; while ((jarEntry = jarin.getNextJarEntry())
         * != null) { if (jarEntry.getName().equals(MANIFEST_FILE_PATH)) break;
         * } Properties pro = new Properties(); pro.load(jarin); snapContextPath
         * = pro.getProperty(SNAP_CONTEXTPATH);
         */
    }

    public void installPlugin() {
        String filePath = getPluginFile();
        String contextPath = getSnapContextPath4Plugin();
        pluginsMananger.addPlugin(contextPath, filePath);
    }

}

