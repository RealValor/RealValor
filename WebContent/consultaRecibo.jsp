
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix = "fn"%>

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
	  <jsp:useBean id="objPaga" class="beans.Login" scope="session" />
	<div class="hero-unit" align="center">
	  <div id="estrutura">
        <table width="760" frame="void">
		   <tr><td colspan="17">&nbsp;</td></tr>	  
		   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Consulta Recibo por Nome</div></td></tr>     
		   <tr><td colspan="17">&nbsp;</td></tr>	  
	    </table>
           
		<form id="formNome" name="formNome" method="post">
        	<table width="760" frame="void">
        	<tr>
         	  <td colspan="5"><div align="left" class="style6">Nome<img src="imagens/spacer_1x1px.png" width="2" border="0" />        	 
			  <input class="input-xxlarge" type="text" name="consulta" id="consulta" size="50" maxlength="100" style="font-size:18px; color:#006633; font-weight:bold" value="${objPaga.nome}" title="Antes de digitar o nome selecione o ano e mês quitado com o recibo" onkeyup="pesquisar(this.value,event)" onclick="pesquisa_test_click(this,event);" onblur="clearTime()"/>
			  <img src="imagens/spacer_1x1px.png" width="5" border="0" />					
              <select class="input-medium" name="situacao" id="situacao" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("situ_paga").value=situacao.value ' >
                 <option value="${situPaga[0]}">${situPaga[1]}</option>
                 <option value="${situPaga[0]=='N'?'A':'N'}">${situPaga[0]=='N'?'Associado':'Não Associado'}</option>
                    							<!-- a inversão acima possibilita a alternância na escolha -->
              </select>
              <input name="situ_paga" id="situ_paga" type="hidden" value="${situPaga[0]}" />              
              <input name="cod_paga" id="cod_paga" type="hidden" value="${objPaga.usuario}"/>
              <input name="urlSet" id="urlSet" type="hidden" value="${urlSet}"/></div>
			  <div id="list" style="display:none"><img src="imagens/spacer_1x1px.png" width="56" border="0" />
			  	 <select name="listboxnome" id="listboxnome" style="font-size:18px; color:#006633; font-weight:bold;width:485px"  
    		        onchange="pesquisa_selecionada(this,event);" onclick="pesquisa_test_click(this,event);" onkeypress="pesquisa_test_key(this,event);">
                 </select>
              </div></td>
            </tr>
            <tr>
            	<td colspan="4"><div align="left" class="style6">Nº. Recibo<img src="imagens/spacer_1x1px.png" width="2" border="0" />
           	    <c:set var="posi" value="5"/>
           	    <input name="cancelado" id="cancelado" type="hidden" value="${excluido}"/>
               	  
			    <c:if  test="${fn:length (listRecibo) gt 1}">
		          <select class="input-medium" name="recibo" id="recibo" style="font-size: 16px; color: #CC3300; font-weight: bold;" onblur='document.getElementById("numero_recibo").value=recibo.value; pegaValor()' >
					 <c:if test="${empty listRecibo}">
		                <option value="0">0000/0000</option>
					 </c:if>
				     <c:forEach var="recibos" items="${listRecibo}">
							<option style='color: #006633' value="${recibos.recibo}"><f:formatNumber value='${recibos.recibo}' pattern="0000" />/<f:formatDate value="${recibos.data.time}" type="date" pattern="yyyy" /></option>
	        		 </c:forEach>
                  </select>
			    </c:if> 
                 
			    <c:if  test="${fn:length (listRecibo) == 1}">
		          <select class="input-medium" name="recibo" id="recibo" style="font-size: 16px; color: #006633; font-weight: bold;" onblur='document.getElementById("numero_recibo").value=recibo.value; pegaValor()'>
					 <c:if test="${empty listRecibo}">
		                <option value="0">0000/0000</option>
					 </c:if>
				     <c:forEach var="recibos" items="${listRecibo}">
							<option  style='color: #006633' value="${recibos.recibo}"><f:formatNumber value='${recibos.recibo}' pattern="0000" />/<f:formatDate value="${recibos.data.time}" type="date" pattern="yyyy" /></option>
	        		 </c:forEach>
                  </select>
			    </c:if>

			      <input name="numero_recibo" id="numero_recibo" type="hidden" value="${numeroRecibo}" />Ano<img src="imagens/spacer_1x1px.png" width="2" border="0" />                
                   <select class="input-small" name="ano" id="ano" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("ano_paga").value=ano.value'>
                      <c:if test="${not empty anoRecibo}">
	                   	  <option value="${anoRecibo}">${anoRecibo}</option>
                   	  </c:if>
                   	  <c:if test="${empty anoRecibo}">
						  <option value="${objMovIni.ano}">${objMovIni.ano}</option>
                   	  </c:if>
                      <option value="${objMovIni.ano - 1}">${objMovIni.ano - 1}</option>
                      <option value="${objMovIni.ano - 2}">${objMovIni.ano - 2}</option>
                      <option value="${objMovIni.ano - 3}">${objMovIni.ano - 3}</option>
                   </select>

					<select name="anoescolha" id="anoescolha" style="display: none;" >
					     <c:forEach var="recibos" items="${listRecibo}">
								<option style='color: #006633' value="${recibos.data.time}"><f:formatDate value="${recibos.data.time}" type="date" pattern="yyyy" /></option>
		        		 </c:forEach>
                   	</select>
                   <input name="ano_paga" id="ano_paga" type="hidden" style="font-size: 16px; color: #006633; font-weight: bold;" value="${objMovIni.ano}" size="1" readonly/>
                   <input name="anoRecibo" id="anoRecibo" type="hidden" value="${anoRecibo}"/>
                   <input name="cargarecibo" id="cargarecibo" type="hidden" value="${cargarecibo}"/>
            	   <img src="imagens/spacer_1x1px.png" width="5" border="0" />Mês<img src="imagens/spacer_1x1px.png" width="2" border="0" />                
                   <select class="input-mini" name="mesini" id="mesini" style="font-size: 16px; color: #006633;" onchange='document.getElementById("mesini_paga").value=mesini.value'>
                      <option value="${objMovIni.mes}">${objMovIni.mesExtenso}</option>
                      <option value="1">jan</option>
                      <option value="2">fev</option>
                      <option value="3">mar</option>
                      <option value="4">abr</option>
                      <option value="5">mai</option>
                      <option value="6">jun</option>
                      <option value="7">jul</option>
                      <option value="8">ago</option>
                      <option value="9">set</option>
                      <option value="10">out</option>
                      <option value="11">nov</option>
                      <option value="12">dez</option>
                   </select>
                   <input name="mesini_paga" id="mesini_paga" type="hidden" value="${objMovIni.mes}" size="1" maxlength="1" readonly/>
                   <c:if test="${fn:length (listRecibo) gt 1}">
	                   <input type="button" onclick="enviar('ConsultaReciboNumero&numero_recibo='+document.getElementById('numero_recibo').value+'&ano_paga='+document.getElementById('ano_paga').value+'&ctrl=pornome')" name="bt5" value="Consultar">
				   </c:if>
            	</div></td>
            </tr>
            </table>
            <table width="760" frame="void" >
			   <tr><td colspan="17">&nbsp;</td></tr>	  
            </table>
            <table width="760" frame="void" >
			   <tr><td colspan="17">&nbsp;</td></tr>	  
            </table>
            <table width="760" class="table table-condensed">
			   <c:if test="${not empty listaRec}">
				   	  <c:if test="${excluido == 'S'}">
					   	  <div align="left" style="font-size: 16px; color: #333333;">Recibo <f:formatNumber value="${numeroRecibo}" pattern="0000" />/<f:formatNumber pattern="0000" value="${anoRecibo}" /> (Cancelado)</div> 
				   	  </c:if>
				   	  <c:if test="${excluido == 'N'}">
					   	  <div align="left" style="font-size: 16px; color: #333333;">Recibo <f:formatNumber value="${numeroRecibo}" pattern="0000" />/<f:formatNumber pattern="0000" value="${anoRecibo}" /></div> 
				   	  </c:if>
				   	  <tr bgcolor="#cccccc">
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>entrada</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>codigo</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>ano</strong></div></td> 
			      	      
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>jan</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>fev</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>mar</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>abr</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>mai</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>jun</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>jul</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>ago</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>set</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>out</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>nov</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>dez</strong></div></td> 
			      	      
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>v.unit.</strong></div></td> 
			      	      <td><div align="center" style="font-size: 12px; color: #000000;"><strong>total</strong></div></td> 
	                  </tr>         
		       </c:if>
		       <c:set var="vlrtotalgeral" value="0"/>
		       <c:forEach var="linhasrecibo" items="${listaRec}">
           	      <tr bgcolor="#ffffff">
           	      
               		<td><div align="center" style="font-size: 12px; color: #333333;">${linhasrecibo.entrada.descricao}</div></td> 
               		<td><div align="center" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasrecibo.socioDevedor.codigo}" pattern="0000"/></div></td>
               		<td><div align="center" style="font-size: 12px; color: #333333;">${linhasrecibo.ano}</div></td> 
					<c:set var="vlrtotal" value="0"/>
					<c:forEach var="i" begin="0" end="11">
					   <c:if test="${linhasrecibo.meses[i] == 0}"> 
					      <td><div align="center" style="font-size: 12px; color: #999999;">-</div></td> 
		     	       </c:if>
		     	       <c:if test="${linhasrecibo.meses[i] != 0}">
               		      <td bgcolor="#009900" ><div align="center" class="style5 style9 style13 style14">${linhasrecibo.meses[i]}</div></td>
               		      <c:set var="vlrtotal" value="${vlrtotal + 1}"/>
		     	       </c:if>
					</c:forEach>					
               	    <td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhasrecibo.valor}" minFractionDigits="2" /></div></td> 
               		<td><div align="right" style="font-size: 12px; color: #333333;"><f:formatNumber value="${vlrtotal*linhasrecibo.valor}" minFractionDigits="2" /></div></td>
               		<c:set var="vlrtotalgeral" value="${vlrtotalgeral + vlrtotal*linhasrecibo.valor}"/> 
                  </tr>
       		   </c:forEach>
       		   <c:set var="socio" value="0"/> 
       		   <c:if test="${vlrtotalgeral != 0}">
       		      <tr bgcolor="#cccccc"><td colspan="16"><div align="right" style="font-size: 12px; color: #000000;"><strong>Total do Recibo:</strong></div></td>
                  <td><div align="right" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${vlrtotalgeral}" minFractionDigits="2" /></strong></div></td></tr>
                  <c:forEach var="linhassocio" items="${listaSoc}">
                     <tr bordercolor="#ffffff">
                        <td ><div align="center" style="font-size: 12px; color: #333333;"><f:formatNumber value="${linhassocio.codigo}" pattern="0000"/></div></td>
                        <td colspan="12"><div align="left" style="font-size: 12px; color: #333333;">${linhassocio.nome}</div></td>
                     </tr>
                     <c:set var="socio" value="${linhassocio.codigo}"/> 
                  </c:forEach>
               </c:if>
            </table>
        </form>
		<form name="form" id="form" method="post">
		  <table>
		  	<tr>
			  	<td>
					<input name="cod_pg" id="cod_pg" type="hidden" value="" />
					<input name="tipo_entr" id="tipo_entr" type="hidden" value="1" />				
			  	</td>
		  	</tr>                        
          </table>
	  	  <table>
  				<tr>
                	<td colspan="3">
                    	<div align="left">
                    		<c:if test="${not empty listaRec}">
					        	<input type="button" onclick="enviar('EmiteRecibo')" name="bt1" value="Visualizar">   
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
	    
	window.onload = function(){
	  	    document.formNome.consulta.focus(); 
	  	    if(document.getElementById('cargarecibo').value*1>0){
	  	    	enviar('ConsultaReciboNumero&numero_recibo='+document.getElementById('numero_recibo').value+'&ano_paga='+document.getElementById('anoRecibo').value+'&ctrl=pornome');
	  	    }
	  	};
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

		function pegaValor() {
			document.getElementById("ano_paga").value = document.formNome.anoescolha.options[document.formNome.recibo.selectedIndex].text;
		}

	var result;
	    var res_cod;
	    var res_nome;
	    var res_situ;
	  	var mudoumes;
	  
	    function pesquisar(buscar,e){
	    	 var codigoTecla;    
	  	     if (!e) 
	  	        var e = window.event; 
  	  	     
  	     if (e.keyCode) 
	  	    codigoTecla = e.keyCode;
	  	    
  	     else if (e.which) 
	  	    codigoTecla = e.which;    

  	     var key = '';
	     key = String.fromCharCode(codigoTecla); // Memoriza o valor da tecla a partir do seu código.
	         
	     if (codigoTecla == 40) { //Se a tecla pressionada foi seta para baixo.        
		    if (document.getElementById('list').display = "block") {			                   
			   document.getElementById("listboxnome").focus(); //Passa o foco para a listbox.
			   document.getElementById("listboxnome").selectedIndex = 0; 
			   document.getElementById("consulta").value = res_nome[0].childNodes[0].data; 
			   document.getElementById("cod_paga").value = res_cod[0].childNodes[0].data; 
			   document.getElementById("situ_paga").value = res_situ[0].childNodes[0].data;
			   document.getElementById("cod_pg").value=document.getElementById("cod_paga").value;
		    }    
	     } else {
		    if (buscar.length > 0) { //Se tem alguma string para ser procurada.
		       if((codigoTecla > 64 && codigoTecla < 123)||codigoTecla == 8){            
		          //ShowLoading(true);
		          document.getElementById("cod_paga").value=0;
		          document.getElementById("cod_pg").value=document.getElementById("cod_paga").value;
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
		    var url = "stu?p=BuscaPessoa&nome_paga="+escape(buscar)+"&situ_paga="+document.getElementById("situacao").value+"&ano="+document.getElementById("ano").value; //Monta a url de pesquisa. 

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
		       res_situ = result.getElementsByTagName("situacao"); //Captura todas as respostas nas Tags <situacao> 
	           document.getElementById("listboxnome").innerHTML = ""; //Apaga o conteúdo da listbox.

	           if( res_nome.length>0){
		           for (var i = 0; i < res_nome.length; i++) { //Popula o listbox
			          res_nome[i].childNodes[0].data = (res_nome[i].childNodes[0].data).replace(/\Æ/, "&");
		        	  //A linha acima soluciona a transferencia de & (e comercial) nessa busca Ajax.
			          new_pgcao = create_pgcao(res_nome[i]);
					  document.getElementById("listboxnome").appendChild(new_pgcao);        
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

	     function create_pgcao(nome) { //Cria um novo elemento OPTION.    
		    var new_pgcao = document.createElement("option"); //Cria um OPTION.    
		    var texto = document.createTextNode(nome.childNodes[0].data); //Cria um texto.	          
	        new_pgcao.setAttribute("value",nome.getAttribute("id")); //Adiciona o atributo de valor a nova opção.     
	        new_pgcao.appendChild(texto); //Adiciona o texto a OPTION.    
	        return new_pgcao; // Retorna a nova OPTION.
	     }
	     //Atualiza o campo de pesquisa com o valor da listbox.
	     function pesquisa_selecionada(listbox,e) { 
			document.getElementById("consulta").value = listbox.options[listbox.selectedIndex].text;
			document.getElementById("cod_paga").value = res_cod[document.getElementById("listboxnome").selectedIndex].childNodes[0].data; 
		 }
		     
	     
	     function pesquisa_test_key(listbox,e) {  //Trata o pressionamento das teclas enter e escape    
		    var code;    
	  	    if (!e) 
		  	   var e = window.event;    

	  	    if (e.keyCode) 
		  	   code = e.keyCode;
	  	    else if (e.which) 
		  	   code = e.which;    

	  	    if ((code == 13)||(code == 27)) { //Caso enter passa o foco para o campo de pesquisa, e desativa a listbox. 
	  	       ShowList(false);
			   if (code == 27) { //Caso escape (ESC), apaga o valor do campo de pesquisa, 
			      limpar();
			   }else{
				   enviar('ConsultaRecibo');
			   }
			   document.getElementById("consulta").focus();  		
		    }
	     }

     function clearTime() {    
	    window.clearTimeout(settimeId); //Limpa qualquer chamada agendada anteriormente.  	
     }
     
     function pesquisa_test_click(listbox,e) { //Testa se a listbox foi clicada.
		ShowList(false);
	    pesquisa_selecionada(listbox,e);
		document.getElementById("consulta").focus(); //Passa o foco para o campo de pesquisa.
		enviar('ConsultaRecibo');
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
		if(pModu!='EmiteRecibo'&&pModu!='ConsultaRecibo'){
			if(!(preencheCampo(document.formNome.consulta,'Nome'))){				
				return false;
			}else{
				var recebeCampo = document.getElementById("situ_paga").value;
				if(recebeCampo!="A"&&recebeCampo!=""){
					if(!confirm(document.getElementById("situ_paga").value=='F'?"Associado Afastado!":document.getElementById("situ_paga").value=='L'?"Associado em Licença!":document.getElementById("situ_paga").value=='T'?"Associado Transferido!":"Nao Associado!")) return false;
				}
			}
		}
		document.form.cod_pg.value=document.formNome.cod_paga.value;
		
		if(pModu=='EmiteRecibo'){
			var width = 840;
			var height = 620;

			var left = 99;
			var top = 99;
			var now=new Date();
			var seconds = now.getSeconds();

			if(document.getElementById("cancelado").value=='S'){
			   window.open(document.getElementById('urlSet').value+'imprimeRecCancelado.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
			}else{
			   window.open(document.getElementById('urlSet').value+'imprimeRecibo.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
			}
		}else{
    		buscaDados(pModu);
		}
	 }

	 function limpar(){
		    document.getElementById("consulta").value=' ';
		    document.getElementById("cod_paga").value='0';
		    document.getElementById("ano_paga").value='';
		    document.getElementById("situ_paga").value='A';
		    buscaDados('ConsultaRecibo&ctrl=limpar');
	 }

	</script>
