package br.com.VitrineCar.entidade;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Perfil perfil;

    @OneToMany(mappedBy = "usuario")
    private List<MeusAnuncios> meusAnuncios;

    @ManyToMany
    @JoinTable(
        name = "usuario_favoritos",
        joinColumns = @JoinColumn(name = "usuario_id"), // Correto
        inverseJoinColumns = @JoinColumn(name = "veiculo_id") // Correto
    )

    private List<Veiculo> favoritos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<MeusAnuncios> getMeusAnuncios() {
		return meusAnuncios;
	}

	public void setMeusAnuncios(List<MeusAnuncios> meusAnuncios) {
		this.meusAnuncios = meusAnuncios;
	}

	public List<Veiculo> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<Veiculo> favoritos) {
		this.favoritos = favoritos;
	}

    // Getters e Setters
    
}

