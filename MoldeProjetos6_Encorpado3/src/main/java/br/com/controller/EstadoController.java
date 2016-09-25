package br.com.controller;

import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.controller.formulario.EstadoFormulario;
import br.com.modelo.Estado;
import br.com.servico.EstadoService;

@ApplicationScoped
@Named("estadoController")
public class EstadoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstadoService service;

	@Inject
	private EstadoFormulario formulario;
	
	private boolean teste=false;

	@PostConstruct
	public void init() {
		this.formulario.setTodosEstados(this.service.getNegocios().getDao()
				.todosEstadosCombo());
	}

	public void acaoAposCadastrar() {

		// Salva estado
		this.service.getNegocios().getDao()
				.guardar(this.getFormulario().getEstado());

		// Limpa campos
		// Obs; Usando actionlistener para salvar limpamos a sujeito da arvore
		// de componentes e a sujeira do bean
		this.formulario.setEstado(new Estado());

	}

	//

	public void pesquisar() {

		System.out.println("TESTANDO ESTADO CONTROLLER: "
				+ this.formulario.getEstado().getNome());

		this.formulario.setTodosEstados(this.service.getNegocios().getDao()
				.pesquisaComCriterios(this.formulario.getEstado()));

		// ATUALIZA TABELA E CAMPO DE PESQUISA
		RequestContext.getCurrentInstance().update(
				Arrays.asList("formPesquisaEstado:tabelaEstados"));

		/*
		 * //Limpa campos apos cadastro
		 * this.formulario.getUsuario().setNome("");
		 * this.formulario.getUsuario().setEmail("");
		 * this.formulario.getUsuario().setUsuario("");
		 * 
		 * //ATUALIZA CAMPOS RequestContext.getCurrentInstance().update(
		 * "formPesquisaUsuario:input_nome");
		 * RequestContext.getCurrentInstance()
		 * .update("formPesquisaUsuario:input_usuario");
		 * RequestContext.getCurrentInstance
		 * ().update("formPesquisaUsuario:input_email");
		 */

	}

	public String preparaEdicao(Estado estadoEditar) {

		this.formulario.setEstado(estadoEditar);

		return "editarEstado";
	}

	public String visualizar(Estado estadoVisualizar) {

		this.formulario.setEstado(estadoVisualizar);

		this.teste=true;
		
		return "visualizarEstado";
	}

	public void excluir(Estado estadoExcluir) {
		this.service.getNegocios().getDao().excluir(estadoExcluir);
	}

	//NAO FUNCIONA
	//FALTA ATUALIZAR PAGINA
	public String voltar() {
		
		RequestContext.getCurrentInstance().update("formPesquisaEstado");
		
		return "pesquisaEstado.xhtml";
	}

	// GETTS AND SETTERS

	public EstadoFormulario getFormulario() {
		return formulario;
	}

	public void setFormulario(EstadoFormulario formulario) {
		this.formulario = formulario;
	}

	public EstadoService getService() {
		return service;
	}

	public void setService(EstadoService service) {
		this.service = service;
	}

	public boolean isTeste() {
		return teste;
	}

	public void setTeste(boolean teste) {
		this.teste = teste;
	}

}
