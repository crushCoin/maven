package com.runningfish.spmk.framework.plugin;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.JarInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.HttpServletBean;

@SuppressWarnings("serial")
public class DevResourceServlet extends HttpServletBean {

    private String defaultUrl;

    private String allowedResources;

    private String contentType;

    private boolean applyLastModified = false;

    private PathMatcher pathMatcher;

    private long startupTime;

    /*插件包含的静态资源目录名称*/
    private static String STATIC_RESOURCE_FOLDER = "resource";

    /**
     * Set the URL within the current web application from which to include
     * content if the requested path isn't found, or if none is specified in the
     * first place.
     * <p>
     * If specifying multiple URLs, they will be included one by one to build
     * the response. If last-modified determination is active, the newest
     * timestamp among those files will be used.
     * 
     * @see #setApplyLastModified
     */
    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    /**
     * Set allowed resources as URL pattern, e.g. "/WEB-INF/res/*.jsp", The
     * parameter can be any Ant-style pattern parsable by AntPathMatcher.
     * 
     * @see org.springframework.util.AntPathMatcher
     */
    public void setAllowedResources(String allowedResources) {
        this.allowedResources = allowedResources;
    }

    /**
     * Set the content type of the target resource (typically a JSP). Default is
     * none, which is appropriate when including resources.
     * <p>
     * For directly accessing resources, for example to leverage this servlet's
     * last-modified support, specify a content type here. Note that a content
     * type header in the target JSP will be ignored when including the resource
     * via a RequestDispatcher include.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Set whether to apply the file timestamp of the target resource as
     * last-modified value. Default is "false".
     * <p>
     * This is mainly intended for JSP targets that don't generate
     * session-specific or database-driven content: Such files can be cached by
     * the browser as long as the last-modified timestamp of the JSP file
     * doesn't change.
     * <p>
     * This will only work correctly with expanded WAR files that allow access
     * to the file timestamps. Else, the startup time of this servlet is
     * returned.
     */
    public void setApplyLastModified(boolean applyLastModified) {
        this.applyLastModified = applyLastModified;
    }

    /**
     * Remember the startup time, using no last-modified time before it.
     */
    @Override
    protected void initServletBean() {
        this.pathMatcher = getPathMatcher();
        this.startupTime = System.currentTimeMillis();
    }

    /**
     * Return a PathMatcher to use for matching the "allowedResources" URL
     * pattern. Default is AntPathMatcher.
     * 
     * @see #setAllowedResources
     * @see org.springframework.util.AntPathMatcher
     */
    protected PathMatcher getPathMatcher() {
        return new AntPathMatcher();
    }

    /**
     * Determine the URL of the target resource and include it.
     * 
     * @see #determineResourceUrl
     */
    @Override
    protected final void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        System.out.println("request.getPathInfo()-->"+request.getPathInfo());
    	String moduleName = getModuleName(request.getPathInfo());
        // determine URL of resource to include
        String resourceUrl = determineResourceUrl(request);

