<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>@${profile}</title>
<jsp:include page="/WEB-INF/template/head.jsp"></jsp:include>
</head>
<body class="sidebar-toggled">
	<jsp:include page="/WEB-INF/template/topmenu.jsp"></jsp:include>

	<div id="wrapper">
		<jsp:include page="/WEB-INF/template/sidebar.jsp"></jsp:include>
		<div id="content-wrapper">
			<div class="container-fluid">
				<h3>@${profile}</h3>
				<jsp:useBean id="dao"
					class="br.com.shellcode.instaclone.dao.PostsDao" />
				<ul>
					<c:forEach var="post" items="${postagens}">
						<li class="userPost" data-postid="${post.id}">
							<div class="postDate">${post.data}</div>
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