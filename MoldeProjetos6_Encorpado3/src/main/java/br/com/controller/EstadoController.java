package br.com.controller;

import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
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
	
	private boolean habilitarEditar=false;
	private boolean esconderBotoes=true;

	
	private String titulo_pagina;
	
	
	
	
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

		//Insere texto no cabeçalho
		titulo_pagina=EstadoFormulario.EDICAO_ESTADO;
		
		//Habilita campo para edição
		this.habilitarEditar=false;
		
		//Exibe botão Alterar
		this.esconderBotoes=true;
				
		this.formulario.setEstado(estadoEditar);

		return "editarEstado";
	}

	public String visualizar(Estado estadoVisualizar) {

		this.formulario.setEstado(estadoVisualizar);
		
		//Insere texto no cabeçalho
		titulo_pagina=EstadoFormulario.VISUALIZACAO_ESTADO;
		
		//Esconde botão Alterar
		this.esconderBotoes=false;		
		
		//Desabilita campo para edição
		this.habilitarEditar=true;
		
		return "visualizarEstado";
	}

	public void excluir(Estado estadoExcluir) {
		this.service.getNegocios().getDao().excluir(estadoExcluir);
	}

	//NAO FUNCIONA
	//FALTA ATUALIZAR PAGINA
	public String voltar() {
		
		this.formulario.setEstado(new Estado());
		RequestContext.getCurrentInstance().update("formPesquisaEstado");
		
		refresh();
		
		return "pesquisaEstado.xhtml";
	}
	
	//NAO ESTA SENDO USADO O METODO ABAIXO.MAS NO FUTURO ELE PODE SER UTIL
	//Atualiza a pagina
	public void refresh() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse();
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

	public boolean isHabilitarEditar() {
		return habilitarEditar;
	}

	public void setHabilitarEditar(boolean habilitarEditar) {
		this.habilitarEditar = habilitarEditar;
	}

	public boolean isEsconderBotoes() {
		return esconderBotoes;
	}

	public void setEsconderBotoes(boolean esconderBotoes) {
		this.esconderBotoes = esconderBotoes;
	}

	public String getTitulo_pagina() {
		return titulo_pagina;
	}

	public void setTitulo_pagina(String titulo_pagina) {
		this.titulo_pagina = titulo_pagina;
	}


}
