<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	  <script>
	      window.onload = function(){document.formNome.consulta.focus();}
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

  	      var result;
    	  var res_cod;
      	  var res_nome;
      	  var res_cpf;

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
				   document.getElementById("cod_oper").value = res_cod[0].childNodes[0].data;
				   document.getElementById("cod_op").value=document.getElementById("cod_oper").value;
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
  		    url = 'stu?p=BuscaOperador&nome_oper='+escape(buscar); //Monta a url de pesquisa. 
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
		       res_cpf  = result.getElementsByTagName("cpf"); //Captura todas as respostas nas Tags <cpf>   

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
			document.getElementById("cod_oper").value = res_cod[document.getElementById("listboxnome").selectedIndex].childNodes[0].data; 
			document.getElementById("cpf_oper").value = res_cpf[document.getElementById("listboxnome").selectedIndex].childNodes[0].data; 
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
			   }else {
					document.getElementById("cod_oper").value = res_cod[document.getElementById("listboxnome").selectedIndex].childNodes[0].data; 
					document.getElementById("cpf_oper").value = res_cpf[document.getElementById("listboxnome").selectedIndex].childNodes[0].data; 
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
		 
		 function validanivel(src){
	    	 var i = src.value;
	    	 if(i<1||i>4){
 	  		    alert('Níveis de 1 a '+document.getElementById("nivel_op").value);
 	  		   	src.value=1;
 	  		   	return false;
 	  		 }else{
 	  			 return true;
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
     	 //------------------------------
     	 function verificaCPF(){ //verificar o uso do parametro
     		 
	         var i;
	         var numero = limpaValor(document.form.cpf_oper);

	         s = numero;
	         c = s.substr(0,9);
	         var dv = s.substr(9,2);
	         var d1 = 0;
	         var result = 1;	
					
	         for (i = 0; i < 9; i++){
	            d1 += c.charAt(i)*(10-i);
	         }
	
	         if (d1 == 0){
	            result = 0;
	         }
	
	         d1 = 11 - (d1 % 11);
	         if (d1 > 9) d1 = 0;
	
	         if (dv.charAt(0) != d1){
	            result = 0;
	         }
	
	         d1 *= 2;
	         for (i = 0; i < 9; i++){
	            d1 += c.charAt(i)*(11-i);
	         }
	
	         d1 = 11 - (d1 % 11);
	         if (d1 > 9) d1 = 0;
	
	         if (dv.charAt(1) != d1){
	            result = 0;
	         }

	         if (result==0) {
	        	 alert("CPF inválido!");
	            document.form.cpf_oper.value = '';
	            document.form.cpf_oper.focus;
	            return false;
	         }else{
	        	 return true;
	         }
   		 }

		function verificaSenha(senha){
			var valorSenha = senha.value;
	        if(valorSenha.length < 6 && valorSenha.length != 0){
				alert("Comprimento mínimo da senha 6 caracteres");
				document.getElementById("senha_oper").value = "";
				document.getElementById("senha_oper").focus();
				return false;
	        }
		}
     	 
     	 function buscaDados(pMod){
     		 
		    document.form.action='stu?p='+pMod;   
  			document.form.submit();
		 }

     	 function enviar(pModu){
     		 
     		 if(pModu!='ListaOperador'){

     			if(!(preencheCampo(document.formNome.consulta,'Nome')&&preencheCampo(document.getElementById("cpf_oper"),'CPF'))){
					return false;
				}

				if(!(verificaCPF()&&validanivel(document.getElementById("nivel_oper")))){
					return false;						
				}
     		 }


			if(document.getElementById("cpf_oper").value!=""){
				document.getElementById("cpf_oper").value=limpaValor(document.getElementById("cpf_oper"));
		    }
		    document.getElementById("cod_op").value=document.formNome.cod_oper.value;

		    buscaDados(pModu);
		 }
     	 
		 function limpar(){
		    document.formNome.consulta.value='';	
		    document.form.cpf_oper.value='';
		    buscaDados('IncluiOperador');
		 }

	  </script>   
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objOper" class="beans.Login" scope="request" />
	  <div class="hero-unit" align="center">
		    <div id="estrutura">
	        <table width="764" frame="void">
		       <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Cadastro de Operador</div></td></tr>     
		       <tr><td colspan="3">&nbsp;</td></tr>     
		    </table>
	              
	        <form id="formNome" name="formNome" method="post" action="stu?p=ConsultaOperador">
	        	<table width="763" frame="void">
	        	<tr>
	         	  <td colspan="4"><div align="left" class="style6">Nome:<img src="imagens/spacer_1x1px.png" width="5" border="0" />         	 
				  <input class="input-xxlarge" type="text" name="consulta" id="consulta" size="50" maxlength="100" style="font-size:18px; color:#006633; font-weight:bold" value="${objOper.nome}" onkeyup="pesquisar(this.value,event)" onclick="pesquisa_test_click(this,event);" onblur="clearTime()"/>
				  <div id="list" style="display:none">
				  	 <img src="imagens/spacer_1x1px.png" width="60" border="0" />
				  	 <select name="listboxnome" id="listboxnome" style="font-size:18px; color:#006633; font-weight:bold;width:485px"  
	    		        onchange="pesquisa_selecionada(this,event);" onclick="pesquisa_test_click(this,event);" onkeypress="pesquisa_test_key(this,event);">
	                 </select>
	              </div>
	              <input name="cod_oper" type="hidden" id="cod_oper" value="${objOper.usuario}"/>
	  			  <input name="procurar" id="procurar" type="hidden" type="submit" value="procurar"/></div></td>
	            </tr>
	            </table>
	        </form>
			<form name="form" id="form" method="post">
			  <table width="763" frame="void">                        
	            <tr>
			      <td><div align="left" class="style6">CPF:<img src="imagens/spacer_1x1px.png" width="17" border="0" />
	                    <input name="cpf_oper" type="text" id="cpf_oper" size="14" maxlength="14" style="font-size: 17px; color: #006633; font-weight: bold;" value="${objOper.cpfStr}" onkeypress="return mascara(event,this,'###.###.###-##');" onkeyup="JumpField(this,'senha_oper');"/>
	              </div></td>
	          <td><div align="left" class="style6">Senha:<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                    <input name="senha_oper" type="password" id="senha_oper" size="15" maxlength="50" onblur="verificaSenha(this);" style="font-size: 17px; color: #006633; font-weight: bold;" value=""/>
	              		<input name="cod_op" type="hidden" id="cod_op" value="" />
	              		<input class="input-mini" type="text" name="nivel_oper" id="nivel_oper" size="1" maxlength="1" style="font-size: 17px; color: #006633; font-weight: bold;" value="${objOper.nivel}" onblur="validanivel(this);"/>
	              </div></td>
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
						        	<input type="button" onclick="enviar('IncluiOperador')" name="bt1" value="Incluir"></input>   
						        	<input type="button" onclick="enviar('AlteraOperador')" name="bt3" value="alterar"></input>   
						        	<input type="button" onclick="enviar('ExcluiOperador')" name="bt4" value="excluir"></input>   
								</c:if>
			              		<input name="nivel_op" id="nivel_op" type="hidden" value="${objUsu.nivel}" />
					        	<input type="button" onclick="enviar('ListaOperador')" name="bt5" value="listar"></input> 
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar"></input> 
							</div>   
	                    </td>   
					</tr>
			  </table>
	   		</form>
		</div>  
	  </div>
	<c:import url="rodape.jsp"/>
