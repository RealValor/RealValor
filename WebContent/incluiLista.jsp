
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
	  
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objLista" class="beans.Lista" scope="request" />
	  <jsp:useBean id="data" class="java.util.Date"/>
	  <div class="hero-unit" align="center">
		<div id="estrutura">
	      <table width="764" frame="void">
			 <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Inclusão de Lista de Arrecadação</div></td></tr>     
			 <tr><td colspan="17">&nbsp;</td></tr>	  
		  </table>
	        <form id="formNome" name="formNome" method="post">
	        	<table width="763" frame="void">
	            <tr>
	         	    <td><div align="left" class="style6">Objetivo:<img src="imagens/spacer_1x1px.png" width="1" border="0" />         	 
					<select class="input-medium" name="listaentrada" id="listaentrada" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("entrada").value=listaentrada.value ' >
			           <c:if test="${objLista.entrada.codigo != 0}">
			              <option value="${objLista.entrada.codigo}">${objLista.entrada.descricao}</option>
			           </c:if>
	                   <c:forEach var="entradas" items="${listTEntr}">
			              <option value="${entradas.codigo}">${entradas.descricao}</option>
	        		   </c:forEach>
	                </select>
	                <input name="entrada" id="entrada" type="hidden" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objLista.entrada.codigo}" maxlength="1" readonly />
	                <img src="imagens/spacer_1x1px.png" width="2" border="0" />Data:<img src="imagens/spacer_1x1px.png" width="1" border="0" />
	                <input class="input-small" name="data_lista" type="text" id="data_lista" size="10" maxlength="10" style="font-size: 16px; color: #006633; font-weight: bold;" value='<f:formatDate value="${data}" pattern="dd/MM/yyyy"/>' onkeypress="return mascara(event,this,'##/##/####');" onkeyup="JumpField(this,'valor_unico');"/>
	                <img src="imagens/spacer_1x1px.png" width="2" border="0" />Valor Único:<img src="imagens/spacer_1x1px.png" width="1" border="0" />
	                <input class="input-small" name="valor_unico" type="text" id="valor_unico" size="8" maxlength="8" style="font-size: 16px; color: #006633; font-weight: bold;" value='<f:formatNumber value="0" minFractionDigits="2" type="currency"/>' onkeypress="return soNumero(event)" onblur="formataValor(this)"/>
	                <img src="imagens/spacer_1x1px.png" width="2" border="0" />Contínua?:<img src="imagens/spacer_1x1px.png" width="1" border="0" />
					<select class="input-mini" name="mensal" style="font-size: 14px; color: #006633; font-weight: bold;" onchange='document.getElementById("continua").value=mensal.value' title="Será cobrada mensalmente junto à mensalidade">
						<option value="N">Não</option>
						<option value="S">Sim</option>
					</select>
					<input name="continua" type="hidden" id="continua" size="1" maxlength="1" style="font-size: 18px; color: #006633; font-weight: bold;" value="N"/>
	                </div>
	                </td>
	            </tr>
	            </table>
	            <table width="400" frame="void">
				   <tr>
				   		<td colspan="6">&nbsp;</td>
				   		<td><input name="fechada" type="hidden" id="fechada" value="1" /></td>
				   </tr>	  
	            </table>
	            <table width="760" border="1">
	               <c:set var="vlrtotalgeral" value="0"/> 
				   <c:if test="${not empty listListas}">
	             	<div align="center" style="font-size: 16px; color: #000000;"><strong>Relação de Listas Em Aberto</strong></div>
					<tr bordercolor="#cccccc" bgcolor="#cccccc">
	           	       <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Editar</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Numero da Lista</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Objetivo da Arrecadação</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Data da Lista</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>a Receber</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Valor</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Fechar</strong></div></td>
	                </tr>          
			       </c:if>
			       <c:forEach var="linhaslista" items="${listListas}">
	           	       <tr bgcolor="#ffffff">
	               		<td><div align="center"><a href='stu?p=EditaLista&num=${linhaslista.numero}&ano=${linhaslista.ano}&fechada=0' title="Editar lista"><img src="imagens/edit.png" width="16" height="16" border="0" align="middle" /></a></div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;"><f:formatNumber value="${linhaslista.numero}" pattern="0000"/>/${linhaslista.ano}</div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;">${linhaslista.entrada.descricao}</div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;"><f:formatDate value="${linhaslista.data.time}" pattern="dd/MM/yyyy"/></div></td>

	               		<td><div align="center" style="font-size: 12px; color: #000000;">${linhaslista.total}</div></td>
	               		<td><div align="right" style="font-size: 12px; color: #000000;"><f:formatNumber value="${linhaslista.valor}" minFractionDigits="2" type="currency"/></div></td>
	               		<c:set var="vlrtotalgeral" value="${vlrtotalgeral+linhaslista.valor}"/>

						<c:if test="${linhaslista.valor > 0}">
							<c:if test="${linhaslista.flContinua == 'S'}">
								<c:if test="${linhaslista.qtdePagamentos > 0}">
					                <td><div align="center"><a href='#' title="Lista parcelada em ${linhaslista.qtdePagamentos} vezes. Não pode ser fechada enquanto existir valor maior que zero"><img src="imagens/lista_parcelada.png" width="16" height="16" border="0" align="middle" /></a></div></td>
								</c:if>
								<c:if test="${linhaslista.qtdePagamentos <= 0}">
					                <td><div align="center"><a href='#' title="Lista contínua. Não pode ser fechada enquanto existir valor maior que zero"><img src="imagens/lista_continua.png" width="16" height="16" border="0" align="middle" /></a></div></td>
								</c:if>
							</c:if>
							<c:if test="${linhaslista.flContinua == 'N'}">
				                <td><div align="center"><a href='#' title="Lista não pode ser fechada enquanto existir valor maior que zero"><img src="imagens/fechar.png" width="16" height="16" border="0" align="middle" /></a></div></td>
							</c:if>
						</c:if>

						<c:if test="${linhaslista.valor < 1}">
			                <td><div align="center"><a href='javascript: confirmaExclusaoFechamento("FechaLista&num="+${linhaslista.numero}+"&ano="+${linhaslista.ano})' title="Fechar lista"><img src="imagens/ok.png" width="16" height="16" border="0" align="middle" /></a></div></td>
						</c:if>
	                  </tr>
	       		   </c:forEach>
 				   <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC"><td bordercolor="#ffffff">&nbsp;</td>
		               <td bordercolor="#ffffff" colspan="4"><div align="right" style="font-size: 12px; color: #000000;"><strong>Total a Receber:</strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>                  
		               <td bordercolor="#ffffff"><div align="right" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${vlrtotalgeral}" minFractionDigits="2" maxFractionDigits="2" type="currency"/></strong></div></td>
		               <td bordercolor="#ffffff" colspan="4"><div align="right" style="font-size: 12px; color: #000000;"></div></td>                  
	               </tr>	       		   
	       		</table>
				<table width="760" border="1">
				<c:if test="${not empty listFechadas}">
		             	<div align="center" style="font-size: 16px; color: #848484;"><strong>Relação de Listas Fechadas</strong></div>            	    
		           	    <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
	           	           <td><div align="center" style="font-size: 12px; color: #6e6e6e;">editar</div></td>
				      	   <td><div align="center" style="font-size: 12px; color: #6e6e6e;"><strong>Numero da Lista</strong></div></td>
				      	   <td><div align="center" style="font-size: 12px; color: #6e6e6e;"><strong>Objetivo da Arrecadação</strong></div></td>
				      	   <td><div align="center" style="font-size: 12px; color: #6e6e6e;"><strong>Data da Lista</strong></div></td>

				      	   <td><div align="center" style="font-size: 12px; color: #6e6e6e;"><strong>a Receber</strong></div></td>

	           	       	   <td><div align="center" style="font-size: 12px; color: #6e6e6e;"><strong>Excluir</strong></div></td>
		                </tr>          
					   <c:forEach var="linhaslista" items="${listFechadas}">
		           	      <tr bgcolor="#f2f2f2">
		               		<td><div align="center"><a href='stu?p=EditaLista&num=${linhaslista.numero}&ano=${linhaslista.ano}&fechada=1' title="Editar lista"><img src="imagens/edit_cinza.png" width="16" height="16" border="0" align="middle" /></a></div></td>
		               		<td><div align="center" style="font-size: 12px; color: #848484;"><f:formatNumber value="${linhaslista.numero}" pattern="0000"/>/${linhaslista.ano}</div></td>
		               		<td><div align="center" style="font-size: 12px; color: #848484;">${linhaslista.entrada.descricao}</div></td>
		               		<td><div align="center" style="font-size: 12px; color: #848484;"><f:formatDate value="${linhaslista.data.time}" pattern="dd/MM/yyyy"/></div></td>

		               		<td><div align="center" style="font-size: 12px; color: #848484;">0</div></td>
	                    	<c:if test="${objUsu.nivel > 2 }">
			               		<td><div align="center"><a href='javascript: confirmaExclusaoFechamento("ExcluiLista&num="+${linhaslista.numero}+"&ano="+${linhaslista.ano})' title="Excluir lista"><img src="imagens/excluir_cinza.png" width="16" height="16" border="0" align="middle" /></a></div></td>
							</c:if>
	                    	<c:if test="${objUsu.nivel < 3 }">
			               		<td><div align="center"><a title="Excluir lista"><img src="imagens/excluir_cinza.png" width="16" height="16" border="0" align="middle" /></a></div></td>
							</c:if>

		                  </tr>
		       		   </c:forEach>
			    </c:if>
	            	</table>
					       
	            
	        </form>
			<form name="form" id="form" method="post">
			  <table width="763" frame="void">
			  	<tr><td>
					<input name="cod_pg" id="cod_pg" type="hidden" value="" />
					<input name="tipo_entr" id="tipo_entr" type="hidden" value="1" />				
			  	</td>
			  	</tr>                        
	            <tr>
	            	<td colspan="3">&nbsp;</td>
	            </tr>
	          </table>
			  <table width="764">
	  				<tr>
	                	<td colspan="3">
	                    	<div align="left">
		                    	<c:if test="${objUsu.nivel > 2 }">
						        <input type="button" onclick="incluirNovaLista()" name="bt1" value="Incluir nova lista">
		                    	 
								</c:if>
					        	<c:if test="${statusLista>0}">
							        <input type="button" onclick="mostraFechadas(0)" name="bt6" value="Ocultar Listas Fechadas">
					        	</c:if>
					        	<c:if test="${statusLista<1}">
							        <input type="button" onclick="mostraFechadas(1)" name="bt6" value="Mostrar Listas Fechadas">
					        	</c:if>
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar">
							</div>   
	                    </td>   
					</tr>
			  </table>
	   		</form>
		</div>   	     
	  </div>
	<c:import url="rodape.jsp"/>

	<script>
  	    window.onload = function(){document.formNome.ano.focus();}
  	      
  	      function mascara(e,src,mask){
   	  	     if(window.event){
   	  		    _TXT = e.keyCode;
   	  	     }else if(e.which){
   	  		    _TXT = e.which;
   	  	     }
   	  	     if(_TXT > 47 && _TXT < 58){	
   	  		    var i = src.value.length;
   	  		    var saida = mask.substring(0,1);
   	  		    var texto = mask.substring(i);
   	  		    if(texto.substring(0,1) != saida){
   	  			   src.value += texto.substring(0,1);
   	  		    }
   	  		    return true;
   	  	     }else{
   	  		    if(_TXT != 8){
   	  			   return false;
   	  		    }else{
   	  			   return true;
   	  		    }
   	  	     }
   	      }

 		 function formataValor(campo){
 	  		var valor=limpaValor(campo);
 	  		var result="",ponto=0,i;

 	  		for(i=1;i<=(valor.length);i++){
 	  			result = valor.substr((valor.length)-i,1)+""+result;
 	  			if (i>1){
 	  				if (i==2){
 	  					result=","+result;
 	  				}else{
 	  					ponto=ponto+1;
 	  					if(ponto==3&&i<valor.length){
 	  						result="."+result;
 	  						ponto=0;
 	  					}
 	  				}
 	  			}
 	  		}
 	  		campo.value="R$ "+result;
 	     }
 		 
 	  	 function limpaValor(campo){
 	  		var val=campo.value; 
 	  		var res="",i;
 	  		for(i=1;i<=(val.length);i++){
 	  				if( (val.charCodeAt(val.length-i)>47) && (val.charCodeAt(val.length-i)<58) ){
 	  					res = val.substr((val.length-i),1)+""+res;
 	  				}
 	  		}
 	  		return res;
 	  	 }
 	  	 function trocaVirgula(campo){
 	  		var val=campo; 
 	  		var res="",i;
 	  		for(i=1;i<=(val.length);i++){
 	  				if( (val.charCodeAt(val.length-i)>47) && (val.charCodeAt(val.length-i)<58) ){
 	  					res = val.substr((val.length-i),1)+""+res;
 	  				}else{
 	  					if( (val.charCodeAt(val.length-i)==44) ){
 	  						res = "."+res;
 	  					}
 	  				}				
 	  		}
 	  		return res;
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
			 document.getElementById("fechada").value=1;
			 if(pModu=='IncluiLista'){
				if(document.getElementById("entrada").value*1==0||document.getElementById("data_lista").value*1==0){
					alert("Necessário preencher os campos Tipo Entrada e Data da Lista");
					return false;
				}
				document.getElementById("fechada").value=0;
			}
			document.getElementById("valor_unico").value=trocaVirgula(document.getElementById("valor_unico").value);							
    		buscaDados(pModu);
		 }
		 function confirmaExclusaoFechamento(pModu){

			 var acao = pModu.substr(0,10)=="FechaLista"?2:1;
			 
			 var mens = "Confirma "+(acao==2?"o FECHAMENTO":"a EXCLUSÃO")+" da lista "+pModu.substr(17-acao,(pModu.length - (26-acao)))+"/"+pModu.substr((pModu.length - 4),4)+"?";

			 if(confirm(mens)){
			    buscaDados(pModu);
			 }
		 }

		 //AQUI
		 function incluirNovaLista(){

			 var qtdePagamentos = 0;
					 
			 if(document.getElementById("continua").value == "S"){
				 if(confirm("Define quantidade de pagamentos? ")){
					 qtdePagamentos = prompt("Quantidade de pagamentos ",qtdePagamentos)*1; 
				 }
			 };
			 enviar('IncluiLista&qtde='+qtdePagamentos);
			 
			 //Fazer o tratamento do total de parcelas!
			 
		 }

		 function limpar(){
			mostraFechadas(0);
		    document.formNome.valor_unico.value='R$ 0,00';
		    document.form.CampoStatus.value='';
			buscaDados('IncluiLista&fechada=0');
		    document.formNome.entrada.focus();
		 }
		 function mostraFechadas(pStatus) {
			 document.getElementById("entrada").value=0;
			 buscaDados('IncluiLista&fechada='+pStatus);
		 }
			 				
	  </script>
	