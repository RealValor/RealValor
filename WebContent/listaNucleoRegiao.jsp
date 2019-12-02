<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	  <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objBan" class="beans.Nucleo" scope="request" />
	  <div class="hero-unit" align="center">
		  <div id="estrutura">
	      	<table width="764" frame="void">
	      	   <tr><td colspan="17">&nbsp;</td></tr>
		       <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Lista Núcleo</div></td></tr>
		       <tr><td colspan="17">&nbsp;</td></tr>     
		    </table>
	      
			<form name="form" id="form" method="post">
	          <table width="760" border="1">
	               <c:set var="totalnucleos" value="0"/>
	               <c:set var="totalsocios" value="0"/>
	               <c:set var="totalregiao" value="0"/> 
	               <c:set var="regiao" value="0"/> 
	               <c:set var="totalgeralsocios" value="0"/>
	               <c:set var="totalgeralnucleos" value="0"/>
				   <c:if test="${not empty listaNucleos}">
	             	<div align="center" style="font-size: 16px; color: #000000;"><strong>Relação de Núcleos por Região</strong></div>
					<tr bordercolor="#cccccc" bgcolor="#cccccc">
	           	       <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Editar</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Região</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Nome</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Cidade</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Cnpj</strong></div></td>
			      	   <td><div align="center" style="font-size: 12px; color: #000000;"><strong>Sócios Ativos</strong></div></td>
	                </tr>          
			       </c:if>
			       <c:forEach var="linhaslista" items="${listaNucleos}">
	                  <c:if test="${linhaslista.regiao.codigo != regiao && totalnucleos>0}">
	               		   <c:set var="totalregiao" value="${totalregiao+1}"/>
		 				   <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
				               <td bordercolor="#ffffff"><div align="center" style="font-size: 12px; color: #000000;"><strong>Totais</strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
				               <td>&nbsp;</td>                  
				               <td bordercolor="#ffffff" ><div align="center" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${totalnucleos}" /></strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
				               <td>&nbsp;</td>                  
				               <td>&nbsp;</td>                  
				               <td bordercolor="#ffffff"><div align="center" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${totalsocios}" /></strong></div></td>
			               </tr>
			               <c:set var="totalsocios" value="0"/>
			               <c:set var="totalnucleos" value="0"/>
	                  </c:if>
                  	   <c:set var="regiao" value="${linhaslista.regiao.codigo}"/> 
	                  
	           	       <tr bgcolor="#ffffff">
	           	       	<!-- stu?p=AlteraNucleo&num=${linhaslista.codigo} -->
	               		<td><div align="center"><a href='IncluiNucleo' title="Edita Núcleo"><img src="imagens/edit.png" width="16" height="16" border="0" align="middle" /></a></div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;">${linhaslista.regiao.descricao}</div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;">${linhaslista.nome}</div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;">${linhaslista.cidade.nome}</div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;">${linhaslista.cnpj}</div></td>
	               		<td><div align="center" style="font-size: 12px; color: #000000;"><f:formatNumber value="${linhaslista.totalsocios}" /></div></td>
	                  </tr>
	                  
               		  <c:set var="totalsocios" value="${totalsocios+linhaslista.totalsocios}"/>
               		  <c:set var="totalgeralsocios" value="${totalgeralsocios+linhaslista.totalsocios}"/>
               		  <c:set var="totalgeralnucleos" value="${totalgeralnucleos+1}"/>
               		  <c:set var="totalnucleos" value="${totalnucleos+1}"/>
	       		   </c:forEach>
	               <c:set var="totalregiao" value="${totalregiao+1}"/>
 				   <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
		               <td bordercolor="#ffffff"><div align="center" style="font-size: 12px; color: #000000;"><strong>Totais</strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
		               <td>&nbsp;</td>                  
		               <td bordercolor="#ffffff" ><div align="center" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${totalnucleos}" /></strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
				       <td>&nbsp;</td>                  
				       <td>&nbsp;</td>                  
		               <td bordercolor="#ffffff"><div align="center" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${totalsocios}" /></strong></div></td>
	               </tr>
 				   <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
		               <td bordercolor="#ffffff"><div align="center" style="font-size: 12px; color: #000000;"><strong>Totais</strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>                  
		               <td bordercolor="#ffffff"><div align="center" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${totalregiao}" /></strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>                  
		               <td bordercolor="#ffffff" ><div align="center" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${totalgeralnucleos}" /></strong><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>
				       <td>&nbsp;</td>                  
				       <td>&nbsp;</td>                  
		               <td bordercolor="#ffffff"><div align="center" style="font-size: 12px; color: #000000;"><strong><f:formatNumber value="${totalgeralsocios}" /></strong></div></td>
	               </tr>	       		   
	       	  </table>
	          
	          <table width="763" frame="void">
	             <tr><td>&nbsp;</td></tr>
	          </table>
			   <table width="764">
	  				<tr>
	                	<td>
	                    	<div align="left">
		                    	<c:if test="${objUsu.nivel > 3 }">
						        	<input type="button" onclick="enviar('IncluiNucleoOperador')" id="bt1" name="bt1" value="Incluir Núcleo">
								</c:if>
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
		     buscaDados(pModu);
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
	