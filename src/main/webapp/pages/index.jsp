<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/template/head.jsp"></jsp:include>
</head>
<body class="sidebar-toggled">
	<jsp:include page="/WEB-INF/template/topmenu.jsp"></jsp:include>

	<div id="wrapper">
		<jsp:include page="/WEB-INF/template/sidebar.jsp"></jsp:include>
		<div id="content-wrapper">
			<div class="container-fluid">
				<h3>Últimas postagens</h3>
				<jsp:useBean id="dao"
					class="br.com.shellcode.instaclone.dao.PostsDao" />
				<ul>
					<c:forEach var="post" items="${dao.list()}">
						<li>${post.id},&nbsp;${post.data}<br /> <img
							src="/posts/${post.id}" class="postsimgs" /></li>
					</c:forEach>
				</ul>

				<hr />
				<h3>Últimos usuários cadastrados</h3>
				<jsp:useBean id="daoUser"
					class="br.com.shellcode.instaclone.dao.PessoaDao" />
				<c:forEach var="p" items="${daoUser.list()}">
		       ${p.id}: @${p.nick}, ${p.nome}<br />
				</c:forEach>
			</div>
			<!-- /.container-fluid -->
			<jsp:include page="/WEB-INF/template/footer.jsp"></jsp:include>
		</div>
		<!-- /.content-wrapper -->

	</div>
	<!-- /#wrapper -->

	<jsp:include page="/WEB-INF/template/scripts.jsp"></jsp:include>

	<!-- instaclone -->
	<script src="/static/js/instaclone.js"></script>
</body>
</html>