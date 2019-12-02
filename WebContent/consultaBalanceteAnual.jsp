
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
	
	<script>
  	     window.onload = function(){visualizar();}

     	 function buscaDados(pMod){
     		document.formNome.action='stu?p='+pMod;   
  			document.formNome.submit();
		 }

		 function enviar(pModu){
			buscaDados(pModu);
		 }

		 function visualizar(){

			if(document.formNome.controle.value*1<0&&confirm('Existe(m) mês(es) em aberto para o ano informado, balancete anual incompleto! Imprime assim mesmo?')){
			   document.formNome.controle.value=1;
			}
	
			if(document.formNome.controle.value+1>1){
			   var width = 1100;
			   var height = 500;
			   var left = 99;
			   var top = 99;
			   var now=new Date();
			   var seconds = now.getSeconds();

			   window.open(document.getElementById('urlSet').value+'imprimeBalanceteAnual.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=yes, fullscreen=no');
		    }
		 }

	  </script>
	  
 <style type="text/css">
 <!-- 
 body,td,th {
	font-size: 14px;
 }
 .style1 {
	color: #FFCC00;
	font-weight: bold;
 }
 .style4 {color: #006600; font-weight: bold; }
 .style9 {color: #000000}
 .style10 {color: #FFCC00}
 .style13 {
	font-size: 12px;
	font-weight: bold;
 }
 .style14 {font-size: 10px}
 -->
      </style>   
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objPaga" class="beans.Login" scope="request" />
	  <jsp:useBean id="objMovIni" class="beans.MovAtual" scope="session" />
	  <jsp:useBean id="objData" class="java.util.Date" scope="request" />
	  <div class="hero-unit" align="center">
		  <div id="estrutura">
	        <table width="764" frame="void">
			   <tr><td colspan="17">&nbsp;</td></tr>	  
			   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Consulta Balancete Anual</div></td></tr>     
			   <tr><td colspan="17">&nbsp;</td></tr>	  
		    </table>
	           
	        <form id="formNome" name="formNome" method="post" >
	        	<table width="763" frame="void">
	            <tr>
					<td><div align="left" class="style6">
	            		Ano:<img src="imagens/spacer_1x1px.png" width="5" border="0" />                
	                   <select class="input-small" name="ano" id="ano" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("ano_paga").value=ano.value; enviar("AtualizaDadosBalanceteAnual");'>
	                      <option value="${objMovIni.ano}">${objMovIni.ano}</option>
	                      <option value="${objMovIni.ano -1}">${objMovIni.ano -1}</option>
	                      <option value="${objMovIni.ano -2}">${objMovIni.ano -2}</option>
	                      <option value="${objMovIni.ano -3}">${objMovIni.ano -3}</option>
	                   </select>
	                   <input name="ano_paga" id="ano_paga" type="hidden" value="${objMovIni.ano}" title="Escolha o ano na caixa de seleção" readonly/>
	                   <input name="controle" id="controle" type="hidden" value="${controle}" size="1" maxlength="2" />
	                   <input name="urlSet" id="urlSet" type="hidden" value="${urlSet}"/>
	                   <input type="button" onclick="enviar('ConsultaBalanceteAnual')" name="bt5" value="Confirma">
	            	</div></td>
	            </tr>
	            </table>
	            <table width="763" frame="void">
		            <tr><td colspan="3">&nbsp;</td></tr>
		            <tr><td colspan="3">&nbsp;</td></tr>
		            <tr>
						<td><div align="center" class="style6">
		            	   <img src="imagens/spacer_1x1px.png" width="5" border="0" />${socioPresidente.nome}<img src="imagens/spacer_1x1px.png" width="5" border="0" />
			            </div></td>
						<td><div align="center" class="style6">
		                   <img src="imagens/spacer_1x1px.png" width="5" border="0" />${socioTesoureiro.nome}<img src="imagens/spacer_1x1px.png" width="5" border="0" />	                   
		            	</div></td>
						<td><div align="center" class="style6">
		             	   <img src="imagens/spacer_1x1px.png" width="5" border="0" />${socioPresFiscal.nome}<img src="imagens/spacer_1x1px.png" width="5" border="0" />
		            	</div></td>
		            </tr>
		            <tr>	   
						<td><div align="center" class="style6">
						   <select name="cargo_pr" id="cargo_pr" style="font-size: 16px; color: #006633; font-weight: bold; " onchange='document.getElementById("cargo_presidente").value=cargo_pr.value'>
						   	   <c:if test="${objCargoPresidente.codigo!=0}" >
		                       	  <option value="${objCargoPresidente.codigo}">${objCargoPresidente.descricao}</option>
	                   	  	   </c:if>
			                   <c:forEach var="cargopresidente" items="${listCargoPresidente}">
				                  <c:if test="${(objCargoPresidente.codigo!=0&&objCargoPresidente.codigo!=cargopresidente.codigo)||objCargoPresidente.codigo==0}" >
			                         <option value="${cargopresidente.codigo}">${cargopresidente.descricao}</option>
		                   	  	  </c:if>
			                   </c:forEach>
		                   </select>
		                   <input name="cargo_presidente" id="cargo_presidente" type="hidden" value="" readonly/>
			            </div></td>
			            
						<td><div align="center" class="style6">
		                   <select name="cargo_tes" id="cargo_tes" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("cargo_tesoureiro").value=cargo_tes.value'>
						   	   <c:if test="${objCargoTesoureiro.codigo!=0}" >
		                       	  <option value="${objCargoTesoureiro.codigo}">${objCargoTesoureiro.descricao}</option>
	                   	  	   </c:if>
			                   <c:forEach var="cargotesoureiro" items="${listCargoTesoureiro}">
			                   	  <c:if test="${(objCargoTesoureiro.codigo!=0&&objCargoTesoureiro.codigo!=cargotesoureiro.codigo)||objCargoTesoureiro.codigo==0}" >
				                     <option value="${cargotesoureiro.codigo}">${cargotesoureiro.descricao}</option>
	                   	  	   	  </c:if>
			                   </c:forEach>
		                   </select>
		                   <input name="cargo_tesoureiro" id="cargo_tesoureiro" type="hidden" value="" readonly/>
			            </div></td>
	
						<td><div align="center" class="style6">
		                   <select name="cargo_prconsf" id="cargo_prconsf" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("cargo_prconsfiscal").value=cargo_prconsf.value'>
						   	   <c:if test="${objCargoConsFiscal.codigo!=0}" >
		                       	  <option value="${objCargoConsFiscal.codigo}">${objCargoConsFiscal.descricao}</option>
	                   	  	   </c:if>
			                   <c:forEach var="cargopresfiscal" items="${listCargoPresFiscal}">
							   	  <c:if test="${(objCargoConsFiscal.codigo!=0&&objCargoConsFiscal.codigo!=cargopresfiscal.codigo)||objCargoConsFiscal.codigo==0}" >
			                      	 <option value="${cargopresfiscal.codigo}">${cargopresfiscal.descricao}</option>
		                   	  	  </c:if>
			                   </c:forEach>
		                   </select>
			               <input name="cargo_prconsfiscal" id="cargo_prconsfiscal" type="hidden" value="" readonly/>
		            	</div></td>
	
		            </tr>
	            </table>
	            
	            <table width="764" frame="void" >
				   <tr><td colspan="3">&nbsp;</td></tr>	  
	            </table>
	        </form>
			<form name="form" id="form" method="post">
		  	  <table width="763" frame="void">
		  	  		<tr>
	                	<td>
	                    	<div align="left">
					        	<input type="button" onclick="enviar('AtualizaDadosBalanceteAnual')" name="bt4" value="atualizar">
					        	<input type="button" onclick="enviar('AtualizaDadosBalanceteAnual&ano=0')" name="bt6" value="limpar"> 
							</div>
	                    </td>   
					</tr>
			  </table>
	   		</form>
		</div>  
	  </div>
	<c:import url="rodape.jsp"/>
