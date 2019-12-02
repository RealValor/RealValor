
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
		<script> 
  	    window.onload = function(){document.formNome.ano.focus();}

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

		function XMLHttpRequestChange() { //Controla as mudanças do objeto XMLHttpRequest.    		    
		    if (xmlhttp.readyState == 4 && xmlhttp.status == 200){ //Verifica se o arquivo foi carregado com sucesso.
			   var result = xmlhttp.responseXML; //Armazena a resposta XML.		                
		       var res_valor = result.getElementsByTagName("valor");
		       
		       if (res_valor[0].childNodes[0].data<0){
			       alert("O valor incluido somado às demais parcelas não pode ser maior que o total da nota");
		       }
	        }
	    }
  	  	
  	  	function enviaDadosHttp(pValor,pAno,pMes,pNumero){
  	  	     var url = "stu?p=AtualizaValorDivida&num="+pNumero+"&ano="+pAno+"&mes="+pMes+"&valor="+pValor; 
  	  		 if (document.getElementById) { //Verifica se o Browser suporta DHTML.        
  	  		    xmlhttp = AbrirAjax();        
  	  		    if (xmlhttp) {
  	  		       xmlhttp.onreadystatechange = XMLHttpRequestChange; 
  	  			   xmlhttp.open("GET", url, true); //Abre a url.
  	  			   xmlhttp.setRequestHeader('Content-Type','text/xml');             
  	  			   xmlhttp.setRequestHeader('encoding','ISO-8859-1');
  	  	 		   xmlhttp.send(null);
  	  		    }    
  	  	     }
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
   	  	  
 		function formataValor(campo,pAno,pMes,pNumero){
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
 	  		if(result*1>0){
		  		enviaDadosHttp(trocaVirgula(result),pAno,pMes,pNumero);
 	  		}
 	  		campo.value="R$ "+result;
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
			if(pModu=='QuitaDivida'){
				if(document.getElementById("ano").value*1==0&&document.getElementById("mes").value*1==0&&document.getElementById("valor").value*1==0){
					alert("Necessário preencher campo");
					document.formNome.ano.focus();
					return false;
				}
			}else{
				 if(!confirm("Confirma Exclusão da dívida?")){
					return false;
				 }
			}
			document.getElementById("valor").value=trocaVirgula(document.getElementById("valor").value);
    		buscaDados(pModu);
		 }
		 function checaMov(mes,ano){
			 
			 var data = document.formNome.pagamento.value;
			 var anodata = data.substr((data.length-4),4);
			 var mesdata = data.substr((data.length-7),2);

			 if(anodata*1<ano){
				 alert('Data de quitação não pode ser anterior ao mes em aberto: '+mes+'/'+ano);
				 document.formNome.pagamento.focus();
				 return false;	
			 }else{
				 if(mesdata*1<mes){
					 alert('Data de quitação não pode ser anterior ao mes em aberto: '+mes+'/'+ano);
				 	 document.formNome.pagamento.focus();
				 	 return false;	
				 }
			 }
		 }
		 
		 function quitar(){
			 var tot=0;
			 var posicoes='';
			 tamlist=document.getElementById("tamlista").value;
			 for(i=0;i<tamlist;i++){
				 var campo=i;
				 if(document.getElementById(campo).checked == true){
					posicoes=posicoes+campo+';';
				    tot++;
				 }
			 }
			 if(tot<1){
				 alert('Necessário selecionar divida para quitação!');
				 return false;
			 }else{
			 	if(!confirm('Data da quitação: '+document.getElementById("pagamento").value+'. Confirma?')){
				  	return false;
			 	} 	
			 }
			 document.getElementById("valor").value=trocaVirgula(document.getElementById("valor").value);
			 buscaDados('QuitaDivida&pos='+posicoes);
		 }				

		 function limpar(){
		    document.getElementById("ano").value='';
		    document.getElementById("mes").value='';
		    document.getElementById("valor").value='';
		    buscaDados('QuitaDivida');
		    document.formNome.ano.focus();	
		 }				
	 </script>
	  
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objSai" class="beans.Saida" scope="request" />
	  <jsp:useBean id="objMov" class="beans.MovAtual" scope="request" />
	  <jsp:useBean id="data" class="java.util.Date"/>
	  <div class="hero-unit" align="center">
		  <div id="estrutura">   
	      <table width="760" frame="void">
			 <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Pagamento de Dividas</div></td></tr>     
			 <tr><td colspan="6">&nbsp;</td></tr>	  
		  </table>
	        
	        <form id="formNome" name="formNome" method="post" action="stu?p=ConsultaMensalidade">
	        <table width="760" frame="void">
	        	<tr>
		           <td><div align="left" class="style6">
		           Ano:<img src="imagens/spacer_1x1px.png" width="3" border="0" /><input class="input-mini" name="ano" type="text" id="ano" size="2" maxlength="4" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${objDiv.ano>0?objDiv.ano:''}" type="number" pattern="####"/>' onkeypress="return soNumero(event);" onkeyup="JumpField(this,'mes');"/>
	               Mes:<img src="imagens/spacer_1x1px.png" width="3" border="0" /><input class="input-mini" name="mes" type="text" id="mes" size="1" maxlength="2" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${objDiv.mes>0?objDiv.mes:''}" type="number" pattern="##"/>' onkeypress="return soNumero(event);" onkeyup="JumpField(this,'valor');"/>
		           Valor:<img src="imagens/spacer_1x1px.png" width="3" border="0" /><input class="input-small" name="valor" type="text" id="valor" size="6" maxlength="9" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatNumber value="0" minFractionDigits="2" type="currency"/>' onkeypress="return soNumero(event)"; onblur="formataValor(this)" />
		           <img src="imagens/spacer_1x1px.png" width="5" border="0" /><input type="button" onclick="enviar('QuitaDivida')" name="bt5" value="Procurar"></div></td>
	        	</tr>
	        </table>
				<table width="760" frame="void" >
				   <tr><td colspan="17">&nbsp;</td></tr>	  
	            </table>
	            <table width="760" frame="void" >
				   <tr><td colspan="6">&nbsp;</td></tr>	  
	            </table>
	            <table width="760" border="1" >
				   <c:if test="${not empty listDiv}">
	             	<div align="center" style="font-size: 16px; color: #000000;"><strong>Relação de dívidas que atendem ao filtro da pesquisa</strong></div>
	             	            	    
	           	    <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
			      	   <td>&nbsp;</td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Documento</strong></div></td> 
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Numero</strong></div></td> 
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Data</strong></div></td> 
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Ano</strong></div></td> 
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Mes</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Vencimento</strong></div></td> 
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Valor</strong></div></td> 
			      	   <td>&nbsp;</td>
	                </tr>
			       </c:if>
			       <c:set var="cont" value="0"/>
			       <c:set var="vlrtotalgeral" value="0"/>
			       <c:forEach var="linhasdivida" items="${listDiv}">
			       
	           	        <tr bordercolor="#CCCCCC" bgcolor="#ffffff">
	           	        <td width="25"><div align="center"><input type="checkbox" name="${cont}" id="${cont}" value="${cont}" title="Seleciona divida para quitação"></div></td>
	
	           	        <td><div align="center" style="font-size: 12px; color: #333333;">${linhasdivida.saida.tipoDocumento.descricao}</div></td>
	               		<td><div align="center" style="font-size: 12px; color: #333333;">${linhasdivida.numeroDocumento!=""?linhasdivida.numeroDocumento:" - "}</div></td>
	               		<td><div align="center" style="font-size: 12px; color: #333333;"><f:formatDate value="${linhasdivida.dataDocumento.time}" pattern="dd/MM/yyyy"/></div></td>
	           	        
	               		<td><div align="center" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasdivida.ano}" pattern="0000"/></div></td>
	               		<td><div align="center" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasdivida.mes}" pattern="00" /></div></td>
	               		<td><div align="center" style="font-size: 12px; color: #333333;"><f:formatDate value="${linhasdivida.vencimento.time}" pattern="dd/MM/yyyy"/></div></td>
	               		
	               		<td><div align="right"><input name="valor" type="text" id="valor" class="input-small" size="10" maxlength="8" style="text-align: right; font-size: 12px; color: #333333;" value='<f:formatNumber value="${linhasdivida.valor}" minFractionDigits="2" type="currency"/>' onkeypress="return soNumero(event)"; onblur="formataValor(this,${linhasdivida.ano},${linhasdivida.mes},${linhasdivida.saida.numero})"/></div></td>
	               		<c:if test="${objUsu.nivel > 2 }">
		               		<td width="25"><div align="center"><a title="Excluir dívida" href='javascript: enviar("ExcluiDivida&ano=${linhasdivida.ano}&mes=${linhasdivida.mes}&saida=${linhasdivida.saida.numero}")' ><img src="imagens/excluir.png" width="16" height="16" border="0" align="middle" /></a></div></td>
						</c:if>
						<c:if test="${objUsu.nivel < 3 }">
		               		<td width="25">&nbsp;</td>
						</c:if>
	           	        <c:set var="cont" value="${cont + 1}"/>
	               		
						<c:set var="vlrtotalgeral" value="${vlrtotalgeral  + linhasdivida.valor}"/>
	                  </tr>
	       		   </c:forEach>
	       		   <tr><td><input name="tamlista" id="tamlista" type="hidden" value="${cont}" readonly /></td></tr>                 
	       		   <c:set var="socio" value="0"/> 
	       		   <c:if test="${vlrtotalgeral != 0}">
	                  <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC"><td bordercolor="#ffffff">&nbsp;</td>
		                  <td bordercolor="#ffffff" colspan="6"></td>                  
		                  <td bordercolor="#ffffff"><div align="right" style="font-size: 12px; color: #000000;"><strong>Total pesquisado:</strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /><strong><f:formatNumber value="${vlrtotalgeral}" minFractionDigits="2" maxFractionDigits="2" type="currency"/></strong></div></td>
				      	  <td>&nbsp;</td>
	                  </tr>
	               </c:if>
	            </table>
	            <c:if test="${not empty listDiv}">
	               <div align="left" style="font-size: 14px; color: #333333;"><strong>Data da Quitação:</strong><img src="imagens/spacer_1x1px.png" width="5" border="0" />
	               <input align="left" name="pagamento" type="text" id="pagamento" class="input-small" size="10" maxlength="10" style="font-size: 14px; color: #333333;" value='<f:formatDate value="${data}" pattern="dd/MM/yyyy"/>' onblur="checaMov(${objMov.mes},${objMov.ano});" onkeypress="return mascara(event,this,'##/##/####');" /></div>
	            </c:if>
	        </form>
			<form name="form" id="form" method="post">
			  <table width="760">
	  				<tr>
	                	<td colspan="3">
	                    	<div align="left">
	                    		<c:if test="${objUsu.nivel > 2 }">
						        	<input type="button" onclick="quitar()" name="bt2" value="quitar" />     
								</c:if>
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar" /> 
							</div>   
	                    </td>   
					</tr>  
			  </table>
	   		</form>
		</div>  		
	  </div>
	<c:import url="rodape.jsp"/>
