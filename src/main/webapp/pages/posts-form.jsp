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
					<div class="row">
						<div class="col">
							<div class="form-group foto">
								<label for="foto">Foto</label>
								<div class="input-group">
									<label class="input-group-btn">
										<span class="btn btn-primary">
											Selecionar <input type="file" style="display: none;" id="foto" aria-describedby="fotoHelp" accept="image/jpeg,image/png" name="foto">
										</span>
									</label> <input id="fotoname" type="text" class="form-control" readonly="readonly">
								</div>
								<small id="fotoHelp" class="form-text text-muted">
									Selecionar o arquivo da foto.</small>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col">
							<div class="form-group descricao">
								<label for="descricao">Descrição</label>
								<input type="text" class="form-control" id="descricao" aria-describedby="descricaoHelp" placeholder="Digite uma descrição para a foto" name="descricao">
								<small id="descricaoHelp" class="form-text text-muted">
									Digite uma descrição para a foto.</small>
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
	<script type="text/javascript">
		$(function() {
			$("#foto")[0].onchange = function() { $("#fotoname").val(this.files[0].name) }
		})
	</script>
</body>
</html>