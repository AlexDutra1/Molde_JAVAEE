package br.com.controller.formulario;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.modelo.Cliente;
import br.com.modelo.Estado;
import br.com.modelo.Municipio;

@ApplicationScoped
@Named("municipioFormulario")
public class MunicipioFormulario implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private Municipio municipio;
	
	@Inject
	private Estado estadoSelecionado;
	
	private List <Estado> todosEstados;

	private List <Municipio> todosMunicipios;
	
	
	//GETS AND SETTERS
	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public List<Estado> getTodosEstados() {
		return todosEstados;
	}

	public void setTodosEstados(List<Estado> todosEstados) {
		this.todosEstados = todosEstados;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public List<Municipio> getTodosMunicipios() {
		return todosMunicipios;
	}

	public void setTodosMunicipios(List<Municipio> todosMunicipios) {
		this.todosMunicipios = todosMunicipios;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
