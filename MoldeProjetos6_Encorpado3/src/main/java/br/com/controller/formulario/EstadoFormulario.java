package br.com.controller.formulario;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.modelo.Estado;


@Named
@ApplicationScoped
public class EstadoFormulario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Estado estado;
	
	private List<Estado> todosEstados;

	//CONSTANTES
	public static final String EDICAO_ESTADO="Edição do Estado";
	public static final String VISUALIZACAO_ESTADO="Visualização do Estado";
	
	//GETTS AND SETTERS
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getTodosEstados() {
		return todosEstados;
	}

	public void setTodosEstados(List<Estado> todosEstados) {
		this.todosEstados = todosEstados;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
