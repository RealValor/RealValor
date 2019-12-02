<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  
	  <div class="hero-unit" align="center">
		  <div id="estrutura">
	      	<table width="764" frame="void">
		       <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Cadastro do Núcleo</div></td></tr>
		       <tr><td colspan="17">&nbsp;</td></tr>     
		    </table>
	      
			<form name="form" id="form" method="post">
			  <table width="764" frame="void">            
	            <tr>
	              <td><div align="left" class="style6">Nome:<img src="imagens/spacer_1x1px.png" width="30" border="0" />
	               <input name="desc_nucleo" type="text" id="desc_nucleo" class="input-xlarge" size="40" maxlength="100" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objNucleo.nome}"/><img src="imagens/spacer_1x1px.png" width="7" border="0" />CNPJ:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	               <input name="cnpj_nucleo" type="text" id="cnpj_nucleo" class="input-large" size="10" maxlength="20" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objNucleo.cnpj}"/>
	               </div></td>
	            </tr>   
	            <tr>
	               <td><div align="left" class="style6">Endereço:<img src="imagens/spacer_1x1px.png" width="2" border="0" />
	               <input name="endereco" type="text" id="endereco" class="input-xlarge" size="40" maxlength="100" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objNucleo.logradouro}"/>
	               
					<img src="imagens/spacer_1x1px.png" width="5" border="0" />Bairo:<img src="imagens/spacer_1x1px.png" width="5" border="0" />	
			          <select name="bairro" style="font-size: 18px; color: #006633; font-weight: bold;" onchange='document.getElementById("cd_bairro").value=bairro.value'>
						 <c:if test="${not empty objBairro.codigo}">
				             <option value="${objBairro.codigo}">${objBairro.nome}</option>
						 </c:if>			          
						 <c:forEach var="bairros" items="${listBairro}">
			                <option value="${bairros.codigo}">${bairros.nome}</option>
	        			 </c:forEach> 
	                  </select>
	                <input name="cd_bairro" id="cd_bairro" type="hidden" value="${objBairro.codigo}" readonly />
	      			</div>

             	   <div align="left" class="style6">CEP:<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	               <input name="cep" type="text" id="cep" class="input-small" size="10" maxlength="14" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objNucleo.cep}" onkeypress="return mascara(event,this,'##.###-###');"/>

             	   <img src="imagens/spacer_1x1px.png" width="1" border="0" />Cidade:<img src="imagens/spacer_1x1px.png" width="1" border="0" />

	              <input type="text" name="consulta" id="consulta" size="50" maxlength="100" style="font-size:18px; color:#006633; font-weight:bold" title="Digite o nome da cidade" value="${objCidade.nome}" onkeyup="pesquisar(this.value,event);" onblur="clearTime();"/>
				  <div id="list" style="display:none">
				  	 <img src="imagens/spacer_1x1px.png" width="210" border="0" />
				  	 <select name="listboxnome" id="listboxnome" style="font-size:18px; color:#006633; font-weight:bold;width:240px"  
	    		        onchange="pesquisa_selecionada(this,event);" onclick="pesquisa_test_click(this,event);" onkeypress="pesquisa_test_key(this,event);">
	                 </select>
	              </div>
                  <input name="cd_cidade" id="cd_cidade" type="hidden" value="${objCidade.codigo}" readonly />

             	   <img src="imagens/spacer_1x1px.png" width="3" border="0" />Estado:<img src="imagens/spacer_1x1px.png" width="2" border="0" />
			          <select name="estado" style="font-size: 18px; color: #006633; font-weight: bold;" onchange='document.getElementById("cd_estado").value=estado.value'>
						 <c:if test="${not empty objEstado.codigo}">
				             <option value="${objEstado.codigo}">${objEstado.nome}</option>
						 </c:if>			          
						 <c:forEach var="estados" items="${listEstado}">
			                <option value="${estados.codigo}">${estados.nome}</option>
	        			 </c:forEach> 
	                  </select>
	               <input name="cd_estado" id="cd_estado" type="hidden" value="${objEstado.codigo}" readonly />

             	   <img src="imagens/spacer_1x1px.png" width="3" border="0" />Banco recebimento on-line:<img src="imagens/spacer_1x1px.png" width="4" border="0" />
			          <select name="banco" style="font-size: 18px; color: #006633; font-weight: bold;" onchange='document.getElementById("cd_banco").value=banco.value'>
						 <c:if test="${not empty objBanco.codigo}">
				             <option value="${objBanco.codigo}">${objBanco.descricao}</option>
						 </c:if>			          
						 <c:forEach var="bancos" items="${listBanco}">
			                <option value="${bancos.codigo}">${bancos.descricao}</option>
	        			 </c:forEach> 
	                  </select>
	                <input name="cd_banco" id="cd_banco" type="hidden" value="${objBanco.codigo}" readonly />

             	   <img src="imagens/spacer_1x1px.png" width="3" border="0" />E-mail:<img src="imagens/spacer_1x1px.png" width="8" border="0" />
                   <input name="email_nucleo" type="text" id="email_nucleo" size="45" maxlength="100" style="font-size: 17px; color: #006633; font-weight: bold;" value="${objNucleo.email}"/>

				   <img src="imagens/spacer_1x1px.png" width="3" border="0" />Observação:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
		           <input class="input-xxlarge" name="observacao" type="text" id="observacao" size="65" maxlength="100" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objNucleo.observacao}"/>
		           </div></td>
	            </tr>
	          </table>
	          
	          <table width="763" frame="void">
	             <tr><td>&nbsp;</td></tr>
	          </table>
			   <table width="764">
	  				<tr>
	                	<td colspan="3">
	                    	<div align="left">
		                    	<c:if test="${objUsu.nivel > 2 }">
						        	<input type="button" onclick="enviar('AlteraNucleo')" id="bt1" name="bt1" value="Confirmar Alterações">
						        	<input type="button" onclick="limpar()" name="bt6" value="limpar">
								</c:if>
							</div>
	                    </td>
	                    <c:if test="${objUsu.nivel > 2 }">
		                    <td align="right">
	                   			<input type="checkbox" name="incluifoto" id="incluifoto" value="0" title="Selecionar Imagem png - tamanho sugerido 760 X 416" onclick="if(this.checked){incluiFotoNucleo();}else{limpaincluiFotoNucleo();}" /><img src="imagens/spacer_1x1px.png" width="5" border="0" />Incluir foto do núcleo pra tela principal<img src="imagens/spacer_1x1px.png" width="1" border="0" />
		                    </td>
	                    </c:if>
	  				</tr>
			  </table>
	   		</form>
  			
  			<div id="mostrabuscafoto" class="style6" style="display:none"><img src="imagens/spacer_1x1px.png" width="5" border="0" >
		   		<form action="stu?p=GravaFoto" enctype="multipart/form-data" method="post">
	               	<div align="right">
	                   	<c:if test="${objUsu.nivel > 2 }">
							<input name="fotonucleo" type="file" accept=".png" >
							<button type="submit">Enviar</button>
				        	<c:if test="${not empty imagem and objUsu.nivel > 4}">
				        		<input type="button" onclick="enviar('stu?p=ExcluiFoto')" name="bt6" value="Excluir Foto">
							</c:if>
						</c:if>
					</div>   
			    </form>
           	</div>
		</div>    
  </div>
  <c:import url="rodape.jsp"/>

