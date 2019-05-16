package br.com.shellcode.instaclone.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.shellcode.instaclone.dao.PostsDao;
import br.com.shellcode.instaclone.util.DateUtil;

@WebServlet(name = "IndexServlet", urlPatterns = { "/home" })
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Date dtInicial = null;
		Date dtFinal = null;

		if (req.getParameter("dtInicial") != null && req.getParameter("dtFinal") != null) {
			try {
				dtInicial = sdf.parse(req.getParameter("dtInicial"));
				dtFinal = sdf.parse(req.getParameter("dtFinal"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (dtInicial == null || dtFinal == null) {
			dtInicial = DateUtil.localDateTimeToDate(LocalDateTime.now().minusDays(30));
			dtFinal = DateUtil.localDateTimeToDate(LocalDateTime.now());
		}
		dtInicial = DateUtil.startOfDay(dtInicial);
		dtFinal = DateUtil.endOfDay(dtFinal);

		req.setAttribute("dataInicial", sdf.format(dtInicial));
		req.setAttribute("dataFinal", sdf.format(dtFinal));
		PostsDao dao = new PostsDao();
		req.setAttribute("postagens", dao.listEntre(dtInicial, dtFinal));

		String nextJSP = "/pages/index.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}