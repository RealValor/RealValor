<%@ page contentType="text/html; charset=ISO-8859-1" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> <!-- charset=utf-8 --> 
   <meta http-equiv="Pragma" content="no-cache" /> 
   <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
   <script src="http://hongru.github.io/proj/canvas2image/canvas2image.js"></script>

   <title>Real Valor - Sistema Financeiro UDV</title>
   <link rel="stylesheet" href="css/principal.css" type="text/css" />
   
   <style>.container { margin-top: 10px; border: solid 1px black; }  </style>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
   
   <style type="text/css">
	   body {
	   		background-repeat: no-repeat;
	   		float:left;
			-webkit-print-color-adjust: exact;
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
				<div align="center" style="font-size: 24px; color: #333333;"><a>RECIBO</a></div>

		 		<c:set var="cont" value="0"/>
		 		<c:forEach var="linhasrecibo" items="${listaRec}">

			 		<c:if test="${cont < 1}">

		           		<c:if test="${not empty linhasrecibo.socioPagador.email}">
			           		<td>
								<div align="right"><a href="javascript: enviaRecibo()" ><img src="imagens/email.png" alt="Enviar" border="0" title="Enviar recibo por e-mail"/></a><a href="javascript:window.print()" ><img src="imagens/imprimir.PNG" alt="Imprimir" border="0" title="Imprimir recibo"/></a></div>
			           		</td>
						</c:if>
		           		<c:if test="${empty linhasrecibo.socioPagador.email}">
							<div align="right"><a href="javascript:window.print()" ><img src="imagens/imprimir.PNG" alt="Imprimir" border="0" title="Imprimir recibo"/></a></div>		   	  
		           		</c:if>
		
					   <div align="left" style="font-size: 16px; color: #333333;">Recibo Nº:<img src="imagens/spacer_1x1px.png" width="5" border="0" /><f:formatNumber value="${numeroRecibo}" pattern="0000" />/<f:formatDate type="date" pattern="yyyy" value="${linhasrecibo.data.time}" /></div>
					   <div align="left" style="font-size: 16px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />${linhasrecibo.flSocio=='N'?'':(linhasrecibo.socioPagador.grau=='M'?'M.':(linhasrecibo.socioPagador.grau=='C'?'Cons.':'Ir.'))}
					   <img src="imagens/spacer_1x1px.png" width="5" border="0" /> ${linhasrecibo.socioPagador.nome}</div>
					</c:if>
					<input name="codigosocio" id="codigosocio" type="hidden" value="${linhasrecibo.socioPagador.codigo}" />
					<input name="email" id="email" type="hidden" value="${linhasrecibo.socioPagador.email}" />

					<c:set var="cont" value="1"/>
					</c:forEach>
		 	           <table width="763" border="1" >
					   <c:if test="${listaRec != null}">
		           	      <tr>
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">&nbsp;</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">codigo</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">ano</div></td> 
				      	      
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">jan</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">fev</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">mar</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">abr</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">mai</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">jun</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">jul</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">ago</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">set</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">out</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">nov</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">dez</div></td> 
				      	      
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">v.unit.</div></td> 
				      	      <td><div align="center" style="font-size: 12px; color: #999999;">total</div></td> 
		                  </tr>          
				       </c:if>
				       <c:set var="vlrtotalgeral" value="0"/>
				       <c:set var="observacao" value=""/>
				       <c:forEach var="linhasrecibo" items="${listaRec}">
		           	      <tr> 
		               		<td><div align="center" style="font-size: 12px; color: #333333;">${linhasrecibo.entrada.descricao}</div></td> 
		               		<td><div align="center" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasrecibo.socioDevedor.codigo}" pattern="0000"/></div></td>
		               		<td><div align="center" style="font-size: 12px; color: #333333;">${linhasrecibo.ano}</div></td> 
							<c:set var="vlrtotal" value="0"/>
							<c:forEach var="i" begin="0" end="11">
							   <c:if test="${linhasrecibo.meses[i] == 0}">
		               		      <td><div align="center" style="font-size: 12px; color: #999999;">-</div></td> <!-- ${linhasrecibo.meses[i]} --> 
				     	       </c:if>
				     	       <c:if test="${linhasrecibo.meses[i] != 0}">
		               		      <td bgcolor="#999999"><div align="center" style="font-size: 9px; color: #000000;"><strong><f:formatDate value="${recibo.data.time}" type="date" pattern="dd/MM" /></strong></div></td>
		               		      <!-- inserir imagem cinza -> usar cellpading pixels (espaçamento entre a borda e o conteúdo da célula) -->
		               		      <!-- #999999 cor cinza -->
		               		      <c:set var="vlrtotal" value="${vlrtotal + 1}"/>
				     	       </c:if>
							</c:forEach>					
		               	    <td><div align="right" style="font-size: 12px; color: #333333;"><strong><f:formatNumber value="${linhasrecibo.valor}" minFractionDigits="2" /></strong></div></td> 
		               		<td><div align="right" style="font-size: 12px; color: #333333;"><strong><f:formatNumber value="${vlrtotal*linhasrecibo.valor}" minFractionDigits="2" /></strong></div></td>
		               		<c:set var="vlrtotalgeral" value="${vlrtotalgeral + vlrtotal*linhasrecibo.valor}"/> 
		                  </tr>
		                  <c:set var="observacao" value="${linhasrecibo.observacao}"/>
		       		   </c:forEach> 
		       		   <c:if test="${vlrtotalgeral != 0}">
		                  <tr><td colspan="16"><div align="right" style="font-size: 12px; color: #333333;"><strong>Total do Recibo:<img src="imagens/spacer_1x1px.png" width="4" border="0" /></strong></div></td>
		                  <td><div align="right" style="font-size: 12px; color: #333333;"><strong><f:formatNumber value="${vlrtotalgeral}" minFractionDigits="2" /></strong></div></td></tr>
		                  <c:forEach var="linhassocio" items="${listaSoc}">
		                     <tr bordercolor="#ffffff"> 
		                        <td ><div align="center" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhassocio.codigo}" pattern="0000"/></div></td>
		                        <td colspan="16"><div align="left" style="font-size: 12px; color: #333333;">${linhassocio.nome}</div></td>
		                     </tr>
		                     <c:set var="socio" value="${linhassocio.codigo}"/> 
		                  </c:forEach>
		               </c:if>
		            </table>
		            <table width="763" border="0">
		           	   <tr>
		           	   	   <td width="590"><div align="center" style="font-size: 12px; color: #999999;">&nbsp;</div></td>
		           	   	   <!-- bordercolor="#000000" --> 
				      	   <td><div align="left" style="font-size: 12px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Tesouraria em:<img src="imagens/spacer_1x1px.png" width="5" border="0" /><f:formatDate value="${recibo.data.time}" type="date" pattern="dd/MM/yyyy" /></div></td>
				       </tr>
		           	   <tr>
		           	   	   <td width="590"><div align="center" style="font-size: 12px; color: #999999;">&nbsp;</div></td>
		           	   	   <!-- bordercolor="#000000" --> 
				      	   <td><div align="left" style="font-size: 12px; color: #333333;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />Emitido por:</div></td>
				       </tr>
				       <c:if test="${not empty recibo.observacao}">
			           	   <tr>
		                        <td colspan="16"><div align="left" style="font-size: 12px; color: #333333;">Observação: ${recibo.observacao}</div></td>
					       </tr>
				       </c:if>
				    </table>  	      
					<hr/>
					
				<table width="760" border="1" bgcolor="#D6D6D6">
		  <tr>
		    <td scope="col"><div align="right" class="style8"><h3 class="style5">Operador: ${recibo.operador.nome} - Cargo: ${recibo.operador.cargo} - &copy; 2010/CEBUDV</h3></div></td>
		  </tr>
		</table>
		</form>
 	</div>
 </div>
</div>
	
	<script>
		function enviaRecibo() {

			let region = document.querySelector("body");

			html2canvas(region,
					{
						onrendered : function(canvas) {

							var codigoSocio = document.getElementById("codigosocio").value;
							var email = document.getElementById("email").value;

							var b64Image = canvas.toDataURL('image/jpeg', 1.0);
							var u8Image = b64ToUint8Array(b64Image);

							var formData = new FormData();
							formData.append("recibo", new Blob([ u8Image ], {
								type : "image/jpg"
							}));

							var xhr = new XMLHttpRequest();
							xhr.open('POST','stu?p=EnviaEmailSocio&ctrl=1&email='+email+'&cd_socio='+codigoSocio+'&url=/imprimeRecibo.jsp', true);
							xhr.send(formData);
							
							alert("Recibo encaminhado para "+email);
							
						},
					});
			
		}

		function b64ToUint8Array(b64Image) {
			var img = atob(b64Image.split(',')[1]);
			var img_buffer = [];
			var i = 0;
			while (i < img.length) {
				img_buffer.push(img.charCodeAt(i));
				i++;
			}
			return new Uint8Array(img_buffer);
		}

	</script>
</body>
</html>


