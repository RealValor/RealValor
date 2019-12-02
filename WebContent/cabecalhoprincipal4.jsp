<%@ page contentType="text/html; charset=ISO-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> <!-- charset=utf-8 --> 
	   <title>Real Valor - Sistema Financeiro UDV</title>
	   <!-- Bootstrap -->
       <link href="css/bootstrap.min.css" rel="stylesheet"/>
	   <link rel="stylesheet" href="css/principal.css" type="text/css" />
	   <script src="scripts/funcoes.js" type="text/javascript" language="javascript" > </script>   
	</head>
	<body>
		<script src="js/bootstrap.min.js"></script>
		<img src="imagens/LogoImagem.png" width=100% height="119" />
		<div>&nbsp;</div>
		<div id="estrutura">
			<ul id="navmenu">
			  <li><a href="stu?p=IncluiRecebimentoRegiao&ctrl=limpar" title="Recebimento de Mensalidades e Doações"><img src="imagens/spacer_1x1px.png" width="7" border="0" />Recebimento<img src="imagens/spacer_1x1px.png" width="6" border="0" /></a></li>
			  <li><a><img src="imagens/spacer_1x1px.png" width="18" border="0" />Inclusão<img src="imagens/spacer_1x1px.png" width="18" border="0" /></a>
			    <ul>
			      <li><a>Banco<img src="imagens/spacer_1x1px.png" width="91" border="0" />+</a>
			      <ul>
			         <li><a href="stu?p=IncluiBanco" title="Cadastro de Bancos">Banco</a></li>
			         <li><a href="stu?p=IncluiSaldoBanco" title="Inclusão mensal de Saldo em Bancos">Saldo em Banco</a></li>
			        </ul>
			      </li>
			      <li><a>Cadastro<img src="imagens/spacer_1x1px.png" width="72" border="0" />+</a>
			        <ul>
			          <li><a href="stu?p=IncluiNucleoOperador&ctrl=1" title="Consulta e Alteração de Núcleos da ${objNucleo.regiao.descricao}">Núcleos da ${objNucleo.regiao.descricao}<img src="imagens/spacer_1x1px.png" width="5" border="0" /></a></li>
				      <li><a href="stu?p=IncluiOperadorNucleo" title="Inclusão de Operador Para o Núcleo de Associação do Tesoureiro Regional">Operador</a></li>
			        </ul>
			      </li>
			      <li><a>Divida<img src="imagens/spacer_1x1px.png" width="89" border="0" />+</a>
			        <ul>
			         <li><a href="stu?p=IncluiDivida" title="Lançamento de Dívidas">Lançamento</a></li>
			         <li><a href="stu?p=QuitaDivida" title="Pagamento e exclusão de Dívidas">Pagamento</a></li>
			        </ul>
			      </li>
			      <li><a href="stu?p=IncluiLista" title="Inclusão de Lista de Arrecadação">Lista de Arrecadação</a></li>
			      <li><a href="stu?p=IncluiTipoEntrada" title="Cadastro de Grupos de Receitas">Tipo Entrada</a></li>
			      <li><a href="stu?p=IncluiTipoSaida" title="Cadastro de Grupos de Despesas">Tipo Saida</a></li>
			    
			   </ul>
			   </li>
			   <li><a href="stu?p=IncluiSaida" title="Lançamento das Despesas do Núcleo"><img src="imagens/spacer_1x1px.png" width="7" border="0" />Pagamento<img src="imagens/spacer_1x1px.png" width="7" border="0" /></a></li>
			   
			   <li><a><img src="imagens/spacer_1x1px.png" width="10" border="0" />Consulta<img src="imagens/spacer_1x1px.png" width="10" border="0" /></a>
			    <ul>
			       <li><a href="stu?p=IncluiNucleoOperador&ctrl=1" title="Consulta e Alteração de Núcleos da ${objNucleo.regiao.descricao}">Núcleos da ${objNucleo.regiao.descricao}<img src="imagens/spacer_1x1px.png" width="5" border="0" /></a></li>
			      <li><a>Recibo<img src="imagens/spacer_1x1px.png" width="86" border="0" />+</a>
			        <ul>
			         <li><a href="stu?p=ConsultaRecibo" title="Consulta recibos por nome">Por Nome</a></li>
			         <li><a href="stu?p=ConsultaReciboNumero" title="Consulta recibos por número">Por Numero</a></li>
			        </ul>
			      </li>

			    </ul>
			  </li>
			  	<li><a><img src="imagens/spacer_1x1px.png" width="10" border="0" />Impressao<img src="imagens/spacer_1x1px.png" width="10" border="0" /></a>
			    <ul>
			       <li><a>Anual<img src="imagens/spacer_1x1px.png" width="96" border="0" />+</a>
			          <ul>
			             <li><a href="stu?p=AtualizaDadosBalanceteAnual" title="Impressão do Balancete Anual">Balancete Anual</a></li>
			          </ul>
			       </li>
			       <li><a>Nucleo<img src="imagens/spacer_1x1px.png" width="94" border="0" />+</a>
			        <ul>          
			             <li><a href="stu?p=ConsultaAniversariante" title="Impressão da Lista de Aniversariantes">Aniversarios</a></li>
			             <li><a href="stu?p=ConsultaListaSocio&tipo=ativo" title="Impressão da Lista de Associados Ativos">Lista de Nucleos</a></li>
			             <li><a href="stu?p=ConsultaInadimplente" title="Impressão da Lista de Inadimplentes">Inadimplentes</a></li>
			          </ul>
			       </li>
			       <li><a>Balancete<img src="imagens/spacer_1x1px.png" width="67" border="0" />+</a>
			           <ul>
			              <li><a href="stu?p=ConsultaBalancete&tipo=finalizado" title="Balancete Mensal Finalizado">Finalizado</a></li>
			              <li><a href="stu?p=ConsultaBalancete&tipo=periodo" title="Balancete Mensal Finalizado Por Período">Por Periodo</a></li>
			              <li><a href="stu?p=ConsultaBalancete&tipo=previo" title="Balancete Mensal Para Conferência">Prévio</a></li>
			           </ul>
			       </li>       
			       <li><a>Movimento Mensal<img src="imagens/spacer_1x1px.png" width="6" border="0" />+</a>
			          <ul>
			             <li><a href="stu?p=ConsultaMovimentoEntrada" title="Relatório Mensal de Entradas">Entrada</a></li>
			             <li><a href="stu?p=ConsultaMovimentoEntradaSocio" title="Relatório Mensal de Entradas por Sócio e Período">Entrada por Nucleo</a></li>
			             <li><a href="stu?p=ConsultaMovimentoEntradaTipo" title="Relatório Mensal de Entradas por Tipo e Período">Entrada por Tipo</a></li>
			             <li><a href="stu?p=ConsultaMovimentoSaida" title="Relatório Mensal de Saidas">Saida</a></li>
			          </ul>
			       </li>
			       <li><a href="stu?p=ConsultaMovimentoPeriodo" title="Relatório de Entradas e Saidas por Período">Fluxo de caixa</a></li>
			    </ul>
			  </li>
			  <li><a><img src="imagens/spacer_1x1px.png" width="25" border="0" />Outros<img src="imagens/spacer_1x1px.png" width="25" border="0" /></a>
			    <ul>
			       <li><a href="stu?p=ConsultaRecibo&controle=cancelar" title="Cancelamento de recibos">Cancela Recibo</a></li>
			       <li><a href="stu?p=FinalizaMes" title="Finaliza movimentação do mês">Finaliza Mes</a></li>
			    </ul>
			  </li>
			  <li><a href="stu?p=Acesso&flg=1" title="Fechar Sistema para o Operador Atual"><img src="imagens/spacer_1x1px.png" width="42" border="0" />Sair<img src="imagens/spacer_1x1px.png" width="41" border="0" /></a></li>
			</ul>
		</div>
	</body>
</html>
