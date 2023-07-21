package com.algaworks.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.SelectEvent;

import com.algaworks.event.PedidoAlteradoEvent;
import com.algaworks.exception.NegocioException;
import com.algaworks.model.Cliente;
import com.algaworks.model.EnderecoEntrega;
import com.algaworks.model.FormaPagamento;
import com.algaworks.model.ItemPedido;
import com.algaworks.model.Pedido;
import com.algaworks.model.Produto;
import com.algaworks.model.Usuario;
import com.algaworks.repository.ClienteRepository;
import com.algaworks.repository.ProdutoRepository;
import com.algaworks.repository.UsuarioRepository;
import com.algaworks.service.PedidoService;
import com.algaworks.util.jsf.FacesUtil;
import com.algaworks.validation.PedidoEdicao;
import com.algaworks.validation.SKU;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Inject
	private ClienteRepository clienteRepository;
	
	@Inject
	private ProdutoRepository produtoRepository;

	@Inject
	private PedidoService pedidoService;
	
	@Produces
	@PedidoEdicao
	private Pedido pedido;
	
	private List<Usuario> vendedores;
	
	private Produto produtoLinhaEditavel;
	
	@SKU
	private String sku;
	
	public CadastroPedidoBean() {
		limpar();
	}
	
	//carrega a lista de vendedores e clientes na inicialização da página
	public void inicializar() {
		this.vendedores = usuarioRepository.vendedores();
		
		//adiciona umm linha vazia na tabela de itens no carregamento da página
		this.pedido.adicionarItemVazio();
		
		this.recalcularPedido();
	}
	
	//limpa os dados da tela após cadastrar um novo pedido
	private void limpar() {
		pedido = new Pedido();
		pedido.setEnderecoEntrega(new EnderecoEntrega());
	}
	
	//Observer de alteração de quantidade de estoque
	public void pedidoAlterado(@Observes PedidoAlteradoEvent event) {
		this.pedido = event.getPedido();
	}
	
	//salva um pedido
	public void salvar() {
		this.pedido.removerItemVazio();
		
		try {
			this.pedido = this.pedidoService.salvar(this.pedido);
		
			FacesUtil.addInfoMessage("Pedido salvo com sucesso.");
		} catch(NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}
	
	//calcula o valor total de um pedido
	public void recalcularPedido() {
		if (this.pedido != null) {
			this.pedido.recalcularValorTotal();
		}
	}
	
	//lista os nomes dos clientes pelo autocomplete
	public List<Cliente> completarCliente(String nome) {
		return this.clienteRepository.buscarPorNome(nome);
	}
	
	//lista os nomes dos produtos pelo autocomplete
	public List<Produto> completarProduto(String nome) {
		return this.produtoRepository.buscarProdutoPorNome(nome);
	}
	
	public void carregarProdutoLinhaEditavel() {
		ItemPedido item = this.pedido.getItens().get(0);
		
		if (this.produtoLinhaEditavel != null) {
			if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado.");
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());
				
				this.pedido.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;
				
				this.pedido.recalcularValorTotal();
			}
		}
	}
	
	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;
		
		for (ItemPedido item : this.getPedido().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		
		return existeItem;
	}
	public void carregarProdutoPorSku() {
		if(StringUtils.isNoneEmpty(this.sku)) {
			this.produtoLinhaEditavel = this.produtoRepository.buscarPorSku(this.sku);
			this.carregarProdutoLinhaEditavel();
		}
	}
	
	public void atualizarQuantidade(ItemPedido item, int linha) {
		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				this.getPedido().getItens().remove(linha);
			}
		}
		
		this.pedido.recalcularValorTotal();
	}
	
	//verifica a existência do id do objeto produto para saber se ele é novo ou não
	public boolean isEditando() {
		return this.pedido.getId() != null;
	}
	
	//lista as foras de pagamento
	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}
	
	//cliente selecionado na linha do dialog
	public void clienteSelecionado(SelectEvent event) {
		this.pedido.setCliente((Cliente) event.getObject());
	}
	
	//método de contorno para exibir o nome do cliente selecionado no input
	@NotBlank
	public String getNomeCliente() {
		return this.pedido.getCliente() == null ? null : this.pedido.getCliente().getNome();
	}
	
	public void setNomeCliente(String nome) {}
	
	public Pedido getPedido() {
		return pedido;
	}
	
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Usuario> getVendedores() {
		return vendedores;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
}
