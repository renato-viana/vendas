package com.algaworks.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.algaworks.model.Pedido;
import com.algaworks.model.StatusPedido;
import com.algaworks.repository.PedidoRepository;
import com.algaworks.repository.filter.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidoRepository;
	
	private PedidoFilter filtro;
	private LazyDataModel<Pedido> model;
	
	public PesquisaPedidosBean() {
		filtro = new PedidoFilter();
		
		model = new LazyDataModel<Pedido>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Pedido> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setPropriedadeOrdenacao(sortField);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				
				setRowCount(pedidoRepository.quantidadeFiltrados(filtro));
				
				return pedidoRepository.filtrados(filtro);
			}
			
		};
	}
	
	//realiza a pesquisa de pedidos com filtro
	
	//lista de status de um pedido
	public StatusPedido[] getStatus() {
		return StatusPedido.values();
	}
	
	//estilização do arquivo XLS
	public void posProcessarXLS(Object documento) {
		HSSFWorkbook planilha = (HSSFWorkbook) documento;
		HSSFSheet folha = planilha.getSheetAt(0);
		HSSFRow cabecalho = folha.getRow(0);
		HSSFCellStyle estiloCelula = planilha.createCellStyle();
		Font fonteCabecalho = planilha.createFont();
		
		fonteCabecalho.setColor(IndexedColors.WHITE.getIndex());
		fonteCabecalho.setBold(true);
		fonteCabecalho.setFontHeightInPoints((short) 14);
		
		estiloCelula.setFont(fonteCabecalho);
		estiloCelula.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		estiloCelula.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		for(int i = 0; i < cabecalho.getPhysicalNumberOfCells(); i++) {
			cabecalho.getCell(i).setCellStyle(estiloCelula);
		}
	}	

	public PedidoFilter getFiltro() {
		return filtro;
	}

	public LazyDataModel<Pedido> getModel() {
		return model;
	}
}
