
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>

	<link rel="stylesheet" href="css/principal.css" type="text/css" />
	  
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objPaga" class="beans.Login" scope="request" />
	  <jsp:useBean id="objSaida" class="beans.Saida" scope="request" />
	  <jsp:useBean id="objData" class="java.util.Date" scope="request" />
	<div class="hero-unit" align="center">
	  <div id="estrutura">
        <table width="764" frame="void">
		   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Lançamento de Despesas</div></td></tr>     
		   <tr><td colspan="17">&nbsp;</td></tr>	  
	    </table>
           
        <form id="formNome" name="formNome" method="post" action="stu?p=ConsultaMensalidade">
        	<table width="763" frame="void">
            <tr>
            	<td><div align="left" class="style6">Tipo despesa<img src="imagens/spacer_1x1px.png" width="3" border="0" />
            	  <c:set var="posi" value="5"/>            	   
		          <select class="input-xxlarge" name="saida" id="saida" style="font-size: 16px; color: #006633; font-weight: bold; " onchange='document.getElementById("tipo_pagamento").value=saida.value; document.getElementById("valor").value=saida.value; pegaValor()' >
		            <c:if test="${objSaida.saida.codigo != 0}">
						<option value="${objSaida.saida.codigo}">${objSaida.saida.descricao}</option>
		          	</c:if>
				    <c:forEach var="saidas" items="${listTSai}">
		                <option value="${saidas.codigo}">${saidas.descricao}</option>
        			</c:forEach> 
                  </select>
 	 			  <input name="tipo_pagamento" id="tipo_pagamento" type="hidden" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objSaida.saida.codigo}" title="Escolha o tipo de Pagamento na caixa de seleção" size="3" maxlength="6" readonly />                  
				</div></td>
				</tr>
        	<tr>
         	  <td><div align="left" class="style6">Fornecedor<img src="imagens/spacer_1x1px.png" width="5" border="0" />         	 
			  <input class="input-xlarge" type="text" name="consulta" id="consulta" size="37" maxlength="100" style="font-size:18px; color:#006633; font-weight:bold" value="${objSaida.flSocio!='N'? objSaida.objetoSocio.nome : objSaida.objetoNaoSocio.nome}" onkeyup="pesquisar(this.value,event)" onblur="clearTime()"/>
			  <img src="imagens/spacer_1x1px.png" width="3" border="0" />					
              <select class="input-large" name="situacao" id="situacao" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("situ_paga").value=situacao.value' >
                 <option value="${situPaga[0]=='N'?'N':'A'}">${situPaga[0]=='N'?'Não Associado':'Associado'}</option>
                 <option value="${situPaga[0]=='N'?'A':'N'}">${situPaga[0]=='N'?'Associado':'Não Associado'}</option>
                 <!-- option mostra tipo fornecedor ^ atual e, ao mesmo tempo, permite alteração -->                 
              </select>
              <input name="situ_paga" id="situ_paga" type="hidden" value="${situPaga[0]}" />              
              <input name="cod_paga" id="cod_paga" type="hidden" value="${objSaida.flSocio!='N'? objSaida.objetoSocio.codigo : objSaida.objetoNaoSocio.codigo}"/>
		      <input type="button" onclick="enviar('IncluiFornecedor')" name="bt7" value="cadastrar" title="Cadastrar dados do fornecedor"></div>
			  <div id="list" style="display:none"><img src="imagens/spacer_1x1px.png" width="100" border="0" />
			  	 <select name="listboxnome" id="listboxnome" style="font-size:18px; color:#006633; font-weight:bold; width:485px"  
    		        onchange="pesquisa_selecionada(this,event);" onclick="pesquisa_test_click(this,event);" onkeypress="pesquisa_test_key(this,event);">
                 </select>
              </div></td>
            </tr>
            <tr>
         	    <td><div align="left" class="style6">Documento<img src="imagens/spacer_1x1px.png" width="5" border="0" />         	 
				<select class="input-medium" name="documento" id="documento" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("tipo_documento").value=documento.value ' >
                   <c:forEach var="tipodocs" items="${listTDoc}">
		              <option value="${tipodocs.codigo}">${tipodocs.descricao}</option>
        		   </c:forEach> 
                 </select>
                   <input name="tipo_documento" id="tipo_documento" type="hidden" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objSaida.tipoDocumento.codigo}" maxlength="1" readonly />                  
                   <img src="imagens/spacer_1x1px.png" width="3" border="0" />Data<img src="imagens/spacer_1x1px.png" width="2" border="0" />                
                   <input class="input-small" name="data_nota" id="data_nota" type="text" size="8" maxlength="10" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatDate value="${objSaida.dataDocumento}" type="date" pattern="dd/MM/yyyy" />' onkeypress="return mascara(event,this,'##/##/####')" onblur="verificaData(this)" />
            	   <img src="imagens/spacer_1x1px.png" width="3" border="0" />Número<img src="imagens/spacer_1x1px.png" width="3" border="0" />
            	   <input class="input-small" name="numero_doc" id="numero_doc" type="text" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objSaida.documento}" title="Número do Documento/Nota" size="7" maxlength="20">                				   
                   <img src="imagens/spacer_1x1px.png" width="2" border="0" />Valor<img src="imagens/spacer_1x1px.png" width="3" border="0" />
                   <input class="input-small" name="valor_doc" id="valor_doc" type="text" size="10" maxlength="20" style="font-size:16px; color:#006633; font-weight:bold" value='<f:formatNumber value="${objSaida.valor}" minFractionDigits="2" type="currency"/>' onkeypress="return soNumero(event)"; onblur="formataValor(this)" title="Valor do Documento/Nota" />			  	   
                   
		           <img src="imagens/spacer_1x1px.png" width="2" border="0" />Observação<img src="imagens/spacer_1x1px.png" width="5" border="0" />         	 
				   <input class="input-xxlarge" type="text" name="observacao" id="observacao" size="100" maxlength="300" style="font-size:18px; color:#006633; font-weight:bold" title="" value="${objSaida.observacao}" />
                   
            	</div></td>
            </tr>
            </table>
        </form>
		<form name="form" id="form" method="post">
		  <table width="763" frame="void">
		  	<tr>
			  	<td><input name="cod_pg" id="cod_pg" type="hidden" value="" /></td>
			  	<td><input name="tipo_entr" id="tipo_entr" type="hidden" value="1" /></td>
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
					        	<input type="button" onclick="enviar('IncluiSaida')" name="bt1" value="Incluir">
					        	<input type="button" onclick="enviar('AlteraSaida&numero=${objSaida.numero}&ano=${objSaida.ano}')" name="bt1" value="alterar">     
					        	<input type="button" onclick="enviar('EstornaSaida&numero=${objSaida.numero}&ano=${objSaida.ano}')" name="bt4" value="estornar">
							</c:if>
				        	<input type="button" onclick="enviar('ListaSaida')" name="bt5" value="listar"> 
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
  	    window.onload = function(){document.formNome.consulta.focus(); pegaValor();}
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
  	      function mascara(e,src,mask){
   	  	     if(window.event){
   	  		    _TXT = e.keyCode;
   	  	     }else if(e.which){
   	  		    _TXT = e.which;
   	  	     }
   	  	     var i = src.value.length;
   	  		 if(i>9){
   	  			_TXT = 8;
   	  		 }
   	  	     if(_TXT > 47 && _TXT < 58){	
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
  	      function pesquisar(buscar,e){
  	    	 var codigoTecla;    
  	  	     if (!e) 
  	  	        var e = window.event; 
	  	  	     
	  	     if (e.keyCode) 
		  	    codigoTecla = e.keyCode;
		  	    
	  	     else if (e.which) 
		  	    codigoTecla = e.which;    

	  	     var key = '';
		     key = String.fromCharCode(codigoTecla); // Pega o valor da tecla a partir do seu código.
		         
		     if (codigoTecla == 40) { //Se a tecla pressionada foi seta para baixo.        
				    document.getElementById('list').display = "block";
					document.getElementById("listboxnome").focus(); //Passa o foco para a listbox.
					document.getElementById("listboxnome").selectedIndex = 0; 
					document.getElementById("consulta").value = res_nome[0].childNodes[0].data; 
					document.getElementById("cod_paga").value = res_cod[0].childNodes[0].data; 
				    document.getElementById("cod_pg").value=document.getElementById("cod_paga").value;
				   	//document.getElementById("situ_paga").value = res_situ[0].childNodes[0].data;
		     } else {
			    if (buscar.length > 0) { //Se tem alguma string para ser procurada.
			       if((codigoTecla > 64 && codigoTecla < 123)||codigoTecla == 8){            
			          document.getElementById("cod_paga").value=0;
			          document.getElementById("cod_pg").value=document.getElementById("cod_paga").value;
			          settimeId = window.setTimeout("startHttpReq('"+buscar+"')",100);
			       }else if (codigoTecla == 27 || codigoTecla == 13){
			    	   pesquisa_test_key(buscar,e);
			       };         
			    }else{
				   if(codigoTecla==27){
				      ShowList(false);
				   };
				};
	         }
	      }

  	      function startHttpReq(buscar){
  		    var url = "stu?p=BuscaPessoa&nome_paga="+escape(buscar)+"&situ_paga="+document.getElementById("situacao").value; //Monta a url de pesquisa.
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
	     function ShowList(exibir){
			 div = document.getElementById("list");
			 if (exibir==true){
	  			div.style.display = 'block';
			 }else{
	  			div.style.display = 'none';
			 }
		 }
		  
	     function XMLHttpRequestChange() { //Controla as mudanças do objeto XMLHttpRequest.   
		    if (xmlhttp.readyState == 4 && xmlhttp.status == 200){ //Verifica se o arquivo foi carregado com sucesso.
			   result = xmlhttp.responseXML; //Armazena a resposta XML.		                
		       res_cod  = result.getElementsByTagName("codigo"); //Captura todas as respostas nas Tags <codigo>         
		       res_nome = result.getElementsByTagName("nome"); //Captura todas as respostas nas Tags <nome>
		       res_situ  = result.getElementsByTagName("situacao"); //Captura todas as respostas nas Tags <situacao> 
	           document.getElementById("listboxnome").innerHTML = ""; //Apaga o conteúdo da listbox.

	           if( res_nome.length>0){

	        	   for (var i = 0; i < res_nome.length; i++) { //Popula o listbox

	        		  res_nome[i].childNodes[0].data = (res_nome[i].childNodes[0].data).replace(/\Æ/, "&");
        		   	  //A linha acima soluciona a transferencia de & (e comercial) nessa busca Ajax.
	        	   	        		  
			          new_opcao = create_opcao(res_nome[i]);
	        		  document.getElementById("listboxnome").appendChild(new_opcao);
	        		  document.getElementById("listboxnome").options[0].text;       
		           }          
		       }
	           if (i>0) {
	        	  var j = i;
		          if(j>3)j=3;
		          document.getElementById("listboxnome").size = j;
		          ShowList(true);
	           }else{
	        	  ShowList(false);
	        	  document.getElementById("consulta").focus();
		       }
	        }
	     }

	     function create_opcao(nome) { //Cria um novo elemento OPTION.    
		    var new_opcao = document.createElement("option"); //Cria um OPTION.    
		    var texto = document.createTextNode(nome.childNodes[0].data); //Cria um texto.	
	        new_opcao.setAttribute("value",nome.getAttribute("id")); //Adiciona o atributo de valor a nova opção.     
	        new_opcao.appendChild(texto); //Adiciona o texto a OPTION.    
	        return new_opcao; // Retorna a nova OPTION.
	     }
	     //Atualiza o campo de pesquisa com o valor da listbox.
	     function pesquisa_selecionada(listbox,e) {    
		    document.getElementById("consulta").value = listbox.options[listbox.selectedIndex].text;
			document.getElementById("cod_paga").value = res_cod[document.getElementById("listboxnome").selectedIndex].childNodes[0].data; 
			document.getElementById("situ_paga").value = res_situ[document.getElementById("listboxnome").selectedIndex].childNodes[0].data;
	     }

	     function pesquisa_test_key(listbox,e) {  //Trata o pressionamento das teclas enter e escape    
		    var code;    
	  	    if (!e) 
		  	   var e = window.event;    

	  	    if (e.keyCode) 
		  	   code = e.keyCode;
	  	    else if (e.which) 
		  	   code = e.which;    

	  	    if ((code == 13)||(code == 27)) { //Caso enter passa o foco para o campo de pesquisa,  e desativa a listbox. 
	  	       ShowList(false);
			   if (code == 27) { //Caso escape (ESC), apaga o valor do campo de pesquisa, 
			      limpar();
			      document.getElementById("tipo_pagamento").value=0;
			   }        
			   
			   document.getElementById("consulta").focus();  		
		    }
	     }

	     function pesquisa_test_click(listbox,e) { //Testa se a listbox foi clicada.
		    ShowList(false);
		    pesquisa_selecionada(listbox,e);
		    document.getElementById("consulta").focus(); //Passa o foco para o campo de pesquisa.    
	     }
	     function clearTime() {    
		    window.clearTimeout(settimeId); //Limpa qualquer chamada agendada anteriormente.  	
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
			if( (pModu!='ListaSaida')&&(pModu!='IncluiSaida') ){
				var recebeCampo = document.getElementById("situ_paga").value;
				if( pModu=='IncluiFornecedor' ){
				   pModu = "IncluiNaoSocio&modulo=IncluiSaida"; //Alterar - MELHORAR CODIGO
				   if(recebeCampo!="N"&&recebeCampo!=""){
					   pModu = "IncluiSocio&modulo=IncluiSaida";
					   if(!confirm(document.getElementById("situ_paga").value=='F'?"Socio Afastado!":document.getElementById("situ_paga").value=='L'?"Socio em Licença!":"Socio!")) return false;
				   }
				}
			}
			document.form.cod_pg.value=document.formNome.cod_paga.value;
		    document.getElementById("valor_doc").value=trocaVirgula(document.getElementById("valor_doc").value);
			if(pModu=='IncluiSaida'||pModu=='AlteraSaida'||pModu=='EstornaSaida'){ //pModu=='IncluiSaida'
				if(document.getElementById("valor_doc").value==0||document.getElementById("data_nota").value*1==0||document.getElementById("cod_paga").value*1==0){
					alert("Necessário o preenchimento dos campos");
					return false;
				}							
			}
    		buscaDados(pModu);
		 }
		 
		 function pegaValor(){
			 document.getElementById("valor_doc").value=document.formNome.valor.options[document.formNome.saida.selectedIndex].text;
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
		 
		 function limpar(){
		    document.formNome.consulta.value='';	
		    document.formNome.situ_paga.value='N';
		    document.formNome.numero_doc.value='';	
		    document.formNome.valor_doc.value='';
		    document.formNome.data_nota.value='';
		    document.formNome.observacao.value='';
		    document.form.CampoStatus.value='';
		    document.formNome.consulta.focus();	
		 }				
	  </script>
	