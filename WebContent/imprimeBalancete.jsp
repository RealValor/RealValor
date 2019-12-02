<%@ page contentType="text/html; charset=ISO-8859-1" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> <!-- charset=utf-8 --> 
   <meta http-equiv="Pragma" content="no-cache"/> 
   <title>Real Valor - Sistema Financeiro UDV</title>
   <link rel="stylesheet" href="css/principal.css" type="text/css" />
   <script src="scripts/funcoes.js" type="text/javascript" language="javascript" > </script>
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
            <table width="660" frame="void" align="center">
			   <tr><td colspan="2">&nbsp;</td></tr>	  
            </table>
            <table width="660" border="1" align="center" bordercolor="#000000">
			   <tr bordercolor="#000000">
			      <td colspan="2"><div align="center" style="font-size: 16px; color: #333333;">BALANCETE DE VERIFICAÇÃO - MÊS:<img src="imagens/spacer_1x1px.png" width="5" border="0" />${objMovIni.mesExtenso}/${objMovIni.ano}</div></td>
			   </tr>			   	  
		       <tr bordercolor="#000000">
		          <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />SALDO ANTERIOR</div></td>
		          <td width="150"><div align="center" style="font-size: 16px; color: #333333;">VALOR</div></td>
		       </tr> 
			   <c:if test="${not empty objSaldoAnt}">
			      <c:if test="${objSaldoAnt.saldoAnteriorCaixa!=0}">
           	         <tr bordercolor="#000000">
		      	        <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo em caixa</div></td> 
		      	        <td width="150"><div align="right" style="font-size: 16px; color: #333333;"><f:formatNumber value="${objSaldoAnt.saldoAnteriorCaixa}" minFractionDigits="2" /></div></td>
		      	     </tr>
		      	  </c:if>
			      <c:if test="${objSaldoAnt.saldoAnteriorBanco!=0}">
		      	     <tr bordercolor="#000000">     
		      	        <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo em banco</div></td> 
		      	        <td width="150"><div align="right" style="font-size: 16px; color: #333333;"><f:formatNumber value="${objSaldoAnt.saldoAnteriorBanco}" minFractionDigits="2" /></div></td> 
		      	     </tr>
		      	  </c:if>
		      	  <c:if test="${(objSaldoAnt.saldoAnteriorCaixa + objSaldoAnt.saldoAnteriorBanco)!=0}">
		      	     <tr bordercolor="#000000">     
		      	        <td width="510"><div align="left" style="font-size: 16px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo Total Anterior</div></td> 
		      	        <td width="150"><div align="right" style="font-size: 16px; color: #333333; font-weight: bold;"><f:formatNumber value="${objSaldoAnt.saldoAnteriorCaixa + objSaldoAnt.saldoAnteriorBanco}" minFractionDigits="2" /></div></td> 
		      	     </tr>
		      	  </c:if>

		       </c:if>

		       <!-- Entradas --> 
		       <tr bordercolor="#ffffff"><td colspan="2">&nbsp;</td></tr>		       
		       <c:set var="vlrtotal" value="0"/>
		       <c:set var="vlrtotalentradas" value="0"/>
		       <c:set var="codEntrada" value="0"/>
		       <c:set var="descEntrada" value="-"/>
		       <tr bordercolor="#000000">
		          <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />ENTRADAS</div></td>
		          <td width="150"><div align="center" style="font-size: 16px; color: #333333;">VALOR</div></td>
		       </tr> 
		       <c:forEach var="linhasentrada" items="${listEntrada}">
                  <c:if test="${codEntrada != linhasentrada.entrada.codigo && vlrtotal!=0}">
		      	     <tr bordercolor="#000000">
		      	        <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />${descEntrada}</div></td> 
		      	        <td width="150"><div align="right" style="font-size: 16px; color: #333333;"><f:formatNumber value="${vlrtotal}" minFractionDigits="2" /></div></td>
                     </tr>                  
                     <c:set var="vlrtotal" value="0"/>
                  </c:if>
		      	  <c:set var="codEntrada" value="${linhasentrada.entrada.codigo}"/> 
                  <c:set var="descEntrada" value="${linhasentrada.entrada.descricao}"/>
                  <c:set var="vlrtotal" value="${vlrtotal + linhasentrada.valor}"/>
                  <c:set var="vlrtotalentradas" value="${vlrtotalentradas + linhasentrada.valor}"/>
       		   </c:forEach>
       		   <c:if test="${vlrtotal!=0}">
		          <tr bordercolor="#000000">     
		      	     <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />${descEntrada}</div></td> 
		      	     <td width="150"><div align="right" style="font-size: 16px; color: #333333;"><f:formatNumber value="${vlrtotal}" minFractionDigits="2" /></div></td> 
                  </tr>                  
               </c:if>       		   
       		   <tr bordercolor="#000000">     
		          <td width="510"><div align="left" style="font-size: 16px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Total Entradas</div></td> 
		      	  <td width="150"><div align="right" style="font-size: 16px; color: #333333; font-weight: bold;"><f:formatNumber value="${vlrtotalentradas}" minFractionDigits="2" /></div></td>
               </tr>    
       		   <tr bordercolor="#000000">     
		          <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo Anterior</div></td> 
		      	  <td width="150"><div align="right" style="font-size: 16px; color: #333333;"><f:formatNumber value="${objSaldoAnt.saldoAnteriorCaixa+objSaldoAnt.saldoAnteriorBanco}" minFractionDigits="2" /></div></td>
               </tr>
			   <!--  -->
			   <c:if test="${dividaAberta!=0}">
				   <tr bordercolor="#000000">     
			          <td width="510"><div align="left" style="font-size: 15px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Antecipação de Socios e/ou de Terceiros (Empréstimos/compras a prazo)</div></td> 
			      	  <td width="150"><div align="right" style="font-size: 16px; color: #333333;"><f:formatNumber value="${dividaAberta}" minFractionDigits="2" /></div></td>
	               </tr>
               </c:if>
			   <!--  -->               
       		   <tr bordercolor="#000000">     
		          <td width="510"><div align="left" style="font-size: 16px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo Total (Entradas)</div></td> 
		      	  <td width="150"><div align="right" style="font-size: 16px; color: #333333; font-weight: bold;"><f:formatNumber value="${vlrtotalentradas+objSaldoAnt.saldoAnteriorCaixa+objSaldoAnt.saldoAnteriorBanco+dividaAberta}" minFractionDigits="2" /></div></td>
               </tr>

		       <!-- Saidas --> 
               <tr bordercolor="#ffffff"><td colspan="2">&nbsp;</td></tr>
               <c:set var="vlrtotal" value="0"/>
		       <c:set var="vlrtotalsaidas" value="0"/>
		       <c:set var="codSaida" value="0"/>
		       <c:set var="descSaida" value="-"/>
		       <tr bordercolor="#000000">
		          <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />SAÍDAS</div></td>
		          <td width="150"><div align="center" style="font-size: 16px; color: #333333;">VALOR</div></td>
		       </tr>
		       <c:forEach var="linhassaidas" items="${listSaida}">
                  <c:if test="${codSaida != linhassaidas.saida.codigo && vlrtotal!=0}">
		      	     <tr bordercolor="#000000">     
		      	        <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />${descSaida}</div></td> 
		      	        <td width="150"><div align="right" style="font-size: 16px; color: #333333;"><f:formatNumber value="${vlrtotal}" minFractionDigits="2" /></div></td> 
                     </tr>                  
                     <c:set var="vlrtotal" value="0"/>
                  </c:if>
          	      <c:set var="codSaida" value="${linhassaidas.saida.codigo}"/> 
                  <c:set var="descSaida" value="${linhassaidas.saida.descricao}"/>
                  <c:set var="vlrtotal" value="${vlrtotal + linhassaidas.valor}"/>
                  <c:set var="vlrtotalsaidas" value="${vlrtotalsaidas + linhassaidas.valor}"/>
       		   </c:forEach>
       		   <c:if test="${vlrtotal!=0}">
		          <tr bordercolor="#000000">
		      	     <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />${descSaida}</div></td> 
		      	     <td width="150"><div align="right" style="font-size: 16px; color: #333333;"><f:formatNumber value="${vlrtotal}" minFractionDigits="2" /></div></td> 
                  </tr>                  
               </c:if>
	       	   <tr bordercolor="#000000">     
			      <td width="510"><div align="left" style="font-size: 16px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Total Saídas</div></td> 
			      <td width="150"><div align="right" style="font-size: 16px; color: #333333; font-weight: bold;"><f:formatNumber value="${vlrtotalsaidas}" minFractionDigits="2" /></div></td>
	           </tr>
               
               <c:if test="${dividaPaga!=0}">
	              <tr bordercolor="#000000">    
	              	  <!-- <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Pagamento de Compras Parceladas</div></td> --> <!-- N. Me. Bartolomeu --> 
			          <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Pagamentos de Antecipação de Socios e/ou de Terceiros</div></td>
			      	  <td width="150"><div align="right" style="font-size: 16px; color: #333333;"><f:formatNumber value="${dividaPaga}" minFractionDigits="2" /></div></td>
	              </tr>       		   
		       	  <tr bordercolor="#000000">     
				     <td width="510"><div align="left" style="font-size: 16px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo Total (Saídas)</div></td> 
				     <td width="150"><div align="right" style="font-size: 16px; color: #333333; font-weight: bold;"><f:formatNumber value="${vlrtotalsaidas+dividaPaga}" minFractionDigits="2" /></div></td>
		          </tr>
               </c:if>
               
               <tr bordercolor="#ffffff"><td colspan="2">&nbsp;</td></tr>
       		   <tr bordercolor="#000000">
       		      <td width="510"><div align="left" style="font-size: 16px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Entradas do Mês - Saídas do Mês</div></td> 
		      	  <td width="150"><div align="right" style="font-size: 16px; color: #333333; font-weight: bold;"><f:formatNumber value="${(vlrtotalentradas + dividaAberta) - (vlrtotalsaidas + dividaPaga)}" minFractionDigits="2" /></div></td>
               </tr> 
       		   <tr bordercolor="#000000">
       		      <td width="510"><div align="left" style="font-size: 16px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo Anterior</div></td> 
		      	  <td width="150"><div align="right" style="font-size: 16px; color: #333333; font-weight: bold;"><f:formatNumber value="${(objSaldoAnt.saldoAnteriorCaixa + objSaldoAnt.saldoAnteriorBanco)}" minFractionDigits="2" /></div></td>
               </tr> 
               <tr bordercolor="#ffffff"><td colspan="2">&nbsp;</td></tr>
               
               <c:set var="vlrtotal" value="0"/>
               <c:if test="${tamanhoLista > 1 }">
		       	   <c:forEach var="linhassaldobancos" items="${listaSaldoBanco}">
		      	     <tr bordercolor="#000000">     
			             <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />SALDO ${linhassaldobancos.banco.descricao}</div></td> 
			      	     <td width="150"><div align="right" style="font-size: 16px; color: #333333;"><f:formatNumber value="${linhassaldobancos.valor}" minFractionDigits="2" /></div></td>
                     </tr>                  
	                 <c:set var="vlrtotal" value="${vlrtotal + 1}"/>
	       		   </c:forEach>
	               <tr bordercolor="#ffffff"><td colspan="2">&nbsp;</td></tr>
               </c:if>

       		   <tr bordercolor="#000000">     
		          <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />SALDO CAIXA</div></td> 
		      	  <td width="150"><div align="right" style="font-size: 16px; color: #333333;"><f:formatNumber value="${(vlrtotalentradas + objSaldoAnt.saldoAnteriorCaixa + objSaldoAnt.saldoAnteriorBanco  + dividaAberta) - (vlrtotalsaidas + dividaPaga + saldoBanco)}" minFractionDigits="2" /></div></td>
               </tr>

               <c:if test="${saldoBanco!=0}"> 
       		      <tr bordercolor="#000000"> 
       		      	 <c:if test="${vlrtotal>0}">
			             <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />SALDO TOTAL BANCOS</div></td> 
       		      	 </c:if> 
       		      	 <c:if test="${vlrtotal<1}">
			             <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />SALDO BANCO</div></td> 
       		      	 </c:if> 
		      	     <td width="150"><div align="right" style="font-size: 16px; color: #333333;"><f:formatNumber value="${saldoBanco}" minFractionDigits="2" /></div></td>
                  </tr>
               </c:if>
               
		       <tr bordercolor="#000000">     
		          <td width="510"><div align="left" style="font-size: 16px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo Total em:<img src="imagens/spacer_1x1px.png" width="5" border="0" />${objMovIni.mesExtenso}/${objMovIni.ano}</div></td> 
		          <td width="150"><div align="right" style="font-size: 16px; color: #333333; font-weight: bold;"><f:formatNumber value="${(vlrtotalentradas + objSaldoAnt.saldoAnteriorCaixa + objSaldoAnt.saldoAnteriorBanco  + dividaAberta) - (vlrtotalsaidas + dividaPaga)}" minFractionDigits="2" /></div></td> 
		       </tr>
            </table>
            <c:if test="${objUsu.nivel > 2}" >
	            <table width="660" frame="void" align="center">
	               <tr><td colspan="3">&nbsp;</td></tr>
	               <tr><td colspan="3">&nbsp;</td></tr>
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
			</c:if>
            
            </form>
		<table width="660" border="1" bgcolor="#D6D6D6" align="center"> <!--  -->
		  <tr>
		    <td colspan="3" ><div align="right" class="style8">Operador: ${objUsu.nome} - Cargo: ${objUsu.cargo} - &copy; 2010 / UDV</div></td>
		  </tr>
		</table>
 </div>
 </div>
 </div> 
</body>
</html>

