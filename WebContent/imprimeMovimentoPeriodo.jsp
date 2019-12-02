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
            <table width="660" frame="void" align="center">
			   <tr><td colspan="2">&nbsp;</td></tr>	  
            </table>
            <table width="660" border="1" align="center" bordercolor="#000000">
			   <tr bordercolor="#000000">
			      <td colspan="2"><div align="center" style="font-size: 16px; color: #333333;">MOVIMENTO DE CAIXA NO PERÍODO:<img src="imagens/spacer_1x1px.png" width="5" border="0" />${inicioperiodo}<img src="imagens/spacer_1x1px.png" width="2" border="0" />a<img src="imagens/spacer_1x1px.png" width="2" border="0" />${finalperiodo}</div></td>
			   </tr>			   	  

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
			   <!--  -->               
       		   <tr bordercolor="#000000">     
		          <td width="510"><div align="left" style="font-size: 16px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo Total</div></td> 
		      	  <td width="150"><div align="right" style="font-size: 16px; color: #333333; font-weight: bold;"><f:formatNumber value="${vlrtotalentradas}" minFractionDigits="2" /></div></td>
               </tr>

		       <!-- Saidas --> 
               <tr bordercolor="#ffffff"><td colspan="2">&nbsp;</td></tr>
               <c:set var="vlrtotal" value="0"/>
		       <c:set var="vlrtotalsaidas" value="0"/>
		       <c:set var="codSaida" value="0"/>
		       <c:set var="descSaida" value="-"/>
		       <tr bordercolor="#000000">
		          <td width="510"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />SAIDAS</div></td>
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
			      <td width="510"><div align="left" style="font-size: 16px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Total Saidas</div></td> 
			      <td width="150"><div align="right" style="font-size: 16px; color: #333333; font-weight: bold;"><f:formatNumber value="${vlrtotalsaidas}" minFractionDigits="2" /></div></td>
	           </tr>
               
		       <tr bordercolor="#000000">     
		          <td width="510"><div align="left" style="font-size: 16px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Saldo do Periodo</div></td> 
		          <td width="150"><div align="right" style="font-size: 16px; color: #333333; font-weight: bold;"><f:formatNumber value="${vlrtotalentradas - vlrtotalsaidas}" minFractionDigits="2" /></div></td>
		       </tr>
		       <tr><td bordercolor="#ffffff" colspan="2">&nbsp;</td></tr>
		       <tr><td bordercolor="#ffffff" colspan="2"><div align="center">As saidas neste relatorio consideram o(s) mês(es) do período</div></td></tr>
            </table>
            </form>
		<table width="660" border="1" bgcolor="#D6D6D6" align="center"> <!--  -->
		  <tr>
		    <td colspan="3" ><div align="right" class="style8">Operador: ${objUsu.nome} - Cargo: ${objUsu.cargo} - &copy; 2010 / CEBUDV</div></td>
		  </tr>
		</table>
 </div>
 </div>
 </div>
</body>
</html>

