<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Sidebar -->
<ul class="sidebar navbar-nav toggled">
	<li class="nav-item active"><a class="nav-link"
		href="/home"> <i class="fas fa-fw fa-camera-retro"></i>
			<span>Últimas fotos</span>
	</a></li>
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown"
			role="button" data-toggle="dropdown" aria-haspopup="true"
			aria-expanded="false"> <i class="fas fa-fw fa-images"></i> <span>Usuário</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<h6 class="dropdown-header">Minhas fotos</h6>
			<a class="dropdown-item" href="/posts/form">Postar foto</a>
		</div>
	</li>
</ul>