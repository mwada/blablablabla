package com.vivareal.spotippos;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Slf4jLog;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.vivareal.spotippos.exception.ServerException;

public class SpotipposMain {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpotipposMain.class);

	private static final int PORT = 8080;
	private static final String CONTEXT_PATH = "/";
	private static final String MAPPING_URL = "/*";

	public static void main(String[] args) throws Exception {
		new SpotipposMain().start(CONTEXT_PATH, PORT);
		LOGGER.info("Server started contextPath[" + CONTEXT_PATH + "] PORT[" + PORT + "]");
	}

	public void start(String contextPath, Integer port) {
		try {
			Log.setLog(new Slf4jLog());
			Server server = new Server(getThreadPool());
			server.addConnector(getConnector(server, port));
			server.setHandler(getServletContextHandler(contextPath));
			server.start();
		} catch (Exception e) {
			throw new ServerException(e);
		}
	}

	private QueuedThreadPool getThreadPool() {
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setDetailedDump(false);
		return threadPool;
	}

	private ServerConnector getConnector(Server server, Integer port) {
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(port);
		return connector;
	}

	private ServletContextHandler getServletContextHandler(String contextPath) throws IOException {
		ServletContextHandler contextHandler = new ServletContextHandler();
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(SpotipposConfig.class);
		contextHandler.addEventListener(new ContextLoaderListener(context));
		contextHandler.setContextPath(contextPath);
		contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
		return contextHandler;
	}

}