<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
       <script>
  	      window.onload = function(){document.form.desc_tipo_entrada.focus();}
  	      
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
  			campo.value=result;
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
  		  function trocaVirgula(campo){ //30/09/2009
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
		  function enviar(pModu){
			 if(pModu!='ListaTipoEntrada'){
				 if(preencheCampo(document.form.desc_tipo_entrada,'Descricao')){
			   		document.form.valor_tipo_entrada.value = trocaVirgula(document.form.valor_tipo_entrada.value);	
		   			buscaDados(pModu);
		   			limpar();
				 }
			 }else{
   			   buscaDados(pModu);
			}		  	
		  }
		  function limpar(){
		   	 document.form.cod_tipo_entrada.value='0';
	   	     document.form.desc_tipo_entrada.value='';
	   	     document.form.valor_tipo_entrada.value='';
	   	     document.form.mensal.value='';
	   	     document.form.ativo.value='';
	   	     document.form.CampoStatus.value='';
	   	     document.form.desc_tipo_entrada.focus();
		  }
		</script>   
   	  
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objTipEnt" class="beans.TipoEntrada" scope="request" />
	  <div class="hero-unit" align="center">
		   <div id="estrutura">
	        <table width="764" frame="void">
		       <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Cadastro de tipos de entrada</div></td></tr>     
	           <tr><td>&nbsp;</td></tr>
		    </table>
	      
			<form name="form" id="form" method="post">
			  <table width="764" frame="void">            
	            <tr>
	              <td><div align="left" class="style6">Descrição:
	              <input class="input-large" name="desc_tipo_entrada" type="text" id="desc_tipo_entrada" size="12" maxlength="50" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objTipEnt.descricao}"/>
	              <img src="imagens/spacer_1x1px.png" width="2" border="0" />Vlr.
	              <input class="input-small" name="valor_tipo_entrada" type="text" id="valor_tipo_entrada" size="7" maxlength="10" style="font-size: 18px; color: #006633; font-weight: bold;" value='<f:formatNumber value="${objTipEnt.valor}" minFractionDigits="2" type="currency"/>' onkeypress="return soNumero(event);" onblur="formataValor(this);" />
	              <img src="imagens/spacer_1x1px.png" width="2" border="0" />Mensal:
	              <select class="input-small" name="mensal" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("mens_tipo_entrada").value=mensal.value'>
	                 <option value="${objTipEnt.mensal=='S'?'S':'N'}">${objTipEnt.mensal=='S'?'Sim':'Não'}</option>
	                 <option value="${objTipEnt.mensal=='S'?'N':'S'}">${objTipEnt.mensal=='S'?'Não':'Sim'}</option>
	              </select>
				  <input name="mens_tipo_entrada" type="hidden" id="mens_tipo_entrada" size="1" maxlength="1" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objTipEnt.mensal}"/>
	
				  <img src="imagens/spacer_1x1px.png" width="2" border="0" />Ativo:
	              <select class="input-small" name="ativo" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("ativo_tipo_entrada").value=ativo.value'>
	                 <option value="${objTipEnt.ativo=='S'?'S':'N'}">${objTipEnt.ativo=='S'?'Sim':'Não'}</option>
	                 <option value="${objTipEnt.ativo=='S'?'N':'S'}">${objTipEnt.ativo=='S'?'Não':'Sim'}</option>
	              </select>			  
				  <input name="ativo_tipo_entrada" type="hidden" id="ativo_tipo_entrada" size="1" maxlength="1" value="${objTipEnt.ativo}"/>
	              <input name="cod_tipo_entrada" type="hidden" id="cod_tipo_entrada" value="${objTipEnt.codigo}" />
	
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
						        	<input type="button" onclick="enviar('IncluiTipoEntrada')" name="bt1" value="Incluir" />   
						        	<input type="button" onclick="enviar('AlteraTipoEntrada')" name="bt3" value="alterar" />   
						        	<input type="button" onclick="enviar('ExcluiTipoEntrada')" name="bt4" value="excluir" />   
								</c:if>
					        	<input type="button" onclick="enviar('ConsultaTipoEntrada')" name="bt2" value="consultar" />   
					        	<input type="button" onclick="enviar('ListaTipoEntrada')" name="bt5" value="listar" /> 
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar" />
							</div>   
	                    </td>   
	  				</tr>
			  </table>
	   		</form>
	</div>   
	  </div>
	<c:import url="rodape.jsp"/>