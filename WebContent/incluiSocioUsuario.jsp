<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
   	  
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objSoc" class="beans.Socio" scope="session" />
	  <jsp:useBean id="data" class="java.util.Date"/>
	  <div class="hero-unit" align="center">
		  <div id="estrutura">
	        <table width="764" frame="void">
		       <tr><td>&nbsp;</td></tr>
		       <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Cadastro de associado</div></td></tr>
		       <tr><td>&nbsp;</td></tr>
		    </table>
			<form name="form" id="form" method="post">
			  <table width="764" frame="void">
	            <tr>
	         	  <td colspan="4"><div align="left" class="style6">
				  <input type="text" name="consulta" id="consulta" size="50" maxlength="100" style="font-size:18px; color:#006633; font-weight:bold" value="${objSoc.nome}" />			  
	         	  <input name="cod_socio" id="cod_socio" type="hidden" value="${objSoc.codigo}"/></div></td>
	              <td><input name="cod_sc" id="cod_sc" type="hidden" value="${objSoc.codigo}" /></td>
	            </tr>
	            <tr><td>&nbsp;</td></tr>
	            <tr>
	              <td colspan="4"><div align="left" class="style6">Telefone(s):<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                    <input name="telefone_socio" type="text" id="telefone_socio" size="20" maxlength="50" style="font-size: 17px; color: #006633; font-weight: bold;" value="${objSoc.telefone}"/>
	             		<img src="imagens/spacer_1x1px.png" width="3" border="0" />E-mail(s):<img src="imagens/spacer_1x1px.png" width="3" border="0" />
	                    <input name="email_socio" type="text" id="email_socio" size="45" maxlength="100" style="font-size: 17px; color: #006633; font-weight: bold;" value="${objSoc.email}"/>
		          </div></td>
	  		    </tr>
	            <tr>
					<td colspan="4"><div align="left" class="style6">Data Nascimento:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	                    <input name="data_nasc" type="text" id="data_nasc" size="10" maxlength="10" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatDate value="${objSoc.dataNasc}"/>' onkeypress="return mascara(event,this,'##/##/####');" onkeyup="JumpField(this,'data_assoc');"/>                     
	     	            <img src="imagens/spacer_1x1px.png" width="10" border="0" />Associa&ccedil;&atilde;o:<img src="imagens/spacer_1x1px.png" alt="" width="5" border="0" />
	                <input name="data_assoc" type="text" id="data_assoc" size="10" maxlength="10" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatDate value="${objSoc.dataAsso}"/>' onkeypress="return mascara(event,this,'##/##/####');" onkeyup="JumpField(this,'cpf_socio');"/>
					<img src="imagens/spacer_1x1px.png" width="5" border="0" />CPF:<img src="imagens/spacer_1x1px.png" width="2" border="0" />
	                    <input name="cpf_socio" type="text" id="cpf_socio" size="18" maxlength="14" style="font-size: 17px; color: #006633; font-weight: bold;" value="${objSoc.cpfStr}" onkeypress="return mascara(event,this,'###.###.###-##');" onblur="verificaCPF(this);" onkeyup="JumpField(this,'sexo');"/>
					</div></td>
				</tr>
	            <tr>
					<td colspan="4"><div align="left" class="style6">Sexo:<img src="imagens/spacer_1x1px.png" width="5" border="0" />					
	                      <select name="sexo" style="font-size: 18px; color: #006633; font-weight: bold;" onchange='document.getElementById("sexo_socio").value=sexo.value'>
	                        <c:if test="${empty objSoc.sexo}">
					             <option value="${objSoc.sexo}"></option>
							 </c:if>
	                        <option value="${objSoc.sexo=='F'?'F':'M'}">${objSoc.sexo=='F'?'Feminino':'Masculino'}</option>
	                        <option value="${objSoc.sexo=='F'?'M':'F'}">${objSoc.sexo=='F'?'Masculino':'Feminino'}</option>
	                        <!-- option mostra sexo atual e, ^ permite alteração -->
	                      </select>
	                	<input name="sexo_socio" type="hidden" id="sexo_socio" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objSoc.sexo}" title="Escolha o sexo na caixa de seleção" size="1" maxlength="1" onfocus='javascript:document.form.data_nasc.focus();' readonly/>
		                <img src="imagens/spacer_1x1px.png" width="1" border="0" />Senha:<img src="imagens/spacer_1x1px.png" width="1" border="0" />	                
						<input name="senha_socio" type="password" id="senha_socio" size="10" maxlength="50" style="font-size: 17px; color: #006633; font-weight: bold;" value=""/>
	      			</div></td>
				</tr>
		          <tr><td>&nbsp;</td></tr>
	          </table>
	          <c:if test="${not empty listDependente}">
			      <table width="650" frame="void">
				      <tr>
			           	 <td colspan="3"><div align="center" class="style6">Dependentes para composição do recibo</div></td>
				      </tr>
				   	  <tr bordercolor="#cccccc" bgcolor="#cccccc">
		      	      	<td><div align="center" style="font-size: 12px; color: #000000;"><img src="imagens/spacer_1x1px.png" width="20" border="0" /><strong>Nome</strong><img src="imagens/spacer_1x1px.png" width="20" border="0" /></div></td> 
		      	      	<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Grau</strong></div></td> 
	               		<td>&nbsp;</td>					
					  </tr>
				  </table>
	          </c:if>
	          <table width="650" border="1">
			      <c:forEach var="linhasdependente" items="${listDependente}">
	           	      <tr bgcolor="#ffffff">
	               		<td width="415"><div style="font-size:18px; color:#006633; font-weight:bold">${linhasdependente.dependente.nome}</div></td> 
	               		<td width="160"><div style="font-size:18px; color:#006633; font-weight:bold"><img src="imagens/spacer_1x1px.png" width="20" border="0" />${linhasdependente.grauparentesco.descricao}</div></td>
	               		<c:if test="${objUsu.nivel < 3 }">
		           	        <td width="25"><div align="center"><a><img src="imagens/excluir_cinza.png" width="16" height="16" border="0" align="middle" /></a></div></td>
						</c:if>
	           	      </tr>
	       		  </c:forEach>
           	      <tr>
           	      	<td>&nbsp;</td>					
           	      </tr>
	          </table>
	          <!-- aqui -->
			  <table width="764">
	  				<tr>
	                	<td colspan="3">
	                    	<div align="left">
					        	<input type="button" onclick="enviar('AlteraSocio')" name="bt3" value="alterar">   
							</div>   
	                    </td>   
					</tr>
			  </table>
	   		</form>
	</div> 
	  </div>
	<c:import url="rodape.jsp"/>
	
	<script>
  	      window.onload = function(){document.form.consulta.focus();}
  	      if (window.attachEvent) window.attachEvent("onload", navHover);
  	      
  	      function mascara(e,src,mask){
  	  	       if(window.event){
  	  		      _TXT = e.keyCode;
  	  	       }else if(e.which){
  	  		      _TXT = e.which;
  	  	       }
	  	  	   var i = src.value.length;
	 	  	   if(i==10){
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
     	    
     	    function preencheCampo(pCampo,pDesc){
     		   if(pCampo.value == ''){
     			  alert('Necessário Campo '+pDesc);
     			  pCampo.focus();
     		      return false;
     		   }
     		   return true;   
     	    }

        	function verificaCPF(src){
         		 
    	         var i;
    	         var s = limpaValor(src); //document.form.cpf_socio

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
         	    
     	    function buscaDados(pMod){
		       document.form.action='stu?p='+pMod;   
  			   document.form.submit();
		    }
		    
		    function enviar(pModu){

		    	if(!preencheCampo(document.form.consulta,'Nome')){
			   		return false;
			   }else{
					if(document.form.cpf_socio.value!=""&&!preencheCampo(document.form.senha_socio,'Senha')){
					   return false;
					}
			   }

			   document.form.cpf_socio.value=limpaValor(document.form.cpf_socio);
	   		   buscaDados(pModu);
		    }
		    
			function limpar(){
			   document.form.consulta.value=''; 
			   buscaDados('IncluiSocio');
	   	       document.form.CampoStatus.value='';
	   	       document.form.sexo.value='';	   	     
	   	       document.form.consulta.focus();
		    }
		</script>   
	