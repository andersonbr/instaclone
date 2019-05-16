<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-expand navbar-dark bg-dark static-top">
	<a class="navbar-brand mr-1" href="/home">Instaclone</a>
	<a class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle" href="#">
		<i class="fas fa-bars"></i>
	</a>
	<!-- Navbar -->
	<ul class="navbar-nav ml-auto notifications">
		<li class="nav-item dropdown no-arrow">
			<a class="nav-link dropdown-toggle" href="#" id="userDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false">${(autenticado != null) ? autenticado.nick : ""} <i class="fas fa-user-circle fa-fw"></i>
			</a>
			<div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
				<c:if test="${autenticado == null}">
						<a class="dropdown-item" href="/pessoa/form">Cadastrar</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="/auth/form">Entrar</a>
				</c:if>
				<c:if test="${autenticado != null}">
						<a class="dropdown-item" href="/pessoa/edit">Editar cadastro</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="/auth/form" data-toggle="modal" data-target="#logoutModal">Sair</a>
				</c:if>
			</div>
		</li>
	</ul>
</nav>