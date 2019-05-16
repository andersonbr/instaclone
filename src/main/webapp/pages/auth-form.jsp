<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Autenticação</title>
<style type="text/css">
.text-center {
    text-align: center !important;
}
.form-signin {
    width: 100%;
    max-width: 330px;
    padding: 15px;
    margin: auto;
}
.form-signin input[name="username"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
}
.form-signin input[name="password"] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
}
.form-signin .form-control {
    position: relative;
    box-sizing: border-box;
    height: auto;
    padding: 10px;
    font-size: 16px;
}
</style>
</head>
<body class="text-center">
	<form class="form-signin" action="/auth/check" method="post">
		<img class="mb-4" src="/static/img/favicon.svg" alt="" width="72" height="72">
		<h1 class="h3 mb-3 font-weight-normal">Autenticação</h1>
		<label for="username" class="sr-only">Usuário</label>
		<input type="text" id="username" name="username" class="form-control" placeholder="Usuário" required autofocus />
		<label for="password" class="sr-only">Senha</label>
		<input type="password" id="password" name="password" class="form-control" placeholder="Senha" required />
		<div class="checkbox mb-3"><label><input type="checkbox" value="remember-me" /> Lembrar</label></div>
		<button class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
		<div class="row"><div class="col"><small>${erroMsg}</small></div></div>
	</form>
	<jsp:include page="/WEB-INF/template/scripts.jsp"></jsp:include>
</body>
</html>