<%@ page contentType="text/html; charset=ISO-8859-1" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:useBean id="data" class="java.util.Date"/>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> 
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
				<tr><td><div align="center" style="font-size: 24px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="170" border="0" />N�cleo ${objNucleo.nome} - ${objNucleo.regiao.descricao}</div></td></tr>	
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
	            <table width="763" frame="void" >
				   <tr><td colspan="17">&nbsp;</td></tr>	  
	            </table>
	            <div align="left" style="font-size: 16px; color: #333333;">Aniversariantes do mes:<img src="imagens/spacer_1x1px.png" width="5" border="0" />${objMovIni.mesExtenso}/<f:formatDate value="${data}" type="date" pattern="yyyy" /></div>               
	            <table width="763" frame="void" >
				   <tr><td colspan="17">&nbsp;</td></tr>	  
	            </table>
	            <table width="763" border="1" bordercolor="#000000">
	           	   <tr>
	               	  <td width="50"><div align="center" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="1" border="0" />Dia</div></td>
	               	  <td width="400"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Nome</div></td>
	               	  <td width="100"><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="10" border="0" />Telefone</div></td> 
	               	  <td><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="10" border="0" />E-mail</div></td> 
	               </tr>
			       <c:forEach var="linhaslistasocio" items="${listSocio}">
	           	      <tr>
	               		<td><div align="center" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="1" border="0" /><f:formatDate value="${linhaslistasocio.dataNasc}" type="date" pattern="dd/MM" /></div></td>
	               		<td><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />${linhaslistasocio.grau=='M'?'M.':(linhaslistasocio.grau=='C'?'Cons.':'Ir.')} ${linhaslistasocio.nome}</div></td>
	               		<td><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="10" border="0" />${linhaslistasocio.telefone}</div></td> 
	               		<td><div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="10" border="0" />${linhaslistasocio.email}</div></td> 
	                 </tr>
	       		   </c:forEach>
	            </table>
            </form>
			<table width="760" border="1" bgcolor="#D6D6D6">
  <tr>
    <td scope="col"><div align="right" class="style8"><h3 class="style5">Operador: ${objUsu.nome} - Cargo: ${objUsu.cargo} - &copy; 2010 / UDV</h3></div></td>
  </tr>
</table>
 </div>
 </div>
 </div> 
</body>
</html>