        if (resourceUrl != null) {
            try {
                doInclude(request, response, moduleName, resourceUrl);
            } catch (ServletException ex) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Failed to include content of resource ["
                            + resourceUrl + "]", ex);
                }
                // Try including default URL if appropriate.
                if (!includeDefaultUrl(request, response)) {
                    throw ex;
                }
            } catch (IOException ex) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Failed to include content of resource ["
                            + resourceUrl + "]", ex);
                }
                // Try including default URL if appropriate.
                if (!includeDefaultUrl(request, response)) {
                    throw ex;
                }
            }
        }

        // no resource URL specified -> try to include default URL.
        else if (!includeDefaultUrl(request, response)) {
            throw new ServletException(
                    "No target resource URL found for request");
        }
    }

    /**
     * 私有化资源询址实现
     * 
     * @param request
     * @return
     */
    protected String determineResourceUrl(HttpServletRequest request) {
    	String resourceUrl = String.format("%s/", STATIC_RESOURCE_FOLDER);
        String _moduleName = getModuleName(request.getPathInfo());

        if (null == _moduleName) {
            resourceUrl += request.getPathInfo();
        } else if (0 == request.getPathInfo().indexOf("/")
                && _moduleName != null) {
            resourceUrl += request.getPathInfo().substring(
                    _moduleName.length() + 2);
        } else if (-1 == request.getPathInfo().indexOf("/")
                && _moduleName != null) {
            resourceUrl += request.getPathInfo().substring(
                    _moduleName.length() + 1);
        }
        System.out.println("resourceUrl-->"+resourceUrl);
        return resourceUrl;
    }

    /**
     * 获取资源模块名称
     * 
     * @param pathInfo
     * @return
     */
    // TODO 可用StringTokenizer处理
    private String getModuleName(String pathInfo) {
        String moduleName = null;
        // 过滤掉第一个/
        if (0 == pathInfo.indexOf("/")) {
            pathInfo = pathInfo.substring(pathInfo.indexOf("/") + 1);
        }
        // 获取模块名称，规则:
        // 1、获取第一个/前的字符
        // 2、如没有/时,获取整个字符，但不能是静态资源
        // pathInfo中含有/时，/前的字符串即为模块名称
        if (-1 != pathInfo.indexOf("/")) {
            moduleName = pathInfo.substring(0, pathInfo.indexOf("/"));
        }
        // pathInfo中不含有/时, 同时也没有携带资源，pathInfo即为模块名称
        else if (-1 == pathInfo.indexOf("/") && -1 == pathInfo.indexOf(".")) {
            moduleName = pathInfo;
        }
        return moduleName;
    }

    /**
     * Include the specified default URL, if appropriate.
     * 
     * @param request
     *            current HTTP request
     * @param response
     *            current HTTP response
     * @return whether a default URL was included
     * @throws ServletException
     *             if thrown by the RequestDispatcher
     * @throws IOException
     *             if thrown by the RequestDispatcher
     */
    private boolean includeDefaultUrl(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        if (this.defaultUrl == null) {
            return false;
        }

        String moduleName = getModuleName(request.getPathInfo());
        doInclude(request, response, moduleName, this.defaultUrl);
        return true;
    }

    /**
     * Include the specified resource via the RequestDispatcher.
     * 
     * @param request
     *            current HTTP request
     * @param response
     *            current HTTP response
     * @param resourceUrl
     *            the URL of the target resource
     * @throws ServletException
     *             if thrown by the RequestDispatcher
     * @throws IOException
     *             if thrown by the RequestDispatcher
     */
    private void doInclude(HttpServletRequest request,
            HttpServletResponse response, String moduleName, String resourceUrl)
            throws ServletException, IOException {

        if (this.contentType != null) {
            response.setContentType(this.contentType);
        }

        //修正component没有注册时，将请求委托给JEE容器处理。
        if (null == moduleName || !PluginsMananger.pluginsMap.containsKey(moduleName)) {
            // check whether URL matches allowed resources
            if (this.allowedResources != null
                    && !this.pathMatcher.match(this.allowedResources,
                            resourceUrl)) {
                throw new ServletException("Resource [" + resourceUrl
                        + "] does not match allowed pattern ["
                        + this.allowedResources + "]");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Including resource [" + resourceUrl + "]");
            }
            response.sendRedirect(request.getRequestURI().replaceFirst(request.getServletPath(), ""));
        } else {
            writeResourceContent(response, moduleName, resourceUrl);
        }
    }

    /**
     * 
     * @param response
     * @param moduleName
     * @param resourceUrl
     */
    // TODO 开发模式下处理流程
    private void writeResourceContent(HttpServletResponse response,
            String moduleName, String resourceUrl) {
        JarInputStream zipIn = null;
        OutputStream out = null;
        String pluginFile = PluginsMananger.pluginsMap.get(moduleName);
        DataInputStream dataIn = null;
        try {
            zipIn = new JarInputStream(new FileInputStream(pluginFile));
            String snapsStaticResourceDevPath = zipIn.getManifest()
                    .getMainAttributes()
                    .getValue("Snaps-StaticResourceDevPath");
            File staticFile = new File(String.format("%s%s%s",
                    snapsStaticResourceDevPath, File.separator, resourceUrl));

            // 文件不存在时，返回403错误
            if (!(staticFile.isFile() && staticFile.canRead())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            dataIn = new DataInputStream(new FileInputStream(staticFile));
            // 文件存在时返回文件内容
            // 写文件到响应流中
            byte[] buf = new byte[1024];
            int bufsize = 0;
            out = response.getOutputStream();
            while ((bufsize = dataIn.read(buf, 0, buf.length)) != -1) {
                out.write(buf, 0, bufsize);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } finally {
            if (null != response) {
                try {
                    response.flushBuffer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != dataIn) {
                try {
                    dataIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != zipIn) {
                try {
                    zipIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Return the last-modified timestamp of the file that corresponds to the
     * target resource URL (i.e. typically the request ".jsp" file). Will simply
     * return -1 if "applyLastModified" is false (the default).
     * <p>
     * Returns no last-modified date before the startup time of this servlet, to
     * allow for message resolution etc that influences JSP contents, assuming
     * that those background resources might have changed on restart.
     * <p>
     * Returns the startup time of this servlet if the file that corresponds to
     * the target resource URL coudln't be resolved (for example, because the
     * WAR is not expanded).
     * 
     * @see #determineResourceUrl
     * @see #getFileTimestamp
     */
    @Override
    protected final long getLastModified(HttpServletRequest request) {
        if (this.applyLastModified) {
            String resourceUrl = determineResourceUrl(request);
            if (resourceUrl == null) {
                resourceUrl = this.defaultUrl;
            }
            if (resourceUrl != null) {
                long latestTimestamp = -1;
                long timestamp = getFileTimestamp(resourceUrl);
                if (timestamp > latestTimestamp) {
                    latestTimestamp = timestamp;
                }
                return (latestTimestamp > this.startupTime ? latestTimestamp
                        : this.startupTime);
            }
        }
        return -1;
    }

    /**
     * Return the file timestamp for the given resource.
     * 
     * @param resourceUrl
     *            the URL of the resource
     * @return the file timestamp in milliseconds, or -1 if not determinable
     */
    protected long getFileTimestamp(String resourceUrl) {
        ServletContextResource resource = new ServletContextResource(
                getServletContext(), resourceUrl);
        try {
            long lastModifiedTime = resource.lastModified();
            if (logger.isDebugEnabled()) {
                logger.debug("Last-modified timestamp of " + resource + " is "
                        + lastModifiedTime);
            }
            return lastModifiedTime;
        } catch (IOException ex) {
            logger.warn("Couldn't retrieve last-modified timestamp of ["
                    + resource + "] - using ResourceServlet startup time");
            return -1;
        }
    }

}

