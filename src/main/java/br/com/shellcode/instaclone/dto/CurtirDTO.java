package br.com.shellcode.instaclone.dto;

public class CurtirDTO {

	private Integer foto;
	private Boolean curtir;

	public CurtirDTO() {
	}
	
	public CurtirDTO(Integer foto, Boolean curtir) {
		this.foto = foto;
		this.curtir = curtir;
	}

	public Integer getFoto() {
		return foto;
	}

	public void setFoto(Integer foto) {
		this.foto = foto;
	}

	public Boolean getCurtir() {
		return curtir;
	}

	public void setCurtir(Boolean curtir) {
		this.curtir = curtir;
	}
}