<script>
  	      window.onload = function(){document.form.desc_nucleo.focus();};
  	      function AbrirAjax() {
   	   	     var xmlhttp_new;
   	   	     try {
   	   	        xmlhttp_new = new ActiveXObject("Microsoft.XMLHTTP"); //Para o Internet Explorer	
   	   	     } 
   	   	     catch(e) {
   	   	        try {  	   	      
   	   	           xmlhttp_new = getXMLHTTP(); //IE8	
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

		  function buscaDados(pMod){
		     document.form.action='stu?p='+pMod;   
  			 document.form.submit();
		  }
     	  
		  function preencheCampo(pCampo,pDesc){
    		   if(pCampo.value == ''){
    			  alert('Necessário Campo '+pDesc);
    			  pCampo.focus();
    		      return false;
    		   }
    		   return true;   
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

     	  function enviar(pModu){
     		 if(pModu!='ListaNucleo'){
				 if(preencheCampo(document.form.desc_nucleo,'Nome')){
		   			buscaDados(pModu);
		   			//limpar();
				 }else{
					alert('Necessário nome do Núcleo'); 
				 }
			 }else{
  			     buscaDados(pModu);
			}		  	
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

		  function limpar(){
			 buscaDados('IncluiNucleo');
		  }

		  	function verificaCNPJ(src) {
		    	  
		    	    cnpj = limpaValor(src);
		    	    
					var retorno = 1;
		    	 	
		    	    if (cnpj.length>0&&cnpj.length<14)
		    	    	retorno = 0;

		    	    if (cnpj == repete('0',14) || 
			    	    cnpj == repete('1',14) || 
			    	    cnpj == repete('2',14) || 
			    	    cnpj == repete('3',14) || 
			    	    cnpj == repete('4',14) || 
			    	    cnpj == repete('5',14) || 
			    	    cnpj == repete('6',14) || 
			    	    cnpj == repete('7',14) || 
			    	    cnpj == repete('8',14) || 
			    	    cnpj == repete('9',14)
				    	)retorno = 0;

					
		    	    tamanho = cnpj.length - 2;
		    	    numeros = cnpj.substring(0,tamanho);
		    	    digitos = cnpj.substring(tamanho);
		    	    
		    	    soma = 0;
		    	    pos = tamanho - 7;
		    	    
		    	    for (i = tamanho; i >= 1; i--) {
		    	      soma += numeros.charAt(tamanho - i) * pos--;
		    	      if (pos < 2)
		    	            pos = 9;
		    	    }
		    	    
		    	    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
		    	    if (resultado != digitos.charAt(0)*1)
		    	    	retorno = 0;
		    	         
		    	    tamanho = tamanho + 1;
		    	    numeros = cnpj.substring(0,tamanho);
		    	    
		    	    soma = 0;
		    	    pos = tamanho - 7;
		    	    
		    	    for (i = tamanho; i >= 1; i--) {
		    	      soma += numeros.charAt(tamanho - i) * pos--;
		    	      if (pos < 2)
		    	            pos = 9;
		    	    }
		    	    
		    	    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
		    	    if (resultado != digitos.charAt(1)*1)
		    	    	retorno = 0;

		    	    if(retorno<1){
		    	    	alert("CNPJ "+src.value+" inválido!");
		    	    	src.value = '';
		    	    	src.focus();
			    	    return false;
		    	    }else{
			    	    return true;	    	    
		    	    }
		      }
		  //-------------------
		  
		  function incluiFotoNucleo() {
			  div_foto = document.getElementById("mostrabuscafoto");
			  div_foto.style.display = 'block';
		  }

		  function limpaincluiFotoNucleo() {
			  div_foto = document.getElementById("mostrabuscafoto");
			  div_foto.style.display = 'none';
		  }

		  
		  var result;
    	  var res_cod;
      	  var res_nome;
      	  var res_uf;

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
			    if (document.getElementById('list').display = "block") {			                   
				   document.getElementById("listboxnome").focus(); //Passa o foco para a listbox.
				   document.getElementById("listboxnome").selectedIndex = 0;   
				   document.getElementById("consulta").value = res_nome[0].childNodes[0].data;
				   document.getElementById("cd_cidade").value = res_cod[0].childNodes[0].data;
				   //document.getElementById("cod_op").value=document.getElementById("cd_cidade").value;
			    }    
		     } else {
			    if (buscar.length > 0) { //Se tem alguma string para ser procurada.
			       if((codigoTecla > 64 && codigoTecla < 123)||codigoTecla == 8){            
			          //ShowLoading(true);
			          settimeId = window.setTimeout("startHttpReq('"+buscar+"')",100);
			       }else if (codigoTecla == 27 || codigoTecla == 13){
			    	   pesquisa_test_key(buscar,e);
			       }         
			    }else{
				   if(codigoTecla==27){
				      ShowList(false);
				   }
				}			    
	         }
	      }

  	      function startHttpReq(buscar){
  	    	  
  	    	//alert('vai acionar buscaCidade');

  		    url = 'stu?p=BuscaCidade&nome_cidade='+escape(buscar); //Monta a url de pesquisa. 
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
			 div = document.getElementById('list');
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
		       res_uf = result.getElementsByTagName("uf"); //Captura todas as respostas nas Tags <nome>         

	           document.getElementById("listboxnome").innerHTML = ""; //Apaga o conteúdo da listbox.
	           if( res_nome.length>0){
		           for (var i = 0; i < res_nome.length; i++) { //Populariza a listbox
			          new_opcao = create_opcao(res_nome[i]);
					  document.getElementById("listboxnome").appendChild(new_opcao);        
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
			document.getElementById("cd_cidade").value = res_cod[document.getElementById("listboxnome").selectedIndex].childNodes[0].data; 
			document.getElementById("consulta").value = res_nome[document.getElementById("listboxnome").selectedIndex].childNodes[0].data; 
			
		    //res_uf = result.getElementsByTagName("uf");         

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
			   if (code == 27) { 
				  limpar();
			   }else {
					document.getElementById("cd_cidade").value = res_cod[document.getElementById("listboxnome").selectedIndex].childNodes[0].data;
					document.getElementById("consulta").value = res_nome[document.getElementById("listboxnome").selectedIndex].childNodes[0].data; 
			   }
		       document.getElementById("consulta").focus();        
		    }
	     }

	     function pesquisa_test_click(listbox,e) { //Testa se a listbox foi clicada.
		    ShowList(false);    
		    document.getElementById("consulta").focus(); //Passa o foco para o campo de pesquisa.    
	     }
	     function clearTime() {    
		    window.clearTimeout(settimeId); //Limpa qualquer chamada agendada anteriormente.  	
	     }

		</script>
