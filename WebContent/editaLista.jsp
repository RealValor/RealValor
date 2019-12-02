
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
	  <jsp:useBean id="objLista" class="beans.Lista" scope="request" />
	   
	<div class="hero-unit" align="center">
	  <div id="estrutura">
      <table width="760" frame="void">
		 <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Manutenção de Lista de Arrecadação</div></td></tr>     
		 <tr><td colspan="3">&nbsp;</td></tr>	  
	  </table>
        <form id="formNome" name="formNome" method="post" action="stu?p=ConsultaMensalidade">
            <table width="760" border="1">
				            
            	<tr bgcolor="#ffffff"><td colspan="2"><input type="button" onclick="enviar('IncluiLista')" name="bt1" value="Voltar"></input></td>
            	<td><div align="right"><a href="javascript:window.print()" ><img src="imagens/imprimir.PNG" alt="Imprimir" border="0"/></a></div></td></tr>
            	
			   <c:if test="${not empty objDetalheLista}">
           	      <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
		      	     <td width="92"> <div align="center" style="font-size: 12px; color: #000000;"><strong>Numero</strong></div></td>
		      	     <td width="570"><div align="center" style="font-size: 12px; color: #000000;"><strong>Objetivo da Arrecadação</strong></div></td>
		      	     <td > <div align="center" style="font-size: 12px; color: #000000;"><strong>Data da Lista</strong></div></td>
                  </tr>
                            
				  <tr  bordercolor="#000000">
	               	 <td width="92"> <div align="center" style="font-size: 14px; color: #333333;"><f:formatNumber value="${objDetalheLista.numero}" pattern="0000"/>/${objDetalheLista.ano}</div></td>
	               	 <td width="570"><div align="center" style="font-size: 14px; color: #333333;">${objDetalheLista.entrada.descricao}</div></td>
	               	 <td > <div align="center" style="font-size: 14px; color: #333333;"><f:formatDate value="${objDetalheLista.data.time}" pattern="dd/MM/yyyy"/></div></td>
                  </tr>
                  <input name="anolista" id="anolista" type="hidden" value="${objDetalheLista.ano}" />
			   </c:if>
		    </table>
		    
		    <table width="760" border="1">

			   <tr  bordercolor="#000000">
                	 <td width="400">&nbsp;</td>
                	 <td><div align="center" style="font-size: 14px; color: #333333;"><strong>Valor</strong></div></td>
                	 <td><div align="center" style="font-size: 14px; color: #333333;"><strong>Pago</strong></div></td>
                	 <td><div align="center" style="font-size: 14px; color: #333333;"><strong>Pendente</strong></div></td>
                	 <td>&nbsp;</td>
                	 <td > <div align="center" style="font-size: 14px; color: #333333;"></div></td>
               </tr>
		    
		       <tr>
			       <td colspan="5"><input name="fecha" id="fecha" type="hidden" value="${fechamentoLista}" />
			       <input name="urlSet" id="urlSet" type="hidden" value="${urlSet}"/></td>
		       </tr> 

		       <c:if test="${not empty detalheLista}">
	               <c:set var="grau" value="N"/>
	               <c:set var="totalrecebido" value="0"/>
	               <c:set var="listafechada" value="0"/>
	               
	               <c:set var="totalpagantes" value="0"/>
	               <c:set var="listarecebido" value="0"/>
	               
			       <c:forEach var="linhaslista" items="${detalheLista}">
			       	  <c:set var="cont" value="${cont + 1}"/>
					  <c:if test="${grau != linhaslista.socioDevedor.grau}">
	                    <tr bordercolor="#ffffff" ><td><div align="left" style="font-size: 16px; color: #333333;">${linhaslista.socioDevedor.grau=='M'?'QUADRO DE MESTRES':(linhaslista.socioDevedor.grau=='C'?'CORPO DO CONSELHO':(linhaslista.socioDevedor.grau=='I'?'CORPO INSTRUTIVO':'QUADRO DE SÓCIOS'))}</div></td>
	                    <td colspan="4">&nbsp;</td>
	                    </tr>
			            <c:set var="grau" value="${linhaslista.socioDevedor.grau}"/>               
	        		  </c:if>       		
	        		  
	           	      <tr bgcolor="#ffffff">
	              		<td ><div align="left" style="font-size: 14px; color: #333333; font-weight: bold;"><img src="imagens/spacer_1x1px.png" width="5" border="0" />${linhaslista.socioDevedor.grau=='M'?'M.':(linhaslista.socioDevedor.grau=='C'?'Cons.':'Ir.')} ${linhaslista.socioDevedor.nome}</div></td>


		     	    	<c:if test="${linhaslista.valorPendente > 0}">
							<c:set var="totalparcelas" value="${linhaslista.qtdePagamentos}"/>

		    	    	    <c:if test="${fechamentoLista == 0}">
		                		<td><input class="input-small" name="valor" type="text" id="valor" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${linhaslista.valor}" minFractionDigits="2" type="currency"/>' onkeypress="return soNumero(event)" onblur="formataValor(this,${linhaslista.socioDevedor.codigo},${linhaslista.valorPago})"/></td>
		     	    	    </c:if>
		     	    	    <c:if test="${fechamentoLista == 1}">
		                		<td><input class="input-small" name="valor" type="text" id="valor" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${linhaslista.valor}" minFractionDigits="2" type="currency"/>' readonly="readonly"/></td>
		     	    	    </c:if>
	                		<td><input class="input-small" name="valorpago" type="text" id="valorpago" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #848484; font-weight: bold;" value='<f:formatNumber value="${linhaslista.valorPago}" minFractionDigits="2" type="currency"/>' readonly="readonly"/></td>
		                	<td><input class="input-small" name="valorpendente" type="text" id="valorpendente" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${linhaslista.valorPendente}" minFractionDigits="2" type="currency"/>' readonly="readonly"/></td>
									     	    	    
		     	    		<c:if test="${linhaslista.valor != 0}">
	                			<td bgcolor="#cc6633" bordercolor="#000000"><div align="center"><a href='javascript: recebeValorLista(${linhaslista.socioDevedor.codigo},${objDetalheLista.entrada.codigo},${objDetalheLista.numero},${linhaslista.valor - linhaslista.valorPago},${totalparcelas})' title="Receber pagamento" class="style5 style9">em aberto</a></div></td>
	                			<c:set var="totalpagantes" value="${totalpagantes + 1}"/>
		     	    		</c:if>
	     	       		</c:if>

	                	<c:if test="${linhaslista.valorPendente == 0 }">
							
	                	 	<c:if test="${linhaslista.valor == 0}">

							   <td><input class="input-small" name="valor" type="text" id="valor" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${linhaslista.valor}" minFractionDigits="2" type="currency"/>' onkeypress="return soNumero(event)" onblur="formataValor(this,${linhaslista.socioDevedor.codigo})"/></td>

		                	   <td><input class="input-small" name="valorpago" type="text" id="valorpago" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #848484; font-weight: bold;" value='<f:formatNumber value="${linhaslista.valorPago}" minFractionDigits="2" type="currency"/>' readonly="readonly"/></td>
		                	   
		                	   <td><input class="input-small" name="valorpendente" type="text" id="valorpendente" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${linhaslista.valorPendente}" minFractionDigits="2" type="currency"/>' readonly="readonly"/></td>
                			   <td bgcolor="#FFFFFF" bordercolor="#000000" ><div align="center" class="style5 style9">-</div></td>
	                		</c:if>
	                	 
	                	 	<c:if test="${linhaslista.valor != 0}">
							   
							   <td><input class="input-small" name="valor" type="text" id="valor" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #848484; font-weight: bold;" value='<f:formatNumber value="${linhaslista.valor}" minFractionDigits="2" type="currency"/>' readonly="readonly"/></td>
		                	   <td><input class="input-small" name="valorpago" type="text" id="valorpago" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #848484; font-weight: bold;" value='<f:formatNumber value="${linhaslista.valorPago}" minFractionDigits="2" type="currency"/>' readonly="readonly"/></td>

		                	   <td><input class="input-small" name="valorpendente" type="text" id="valorpendente" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #848484; font-weight: bold;" value='<f:formatNumber value="${linhaslista.valorPendente}" minFractionDigits="2" type="currency"/>' readonly="readonly"/></td>
							   <td bgcolor="#669933" onclick='enviar("ConsultaReciboNumero&ctrl=lista&recibo=${linhaslista.reciboGerado}")'><div align="center" ><a href="#" title="Consultar recibo" class="style5 style9">${linhaslista.reciboGerado}</a></div></td>
	                		</c:if>

						    <c:set var="totalrecebido" value="${totalrecebido + linhaslista.valor}"/>
						    <c:set var="listafechada" value="${listafechada + 1}"/>
						    <c:if test="${linhaslista.valor>0}">
						    	<c:set var="listarecebido" value="${listarecebido + 1}"/>
						    </c:if>
						   
	     	            </c:if>
	                  </tr>

	                  <input name="numero" type="hidden" id="numero" value="${linhaslista.numero}"/>
	                  <input name="ano" type="hidden" id="ano" value="${linhaslista.ano}"/>
	                  <input name="codsocio" type="hidden" id="codsocio" value="${linhaslista.socioDevedor.codigo}"/>
	                  
	       		   </c:forEach>
	       		   
               	   <tr bgcolor="#CCCCCC">
	                  <td colspan="3" ><div align="right" style="font-size: 14px; color: #000000;"><strong>Total de Pagantes:</strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
	                  <td><input class="input-small" name="valortotal" type="text" id="valortotal" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #000000; font-weight: bold;" value='<f:formatNumber value="${totalLista}" minFractionDigits="2" type="currency"/>' readonly="readonly"/></td>
	                  <td><input class="input-small" name="listapagantes" type="text" id="listapagantes" size="5" maxlength="3" style="text-align: center; font-size: 15px; color: #000000; font-weight: bold;" value='<f:formatNumber value="${totalpagantes+listarecebido}" />' readonly="readonly"/></td>
                   </tr>
                   <tr bgcolor="#CCCCCC">
                      <td colspan="3" ><div align="right" style="font-size: 14px; color: #000000;"><strong>Total Recebido:</strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
	                  <td><input class="input-small" name="valorrecebido" type="text" id="valorrecebido" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${totalrecebido}" minFractionDigits="2" type="currency"/>' readonly="readonly"/></td>
	                  <td><input class="input-small" name="listarecebidos" type="text" id="listarecebidos" size="5" maxlength="3" style="text-align: center; font-size: 15px; color: #000000; font-weight: bold;" value='<f:formatNumber value="${listarecebido}" />' readonly="readonly"/></td>
	               </tr>
                   <tr bgcolor="#CCCCCC">
                      <td colspan="3" ><div align="right" style="font-size: 14px; color: #000000;"><strong>Total a Receber:</strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
	                  <td><input class="input-small" name="valorareceber" type="text" id="valorareceber" size="8" maxlength="7" style="text-align: right; font-size: 15px; color: #CC3300; font-weight: bold;" value='<f:formatNumber value="${totalLista-totalrecebido}" minFractionDigits="2" type="currency"/>' readonly="readonly"/></td>
	                  <td><input class="input-small" name="listaareceber" type="text" id="listaareceber" size="5" maxlength="3" style="text-align: center; font-size: 15px; color: #000000; font-weight: bold;" value='<f:formatNumber value="${totalpagantes}" />' readonly="readonly"/></td>
	               </tr>
       		   </c:if>
            </table>
        </form>
	</div>   
	</div>
	<c:import url="rodape.jsp"/>

	<script>
  	    window.onload = function(){document.formNome.ano.focus();}
  	  	function AbrirAjax() {
	   	     var xmlhttp_new;
	   	     try {
	   	        xmlhttp_new = new ActiveXObject("Microsoft.XMLHTTP"); //Para o Internet Explorer	
	   	     }catch(e) {
	   	        try {  	   	      
	   	           xmlhttp_new = new getXMLHTTP(); //IE8	
	   	        }catch(ex) {
	   	           try {  	   	         
	   	              xmlhttp_new = new XMLHttpRequest(); //Para todos os outros Browsers (FireFox,Opera, etc ...)
	   	           }catch(exc) {
	   	              alert("Seu navegador não tem recursos para uso de Ajax");  	   	            
	   	              xmlhttp_new = null;
	   	           };
	   	        };
	   	     }
	   	     return xmlhttp_new;
	      }

  	  function formataRetorno(campo){
	  		var result="",ponto=0,i;

	  		for(i=1;i<=(campo.length);i++){
	  		    result = (campo.substr((campo.length)-i,1)=="."?",":campo.substr((campo.length)-i,1))+""+result;
	  		    if(result=="0"){
	  		    	result=result+"0";
	  		    }
	  			if (i>1){
	  				if (i!=2){
	  					ponto=ponto+1;
	  					if(ponto==3&&i<campo.length){
	  						result="."+result;
	  						ponto=0;
	  					};
	  				};
	  			};
	  		}
	  		return "R$ "+result;
	    }
  	  	//melhorar o codigo acima
  	  	
		  function XMLHttpRequestChange() { //Controla as mudanças do objeto XMLHttpRequest.    		    
			    if (xmlhttp.readyState == 4 && xmlhttp.status == 200){ //Verifica se o arquivo foi carregado com sucesso.
				   var result = xmlhttp.responseXML; //Armazena a resposta XML.		                
			       var res_valor = result.getElementsByTagName("valor");
			       document.getElementById("valortotal").value=formataRetorno(res_valor[0].childNodes[0].data);		               
		        }
		     }
	  	  	
	  	  	  function enviaDadosHttp(pValor,pSocio){
	  	  	     var url = "stu?p=AtualizaValorLista&num="+document.getElementById("numero").value+"&ano="+document.getElementById("ano").value+"&socio="+pSocio+"&valor="+pValor; 
	  	  		 if (document.getElementById) { //Verifica se o Browser suporta DHTML.        
	  	  		    xmlhttp = AbrirAjax();        
	  	  		    if (xmlhttp) {
	  	  		       xmlhttp.onreadystatechange = XMLHttpRequestChange; 
	  	  			   xmlhttp.open("GET", url, true); //Abre a url.
	  	  			   xmlhttp.setRequestHeader('Content-Type','text/xml');             
	  	  			   xmlhttp.setRequestHeader('encoding','ISO-8859-1');
	  	  	 		   xmlhttp.send(null);
	  	  		    };   
	  	  	     };
			  }

		 	  function limpaValor(campo){
		  	  		var val=campo.value+""; 
		  	  		var res="",i;
		  	  		for(i=1;i<=(val.length);i++){
		  	  				if( (val.charCodeAt(val.length-i)>47) && (val.charCodeAt(val.length-i)<58) ){
		  	  					res = val.substr((val.length-i),1)+""+res;
		  	  				};
		  	  		};
		  	  		return res;
		  	  }

		 	  function trocaVirgula(campo){
		  		  
		  	  		var val=campo+"";
		  	  		
		  	  		var res="",i;
		  	  		for(i=1;i<=(val.length);i++){
		  	  				if( (val.charCodeAt(val.length-i)>47) && (val.charCodeAt(val.length-i)<58) ){
		  	  					res = val.substr((val.length-i),1)+""+res;
		  	  				}else{
		  	  					if( (val.charCodeAt(val.length-i)==44) ){
		  	  						res = "."+res;
		  	  					};
		  	  				};			
		  	  		};
		  	  		return res;
		  	  }
		 	  
		  	  function formataValor(campo,pSocio,pValorpago){
		  		  
	 	  		var valor=limpaValor(campo);
	 	  		var vlrpago = pValorpago;
	 	  		var result="",ponto=0,i;

	 	  		if(valor<(vlrpago*100)){
	 	  			alert("O valor alterado não pode ser menor que o total pago.");
	 	  		}else{
		 	  		document.getElementById("valorareceber").value=valor*1+limpaValor(document.getElementById("valorareceber"))*1;
		 	  		
		 	  		for(i=1;i<=(valor.length);i++){
		 	  			result = valor.substr((valor.length)-i,1)+""+result;
		 	  			if (i>1){
		 	  				if (i==2){
		 	  					result=","+result;
		 	  				}else{			//Verficar este código
		 	  					ponto=ponto+1;
		 	  					if(ponto==3&&i<valor.length){
		 	  						result="."+result;
		 	  						ponto=0;
		 	  					};
		 	  				};
		 	  			};
		 	  		}
		 	  		campo.value=result;
		 	  		if(pSocio!=null){
		  	  		    enviaDadosHttp(trocaVirgula(result),pSocio);
			 	  		campo.value="R$ "+result;
		 	  		};
	 	  		};
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

  		 function imprimeRecibo(){
				var width = 840;
				var height = 620;
				
				var left = 99;
				var top = 99;
				var now=new Date();
				var segundos = now.getSeconds();

				window.open(document.getElementById('urlSet').value+'imprimeRecibo.jsp?cod='+segundos,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
		 }
		 
		 function enviar(pModu){

			if(limpaValor(document.getElementById("valortotal"))!=0&&limpaValor(document.getElementById("valorareceber"))==0&&document.getElementById("fecha").value==0){
				if(confirm('Valor(es) já recebido(s). Fecha a lista?')){
					pModu='FechaLista&num='+document.getElementById("numero").value+'&ano='+document.getElementById("ano").value;
				}
			}
    		buscaDados(pModu);

    		if(pModu.substr(0,34)=='ConsultaReciboNumero&ctrl=consulta'){

			   if(confirm('Visualiza o recibo '+pModu.substr(42,16)+'?')){
				  imprimeRecibo();
			   }else{
			      return false;							  
			   }
			}
		 }
		
  	     function formataValorLista(pValor){
	 	  		
	  	  	 var valorFormatado = pValor+"";

		  	 if(valorFormatado.indexOf(".") != -1){
		  		 valorFormatado = valorFormatado.replace(".",",");
		  		 valorFormatado = valorFormatado+"0";
			 }else{
		  		 valorFormatado = valorFormatado+",00"; 
			 }
 	  		 return valorFormatado;
	 	 }
		 
		 function recebeValorLista(pCodsocio,pTipo_entrada,pCod_lista,pValor,pParcelas){

			 var qtdPagtos = pParcelas;
			 
			 var anolista  = document.getElementById("anolista").value;
			 var valorPagamento = qtdPagtos>0?pValor/qtdPagtos:pValor;
			 
			 valorPagamento = prompt("Valor a pagar: ","R$ "+formataValorLista(valorPagamento));
			 
			 if(trocaVirgula(valorPagamento)*1 > pValor){
				 alert(" R$ "+valorPagamento+"\n O valor digitado não pode ser maior que o total a pagar! R$ "+formataValorLista(pValor));
			 }else{
				 
				 document.getElementById("valortotal").value = trocaVirgula(valorPagamento);
				 enviar("IncluiRecebimento&codsocio="+pCodsocio+"&tipo_entrada="+pTipo_entrada+"&cod_lista="+pCod_lista+"&vialista=1"+"&anolista="+anolista);
			 };
		 }

		 function limpar(){
			    document.formNome.data_lista.value='';
			    document.formNome.valor_unico.value='';
			    document.formNome.entrada.focus();
		}

	</script>
