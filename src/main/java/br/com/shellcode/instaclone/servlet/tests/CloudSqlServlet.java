package br.com.shellcode.instaclone.servlet.tests;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Stopwatch;

@SuppressWarnings("serial")
//With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "CloudSQL", description = "CloudSQL: Write timestamps of visitors to Cloud SQL", urlPatterns = "/cloudsql")
public class CloudSqlServlet extends HttpServlet {
	Connection conn;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		final String createTableSql = "CREATE TABLE IF NOT EXISTS visits ( "
				+ "visit_id SERIAL NOT NULL, ts timestamp NOT NULL, " + "PRIMARY KEY (visit_id) );";
		final String createVisitSql = "INSERT INTO visits (ts) VALUES (?);";
		final String selectSql = "SELECT ts FROM visits ORDER BY ts DESC " + "LIMIT 10;";

		String path = req.getRequestURI();
		if (path.startsWith("/favicon.ico")) {
			return; // ignore the request for favicon.ico
		}

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/plain");

		Stopwatch stopwatch = Stopwatch.createStarted();
		try (PreparedStatement statementCreateVisit = conn.prepareStatement(createVisitSql)) {
			conn.createStatement().executeUpdate(createTableSql);
			statementCreateVisit.setTimestamp(1, new Timestamp(new Date().getTime()));
			statementCreateVisit.executeUpdate();

			try (ResultSet rs = conn.prepareStatement(selectSql).executeQuery()) {
				stopwatch.stop();
				out.print("Last 10 visits:\n");
				while (rs.next()) {
					String timeStamp = rs.getString("ts");
					out.println("Visited at time: " + timeStamp);
				}
			}
		} catch (SQLException e) {
			throw new ServletException("SQL error", e);
		}
		out.println("Query time (ms):" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
		try {
			out.println("\nDriver: " + DriverManager.getDriver(System.getProperty("cloudsql")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws ServletException {
		String url = System.getProperty("cloudsql");
		log("connecting to: " + url);
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new ServletException("Unable to connect to Cloud SQL", e);
		}
	}
}