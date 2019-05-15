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
				<form action="/pessoa/new" method="post">
					<div class="form-group">
						<label>Dados pessoais</label>
						<div class="row">
							<div class="col col-sm-4">
								<input type="text" id="email" class="form-control"
									placeholder="E-mail" />
							</div>
							<div class="col col-sm-8">
								<input type="text" id="nome" class="form-control"
									placeholder="Nome completo" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label>Dado de acesso</label>
						<div class="row">
							<div class="col">
								<input type="text" id="usuario" class="form-control"
									placeholder="Usuário de acesso" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col col-sm-6">
								<input type="password" id="senha" class="form-control"
									placeholder="Senha" />
							</div>
							<div class="col col-sm-6">
								<input type="password" id="senhaRepeticao" class="form-control"
									placeholder="Confirmação de senha" />
							</div>
						</div>
					</div>
					<button type="button" class="btn btn-primary">Cadastrar</button>
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