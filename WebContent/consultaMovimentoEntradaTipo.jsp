
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
<link rel="stylesheet" href="css/principal.css" type="text/css" />

<style type="text/css">
	body,td,th {
		font-size: 14px;
	}
	
	.style1 {
		color: #FFCC00;
		font-weight: bold;
	}
	
	.style4 {
		color: #006600;
		font-weight: bold;
	}
	
	.style9 {
		color: #000000
	}
	
	.style10 {
		color: #FFCC00
	}
	
	.style13 {
		font-size: 12px;
		font-weight: bold;
	}
	
	.style14 {
		font-size: 10px
	}
</style>

<jsp:useBean id="objUsu" class="beans.Login" scope="session" />
<jsp:useBean id="objPaga" class="beans.Login" scope="request" />
<jsp:useBean id="objMovIni" class="beans.MovAtual" scope="session" />
<jsp:useBean id="objMovFin" class="beans.MovAtual" scope="session" />
<div class="hero-unit" align="center">
	<div id="estrutura">
		<table width="764" frame="void">
			<tr>
				<td colspan="17">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3"><div align="left"
						style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Consulta Movimento Entrada Por Tipo</div></td>
			</tr>
			<tr><td colspan="17">&nbsp;</td></tr>
		</table>

		<form id="formNome" name="formNome" method="post" action="">
			<table width="763" frame="void">
				<tr>
					<td><div align="left" class="style6">Tipo Entrada<img src="imagens/spacer_1x1px.png" width="2" border="0" />
					
				          <select class="input-medium" name="entrada" id="entrada" style="font-size: 16px; color: #006633; font-weight: bold;" title="Selecione o Tipo de Entrada" onchange='document.getElementById("tipo_entrada").value=entrada.value; document.getElementById("valor").value=entrada.value;' >
				          	 <c:if test="${tipoEntrada.codigo!=0}" >
			                    <option value="${tipoEntrada.codigo}">${tipoEntrada.descricao}</option>
		                   	 </c:if> 
						     <c:forEach var="entradas" items="${listTEnt}">
				                <option value="${entradas.codigo}">${entradas.descricao}</option>
		        			 </c:forEach>
		                  </select>
		 	 			  <input name="tipo_entrada" id="tipo_entrada" type="hidden" style="font-size: 18px; color: #006633; font-weight: bold;" value="${tipoEntrada.codigo}" size="3" maxlength="6" readonly />
						  <input name="urlSet" id="urlSet" type="hidden" value="${urlSet}" />
						</div>

					</td>

					<td><div align="left" class="style6">
							Ano:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
							<select class="input-small" name="ano" id="ano" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("ano_paga").value=ano.value'>
								<option value="${objMovIni.ano}">${objMovIni.ano}</option>
								<option value="${objMovIni.ano -1}">${objMovIni.ano -1}</option>
								<option value="${objMovIni.ano -2}">${objMovIni.ano -2}</option>
							</select> 
							<input name="ano_paga" id="ano_paga" type="hidden" style="font-size: 16px; color: #006633; font-weight: bold;" value="${objMovIni.ano}" title="Escolha o ano na caixa de seleção" size="1" maxlength="1" readonly /> <img src="imagens/spacer_1x1px.png" width="5" border="0" />Mês:<img src="imagens/spacer_1x1px.png" width="5" border="0" /> 
							<select class="input-small" name="escolhemesIni" id="escolhemesIni" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("mes_inicial").value=escolhemesIni.value;'>
								<c:if test="${objMovIni.mes != 0}">
									<option value="${objMovIni.mes}">${objMovIni.mesExtenso}</option>
								</c:if>
								<c:forEach var="meses" items="${listObjMov}">
									<option value="${meses.mes}">${meses.mesExtenso}</option>
								</c:forEach>
							</select> 
							<input name="mes_inicial" id="mes_inicial" type="hidden" style="font-size: 16px; color: #006633; font-weight: bold;" value="${objMovIni.mes}" title="Escolha o mes inicial na caixa de seleção" size="1"
								maxlength="1" readonly /> <img src="imagens/spacer_1x1px.png" width="5" border="0" />Mês:<img src="imagens/spacer_1x1px.png" width="5" border="0" /> 
								<select class="input-small" name="escolhemesfim" id="escolhemesfim" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("mes_final").value=escolhemesfim.value;'>
								<c:if test="${objMovFin.mes != 0}">
									<option value="${objMovFin.mes}">${objMovFin.mesExtenso}</option>
								</c:if>
								<c:forEach var="meses" items="${listObjMov}">
									<option value="${meses.mes}">${meses.mesExtenso}</option>
								</c:forEach>
							</select> <input name="mes_final" id="mes_final" type="hidden" style="font-size: 16px; color: #006633; font-weight: bold;" value="${objMovFin.mes}" title="Escolha o mes final na caixa de seleção" size="1" maxlength="1" readonly /> 
								<input name="controle" id="controle" type="hidden" value="${totEntradas}" size="1" maxlength="2" />
								<input name="urlSet" id="urlSet" type="hidden" value="${urlSet}" />
								
						</div></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
		</form>
		<form name="form" id="form" method="post">
		  <table>
		  	<tr>
			  	<td>
					<input name="cod_pg" id="cod_pg" type="hidden" value="" />
			  	</td>
		  	</tr>                        
          </table>
	  	  <table>
  				<tr>
                	<td colspan="3">
                    	<div align="left">				        	 
				        	<input type="button" onclick="limpar()" name="bt6" value="limpar">
				        	<input type="button" onclick="enviar('ConsultaMovimentoEntradaTipo')" name="bt5" value="Confirma" />
						</div>   
                    </td>   
			    </tr>
		  </table>
   		</form>
		
	</div>
