<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/template/head.jsp"></jsp:include>
<style type="text/css">
	.btselect {
		border-top-right-radius: 0px;
		border-bottom-right-radius: 0px;
	}
</style>
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
								<input type="text" id="email" class="form-control" name="email"
									placeholder="E-mail" value="${(autenticado == null)?'':autenticado.email}" />
							</div>
							<div class="col col-sm-8">
								<input type="text" id="nome" class="form-control" name="nome"
									placeholder="Nome completo" value="${(autenticado == null)?'':autenticado.nome}" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label>Dado de acesso</label>
						<div class="row">
							<div class="col">
								<input type="text" id="usuario" class="form-control" name="nick"
									placeholder="Usuário de acesso" value="${(autenticado == null)?'':autenticado.nick}" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col col-sm-6">
								<input type="password" id="senha" class="form-control" name="senha"
									placeholder="Senha" value="${(autenticado == null)?'':autenticado.senha}" />
							</div>
							<div class="col col-sm-6">
								<input type="password" id="senhaRepeticao" class="form-control"
									placeholder="Confirmação de senha" value="${(autenticado == null)?'':autenticado.senha}" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col">
								<div class="form-group foto">
									<label for="foto">Foto</label>
									<div class="input-group">
										<label class="input-group-btn">
											<span class="btn btn-primary btselect">
												Selecionar <input type="file" style="display: none;" id="foto" aria-describedby="fotoHelp" accept="image/jpeg,image/png" name="foto">
											</span>
										</label> <input id="fotoname" type="text" class="form-control" readonly="readonly">
									</div>
									<small id="fotoHelp" class="form-text text-muted">
										Selecionar o arquivo da foto.</small>
								</div>
							</div>
						</div>
					</div>
					<input type="submit" class="btn btn-primary" value="${edit?'Editar':'Cadastrar'}" />
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
	<script type="text/javascript">
		$(function() {
			$("#foto")[0].onchange = function() { $("#fotoname").val(this.files[0].name) }
		})
	</script>
</body>
</html>