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
				<form action="/posts/new" method="post"
					enctype="multipart/form-data">
					<div class="form-group">
						<label>Descrição da foto</label>
						<div class="row">
							<div class="col">
								<input type="text" multiple="multiple" id="descricao"
									name="descricao" class="form-control"
									placeholder="Descrição da foto" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col">
								<input type="file" name="foto" id="foto" class="form-control"
									placeholder="Foto" />
							</div>
						</div>
					</div>
					<input type="submit" class="btn btn-primary" value="Postar" />
				</form>
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