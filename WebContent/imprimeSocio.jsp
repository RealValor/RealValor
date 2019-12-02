<%@ page contentType="text/html; charset=ISO-8859-1" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:useBean id="data" class="java.util.Date"/>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> <!-- charset=utf-8 --> 
   <meta http-equiv="Pragma" content="no-cache" /> 
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
 		<c:set var="contgrau" value="0"/>
 		<c:set var="conttotal" value="0"/>
 		<!-- inicio -->
            <table width="763" frame="void" >
			   <tr><td colspan="17">&nbsp;</td></tr>	  
            </table>
            <table width="770" border="1" bordercolor="#000000">       
            
               <div align="left" style="font-size: 16px; color: #333333;">Lista de Associados (Todos)<img src="imagens/spacer_1x1px.png" width="5" border="0" />- <f:formatDate value="${data}" type="date" pattern="dd/MM/yyyy" /></div>
               <c:set var="grau" value="N"/>               
		       <c:forEach var="linhaslistasocio" items="${listSocio}">
		       	  <c:set var="cont" value="${cont + 1}"/>
				  <c:if test="${grau != linhaslistasocio.grau}">
		            <c:if test="${contgrau > 0}">
		            	<tr bordercolor="#ffffff"><td colspan="17"><div align="left" style="font-size: 16px; color: #333333;">Total: ${contgrau}</div></td></tr>
		            </c:if>               
				  	<tr bordercolor="#ffffff"><td colspan="17">&nbsp;</td></tr>					
                    <tr bordercolor="#ffffff"><td colspan="17"><div align="left" style="font-size: 16px; color: #333333;">${linhaslistasocio.grau=='M'?'QUADRO DE MESTRES':(linhaslistasocio.grau=='C'?'CORPO DO CONSELHO':(linhaslistasocio.grau=='I'?'CORPO INSTRUTIVO':'QUADRO DE SÓCIOS'))}</div></td></tr>
		            <c:set var="grau" value="${linhaslistasocio.grau}"/>
		            <c:set var="contgrau" value="0"/>
        		  </c:if>       		
				  <c:set var="contgrau" value="${contgrau + 1}"/>
				  <c:set var="conttotal" value="${conttotal + 1}"/>
           	      <tr bordercolor="#000000">
               		<td width="45" border="0"><div align="center" style="font-size: 14px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="1" border="0" />${cont}</div></td>               		
               		<td width="542"><div align="left" style="font-size: 14px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="10" border="0" />${linhaslistasocio.nome}</div></td> 
               		<td width="54" colspan="1 62"><div align="left" class="style5 style9"><img src="imagens/spacer_1x1px.png" width="1" border="0" /></div></td>
                 </tr>
       		   </c:forEach>
		       <tr bordercolor="#ffffff"><td colspan="17"><div align="left" style="font-size: 16px; color: #333333;">Total: ${contgrau}</div></td></tr>       		   
		       <tr bordercolor="#ffffff"><td colspan="17"><div align="left" style="font-size: 16px; color: #333333;">Total Geral: ${conttotal}</div></td></tr>       		   
            </table>
            </form>
			<table width="760" border="1" bgcolor="#D6D6D6">
		<hr />
  <tr>
    <td scope="col"><div align="right" class="style8"><h3 class="style5">Operador: ${objUsu.nome} - Cargo: ${objUsu.cargo} - &copy; 2010 / UDV</h3></div></td>
  </tr>
</table>
 </div>
 </div>
 </div> 
</body>
</html>

