
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
	  
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
	  <jsp:useBean id="objMovFin" class="beans.MovAtual" scope="session" />
	  <div class="hero-unit" align="center">
		 <div id="estrutura">
	        <table width="764" frame="void">
			   <tr><td colspan="17">&nbsp;</td></tr>	  
			   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Consulta D�vida por Per�odo</div></td></tr>     
			   <tr><td colspan="17">&nbsp;</td></tr>	  
		    </table>
	           
	        <form id="formNome" name="formNome" method="post">
	        	<!--  action="stu?p=ConsultaMensalidade" -->
	        	<table width="763" frame="void">
	            <tr>
					<td><div align="left" class="style6">Ano<img src="imagens/spacer_1x1px.png" width="1" border="0" />                
	                   <select class="input-small" name="ano_balancete" id="ano_balancete" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("ano").value=ano_balancete.value; enviar("AtualizaDadosBalancete&tipo=periodo");'>
	                      <option value="${objMovIni.ano}">${objMovIni.ano}</option>                      
	                      <option value="${objMovIni.ano -1}">${objMovIni.ano -1}</option>
	                      <option value="${objMovIni.ano -2}">${objMovIni.ano -2}</option>
	                      <option value="${objMovIni.ano -2}">${objMovIni.ano -3}</option>
	                   </select>
	                   <input name="ano" id="ano" type="hidden" style="font-size: 16px; color: #006633; font-weight: bold;" value="${objMovIni.ano}" title="Escolha o ano na caixa de sele��o" size="1" maxlength="1" readonly/>
	            	   <img src="imagens/spacer_1x1px.png" width="5" border="0" />M�s:<img src="imagens/spacer_1x1px.png" width="5" border="0" />                

	                   <select class="input-small" name="mesini" id="mesini" style="font-size: 16px; color: #006633; font-weight: bold;">
						  <c:if test="${objMovIni.mes != 0}">
							 <option value="${objMovIni.mes}">${objMovIni.mesExtenso}</option>
						  </c:if>	                   
	                      <c:forEach var="meses" items="${listObjMov}">
	                         <option value="${meses.mes}">${meses.mesExtenso}</option>
	                      </c:forEach>
	                   </select>
	                   
	            	   <img src="imagens/spacer_1x1px.png" width="5" border="0" />M�s:<img src="imagens/spacer_1x1px.png" width="5" border="0" />                
	                   
	                   <select class="input-small" name="escolhemesfim" id="escolhemesfim" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("mesfim").value=escolhemesfim.value;'>
	                      <c:forEach var="meses" items="${listObjMov}">
	                         <option value="${meses.mes}">${meses.mesExtenso}</option>
	                      </c:forEach>
	                   </select>

	                   <input name="mesfim" id="mesfim" type="hidden" value="${objMovFin.mes}" title="Escolha o mes final na caixa de sele��o" size="1" maxlength="1" readonly/>
	                   
	                   <input name="controle" id="controle" type="hidden" value="${controle}" />
	                   <input name="urlSet" id="urlSet" type="hidden" value="${urlSet}"/>
	                   <input type="button" onclick="enviar('ConsultaDividaPeriodo')" name="bt5" value="Confirma">
	            	</div></td>
	            </tr>
	            </table>
	            <table width="763" frame="void" >
				   <tr><td colspan="17">&nbsp;</td></tr>	  
	            </table>
	        </form>
		</div>  
	</div>
	<c:import url="rodape.jsp"/>

			<script>
        window.onload = function(){document.getElementById("ano").focus();visualizar();}

     	 function buscaDados(pMod){
		    document.formNome.action='stu?p='+pMod;   
  			document.formNome.submit();
		 }

		 function enviar(pModu){
			buscaDados(pModu);
		 }

		 function visualizar(){
			var width = 900;
			var height = 500;
			var left = 99;
			var top = 99;
			var now=new Date();
			var seconds = now.getSeconds();
		    if(document.getElementById("controle").value>1){
			   window.open(document.getElementById('urlSet').value+'imprimeDividaPeriodo.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
		    }
		 }
		 
	  </script>
	