<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objBan" class="beans.Nucleo" scope="request" />
	  <div class="hero-unit" align="center">
		  <div id="estrutura">
	      	<table width="764" frame="void">
	      	   <tr><td colspan="17">&nbsp;</td></tr>
		       <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Inclui Núcleo</div></td></tr>
		       <tr><td colspan="17">&nbsp;</td></tr>     
		    </table>
	      
			<form name="form" id="form" method="post">
			  <table width="764" frame="void">            
	            <tr>
	              <td colspan="3"><div align="left" class="style6">Região:<img src="imagens/spacer_1x1px.png" width="24" border="0" />
	              <input name="sel_regiao" type="text" id="sel_regiao" class="input-medium" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objNucleo.regiao.descricao}" disabled="disabled"/><img src="imagens/spacer_1x1px.png" width="5" border="0" />
 	 			  <input name="regiao" id="regiao" type="hidden" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objNucleo.regiao.codigo}" title="Escolha a Região na caixa de seleção" size="3" maxlength="6" readonly />
	              </div></td>
	            </tr>   
	            <tr>
	              <td colspan="3"><div align="left" class="style6">Núcleo:<img src="imagens/spacer_1x1px.png" width="24" border="0" />
	               <input name="desc_nucleo" type="text" id="desc_nucleo" class="input-xlarge" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objNucleo.nome}" disabled="disabled"/><img src="imagens/spacer_1x1px.png" width="5" border="0" />CNPJ:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	               <input name="cnpj_nucleo" type="text" id="cnpj_nucleo" class="input-large" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objNucleo.cnpj}" disabled="disabled"/>
	               <!-- 
	               <img src="imagens/spacer_1x1px.png" width="100" border="0" />Cidade:<img src="imagens/spacer_1x1px.png" width="5" border="0" /><input name="desc_cidade" type="text" id="desc_cidade" class="input-xxlarge" size="100" maxlength="100" style="font-size: 18px; color: #006633; font-weight: bold;" value=""/>
	                -->
	               
	               </div></td>
	            </tr>   
	            <tr>
	               <td colspan="3"><div align="left" class="style6">Operador:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	               <input name="nome_operador" type="text" id="nome_operador" class="input-xlarge" size="40" maxlength="100" style="font-size: 18px; color: #006633; font-weight: bold;" value=""/><img src="imagens/spacer_1x1px.png" width="5" border="0" />CPF:<img src="imagens/spacer_1x1px.png" width="16" border="0" />
	               <input name="cpf_operador" type="text" id="cpf_operador" class="input-large" size="10" maxlength="14" style="font-size: 18px; color: #006633; font-weight: bold;" value="" onkeypress="return mascara(event,this,'###.###.###-##');" onblur="verificaCPF(this);" onkeyup="JumpField(this,'bt1');"/>
	               <input type="button" onclick="enviar('IncluiOperadorNucleo')" id="bt1" name="bt1" value="Confirmar">
				  </div></td>
	            </tr>
	          </table>

	          <table width="763" frame="void">
	             <tr><td>&nbsp;</td></tr>
	          </table>
			   <table width="764">
	  				<tr>
	                	<td colspan="3">
	                    	<div align="left"></div>   
	                    </td>   
	  				</tr>
			  </table>
	   		</form>
		</div>    
  </div>
	<c:import url="rodape.jsp"/>
	
	<script>
  	      window.onload = function(){document.form.nome_operador.focus();};
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
	