<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Inicial</title>
<jsp:include page="/WEB-INF/template/head.jsp"></jsp:include>
</head>
<body class="sidebar-toggled">
	<jsp:include page="/WEB-INF/template/topmenu.jsp"></jsp:include>

	<div id="wrapper">
		<jsp:include page="/WEB-INF/template/sidebar.jsp"></jsp:include>
		<div id="content-wrapper">
			<div class="container-fluid">
				<h3>Últimas postagens geral</h3>
				<!-- 					<div class="form-group"> -->
				<!-- 						<label>Intervalo de datas</label> -->
				<!-- 						<div class="row"> -->
				<!-- 							<div class="col"> -->
				<!-- 								<input type="text" id="dtInicial" class="form-control" placeholder="Data inicial" /> -->
				<!-- 							</div> -->
				<!-- 							<div class="col"> -->
				<!-- 								<input type="text" id="dtFinal" class="form-control" placeholder="Data final" /> -->
				<!-- 							</div> -->
				<!-- 						</div> -->
				<!-- 					</div> -->
				<jsp:useBean id="dao"
					class="br.com.shellcode.instaclone.dao.PostsDao" />
				<ul>
					<c:forEach var="post" items="${dao.list()}">
						<li class="userPost" data-postid="${post.id}">
							<div class="postDate">${post.data}</div>
							<div class="postNick"><a href="/@${post.pessoa.nick}">@${post.pessoa.nick}</a></div>
							<div class="postImg"><img src="/posts/${post.id}" class="postsimgs" /></div>
							<div class="postDesc">${post.descricao}</div>
							<div class="postButtons">
								<button type="button" class="btlike btn btn-default btn-sm disabled" onclick="like(${post.id})">
									<span class="fa fa-thumbs-up"></span> Curtir
								</button>
								<button type="button" class="btdislike btn btn-default btn-sm disabled" onclick="dislike(${post.id})">
									<span class="fa fa-thumbs-down"></span> Descurtir
								</button>
							</div>
						</li>
					</c:forEach>
				</ul>

				<hr />
				<h3>Últimos usuários cadastrados</h3>
				<jsp:useBean id="daoUser"
					class="br.com.shellcode.instaclone.dao.PessoaDao" />
				<c:forEach var="p" items="${daoUser.list()}">
		       ${p.id}: <a href="/@${p.nick}">@${p.nick}</a>, ${p.nome}<br />
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