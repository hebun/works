package freela.util;

import java.io.IOException;
import java.net.HttpRetryException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import freela.util.Sql.Select;


public class FaceUtils {
	public static final Logger log = Logger.getLogger("FazlaStoklar");
	public static String uploadDir;
	static {
		log.setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		consoleHandler.setLevel(Level.ALL);

		if (log.getHandlers().length == 0) {
			log.addHandler(consoleHandler);
		}

		log.setLevel(Level.ALL);

	}

	public static String getRootUrl() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String contextURL = request.getRequestURL().toString()
				.replace(request.getRequestURI().substring(0), "")
				+ request.getContextPath();
		return contextURL;
	}

	public static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1)
						.substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}

	public static <T> T getObjectFromGETParam(String param, Class<T> type,
			String table) {

		String string = getGET(param);
		if (string != null)
			return getObjectById(type, table, string);
		else {
			try {
				return type.newInstance();
			} catch (InstantiationException e) {
				log.warning(e.getMessage());
			} catch (IllegalAccessException e) {
				log.warning(e.getMessage());
			}
		}
		return null;

	}

	public static <T> T getObjectById(Class<T> type, String table, String id) {
		Sql.Select sql = (Select) new Sql.Select().from(table).where("id=", id)
				.prepare();

		List<T> li = Db.preparedSelect(sql.get(), sql.params(), type);
		T ret = null;
		try {
			if (li.size() == 0) {

				ret = type.newInstance();
			} else {
				ret = li.get(0);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String getGET(String param) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext context = facesContext.getExternalContext();
		String string = context.getRequestParameterMap().get(param);
		return string;
	}

	public static void addError(String msg) {
		addError(null, msg);
	}

	public static void addError(String id, String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				msg, msg);
		FacesContext.getCurrentInstance().addMessage(id, message);
	}

	public static void redirectTo(String url) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext context = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) context
				.getResponse();

		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			log.warning(e.getMessage());
			e.printStackTrace();
		}
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static void addCookie(String name, String value, int maxAge) {
		addCookie((HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse(), name, value, maxAge);

	}

	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, String name) {
		addCookie(response, name, null, 0);
	}

	public static Map<String, String> getRecordFromGET(String param,
			String table) {

		String col = getGET(param);
		Map<String, String> rec = new HashMap<>();
		if (col != null) {

			try {
				Sql prepare = new Sql.Select().from(table).where("code", col)
						.prepare();
				List<Map<String, String>> result = Db.preparedSelect(
						prepare.get(), ((Select) prepare).params());
				if (result.size() > 0)
					rec = result.get(0);
			} catch (Exception e) {
				log.warning(e.getMessage());
			}

		}

		return rec;
	}
	
	public static String getFormattedTime(){
		Date time = Calendar.getInstance().getTime();
		return getFormattedTime(time);
		
		
	}

	public static String getFormattedTime(Date time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("Y-M-d H:m:s");

		String formattedTime = dateFormat.format(time);
		return formattedTime;
	}

}