</div>
<c:import url="rodape.jsp" />

<script>

  	    window.onload = function(){document.formNome.entrada.focus();visualizar();}

  		 	if (window.attachEvent) window.attachEvent("onload", navHover);
  		      function AbrirAjax() {
  		   	     var xmlhttp_new;
  		   	     try {
  		   	        xmlhttp_new = new ActiveXObject("Microsoft.XMLHTTP"); //Para o Internet Explorer	
  		   	     } 
  		   	     catch(e) {
  		   	        try {  	   	      
  		   	           xmlhttp_new = new getXMLHTTP(); //IE8	
  		   	        } 
  		   	        catch(ex) {
  		   	           try {  	   	         
  		   	              xmlhttp_new = new XMLHttpRequest(); //Para todos os outros Browsers (FireFox,Opera, etc ...)
  		   	           } 
  		   	           catch(exc) {
  		   	              alert("Seu navegador não tem recursos para uso de Ajax");  	   	            
  		   	              xmlhttp_new = null;
  		   	           }
  		   	        }
  		   	     }
  		   	     return xmlhttp_new;
  		      }

  		var result;
  		    var res_cod;
  		    var res_nome;
  		    var res_situ;
  		  	var mudoumes;

  	     function ShowList(exibir){
  			 div = document.getElementById("list");
  			 
  			 if (exibir==true){
  	  			div.style.display = 'block';
  			 }else{
  	  			div.style.display = 'none';
  			 }
  		 }

  	 	 function preencheCampo(pCampo,pDesc){
  	 	    if(pCampo.value == ""){
  	 	       alert('Necessário Campo '+pDesc);
  	 		   pCampo.focus();
  	 		   return false;
  	 		}
  	 		return true;   
  	 	 }

  	 	 function buscaDados(pMod){
  		    document.formNome.action='stu?p='+pMod;   
  				document.formNome.submit();
  		 }
  		 
  	 	function enviar(pModu){
  			buscaDados(pModu);
  		 }

  		 function limpar(){
  			    document.getElementById("ano").value='';
  			    document.getElementById("mes_inicial").value='';
  			    document.getElementById("mes_final").value='';
  			    buscaDados('ConsultaMovimentoEntradaTipo');
  		 }


	function visualizar() {
		
		var width = 900;
		var height = 500;
		var left = 99;
		var top = 99;
		var now = new Date();
		var seconds = now.getSeconds();
		if (document.getElementById("controle").value+1 > 1) {
			window.open(document.getElementById('urlSet').value+'imprimeMovimentoEntradaTipo.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
		}
	}
</script>
