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
				<form>
					<div class="form-group">
						<label>Filtros MASTER</label>
						<div class="row">
							<div class="col">
								<input type="text" id="codFilaMaster" class="form-control" placeholder="Código do registro de agenda cirúrgica (MASTER)" autofocus="autofocus" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label>Filtros AGHU</label>
						<div class="row">
							<div class="col col-sm-4">
								<input type="text" id="codProntuarioAghu" class="form-control" placeholder="Prontuário paciente (AGHU)" />
							</div>
							<div class="col col-sm-8">
								<input type="text" id="nomePaciente" class="form-control" placeholder="Nome do paciente (AGHU)" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label>Filtros Unisus</label>
						<div class="row">
							<div class="col">
								<input type="text" id="codFilaUnisus" class="form-control" placeholder="Código UNISUS para registro de agenda cirúrgica (UNISUS)" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label>Intervalo de datas</label>
						<div class="row">
							<div class="col">
								<input type="text" id="dtInicial" class="form-control" placeholder="Data inicial" />
							</div>
							<div class="col">
								<input type="text" id="dtFinal" class="form-control" placeholder="Data final" />
							</div>
						</div>
					</div>
					<a class="btn btn-primary btn-block">Buscar</a>
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