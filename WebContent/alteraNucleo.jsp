<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
       <script>
  	      window.onload = function(){document.form.desc_nucleo.focus();}
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
			 if(preencheCampo(document.form.desc_nucleo,'Descricao')){
	   			buscaDados(pModu);
	   			limpar();
			 }
	 		 buscaDados(pModu);
		  }

		  function limpar(){
		  }
		</script>   
   	  
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objNucleo" class="beans.Nucleo" scope="request" />
	  <div class="hero-unit" align="center">
		  <div id="estrutura">
	      	<table width="764" frame="void">
				  <tr><td>&nbsp;</td></tr>
	        	  <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Cadastro de Núcleo</div></td></tr>
	        	  <tr><td>&nbsp;</td></tr>
		    </table>
	      
			<form name="form" id="form" method="post">
			  <table width="764" frame="void">            
	            <tr>
	              <td colspan="3"><div align="left" class="style6">Nome:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	              <input class="input-xxlarge" name="desc_nucleo" type="text" id="desc_nucleo" size="20" maxlength="50" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objNucleo.descricao}"/>
	              
	              Cnpj:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	              <input class="input-xxlarge" name="cnpj_nucleo" type="text" id="cnpj_nucleo" size="20" maxlength="50" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objNucleo.descricao}"/>
	              
	              <input name="cod_nucleo" type="hidden" id="cod_nucleo" value="${objNucleo.codigo}" /></div></td>
	            </tr>
	            <tr><td>&nbsp;</td></tr>
	          </table>
			   <table width="764">
	  				<tr>
	                	<td colspan="3">
	                    	<div align="left">
		                    	<c:if test="${objUsu.nivel > 2 }">
						        	<input type="button" onclick="enviar('Incluinucleo')" name="bt1" value="Incluir">   
						        	<input type="button" onclick="enviar('Alteranucleo')" name="bt3" value="alterar">   
						        	<input type="button" onclick="enviar('Excluinucleo')" name="bt4" value="excluir">   
								</c:if>
					        	<input type="button" onclick="enviar('Consultanucleo')" name="bt2" value="consultar">   
					        	<input type="button" onclick="enviar('Listanucleo')" name="bt5" value="listar"> 
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar">
							</div>   
	                    </td>   
	  				</tr>
			  </table>
	   		</form>
		</div> 
	  </div>   
	<c:import url="rodape.jsp"/>