package com.acs.core.servlets;

import com.acs.core.services.FastlyPurger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.apache.sling.servlets.post.JSONResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.net.http.HttpResponse;

@Component(service = {Servlet.class}, property = {"sling.servlet.methods=POST"})
@SlingServletResourceTypes(resourceTypes = {FastlyPurgeServlet.RESOURCE_TYPE}, extensions = {"json"})
@ServiceDescription("ACS Fastly Purge Servlet")
@Slf4j
public class FastlyPurgeServlet extends SlingAllMethodsServlet {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    protected static final String RESOURCE_TYPE = "fastly-cache-purge/app";
    public static final String PARAM_PATH = "path";
    public static final String PARAM_HOST = "host";

    /**
     * The job search service
     */
    @Reference
    private transient FastlyPurger fastlyPurger;

    // this is an experamental console servlet
    @Override
    @SuppressWarnings("CQRules:CWE-676") // appropriate use of Thread#interrupt
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException, ServletException {
        String path = request.getParameter(PARAM_PATH);
        String host = request.getParameter(PARAM_HOST);
        if (StringUtils.isBlank(path)) {
            response.sendError(HttpStatus.SC_BAD_REQUEST, "Path is required");
        } else {
            try {
                // synchronous call to purge fastly cache for given path
                HttpResponse<String> fastlyResponse = purge(host, path);
                writeJsonResponse(response, fastlyResponse.body(), fastlyResponse.statusCode());
            } catch (InterruptedException e) {
                response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Request to fastly was interrupted. See error log for more details");
                log.error("Request to fastly was interrupted", e);
                Thread.currentThread().interrupt();
            }
        }
        response.setContentType(JSONResponse.RESPONSE_CONTENT_TYPE);
    }

    // this is an experamental console servlet
    private HttpResponse<String> purge(String host, String path) throws IOException, InterruptedException {
        if ( StringUtils.isBlank(host)) {
            return fastlyPurger.purge(path);
        } else {
            return fastlyPurger.purge(host, path);
        }
    }

    // this is an experamental console servlet
    protected void writeJsonResponse(final SlingHttpServletResponse response, String toWrite, int status) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(toWrite);
    }
}

