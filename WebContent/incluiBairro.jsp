<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>    
       <script>
  	      window.onload = function(){document.form.desc_bairro.focus();}
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
			 if(pModu!='ListaBairro'){
				 if(preencheCampo(document.form.desc_bairro,'nome')){
		   			buscaDados(pModu);
		   			limpar();
				 }
			 }else{
   			   buscaDados(pModu);
			}		  	
		  }
		  function limpar(){
		   	 document.form.cod_bairro.value='0';
	   	     document.form.desc_bairro.value='';
	   	     document.form.CampoStatus.value='';	   	     
	   	     document.form.desc_bairro.focus();
		  }
		</script>   
   	  
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objBairro" class="beans.Bairro" scope="request" />
	  <div class="hero-unit" align="center">
		  <div id="estrutura">
	      	<table width="764" frame="void">
	        	  <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Cadastro de Bairro</div></td></tr>
	        	  <tr><td>&nbsp;</td></tr>
		    </table>
	      
			<form name="form" id="form" method="post">
			  <table width="764" frame="void">            
	            <tr>
	              <td colspan="3"><div align="left" class="style6">Nome:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	              <input class="input-xxlarge" name="desc_bairro" type="text" id="desc_bairro" size="20" maxlength="50" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objBairro.nome}"/>
	              <input name="cod_bairro" type="hidden" id="cod_bairro" value="${objBairro.codigo}" /></div></td>
	            </tr>
	            <tr><td>&nbsp;</td></tr>
	          </table>
			   <table width="764">
	  				<tr>
	                	<td colspan="3">
	                    	<div align="left">
		                    	<c:if test="${objUsu.nivel > 2 }">
						        	<input type="button" onclick="enviar('IncluiBairro')" name="bt1" value="Incluir">   
						        	<input type="button" onclick="enviar('AlteraBairro')" name="bt3" value="alterar">
						        	<!-- 
						        	<input type="button" onclick="enviar('ExcluiBairro')" name="bt4" value="excluir">   
						        	 -->   
								</c:if>
					        	<input type="button" onclick="enviar('ConsultaBairro')" name="bt2" value="consultar">   
					        	<input type="button" onclick="enviar('ListaBairro')" name="bt5" value="listar"> 
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar">
							</div>   
	                    </td>   
	  				</tr>
			  </table>
	   		</form>
		</div> 
	  </div>   
	<c:import url="rodape.jsp"/>