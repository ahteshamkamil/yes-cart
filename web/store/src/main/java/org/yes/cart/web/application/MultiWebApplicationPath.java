package org.yes.cart.web.application;

import org.apache.wicket.util.file.File;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.file.IResourcePath;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.UrlResourceStream;
import org.apache.wicket.util.string.StringList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yes.cart.util.ShopCodeContext;

import javax.servlet.ServletContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Igor Azarny iazarny@yahoo.com
 * Date: 7/10/11
 * Time: 9:13 AM
 */
public class MultiWebApplicationPath   implements IResourcePath {

    private static final Logger LOG = LoggerFactory.getLogger(ShopCodeContext.getShopCode());

    /** The list of urls in the path */
    private final List<String> webappPaths = new ArrayList<String>();

    /** The list of folders in the path */
    private final List<Folder> folders = new ArrayList<Folder>();

    /** The web apps servlet context */
    private final ServletContext servletContext;

    /**
     * Constructor
     *
     * @param servletContext
     *            The webapplication context where the resources must be loaded from
     */
    public MultiWebApplicationPath(final ServletContext servletContext)
    {
        this.servletContext = servletContext;
    }

    /**
     * @param path
     *            add a path that is lookup through the servlet context
     */
    public void add(String path)
    {
        final Folder folder = new Folder(path);
        if (folder.exists())
        {
            folders.add(folder);
        }
        else
        {
            if (!path.startsWith("/"))
            {
                path = "/" + path;
            }
            if (!path.endsWith("/"))
            {
                path += "/";
            }
            webappPaths.add(path);
        }
    }

    /**
     *
     * @see org.apache.wicket.util.file.IResourceFinder#find(Class, String)
     */
    public IResourceStream find(final Class<?> clazz, final String pathname)
    {
        Iterator<Folder> foldersIter = folders.iterator();
        while (foldersIter.hasNext())  {
            Folder folder = foldersIter.next();
            final File file = new File(folder, pathname);
            if (file.exists()) {
                return new FileResourceStream(file);
            }
        }

        Iterator<String> webappPathsIter = webappPaths.iterator();
        while (webappPathsIter.hasNext()) {
            String path = webappPathsIter.next();
            try {
                final URL url = servletContext.getResource(path + pathname);
                if (url != null) {
                    //if (LOG.isDebugEnabled()) {
                        LOG.debug("Retrieving resource: " + path + pathname);
                    //}

                    return new UrlResourceStream(url);
                }
                //if (LOG.isDebugEnabled()) {
                    LOG.debug("Lookup resource: " + path + pathname);
                //}
            }
            catch (Exception ex)  {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("File couldn't be found: " + path + pathname);
                }
            }
        }

        return null;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()  {
        return "[folders = " + StringList.valueOf(folders) + ", webapppaths: " +
            StringList.valueOf(webappPaths) + "]";
    }

}