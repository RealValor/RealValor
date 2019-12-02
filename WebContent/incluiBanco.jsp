<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
       <script>
  	      window.onload = function(){document.form.desc_banco.focus();}
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
			 if(pModu!='ListaBanco'){
				 if(preencheCampo(document.form.desc_banco,'Descricao')){
		   			buscaDados(pModu);
		   			limpar();
				 }
			 }else{
   			   buscaDados(pModu);
			}		  	
		  }
		  function limpar(){
		   	 document.form.cod_banco.value='0';
	   	     document.form.desc_banco.value='';
	   	     document.form.sigla_banco.value='';
	   	     document.form.CampoStatus.value='';	   	     
	   	     document.form.desc_banco.focus();
		  }
		</script>   
   	  
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objBan" class="beans.Banco" scope="request" />
	  <div class="hero-unit" align="center">
		  <div id="estrutura">
	      	<table width="764" frame="void">
		       <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Cadastro de banco</div></td></tr>
		       <tr><td colspan="17">&nbsp;</td></tr>     
		    </table>
	      
			<form name="form" id="form" method="post">
			  <table width="764" frame="void">            
	            <tr>
	              <td colspan="3"><div align="left" class="style6">Descrição<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	              <input class="input-xlarge" name="desc_banco" type="text" id="desc_banco" maxlength="100" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objBan.descricao}"/><img src="imagens/spacer_1x1px.png" width="10" border="0" />Sigla<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	               <input name="sigla_banco" type="text" id="sigla_banco" class="input-small" size="10" maxlength="20" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objBan.sigla}"/>
	               <input name="cod_banco" type="hidden" id="cod_banco" value="${objBan.codigo}" /></div></td>
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
						        	<input type="button" onclick="enviar('IncluiBanco')" name="bt1" value="Incluir">   
						        	<input type="button" onclick="enviar('AlteraBanco')" name="bt3" value="alterar">   
						        	<input type="button" onclick="enviar('ExcluiBanco')" name="bt4" value="excluir">   
								</c:if>
					        	<input type="button" onclick="enviar('ConsultaBanco')" name="bt2" value="consultar">   
					        	<input type="button" onclick="enviar('ListaBanco')" name="bt5" value="listar"> 
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar">
							</div>   
	                    </td>   
	  				</tr>
			  </table>
	   		</form>
		</div>    
  </div>
	<c:import url="rodape.jsp"/>