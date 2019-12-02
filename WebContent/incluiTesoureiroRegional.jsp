<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objBan" class="beans.Nucleo" scope="request" />
	  <div class="hero-unit" align="center">
		  <div id="estrutura">
	      	<table width="764" frame="void">
	      	   <tr><td colspan="17">&nbsp;</td></tr>
		       <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Inclui Tesoureiro Regional</div></td></tr>
		       <tr><td colspan="17">&nbsp;</td></tr>     
		    </table>
	      
			<form name="form" id="form" method="post">
			  <table width="764" frame="void">            
	            <tr>
	              <td colspan="3"><div align="left" class="style6">Região:<img src="imagens/spacer_1x1px.png" width="10" border="0" />
	              
	              <select class="input-medium" name="sel_regiao" id="sel_regiao" style="font-size: 16px; color: #006633; font-weight: bold;" title="Para inclusão em mês pago, selecione o Tipo: antes de digitar o Nome:" onchange='document.getElementById("regiao").value=sel_regiao.value; document.getElementById("valor").value=sel_regiao.value; pegaValor()' >
		          	 <c:if test="${objNucleo.regiao.codigo!=0}" >
	                    <option value="${objNucleo.regiao.codigo}">${objNucleo.regiao.descricao}</option>
                   	 </c:if> 
				     <c:forEach var="regioes" items="${listaRegioes}">
		                <option value="${regioes.codigo}">${regioes.descricao}</option>
        			 </c:forEach> 
                  </select>
 	 			  <input name="regiao" id="regiao" type="hidden" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objNucleo.regiao.codigo}" title="Escolha a Região na caixa de seleção" size="3" maxlength="6" readonly />
	               </div></td>
	            </tr>   
	            <tr>
	              <td colspan="3"><div align="left" class="style6">Núcleo:<img src="imagens/spacer_1x1px.png" width="11" border="0" />
	               <input name="desc_nucleo" type="text" id="desc_nucleo" class="input-xlarge" size="40" maxlength="100" style="font-size: 18px; color: #006633; font-weight: bold;" value=""/><img src="imagens/spacer_1x1px.png" width="5" border="0" />CNPJ:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	               <input name="cnpj_nucleo" type="text" id="cnpj_nucleo" class="input-large" size="10" maxlength="18" style="font-size: 18px; color: #006633; font-weight: bold;" value="" onkeypress="return mascara(event,this,'##.###.###/####-##');" onblur="verificaCNPJ(this);" onkeyup="JumpField(this,'nome_operador');"/>
	               </div></td>
	            </tr>   
	            <tr>
	               <td colspan="3"><div align="left" class="style6">Nome:<img src="imagens/spacer_1x1px.png" width="20" border="0" />
	               
	               <input name="nome_operador" type="text" id="nome_operador" class="input-xlarge" size="40" maxlength="100" style="font-size: 18px; color: #006633; font-weight: bold;" value=""/><img src="imagens/spacer_1x1px.png" width="5" border="0" />CPF:<img src="imagens/spacer_1x1px.png" width="16" border="0" />
	               <input name="cpf_operador" type="text" id="cpf_operador" class="input-large" size="10" maxlength="14" style="font-size: 18px; color: #006633; font-weight: bold;" value="" onkeypress="return mascara(event,this,'###.###.###-##');" onblur="verificaCPF(this);" onkeyup="JumpField(this,'bt1');"/>
	               <input type="button" onclick="enviar('IncluiTesoureiroRegional')" id="bt1" name="bt1" value="Confirmar">
				  </div></td>
	            </tr>
	          </table>
	          <table width="760" border="1">
	               
	               <c:set var="totalnucleos" value="0"/>
	               <c:set var="totalsocios" value="0"/>
	               <c:set var="totalregiao" value="0"/> 
	               <c:set var="regiao" value="0"/> 
	               <c:set var="totalgeralsocios" value="0"/>
	               <c:set var="totalgeralnucleos" value="0"/>
	               
				   <c:if test="${not empty listaTesoureiros}">
	             	<div align="center" style="font-size: 16px; color: #000000;"><strong>Tesoureiros Regionais Cadastrados</strong></div>
					<tr bordercolor="#cccccc" bgcolor="#cccccc">
	           	       <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Editar</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Região</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Nome</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Núcleo</strong></div></td>
					   <!-- 
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Cidade</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Total Sócios</strong></div></td>
					    -->	
	                </tr>          
			       </c:if>
			       <c:forEach var="linhaslista" items="${listaTesoureiros}">
			       
	           	       <tr bgcolor="#ffffff">
	               		<td><div align="center"><a href='#' title="Edita Núcleo"><img src="imagens/edit.png" width="16" height="16" border="0" align="middle" /></a></div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;">${linhaslista.regiao.descricao}</div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;">${linhaslista.nome}</div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;">${linhaslista.nucleo.nome}</div></td>
	                  </tr>
	                  
	       		   </c:forEach>
	               <c:set var="totalregiao" value="${totalregiao+1}"/>
 				   <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
		               <td bordercolor="#ffffff"><div align="center" style="font-size: 12px; color: #000000;"><strong></strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>                  
		               <td bordercolor="#ffffff"><div align="center" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="" /></strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>                  
		               <td bordercolor="#ffffff" ><div align="center" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="" /></strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
				       <td>&nbsp;</td>          
					   <!-- 
				       <td>&nbsp;</td>                  
		               <td bordercolor="#ffffff"><div align="center" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${totalgeralsocios}" /></strong></div></td>
					    -->				               
	               </tr>	       		   

	       	  </table>
	          
	          <table width="763" frame="void">
	             <tr><td>&nbsp;</td></tr>
	          </table>
			   <table width="764">
	  				<tr>
	                	<td colspan="3">
	                    	<div align="left">
		                    	<c:if test="${objUsu.nivel > 4 }">
						        	<!-- 
						        	<input type="button" onclick="enviar('IncluiNucleo')" id="bt1" name="bt1" value="Confirmar">
						        	 -->   
								</c:if>
								<!-- 
					        	<input type="button" onclick="enviar('ConsultaNucleo')" name="bt2" value="consultar">   
					        	<input type="button" onclick="enviar('ListaNucleo')" name="bt5" value="listar"> 
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar">
								 -->
							</div>   
	                    </td>   
	  				</tr>
			  </table>
	   		</form>
		</div>    
  </div>
	<c:import url="rodape.jsp"/>
	
	<script>
  	      window.onload = function(){document.form.desc_nucleo.focus();};
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
				 if(preencheCampo(document.form.desc_nucleo,'Núcleo')&&preencheCampo(document.form.cnpj_nucleo,'CNPJ')&&preencheCampo(document.form.nome_operador,'Operador')&&preencheCampo(document.form.cpf_operador,'CPF')){
					document.getElementById("cpf_operador").value = limpaValor(document.getElementById("cpf_operador"));
		   			buscaDados(pModu);
		   			limpar();
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
		   	 document.form.cod_Nucleo.value='0';
	   	     document.form.desc_nucleo.value='';
	   	     document.form.cnpj_nucleo.value='';
	   	     document.form.CampoStatus.value='';	   	     
	   	     document.form.desc_nucleo.focus();
		  }
		  
		  	function verificaCPF(src){
		   		 
		         var i;
		         var s = limpaValor(src);

		         if(s.length < 1) return true;
		         
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
		            alert("CPF "+src.value+" inválido!");
		            src.value = '';
		            src.focus();
		            return false;
		         }else{
			        return true;
		         }
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

		</script>   
	