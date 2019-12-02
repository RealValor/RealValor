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
			  <li><a><img src="imagens/spacer_1x1px.png" width="18" border="0" />Inclusão<img src="imagens/spacer_1x1px.png" width="18" border="0" /></a>
			    <ul>
			      <!-- 
			      <li><a href="stu?p=IncluiNucleo&cpf_operador=${objUsu.cpf}" title="Inclusão de Núcleos da ${objNucleo.regiao.descricao}">Núcleo<img src="imagens/spacer_1x1px.png" width="72" border="0" /></a>
			       -->
			      <li><a href="stu?p=IncluiNucleoOperador&ctrl=0" title="Inclusão de Núcleos">Núcleo<img src="imagens/spacer_1x1px.png" width="72" border="0" /></a></li>
			      <li><a href="stu?p=IncluiRegiao" title="Inclusão de Regiões">Região<img src="imagens/spacer_1x1px.png" width="72" border="0" /></a></li>
			      <li><a href="stu?p=IncluiTesoureiroRegional" title="Inclusão de Tesoureiros Regionais">Tesoureiro Regional<img src="imagens/spacer_1x1px.png" width="72" border="0" /></a></li>
			   </ul>
			   </li>
			  <li><a><img src="imagens/spacer_1x1px.png" width="10" border="0" />Consulta<img src="imagens/spacer_1x1px.png" width="10" border="0" /></a>
			    <ul>
			       <li><a  href="stu?p=IncluiNucleoOperador&ctrl=1" title="Consulta e Alteração de Regiões e Núcleos">Regiões e Núcleos<img src="imagens/spacer_1x1px.png" width="5" border="0" /></a>
			       <!-- 
			          <ul>          
			             <li><a href="stu?p=ConsultaAniversariante" title="Impressão da Lista de Aniversariantes">Aniversariantes</a></li>
			             <li><a href="stu?p=ConsultaListaSocio&tipo=ativo" title="Impressão da Lista de Associados Ativos">Associados Ativos</a></li>
			             <li><a href="stu?p=ConsultaListaSocio&tipo=todos" title="Relação de Todos os Associados do Núcleo">Todos</a></li>
			          </ul>
			        -->
			       </li>
			       <li><a>Balancete<img src="imagens/spacer_1x1px.png" width="64" border="0" />+</a>
			           <ul>
			           <!-- 
			              <li><a href="stu?p=ConsultaBalancete&tipo=finalizado" title="Balancete Mensal Finalizado">Finalizado</a></li>
			              <li><a href="stu?p=ConsultaBalancete&tipo=periodo" title="Balancete Mensal Finalizado Por Período">Por Periodo</a></li>
			              <li><a href="stu?p=ConsultaBalancete&tipo=previo" title="Balancete Mensal Para Conferência">Prévio</a></li>
			            -->
			              <li><a href="#" title="Balancete Mensal Finalizado">Finalizado</a></li>
			              <li><a href="#" title="Balancete Mensal Finalizado Por Período">Por Periodo</a></li>
			              <li><a href="#" title="Balancete Mensal Para Conferência">Prévio</a></li>
			           </ul>
			       </li>       
			       <li><a>Movimento Mensal<img src="imagens/spacer_1x1px.png" width="7" border="0" />+</a>
			          <ul>
			          <!-- 
			             <li><a href="stu?p=ConsultaMovimentoEntrada" title="Relatório Mensal de Entradas">Entrada</a></li>
			             <li><a href="stu?p=ConsultaMovimentoSaida" title="Relatório Mensal de Saidas">Saida</a></li>
			           -->
			             <li><a href="#" title="Relatório Mensal de Entradas">Entrada</a></li>
			             <li><a href="#" title="Relatório Mensal de Saidas">Saida</a></li>
			          </ul>
			       </li>
			       <li><a href="#" title="Consulta Recebimentos de Fundo de Participação e Fundo de Saúde">Recebimento de F.P.S.G. e F.S.</a></li>
			    </ul>
			  </li>
			  <li><a href="stu?p=Acesso&flg=1" title="Fechar Sistema para o Operador Atual"><img src="imagens/spacer_1x1px.png" width="258" border="0" />Sair<img src="imagens/spacer_1x1px.png" width="259" border="0" /></a></li>
			</ul>
		</div>
	</body>
</html>
