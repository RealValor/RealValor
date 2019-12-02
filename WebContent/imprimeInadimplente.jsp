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
	   body {
	   		background-repeat: no-repeat;
	   		float:left;
			-webkit-print-color-adjust: exact;	   		
	   }	   

	   #semborda
	   {
			border-left:0px;
			border-right:0px;
			border-bottom:0px;
			border-top:0px;
	   }
	   
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
			<div align="right"><a href="javascript: enviar('EnviaEmailSocio&ctrl=3&cd_socio=0&url=/consultaInadimplente.jsp')" ><img src="imagens/email.png" alt="Enviar" border="0" title="Notificar todos por e-mail"/></a><a href="javascript:window.print()" ><img src="imagens/imprimir.PNG" alt="Imprimir" border="0"/></a></div>

		    <div align="right"></div>		   	  
					   	  
	 		<c:set var="cont" value="0"/>
	 		<!-- inicio -->
				<c:if test="${debitoAnterior > 0}">
		           <div align="center" style="font-size: 18px; color: #333333;">( Existe(m) inadimplente(s) no ano anterior! )</div>
	       		</c:if> 
	            <table width="820" frame="void" >
				   <tr><td colspan="17">&nbsp;</td></tr>	  
	            </table>
	            <table width="820" border="1" bordercolor="#000000">
				   <c:if test="${not empty listRecI}">
				   	  <div align="left" style="font-size: 16px; color: #333333;">Lista de Inadimplentes -<img src="imagens/spacer_1x1px.png" width="5" border="0" />${objMovIni.mesExtenso}/${objMovIni.ano}</div>			   	  
	           	      <tr bordercolor="#000000">
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">&nbsp;</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">&nbsp;</div></td>
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">jan</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">fev</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">mar</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">abr</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">mai</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">jun</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">jul</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">ago</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">set</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">out</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">nov</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">dez</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">mensal</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">listas</div></td> 
			      	      <td><div align="center" style="font-size: 14px; color: #999999;">total</div></td>
	                  </tr>          
			       </c:if>		       
			       <c:set var="cont" value="0"/>
			       <c:set var="vlrtotalgeral" value="0"/>
			       <c:set var="totalInadimplentes" value="0"/>
			       <c:set var="totalSociosQuites" value="0"/>
			       
			       <c:forEach var="linhasrecibo" items="${listRecI}">
	           	      <c:if test="${not empty linhasrecibo.socioDevedor.nome}">
				          <c:set var="cont" value="${cont + 1}"/>
		           	      <tr bordercolor="#000000">
		           	        <td width="20" border="0"><div align="center" style="font-size: 14px; color: #333333;" >${cont}</div></td>
		               		<td><div align="left" style="font-size: 14px; color: #333333;">${linhasrecibo.socioDevedor.nome}</div></td> 
							<c:set var="totalmeses" value="0"/>
							<c:forEach var="i" begin="0" end="11">
							   <c:if test="${linhasrecibo.meses[i] == 0}">
				     	    	  <c:if test="${i+1 <= objMovIni.mes}">
		                			 <td bgcolor="#cc6633" ><div align="center" style="font-size: 11px; color: #333333;">atraso</div></td>
		                			 
			              		     <c:if test="${totalmeses < 1}">
				               		 	<c:set var="totalInadimplentes" value="${totalInadimplentes + 1}"/>
			               		     </c:if>
			               		     <c:set var="totalmeses" value="${totalmeses + 1}"/>
				     	    	  </c:if>
				     	    	  <c:if test="${i+1 > objMovIni.mes}">
				                	 <td><div align="center" style="font-size: 12px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="10" border="0" />-<img src="imagens/spacer_1x1px.png" width="10" border="0" /></div></td>
				     	    	  </c:if>
				     	       </c:if>
				     	       <c:if test="${linhasrecibo.meses[i] > 0}">
		               		      <td bgcolor="#669933" ><div align="center" style="font-size: 11px; color: #333333;">paga</div></td>

				     	       </c:if>
				     	       <c:if test="${linhasrecibo.meses[i] < 0}">
			                	  <td><div align="center" style="font-size: 12px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="10" border="0" />-<img src="imagens/spacer_1x1px.png" width="10" border="0" /></div></td>
				     	       </c:if>
							</c:forEach>
			               	<td><div align="right" style="font-size: 14px; color: #333333;"><f:formatNumber value="${totalmeses*linhasrecibo.valorMensalidade}" minFractionDigits="2" /></div></td> 
			               	<td><div align="right" style="font-size: 14px; color: #333333;"><f:formatNumber value="${linhasrecibo.valor}" minFractionDigits="2" /></div></td> 
			               	<td><div align="right" style="font-size: 14px; color: #333333;"><f:formatNumber value="${linhasrecibo.valor + totalmeses*linhasrecibo.valorMensalidade}" minFractionDigits="2" /></div></td>
		               		<c:set var="vlrtotalgeral" value="${vlrtotalgeral + totalmeses*linhasrecibo.valorMensalidade + linhasrecibo.valor}"/> 
		               																								  <!-- linhasrecibo.valor = valor total de listas -->
		               		<c:if test="${not empty linhasrecibo.socioDevedor.email}">
			               		<td id="semborda">
				      	      		<div align="right"><a href="javascript: enviar('EnviaEmailSocio&ctrl=2&cd_socio=${linhasrecibo.socioDevedor.codigo}&url=/consultaInadimplente.jsp')" ><img src="imagens/email.png" alt="Enviar" border="0" title="Enviar notificação por e-mail"/></a></div>		   	  
			               		</td>
			      	        </c:if> 
		               		
		                  </tr>
	           	      </c:if>
	       		   </c:forEach>
	       		   <c:set var="socio" value="0"/> 
	       		   <c:if test="${vlrtotalgeral != 0}">
	       		   	  <tr bordercolor="#000000">
	       		   	  <c:if test="${debitoAnterior > 0}">
		                  <td colspan="16"><div align="right" style="font-size: 14px; color: #333333;">Total a receber para o ano informado:<img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
	       		   	  </c:if> 
	       		   	  <c:if test="${debitoAnterior <= 0}">
		                  <td colspan="16"><div align="right" style="font-size: 14px; color: #333333;">Total a receber:<img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
	       		   	  </c:if> 
	                  <td><div align="right" style="font-size: 14px; color: #333333;"><f:formatNumber value="${vlrtotalgeral}" minFractionDigits="2" /></div></td></tr>
	               </c:if>
	               <c:if test="${totalSociosAtivos != 0}">
	                  <tr bordercolor="#ffffff"><td colspan="16"><div align="right" style="font-size: 14px; color: #333333;">Total de socios ativos:<img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
	                  <td><div align="right" style="font-size: 14px; color: #333333;"><f:formatNumber value="${totalSociosAtivos}" /></div></td></tr>
	               </c:if>
	               <c:if test="${totalInadimplentes != 0}">
	                  <tr bordercolor="#ffffff"><td colspan="16"><div align="right" style="font-size: 14px; color: #333333;">Total de inadimplentes da lista:<img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
	                  <td><div align="right" style="font-size: 14px; color: #333333;"><f:formatNumber value="${totalInadimplentes}" /></div></td></tr>
	               </c:if>
	               <c:if test="${totalSociosAtivos != 0}">
	                  <tr bordercolor="#ffffff"><td colspan="16"><div align="right" style="font-size: 14px; color: #333333;">Total de socios quites para a faixa de inadimplencia informada:<img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
	                  <td><div align="right" style="font-size: 14px; color: #333333;"><f:formatNumber value="${totalSociosAtivos-totalInadimplentes}" /></div></td></tr>
	               </c:if>
	            </table>
	            <div align="right" style="font-size: 14px; color: #333333;">Este relatório não inclui débitos de listas contínuas<img src="imagens/spacer_1x1px.png" width="1" border="0" /></div>
            </form>
			<hr />
			<table width="820" border="1" bgcolor="#D6D6D6">
			  <tr>
			    <td scope="col"><div align="right" class="style8"><h3 class="style5">Operador: ${objUsu.nome} - Cargo: ${objUsu.cargo} - &copy; 2010 / UDV</h3></div></td>
			  </tr>
			</table>
		 </div> 
	</div>
	
</div>
 <script> 

 	function enviar(pModu){
		document.form.action='stu?p='+pModu;   
		document.form.submit();
		
		if(pModu.substring(0, 15) == "EnviaEmailSocio"){
			alert("Email encaminhado!");			
		}
	}

 </script>

</body>
</html>

