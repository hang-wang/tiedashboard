/**
 * 
 */
/**
 * @author awang
 *
 */
package com.tie.servlets;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tie.app.TieController;
import com.tie.app.TieSecurityManager;
import com.tie.app.TieSessionController;
import com.tie.dao.TieAppDao;
import com.tie.dao.TiePersister;
import com.tie.model.TieDoc;
import com.tie.model.TieMsg;
import com.tie.ui.Header;
import com.tie.ui.Param;
import com.tie.ui.TieMainPage;
import com.tie.dao.LoginDao;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// TODO: rewrite doPost for switch
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		// Get TieSessionController from the httpsession
		TieSessionController sessionController = (TieSessionController) session
				.getAttribute(TieSessionController.sesssionControllerName);
		// System.out.println(sessionController);

		// Get username and pwd from the session
		String username = request.getParameter("username");
		String password = request.getParameter("userpass");

		// ---- determine action to take after user logged in ------
		// String action;
		// if (request.getParameter("action") == null) {
		// System.out.println("action is: " + request.getParameter("action"));
		// action = "";
		// } else {
		// System.out.println("request.getParameter action is:" +
		// request.getParameter("action"));
		// action = request.getParameter("action");
		// }

		// If user has already loggin in
		if (sessionController != null) {

			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String json = "";
			if (br != null) {
				json = br.readLine();

			}

			// 2. initiate jackson mapper
			ObjectMapper mapper = new ObjectMapper();

			// 3. Convert received JSON to Article
			// Article article = mapper.readValue(json, Article.class);
			Param param = mapper.readValue(json, Param.class);

			System.out.println(param.toString());
			// 4. Set response type to JSON
			String action = param.getAction();
			int messageId = param.getMessageId();
			int docId = param.getDocId();

			System.out.println("param.getAction: " + param.getAction());
			if (action.equals("initPage")) {
				System.out.println("Directing to initPage function");
				initPage(request, response, sessionController);
			} else if (action.equals("selectCurrentMsg")) {
				selectCurrentMsg(request, response, sessionController, messageId);
			} else if (action.equals("selectCurrentDoc")) {
				selectCurrentDoc(request, response, sessionController, docId);
			} else if (action.equals("save")) {
				System.out.println("======Directing to save function======");
				System.out.println("tieMsg.toString " + param.getTieMsg());
				
				//Call session controller to save currentMessage into database
				saveCurrentMessage(sessionController, param.getTieMsg());
				
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("dist/index.html");
				rd.forward(request, response);
			}

			// 5. Add article to List<Article>
			// articles.add(article);

			// 6. Send List<Article> as JSON to client
			// mapper.writeValue(response.getOutputStream(), articles);
			// switch logic based on action value

			// if (action.equals("initPage")) {
			// System.out.println("action = initPage!");
			// initPage(request, response, sessionController);
			// } else if (action.equals("selectCurrentMsg")) {
			// selectCurrentMsg(request, response, sessionController);
			// } else if (action.equals("selectCurrentDoc")) {
			// selectCurrentDoc(request, response, sessionController);
			// } else {
			// // RequestDispatcher rd =
			// // request.getRequestDispatcher("welcome.jsp");
			// System.out.println("request:" + request.toString());
			// System.out.println("request.getParameter failed");
			// RequestDispatcher rd =
			// request.getRequestDispatcher("dist/index.html");
			// rd.forward(request, response);
			// } // end switch on action

		} else {

			// TieController tieController = new TieController();
			// user touch for the first time
			TieSecurityManager securityManager = TieController.getController().getSecurityManager();
			if (securityManager.authentiate(username, password)) {
				sessionController = new TieSessionController();
				String code = username;
				// String code =
				// TieController.getController().getPersister().getTieUserDao().findTieUserByName(username).getCode();
				sessionController.setUserCode(code);
				session.setAttribute(code, username);
				// TieController.getController().getPersister().getLoginDao().setUsername(username);

				// Save the session controller, since the user is authenticated
				session.setAttribute(TieSessionController.sesssionControllerName, sessionController);

				/*
				 * Handle the login event for the user for the first time All
				 * data on the main page is populated in the mainPage object of
				 * the session controller.
				 */
				sessionController.handleLogin(username);

				// ToDo: change the tow lines blow to send back the object to
				// existing ng2 page
				// Header header = sessionController.initMainPage();
				// ObjectMapper ma = new ObjectMapper();
				// String headerjson = ma.writeValueAsString(header);
				// System.out.println(headerjson);
				// response.setContentType("text/json");
				// response.setCharacterEncoding("UTF-8");
				// response.getWriter().write(headerjson);
				/*
				 * Put logic here?
				 */
				// RequestDispatcher rd =
				// request.getRequestDispatcher("welcome.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("dist/index.html");

				rd.include(request, response);
			} else {
				out.print("<p style=\"color:red; text-align: center; \">Sorry username or password error</p>");
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.include(request, response);
			} // end if securityMgr.authenticate() else
		} // end if sessionController !=null else

		out.flush();
		out.close();
	}// end doPost(..)

	private void saveCurrentMessage(TieSessionController sessionController, TieMsg tieMsg) {
		// TODO Auto-generated method stub
		sessionController.handleSaveMessage(tieMsg);
	}

	// Return the whole TIEapp json when init
	// Customize init json to front end
	private void initPage(HttpServletRequest request, HttpServletResponse response,
			TieSessionController sessionController) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Header header = sessionController.initMainPage();
		ObjectMapper ma = new ObjectMapper();

		// SimpleModule module = new SimpleModule();
		// module.addSerializer(Header.class, new InitSerializer());
		// ma.registerModule(module);
		ObjectMapper ma2 = new ObjectMapper();
		TieMainPage retval = null;
		retval = TieMainPage.getTieMainPage();
		String tieJson = ma2.writeValueAsString(retval);
		String headerjson = ma.writeValueAsString(header);
		// String serialized = ma.writeValueAsString(header);
		System.out.println("init json string: " + tieJson);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(tieJson);
	}

	/**
	 * Prepare Ajax response to the select current msg request
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void selectCurrentMsg(HttpServletRequest request, HttpServletResponse response,
			TieSessionController sessionController, int messageId) throws ServletException, IOException {
		// int msgid = 0;
		// // if( id==null ) id =0;
		// if (request.getParameter("msgid") == null) {
		// msgid = 0;
		// } else {
		// msgid = Integer.parseInt(request.getParameter("msgid"));
		//
		// }
		// String contextPath = request.getContextPath();

		// -------Temp code, will clean later
		// TiePersister persister =
		// TieController.getController().getPersister();
		System.out.println("Current Msg Id" + messageId);
		TieMsg msg = sessionController.handleSelectCurrentMsg(messageId);
		// sessionController.handleSelectCurrentMsg(msgid);
		// persister.getTieMsgDao().findTieMsgByTieMsgId(msgid);
		// System.out.println("MSG pojo :" + msg.toString());

		TieMainPage retval = null;
		retval = TieMainPage.getTieMainPage();

		ObjectMapper ma = new ObjectMapper();
		String tieJson = ma.writeValueAsString(retval);
		String msgjson = ma.writeValueAsString(msg);
		System.out.println("msgJSON" + msgjson);

		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(tieJson);

		// System.out.println(msgjson);

	}// end selectCurrentMsg

	public void selectCurrentDoc(HttpServletRequest request, HttpServletResponse response,
			TieSessionController sessionController, int docId) throws ServletException, IOException {
		// int tieDocId = 0;
		//
		// if (request.getParameter("tieDocId") == null) {
		// tieDocId = 0;
		// } else {
		// tieDocId = Integer.parseInt(request.getParameter("tieDocId"));
		// }
		TieDoc tieDoc = sessionController.handleSelectCurrentDoc(docId);

		ObjectMapper ma = new ObjectMapper();
		String docjson = ma.writeValueAsString(tieDoc);

		TieMainPage retval = null;
		retval = TieMainPage.getTieMainPage();

		String tieJson = ma.writeValueAsString(retval);
		System.out.println("DocJSON" + docjson);

		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(tieJson);
	}// end
		// selectCurrentDoc

}// end class LoginService
