<%@ page contentType="text/html; charset=ISO-8859-1" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
   <meta http-equiv="Pragma" content="no-cache" /> 
   <title>Real Valor - Sistema Financeiro UDV</title>
   
   <link rel="stylesheet" href="css/principal.css" type="text/css" />
   <script src="scripts/funcoes.js" type="text/javascript" language="javascript" ></script>
   <style type="text/css">
	   <!--
	   body {
	   		background-repeat: no-repeat;
	   		float:left;
			-webkit-print-color-adjust: exact;
	   }	   
	   -->
   </style>   
</head>
   <body onload="window.focus()">

<div style = "position: relative;">
    <img src="imagens/Papel_Timbrado_UDV.png" />
    
    <div style="position: absolute; top: 0px; left: 70px;">
		<div id="estrutura">
		 	<form id="form" name="form" method="post" action="">
			<table  width="763" border="0">
				<tr><td>&nbsp;</td></tr>	
				<tr><td>&nbsp;</td></tr>	
	<!-- 
				<tr><td><div align="center" style="font-size: 24px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="170" border="0" />Núcleo ${objNucleo.nome} - ${objNucleo.regiao.descricao}</div></td></tr>	
	 -->
				<tr><td><div align="center" style="font-size: 24px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="170" border="0" />${objNucleo.nome} - ${objNucleo.regiao.descricao}</div></td></tr>	
				<tr><td><div align="center" style="font-size: 20px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="170" border="0" />${objNucleo.cnpj}</div></td></tr>
				<c:if test="${empty objNucleo.cnpj}">
					<tr><td><div align="center" style="font-size: 20px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="170" border="0" />-</div></td></tr>	
				</c:if>	
			</table>
			<hr/>
		<div align="right"><a href="javascript:window.print()" ><img src="imagens/imprimir.PNG" alt="Imprimir" border="0"/></a></div>		   	  
 		<c:set var="cont" value="0"/>
 		<!-- inicio -->
            <table width="860" frame="void" align="center">
			   <tr><td colspan="2">&nbsp;</td></tr>	  
            </table>
            <table width="860" border="1" align="center" bordercolor="#000000">
			   <tr >
			      <td colspan="14"><div align="center" style="font-size: 16px; color: #333333;">BALANCETE ANUAL - EXERCICIO<img src="imagens/spacer_1x1px.png" width="5" border="0" />${objMovIni.ano}</div></td>
			   </tr>
		       <tr >
		          <!-- -------------------------- -->
		      	      <td><div align="center" style="font-size: 14px; color: #000000;">HISTÓRICO</div></td> <!-- &nbsp; --> 
		      	      
		          <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">jan</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">fev</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">mar</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">abr</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">mai</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">jun</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">jul</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">ago</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">set</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">out</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">nov</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">dez</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">anual</div></td>
			   </tr>
			   <tr >
		      	      <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">Saldo Anterior</div></td>
					  <c:set var="anual" value="0"/>
		      	      <c:set var="controle" value="0"/>
					  <c:forEach var="linhassaldo" items="${listSaldoAnterior}">
						  	 <c:if test="${controle<1}">
						  	    <c:set var="anual" value="${linhassaldo.saldoAnteriorCaixa}"/>
						  	 	<c:set var="controle" value="1"/>
			                 </c:if>
				      	     <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhassaldo.saldoAnteriorCaixa}" minFractionDigits="2" /></div></td>
	       		      </c:forEach>
	       		      <td><div align="right" style="font-size: 12px; color: #333333;"><strong><f:formatNumber value="${anual}" minFractionDigits="2" /></strong></div></td>
			   </tr>

		       <!-- Entradas --> 
		       <tr bordercolor="#ffffff"><td colspan="14">&nbsp;</td></tr>		       
			       <c:set var="vlrtotal" value="0"/>
			       <c:set var="vlrtotalentradas" value="0"/>
			       <c:set var="codEntrada" value="0"/>
			       <c:set var="descEntrada" value="-"/>
			       <c:set var="controlaLinha" value="0"/>
			       <tr >
			    
		          <td ><div align="center" style="font-size: 14px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />ENTRADAS</div></td>
		          
		          <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">jan</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">fev</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">mar</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">abr</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">mai</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">jun</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">jul</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">ago</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">set</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">out</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">nov</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">dez</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">anual</div></td>
		       </tr>
		       <tr > 
				   <c:forEach var="linhasentrada" items="${listEntrada}">
	                  <c:if test="${codEntrada != linhasentrada.entrada.codigo}">
	                     <c:if test="${vlrtotal != 0 }">
		                  	 <td><div align="right" style="font-size: 12px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" /><strong><f:formatNumber value="${vlrtotal}" minFractionDigits="2" /></strong></div></td>
	                     </c:if>
		       			 <tr></tr>
			      	     <td><div align="left" style="font-size: 12px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />${linhasentrada.entrada.descricao}</div></td>
	                     <c:set var="vlrtotal" value="0"/>
	                     <c:set var="controlaLinha" value="0"/>
		      	  		 <c:set var="codEntrada" value="${linhasentrada.entrada.codigo}"/> 
	                  </c:if>
	                  <c:set var="controlaLinha" value="${controlaLinha + 1}"/>
			      	  <td><div align="right" style="font-size: 12px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" /><f:formatNumber value="${linhasentrada.valor}" minFractionDigits="2" /></div></td>

	                  <c:set var="vlrtotal" value="${vlrtotal + linhasentrada.valor}"/>
	                  <c:set var="vlrtotalentradas" value="${vlrtotalentradas + linhasentrada.valor}"/>
	       		   </c:forEach>
	       		   
	       		   <c:if test="${controlaLinha > 0 && controlaLinha < 12}">
				      <c:forEach var="i" begin="1" end="${12 - controlaLinha}">
				      	 <td><div align="right" style="font-size: 12px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" /><f:formatNumber value="0" minFractionDigits="2" /></div></td>
		              </c:forEach>
	               </c:if>
			       <td><div align="right" style="font-size: 12px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" /><f:formatNumber value="${vlrtotal}" minFractionDigits="2" /></div></td>
		       </tr>

               <tr >
		      	  <td ><div align="center" style="font-size: 12px; color: #000000;"><strong>Total Entradas</strong></div></td>
				  <c:set var="anual" value="0"/> <!-- este está certo -->
				  <c:forEach var="linhasreceitas" items="${listReceitas}">
					 <c:set var="anual" value="${anual+linhasreceitas.saldoAnteriorCaixa}"/>
					 <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasreceitas.saldoAnteriorCaixa}" minFractionDigits="2" /></div></td>
	       		  </c:forEach>
	       		  <td><div align="right" style="font-size: 12px; color: #333333;"><strong><f:formatNumber value="${anual}" minFractionDigits="2" /></strong></div></td>
			   </tr>
               <tr >
		      	  <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Saldo Anterior</strong></div></td>
				  <c:set var="saldoanual" value="0"/>
		      	  <c:set var="controle" value="0"/>
				  <c:forEach var="linhassaldo" items="${listSaldoAnterior}">
				  		<c:if test="${controle<1}">
						  	<c:set var="saldoanual" value="${linhassaldo.saldoAnteriorCaixa}"/>
						  	<c:set var="controle" value="1"/>
			            </c:if>
					    <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhassaldo.saldoAnteriorCaixa}" minFractionDigits="2" /></div></td>
	       		  </c:forEach>
	       		  <td><div align="right" style="font-size: 12px; color: #333333;"><strong><f:formatNumber value="${saldoanual}" minFractionDigits="2" /></strong></div></td>
			   </tr>
			   <!-- incluir aqui anecipação aberta -->
               <tr >
		      	  <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Antecipação/Empréstimos</strong></div></td>
				  <c:set var="dividaabertaanual" value="0"/>
				  <c:forEach var="linhasdividaaberta" items="${listDividaAberta}">
					 <c:set var="dividaabertaanual" value="${dividaabertaanual+linhasdividaaberta.valor}"/>
					 <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasdividaaberta.valor}" minFractionDigits="2" /></div></td>
	       		  </c:forEach>
	       		  <td><div align="right" style="font-size: 12px; color: #333333;"><strong><f:formatNumber value="${dividaabertaanual}" minFractionDigits="2" /></strong></div></td>
			   </tr>
			   <!--  -->
       		   <tr>
		          <td ><div align="center" style="font-size: 12px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo Total (Entradas)</div></td> 
		      	  <c:set var="receitaanual" value="0"/>
				  <c:forEach var="linhasreceitas" items="${listSaldoReceitas}">
					<td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasreceitas.saldoAnteriorCaixa}" minFractionDigits="2" /></div></td>
	       		  </c:forEach>
				  <c:set var="receitaanual" value="${anual+saldoanual+dividaabertaanual}"/>
	       		  <td><div align="right" style="font-size: 12px; color: #333333;"><strong><f:formatNumber value="${receitaanual}" minFractionDigits="2" /></strong></div></td>
               </tr>

		       <!-- Saidas --> 
		       <tr bordercolor="#ffffff"><td colspan="14">&nbsp;</td></tr>		       
		       <c:set var="vlrtotal" value="0"/>
		       <c:set var="vlrtotalsaida" value="0"/>
		       <c:set var="controlaLinha" value="0"/>
		       <c:set var="codSaida" value="0"/>
		       <c:set var="descSaida" value="-"/>
		       <tr>

		          <td ><div align="center" style="font-size: 14px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />SAÍDAS</div></td>
		          
		          <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">jan</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">fev</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">mar</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">abr</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">mai</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">jun</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">jul</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">ago</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">set</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">out</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">nov</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">dez</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">anual</div></td>
		       </tr>
		       <tr >
			       <c:forEach var="linhassaida" items="${listSaida}">
	                  <c:if test="${codSaida != linhassaida.saida.codigo}">
	                  	 <c:if test="${vlrtotal != 0 }">
		                  	 <td><div align="right" style="font-size: 12px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" /><strong><f:formatNumber value="${vlrtotal}" minFractionDigits="2" /></strong></div></td>
	                     </c:if>
		       			 <tr></tr>
			      	     <td  ><div align="left" style="font-size: 12px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />${linhassaida.saida.descricao}</div></td>
	                     <c:set var="vlrtotal" value="0"/>
	                     <c:set var="controlaLinha" value="0"/>
		      	  		 <c:set var="codSaida" value="${linhassaida.saida.codigo}"/> 
	                  </c:if>
	                  <c:set var="controlaLinha" value="${controlaLinha + 1}"/>
			      	  <td><div align="right" style="font-size: 12px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" /><f:formatNumber value="${linhassaida.valor}" minFractionDigits="2" /></div></td>
	                  <c:set var="vlrtotal" value="${vlrtotal + linhassaida.valor}"/>
	                  <c:set var="vlrtotalsaidas" value="${vlrtotalsaidas + linhassaida.valor}"/>
	       		   </c:forEach>
	       		   <c:if test="${controlaLinha > 0 && controlaLinha < 12}">
				      <c:forEach var="i" begin="1" end="${12 - controlaLinha}">
		                 <td><div align="right" style="font-size: 12px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" /><f:formatNumber value="0" minFractionDigits="2" /></div></td>
		              </c:forEach>
	               </c:if>
		           <td><div align="right" style="font-size: 12px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" /><f:formatNumber value="${vlrtotal}" minFractionDigits="2" /></div></td>
               </tr>
               
               <tr >
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">Total Saídas</div></td>
				  <c:set var="despesaanual" value="0"/>
				  <c:forEach var="linhasdespesas" items="${listDespesas}">
					 <c:set var="despesaanual" value="${despesaanual+linhasdespesas.saldoAnteriorCaixa}"/>
					 <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasdespesas.saldoAnteriorCaixa}" minFractionDigits="2" /></div></td>
	       		  </c:forEach>
	       		  <td><div align="right" style="font-size: 12px; color: #333333; font-weight: bold;"><f:formatNumber value="${despesaanual}" minFractionDigits="2" /></div></td>
			   </tr>
			   <!-- antecipação anual paga -->
               <tr >
		      	  <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Pagamentos Antecipação</strong></div></td>
				  <c:set var="dividapagaanual" value="0"/>
				  <c:forEach var="linhasdividapaga" items="${listDividaPaga}">
					 <c:set var="dividapagaanual" value="${dividapagaanual+linhasdividapaga.valor}"/>
					 <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasdividapaga.valor}" minFractionDigits="2" /></div></td>
	       		  </c:forEach>
	       		  <td><div align="right" style="font-size: 12px; color: #333333;"><strong><f:formatNumber value="${dividapagaanual}" minFractionDigits="2" /></strong></div></td>
			   </tr>
			   <!--  -->
			   <tr >
		          <td ><div align="center" style="font-size: 12px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo Total (Saídas)</div></td> 
				  <c:set var="despesaanual" value="0"/>
				  <c:forEach var="linhasdespesas" items="${listDespesas}">
				  	<c:set var="despesaanual" value="${despesaanual+linhasdespesas.saldoAnteriorCaixa}"/>
					<c:forEach var="linhasdividaanualpaga" items="${listDividaPaga}">
				  		<c:if test="${linhasdespesas.mes==linhasdividaanualpaga.mes}">
							<td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasdespesas.saldoAnteriorCaixa+linhasdividaanualpaga.valor}" minFractionDigits="2" /></div></td>
						</c:if>
					</c:forEach> 
	       		  </c:forEach>
	       		  <td><div align="right" style="font-size: 12px; color: #333333; font-weight: bold;"><f:formatNumber value="${despesaanual+dividapagaanual}" minFractionDigits="2" /></div></td>
			   </tr>
			   
			   <tr bordercolor="#ffffff"><td colspan="14">&nbsp;</td></tr>
			   <tr>     
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">Entradas - Saídas</div></td>
				  <c:set var="saldoanterior" value="0"/>
				  <c:forEach var="linhasreceitasmenosdespesas" items="${listReceitasMenosDespesas}">
					 <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasreceitasmenosdespesas.saldoAnteriorCaixa}" minFractionDigits="2" /></div></td>
	       		  </c:forEach>
	       		  <td><div align="right" style="font-size: 12px; color: #333333; font-weight: bold;"><f:formatNumber value="${receitaanual-(despesaanual+dividapagaanual)}" minFractionDigits="2" /></div></td>
               </tr>
			   
			   <tr bordercolor="#ffffff"><td colspan="14">&nbsp;</td></tr>
			   <tr>
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold">SALDOS</div></td>
		      	  
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">jan</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">fev</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">mar</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">abr</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">mai</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">jun</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">jul</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">ago</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">set</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">out</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">nov</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">dez</div></td> 
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">anual</div></td>

			   </tr>
			   <tr>     
		      	  <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Caixa</strong></div></td>
		      	  
		      	  <c:set var="anual" value="0"/>
				  <c:forEach var="linhascaixa" items="${listSaldoCaixa}">
					  <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhascaixa.saldoAnteriorCaixa}" minFractionDigits="2" /></div></td>
					  <c:set var="anual" value="${linhascaixa.saldoAnteriorCaixa}"/>	       		  
				  </c:forEach>
	       		  <td  ><div align="right" style="font-size: 12px; color: #333333; font-weight: bold;"><f:formatNumber value="${anual}" minFractionDigits="2" /></div></td>
               </tr>
			   
			   <tr >     
		      	  <td><div align="center" style="font-size: 12px; color: #000000; font-weight: bold;">Bancos</div></td>
		      	  <c:set var="anual" value="0"/>
				  <c:forEach var="linhasbanco" items="${saldoBancos}">
						<td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasbanco.valor}" minFractionDigits="2" /></div></td>
						<c:set var="anual" value="${linhasbanco.valor}"/>
	       		  </c:forEach>
	       		  <td  ><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${anual}" minFractionDigits="2" /></div></td>
               </tr>
			   
       		   <tr >
		          <td ><div align="center" style="font-size: 12px; color: #333333; font-weight: bold;">Totais ${objMovIni.ano}</div></td>
				  <c:set var="saldoanterior" value="0"/>
				  <c:forEach var="linhasreceitasmenosdespesas" items="${listReceitasMenosDespesas}">
					 <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasreceitasmenosdespesas.saldoAnteriorCaixa}" minFractionDigits="2" /></div></td>
	       		  </c:forEach>
	       		  <td ><div align="right" style="font-size: 12px; color: #333333; font-weight: bold;"><f:formatNumber value="${receitaanual-(despesaanual+dividapagaanual)}" minFractionDigits="2" /></div></td>
               </tr>
            </table>
            <table width="860" frame="void" align="center">
               <tr><td colspan="14">&nbsp;</td></tr>
               <tr><td colspan="14">&nbsp;</td></tr>
			   <tr>
			   	  <td><div align="center" style="font-size: 14px; color: #333333; font-weight: bold;">_______________________</div></td>
			   	  <td><div align="center" style="font-size: 14px; color: #333333; font-weight: bold;">_______________________</div></td>
			   	  <td><div align="center" style="font-size: 14px; color: #333333; font-weight: bold;">_______________________</div></td>
			   </tr>

               <!-- assinaturas -->
       		   <tr>     
		          <td><div align="center" style="font-size: 14px; color: #333333; font-weight: bold;">${socioPresidente.nome}</div></td> 
		          <td><div align="center" style="font-size: 14px; color: #333333; font-weight: bold;">${socioTesoureiro.nome}</div></td> 
		          <td><div align="center" style="font-size: 14px; color: #333333; font-weight: bold;">${socioPresFiscal.nome}</div></td> 
               </tr>
               
       		   <tr>     
		          <td><div align="center" style="font-size: 14px; color: #333333; font-weight: bold;">${objCargoPresidente.descricao}</div></td> 
		          <td><div align="center" style="font-size: 14px; color: #333333; font-weight: bold;">${objCargoTesoureiro.descricao}</div></td> 
		          <td><div align="center" style="font-size: 14px; color: #333333; font-weight: bold;">${objCargoConsFiscal.descricao}</div></td> 
               </tr>
            
            </table>
        </form>
		<table width="860" border="1" bgcolor="#D6D6D6" align="center">
		  <tr>
		    <td colspan="3" ><div align="right" class="style8">Operador: ${objUsu.nome} - Cargo: ${objUsu.cargo} - &copy; 2010 / UDV</div></td>
		  </tr>
		</table>
 		</div> 
 		</div>
 		</div>
	</body>
</html>

