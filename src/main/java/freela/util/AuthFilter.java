package freela.util;

import static freela.util.FaceUtils.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import freela.util.FaceUtils;

@WebFilter(filterName = "AuthFilter", servletNames = "Faces Servlet")
public class AuthFilter implements Filter {

	static boolean devStage = false;

	public AuthFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	static int callCount = 0;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		/*
		 * String COOKIE_NAME = "remember"; try { HttpServletRequest req =
		 * (HttpServletRequest) request; HttpServletResponse res =
		 * (HttpServletResponse) response; HttpSession ses =
		 * req.getSession(false); String[] split =
		 * req.getRequestURI().split("\\.");
		 * 
		 * String reqURI = split.length == 0 ? req.getRequestURI() :
		 * split[split.length - 1]; System.out.println(callCount++ + reqURI);
		 * User user = (User) req.getSession().getAttribute("user");
		 * 
		 * if (user == null) { String uuid = getCookieValue(req, COOKIE_NAME);
		 * 
		 * if (uuid != null) { List<User> users = Db.select(new
		 * Sql.Select().from("user") .where("uuid", uuid).get(), User.class);
		 * 
		 * if (users.size() > 0) { user = users.get(0);
		 * 
		 * req.getSession().setAttribute("user", user); // addCookie(res,
		 * "remember", uuid, 100_000_000);
		 * 
		 * } else {
		 * 
		 * removeCookie(res, COOKIE_NAME); } } }
		 * 
		 * if (user != null) { chain.doFilter(request, response); } else {
		 * 
		 * if (devStage) { chain.doFilter(request, response); } else {
		 * 
		 * if (adminControle(req, res, ses, reqURI)) {
		 * 
		 * if (memberControle(req, res, ses, reqURI)) {
		 * 
		 * chain.doFilter(request, response); } } } } } catch (Throwable t) {
		 * FaceUtils.log.info(t.getMessage()); t.printStackTrace(); }
		 */
	}

	private boolean memberControle(HttpServletRequest req,
			HttpServletResponse res, HttpSession ses, String reqURI)
			throws IOException {

		/*
		 * String[] memberPages = { "urunlerim", "urun-ekle", "uye-profil" };
		 * 
		 * for (String string : memberPages) {
		 * 
		 * if (reqURI.indexOf(string) >= 0) {
		 * 
		 * if (ses != null && ses.getAttribute("login") != null) {
		 * 
		 * Login bean = ((Login) (ses.getAttribute("login")));
		 * 
		 * if (!bean.isLoggedIn()) {
		 * 
		 * res.sendRedirect(req.getContextPath() + "/kullanici-giris"); return
		 * false; }
		 * 
		 * } else {
		 * 
		 * res.sendRedirect(req.getContextPath() + "/kullanici-giris"); return
		 * false; } } }
		 */
		return true;

	}

	private boolean adminControle(HttpServletRequest req,
			HttpServletResponse res, HttpSession ses, String reqURI)
			throws IOException {
		/**
		 * admin access only
		 */

		/*
		 * if (reqURI.indexOf("/admin") >= 0) {
		 * 
		 * HttpSession session = req.getSession(); if (session == null ||
		 * session.getAttribute("user") == null) {
		 * FaceUtils.log.warning("not authorized attempt to access admin/" +
		 * session.getAttribute("user"));
		 * 
		 * res.sendRedirect(req.getContextPath() + "/kullanici-giris"); return
		 * false; } else { User user = (User) session.getAttribute("user");
		 * System.out.println(user); if (!user.getState().equals("ADMIN")) {
		 * FaceUtils.log .warning("Not authorized attempt to access admin/");
		 * 
		 * res.sendRedirect(req.getContextPath() + "/kullanici-giris"); return
		 * false; } }
		 * 
		 * }
		 */
		return true;
	}

	@Override
	public void destroy() {

	}
}