package br.com.persistencia.implementacao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.modelo.Cliente;
import br.com.modelo.Estado;
import br.com.modelo.Municipio;
import br.com.persistencia.interfaces.MunicipioGerenciable;

public class MunicipioDAO implements MunicipioGerenciable {

	private EntityManager manager;

	@Inject
	public MunicipioDAO(EntityManager manager) {
		this.manager = manager;
	}

	/*
	 * Permite fazer o CREATE e UPDATE
	 */
	public void guardar(Municipio municipio) {

		EntityTransaction trx = this.manager.getTransaction();
		trx.begin();

		municipio = this.manager.merge(municipio);
		this.manager.merge(municipio);

		trx.commit();

	}
	

	//EXCLUIR MUNICIPIO
	public void excluir(Municipio municipio) {
		
		EntityTransaction trx = this.manager.getTransaction();
		trx.begin();
		
		municipio=manager.merge(municipio);
		manager.remove(municipio);
		
		trx.commit();
		
	}

	@SuppressWarnings("unchecked")
	public List<Municipio> consultaMunicipiosPeloEstado(Estado estado) {

		Query consulta = manager.createQuery(
				"select a from Municipio a where idestado='" + estado.getId()
						+ "'", Municipio.class);

		return consulta.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Municipio> consultaTodosMunicipios() {

		Query consulta = manager.createQuery("select a from Municipio a ",
				Municipio.class);

		return consulta.getResultList();

	}

	public List<Municipio> pesquisaComCriterios(Municipio municipio,
			Estado estado) {

		// CRITERIA JPA

		CriteriaBuilder criteriaBuilder = this.manager.getCriteriaBuilder();
		CriteriaQuery<Municipio> criteriaQuery = criteriaBuilder
				.createQuery(Municipio.class);
		Root<Municipio> root = criteriaQuery.from(Municipio.class);

		List<Predicate> condicoes = new ArrayList<Predicate>();

		// CONSULTA POR NOME DO MUNICIPIO

		if (!municipio.getNome().equals("")) {
			Path<String> atributoNome = root.get("nome");
			Predicate whereNome = criteriaBuilder.like(atributoNome,
					municipio.getNome());
			condicoes.add(whereNome);
		}

		// CONSULTA POR NOME DO ESTADO

		if (estado != null) {
			Path<String> atributoEstado = root.join("estado").get("nome");
			Predicate whereEstado = criteriaBuilder.equal(atributoEstado,
					estado.getNome());
			condicoes.add(whereEstado);
		}

		// COMENTAMOS POR DOIS MOTIVOS
		// ARRUMAR ERROS DEPOIS-ADPATAR PARA USO NO FUTUTO
		// REFERENCIA

		Predicate[] condicoesComoArray = condicoes
				.toArray(new Predicate[condicoes.size()]);
		Predicate todasCondicoes = criteriaBuilder.and(condicoesComoArray);
		criteriaQuery.where(todasCondicoes);

		TypedQuery<Municipio> query = this.manager.createQuery(criteriaQuery);

		List<Municipio> list = query.getResultList();

		for (Municipio usuario2 : list) {
			System.out.println("RESULTADO CRITERIA: " + usuario2.getNome());
		}

		return list;
	}

}
