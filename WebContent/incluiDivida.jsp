
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
		.style4 {color: #006600; font-weight: bold; }
		.style9 {color: #000000}
		.style10 {color: #FFCC00}
		.style13 {
			font-size: 12px;
			font-weight: bold;
		}
		.style14 {font-size: 10px} 
		
		#cor_fonte { color: #666666; }
	 
	</style>
	
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objSaida" class="beans.Saida" scope="request" />
	  <div class="hero-unit" align="center">
		  <div id="estrutura">
	      <table width="760" frame="void">
			 <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Inclusão de Dividas<img src="imagens/spacer_1x1px.png" width="250" border="0" /><img src="imagens/spacer_1x1px.png" width="300" border="0" /></div></td></tr>
			 <!-- 
			 	<a href="javascript:mostraVideo();" id="cor_fonte" title="Tutorial Recebimentos">Vídeo</a>     
			  -->
			 <tr><td colspan="17">&nbsp;</td></tr>	  
		  </table>
	        
	        <form id="formNome" name="formNome" method="post" action="stu?p=ConsultaMensalidade">
	        
	        	<table width="760" frame="void">
	            <tr>
	         	    <td><div align="left" class="style6">Referente:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	         	    <!-- class="input-xxlarge" -->
					<select class="input-xxlarge" name="listasaida" id="listasaida" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("saida").value=listasaida.value; document.getElementById("valor").value=listasaida.value; pegaValor()' >
					   <c:if test="${objSaida.numero != 0}">
			              <option value="${objSaida.numero}">${objSaida.tipoDocumento.descricao} - N°. ${objSaida.documento!=""?objSaida.documento:" - "} - Data: <f:formatDate value="${objSaida.dataDocumento}" type="date" pattern="dd/MM/yyyy"/> - Valor: <f:formatNumber value="${objSaida.valor}" type="currency" minFractionDigits="2" /></option>
					   </c:if>
					   <c:if test="${empty listSai}">
							<option value="0"> Inexiste lançamento de notas para o mes em aberto: ${objSaida.mes}/${objSaida.ano}</option>				   
					   </c:if>
	                   <c:forEach var="saida" items="${listSai}">
	                      <c:if test="${objSaida.numero != saida.numero}">
	                         <option value="${saida.numero}">${saida.tipoDocumento.descricao} - N°. ${saida.documento!=""?saida.documento:" - "} - Data: <f:formatDate value="${saida.dataDocumento}" type="date" pattern="dd/MM/yyyy"/> - Valor: <f:formatNumber value="${saida.valor}" type="currency" minFractionDigits="2" /></option>                         
	                      </c:if>
	        		   </c:forEach>
	                </select>
	                <input name="saida" id="saida" type="hidden" value="${objSaida.numero}" readonly />
	                </div></td>
	             </tr>
				 <!-- melhor usar ajax -->
	
				   <c:if test="${not empty listaParcelamento}">			   	  
				   	  <div align="center" style="font-size: 16px; color: #000000;"><strong>Despesa(s) Correspondente(s) à Dívida</strong></div> 
	           	      <c:forEach var="parcelamento" items="${listaParcelamento}">
		           	      <tr bordercolor="#000000">
		           	      	<td>${parcelamento.tipoDocumento.descricao} - N°. ${parcelamento.documento!=""?parcelamento.documento:" - "} - Data: <f:formatDate value="${parcelamento.dataDocumento}" type="date" pattern="dd/MM/yyyy"/> - Valor: <f:formatNumber value="${parcelamento.valor}" type="currency" minFractionDigits="2" /></td>
		                  </tr>
	       		   	  </c:forEach>
			       </c:if>             
				 <!-- aqui -->
			       <c:set var="vlrtotalgeral" value="0"/>
	       		   <c:set var="socio" value="0"/> 
	       		   <c:if test="${vlrtotalgeral != 0}">
	                  <tr bordercolor="#FFFFFF"><td colspan="16"><div align="right" style="font-size: 12px; color: #000000;"><strong>Total do Recibo:</strong></div></td>
	                  <td><div align="right" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${vlrtotalgeral}" minFractionDigits="2" /></strong></div></td></tr>
	                  <c:forEach var="linhassocio" items="${listaSocio}">
	                     <tr bordercolor="#ffffff">
	                        <td ><div align="center" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhassocio.codigo}" pattern="0000"/></div></td>
	                        <td colspan="12"><div align="left" style="font-size: 12px; color: #333333;">${linhassocio.nome}</div></td> 
	                     </tr>
	                     <c:set var="socio" value="${linhassocio.codigo}"/> 
	                  </c:forEach>
	               </c:if>
	               <tr bordercolor="#ffffff">
			          <td bordercolor="#ffffff">            
				  		<input name="totrecibo" id="totrecibo" type="hidden" value="${vlrtotalgeral}" readonly="readonly" />
		              </td>
	               	</tr>
	            </table>
	            <table> 
	             <tr>   
	                <td><div align="left" class="style6">Total parcelado:<img src="imagens/spacer_1x1px.png" width="3" border="0" />
			           <select class="input-mini" name="valor" id="valor" style="display: none; font-size: 16px; color: #006633; font-weight: bold;">
			           
			              <c:if test="${objSaidaAux.numero != 0}" >
			              	<option value="${objSaidaAux.numero}">${objSaidaAux.valorStr}</option>
			              </c:if>
			           	
					      <c:forEach var="valores" items="${listSai}">
					      	<c:if test="${objSaidaAux.numero != valores.numero}">
					      		<option value="${valores.numero}" >${valores.valorStr} </option>
					      	</c:if>
	        			  </c:forEach> 
	                   </select>
	            	
	                <input class="input-small" name="valorTotal" id="valorTotal" type="text" size="9" maxlength="9" title="Valor total" style="font-size: 16px; color: #006633; font-weight: bold" value='<f:formatNumber value="${objSaidaAux.valor}" minFractionDigits="2" type="currency"/>' onkeypress="return soNumero(event)" onblur="formataValor(this); verificaValor(this)"/>
	                
	            	<img src="imagens/spacer_1x1px.png" width="5" border="0" />em:<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                <input class="input-mini" name="vezes" id="vezes" type="text" size="1" maxlength="2" title="Total de parcelas" style="font-size: 16px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${vezes}" type="number" pattern="###"/>' onkeypress="return soNumero(event);" />
				    <img src="imagens/spacer_1x1px.png" width="2" border="0" />Vez(es)<img src="imagens/spacer_1x1px.png" width="15" border="0" />
	            	Início: <img src="imagens/spacer_1x1px.png" width="1" border="0" />
					<input class="input-small" name="data_inicio" id="data_inicio" type="text" size="8" maxlength="10" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatDate value="${objDataInicio.vencimento.time}" type="date" pattern="dd/MM/yyyy" />' onkeypress="return mascara(event,this,'##/##/####');" onblur="checaMov(${objMov.mes},${objMov.ano})"/>
	            	</div></td>
	            </tr>
	            </table>
	            <table width="500" frame="void" >
				   <tr><td colspan="3">&nbsp;</td>
				   </tr>	  
	            </table>
	            <table width="760" border="1">
				   <c:if test="${not empty listDiv}">
				   	  <div align="center" style="font-size: 16px; color: #000000;"><strong>Relação de Parcelas</strong></div> 
					  <tr bordercolor="#cccccc" bgcolor="#cccccc">
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Documento</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Numero</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Data do Documento</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Parcela</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Vencimento</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Valor</strong></div></td> 
	                  </tr>          
			       </c:if>
			       <c:set var="parcela" value="0"/>
			       <c:set var="vlrtotalgeral" value="0"/>
			       <c:forEach var="linhasdivida" items="${listDiv}">
	           	        <tr bgcolor="#ffffff">
			            <c:set var="parcela" value="${parcela + 1}"/>
	               		<td><div align="center" style="font-size: 12px; color: #000000;">${linhasdivida.saida.tipoDocumento.descricao}</div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;">${linhasdivida.numeroDocumento!=""?linhasdivida.numeroDocumento:" - "}</div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;"><f:formatDate value="${linhasdivida.dataDocumento.time}" pattern="dd/MM/yyyy"/></div></td>
			            <td><div align="center" style="font-size: 12px; color: #000000;"> ${parcela}ª</div></td> 
	               		<td><div align="center" style="font-size: 12px; color: #000000;"><f:formatDate value="${linhasdivida.vencimento.time}" pattern="dd/MM/yyyy"/></div></td>
	               		<td><div align="right"  style="font-size: 12px; color: #000000;"><f:formatNumber value="${linhasdivida.valor}" minFractionDigits="2" maxFractionDigits="2"/></div></td>
						<c:set var="vlrtotalgeral" value="${vlrtotalgeral  + (linhasdivida.valor)}"/>
	                  </tr>
	       		   </c:forEach>
	       		   <c:set var="socio" value="0"/> 
	       		   <c:if test="${vlrtotalgeral != 0}">
	       		   
	       		   <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC"><td bordercolor="#ffffff">&nbsp;</td>
		              <td bordercolor="#ffffff" colspan="4"><div align="right" style="font-size: 12px; color: #000000;"><strong>Valor Total:</strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
	                  <td><div align="right" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${vlrtotalgeral}" minFractionDigits="2" /></strong></div></td>
		              <td bordercolor="#ffffff" colspan="4"><div align="right" style="font-size: 12px; color: #000000;"></div></td>                  
	               </tr>	       		   
	               </c:if>
	            </table>
	        </form>
			<form name="form" id="form" method="post">
			  <table width="760" frame="void">
			  	<tr>
			  		<td><input name="tipo_entr" id="tipo_entr" type="hidden" value="1" /></td>
			  	</tr>                        
	            <tr>
	            	<td colspan="3">&nbsp;</td>
	            </tr>
	          </table>
			  <table width="760">
			  		<tr>
	                	<td colspan="3">
	                    	<div align="left">
	                    		<c:if test="${objUsu.nivel > 2 }">
						        	<input type="button" onclick="enviar('GravaDivida')" name="bt1" value="Confirmar Lançamento de Dívida"/>
								</c:if>
								<!-- 
					        	<input type="button" onclick="enviar('IncluiDivida')" name="bt5" value="Calcular parcelas"/>
								 -->
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar"/> 
							</div>   
	                    </td>   
					</tr>
			  </table>
	        </form>
		</div>    
	  </div>
	<c:import url="rodape.jsp"/>

	<script>
  	    window.onload = function(){document.formNome.vezes.focus();}
  	      
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
			if(pModu=='IncluiDivida'){

				var dataInicio = document.getElementById("data_inicio").value;
				 
				if(!(document.getElementById("vezes").value*1>0&&dataInicio.length>0)){
					alert("Necessário o preenchimento dos campos");
					return false;
				}else{
					document.getElementById("valorTotal").value=trocaVirgula(document.getElementById("valorTotal").value);
				};							
			}
    		buscaDados(pModu);
		 }
		 function pegaValor(){
			 document.getElementById("valorTotal").value=document.formNome.valor.options[document.formNome.listasaida.selectedIndex].text;
			 //document.getElementById("valorTotal").value = limpaValor(document.getElementById("valorTotal").value*1)+ limpaValor(document.formNome.valor.options[document.formNome.listasaida.selectedIndex].text *1);
			 //tratar ajax aqui para alimentar lista com notas escolhidas.
		 }
		 
		 function verificaValor(){
			 if( trocaVirgula(document.getElementById("valorTotal").value)*1 > trocaVirgula(document.formNome.valor.options[document.formNome.listasaida.selectedIndex].text)*1 ){
				alert("O total informado "+document.getElementById("valorTotal").value+" não pode ser superior ao valor do documento "+trocaVirgula(document.formNome.valor.options[document.formNome.listasaida.selectedIndex].text)*1);
				document.formNome.valorTotal.focus();
			 }else{
				 if(trocaVirgula(document.getElementById("valorTotal").value)<=0){
					 alert("Valor inválido");
					 document.formNome.valorTotal.focus();
				 }
			 }
		 }
		 
		 function verificaData(pData){
			 
			 var validaData = /^(((0[1-9]|[12][0-9]|3[01])([-.\/])(0[13578]|10|12)([-.\/])(\d{4}))|(([0][1-9]|[12][0-9]|30)([-.\/])(0[469]|11)([-.\/])(\d{4}))|((0[1-9]|1[0-9]|2[0-8])([-.\/])(02)([-.\/])(\d{4}))|((29)(\.|-|\/)(02)([-.\/])([02468][048]00))|((29)([-.\/])(02)([-.\/])([13579][26]00))|((29)([-.\/])(02)([-.\/])([0-9][0-9][0][48]))|((29)([-.\/])(02)([-.\/])([0-9][0-9][2468][048]))|((29)([-.\/])(02)([-.\/])([0-9][0-9][13579][26])))$/;  

			 var data = pData.value;
			 
			 if(!validaData.test(data)&&data.length>0){  
			     alert("Data inválida");
				 pData.value="";
				 pData.focus();
			     return false;
			 }else{
				 return true;
			 };
		 }

		 
		 function checaMov(mes,ano){

			 verificaData(document.getElementById("data_inicio"));
			 var dataInicio = document.getElementById("data_inicio").value;
			 
			 if(dataInicio.length > 0){
				 dataInicio = dataInicio.replace(/\D/g,"");             
	
				 var anoData = dataInicio.substring(4,8);
				 var mesData = dataInicio.substring(2,4);
				 
				 //alert(anoData+" anos "+ano+" | "+mesData+" meses "+mes);
				 
				 if(anoData*1<ano){
					 alert('Data inicial não pode ser anterior ao mes em aberto: '+mes+'/'+ano);
					 document.getElementById("data_inicio").value="";
					 document.getElementById("data_inicio").focus();
					 return false;
				 }else{
					 if(mesData*1<mes&&anoData*1==ano){
						 alert('Data inicial não pode ser anterior ao mes em aberto: '+mes+'/'+ano);
						 document.getElementById("data_inicio").value="";
						 document.getElementById("data_inicio").focus();
					 	 return false;	
					 };
				 };
			 };
			 enviar('IncluiDivida');
		 }
		 
	     function mostraVideo(){
	    	 
				var width = 1310;
				var height = 716;
				
				var left = 99;
				var top = 99;
				var now=new Date();
				var segundos = now.getSeconds();
				
				//alert(urlSet.value); //urlSet
				
				window.open(document.getElementById("urlSet").value+'videoTutorial.jsp?cod='+segundos,'janela, top='+top+', left='+left+'resizable=no, fullscreen=no');
				//'width='+width+', height='+height+ +', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=yes, fullscreen=no'
		 }

		 function limpar(){
			 document.getElementById("data_inicio").value="";
			 document.getElementById("saida").value="";
			 document.getElementById("vezes").value="";
			 buscaDados('IncluiDivida');
		}
	  </script>
	