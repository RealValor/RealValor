<%@ page contentType="text/html; charset=ISO-8859-1" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
            <table width="770" frame="void" >
			   <tr><td colspan="17">&nbsp;</td></tr>	  
            </table>
            <table width="770" border="1" align="center" bordercolor="#000000">
			   <c:if test="${not empty listSaida}">
			   	  <div align="left" style="font-size: 16px; color: #333333;">Despesas - mes:<img src="imagens/spacer_1x1px.png" width="5" border="0" /><f:formatNumber value="${objMovIni.mes}" pattern="00" />/${objMovIni.ano}</div>			   	  
           	      <tr bordercolor="#000000">
		      	      <td><div align="center" style="font-size: 12px; color: #999999;">Tipo de Despesa</div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #999999;">Data</div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #999999;">Tipo Docto.</div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #999999;">Número</div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #999999;">Fornecedor</div></td> 
		      	      <td><div align="center" style="font-size: 12px; color: #999999;">Valor</div></td> 
                  </tr>          
		       </c:if>		       
		       <c:set var="vlrtotal" value="0"/>
		       <c:set var="vlrtotalgeral" value="0"/>
		       <c:set var="tipoSaida" value="${linhassaida.saida.codigo}"/>
		       <c:forEach var="linhassaida" items="${listSaida}">
                  <c:if test="${tipoSaida != linhassaida.saida.codigo && vlrtotal!=0}">
                     <tr bordercolor="#000000"><td colspan="5"><div align="right" style="font-size: 12px; color: #333333;">Total do Grupo:<img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
                     <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${vlrtotal}" minFractionDigits="2" /></div></td></tr>
					 <tr bordercolor="#ffffff"><td colspan="6">&nbsp;</td></tr>
                     <c:set var="vlrtotal" value="0"/>
                  </c:if>
                  <c:set var="tipoSaida" value="${linhassaida.saida.codigo}"/>
           	      <tr bordercolor="#000000">
               	  <td><div align="left" style="font-size: 12px; color: #333333;">${linhassaida.saida.descricao}</div></td> 
               	  <td><div align="center" style="font-size: 12px; color: #333333;"><f:formatDate value="${linhassaida.dataDocumento}" type="date" pattern="dd/MM/yyyy" /></div></td> 
               	  <td><div align="left" style="font-size: 12px; color: #333333;">${linhassaida.tipoDocumento.descricao}</div></td> 
               	  <c:if test="${linhassaida.documento == ''}">
               	     <td><div align="center" style="font-size: 12px; color: #333333;">-</div></td> 
               	  </c:if> 
               	  <c:if test="${linhassaida.documento != ''}">
               	     <td><div align="left" style="font-size: 12px; color: #333333;">${linhassaida.documento}</div></td> 
               	  </c:if> 
               	  <c:if test="${linhassaida.flSocio != 'N'}">
               	     <td><div align="left" style="font-size: 12px; color: #333333;">${linhassaida.objetoSocio.nome}</div></td>
               	  </c:if>
               	  <c:if test="${linhassaida.flSocio == 'N'}">
               	     <td><div align="left" style="font-size: 12px; color: #333333;">${linhassaida.objetoNaoSocio.nome}</div></td>
               	  </c:if>
               	  <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhassaida.valor}" minFractionDigits="2" /></div></td>
                  </tr>
                  <c:set var="vlrtotal" value="${vlrtotal + linhassaida.valor}"/>
                  <c:set var="vlrtotalgeral" value="${vlrtotalgeral + linhassaida.valor}"/>
       		   </c:forEach>
               <c:if test="${vlrtotal!=0}">
                  <tr bordercolor="#000000"><td colspan="5"><div align="right" style="font-size: 12px; color: #333333;">Total do Grupo:<img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
                  <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${vlrtotal}" minFractionDigits="2" /></div></td></tr>
				  <tr bordercolor="#ffffff"><td colspan="6">&nbsp;</td></tr>
               </c:if>
       		   <c:if test="${vlrtotalgeral != 0}">
                  <tr bordercolor="#000000"><td colspan="5"><div align="right" style="font-size: 12px; color: #333333;">Total Geral:<img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
                  <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${vlrtotalgeral}" minFractionDigits="2" /></div></td></tr>
               </c:if>
            </table>
            </form>
			<table width="760" border="1" bgcolor="#D6D6D6">
		<hr/>
  <tr>
    <td scope="col"><div align="right" class="style8"><h3 class="style5">Operador: ${objUsu.nome} - Cargo: ${objUsu.cargo} - &copy; 2010 / UDV</h3></div></td>
  </tr>
</table>
 </div>
 </div>
 </div>
</body>
</html>

