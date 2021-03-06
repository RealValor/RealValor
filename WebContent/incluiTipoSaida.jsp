<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
       <script>
  	      window.onload = function(){document.form.desc_tipo_saida.focus();}
		  function buscaDados(pMod){
		     document.form.action='stu?p='+pMod;   
  			 document.form.submit();
		  }
     	  function preencheCampo(pCampo,pDesc){
    		   if(pCampo.value == ''){
    			  alert('Necess�rio Campo '+pDesc);
    			  pCampo.focus();
    		      return false;
    		   }
    		   return true;   
    	  }
		  function enviar(pModu){
			 if(pModu!='ListaTipoSaida'){
				 if(preencheCampo(document.form.desc_tipo_saida,'Descricao')){
		   			buscaDados(pModu);
		   			limpar();
				 }
			 }else{
   			   buscaDados(pModu);
			}		  	
		  }
		  function limpar(){
		   	 document.form.cod_tipo_saida.value='0';
	   	     document.form.desc_tipo_saida.value='';
	   	     document.form.CampoStatus.value='';
	   	     document.form.desc_tipo_saida.focus();
		  }
		</script>   
   	  
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objTipSai" class="beans.TipoSaida" scope="request" />
	  <div class="hero-unit" align="center">
		  <div id="estrutura">
	      	<table width="764" frame="void">
		       <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Cadastro de tipos de saida</div></td></tr>
		       <tr><td>&nbsp;</td></tr>     
		    </table>
			<form name="form" id="form" method="post">
			  <table width="764" frame="void">            
	            <tr>
	              <td colspan="3"><div align="left" class="style6">Descri��o:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	              <input class="input-xlarge" name="desc_tipo_saida" type="text" id="desc_tipo_saida" size="60" maxlength="50" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objTipSai.descricao}"/>
	              
	              <img src="imagens/spacer_1x1px.png" width="5" border="0" />Ativo:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	              <select class="input-small" name="ativo" style="font-size: 18px; color: #006633; font-weight: bold;" onchange='document.getElementById("ativo_tipo_saida").value=ativo.value'>
	                 <option value="${objTipSai.ativo=='S'?'S':'N'}">${objTipSai.ativo=='S'?'Sim':'N�o'}</option>
	                 <option value="${objTipSai.ativo=='S'?'N':'S'}">${objTipSai.ativo=='S'?'N�o':'Sim'}</option>
	              </select>			  
	              <input name="ativo_tipo_saida" type="hidden" id="ativo_tipo_saida" size="60" maxlength="50" value="${objTipSai.ativo}"/>              
	              <input name="cod_tipo_saida" type="hidden" id="cod_tipo_saida" value="${objTipSai.codigo}" /></div></td>
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
						        	<input type="button" onclick="enviar('IncluiTipoSaida')" name="bt1" value="Incluir" />   
						        	<input type="button" onclick="enviar('AlteraTipoSaida')" name="bt3" value="alterar" />   
						        	<input type="button" onclick="enviar('ExcluiTipoSaida')" name="bt4" value="excluir" />   
								</c:if>
					        	<input type="button" onclick="enviar('ConsultaTipoSaida')" name="bt2" value="consultar" />   
					        	<input type="button" onclick="enviar('ListaTipoSaida')" name="bt5" value="listar" /> 
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar" />
							</div>   
	                    </td>   
	  				</tr>
			  </table>
	   		</form>
	</div> 
	  </div>
	<c:import url="rodape.jsp"/>