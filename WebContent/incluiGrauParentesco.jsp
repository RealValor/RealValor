<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<script>
  	   	  window.onload = function(){document.form.desc_grauparentesco.focus();}
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
			 if(pModu!='ListaGrauParentesco'){
				 if(preencheCampo(document.form.desc_grauparentesco,'Descricao')){
		   			buscaDados(pModu);
		   			limpar();
				 }
			 }else{
   			   buscaDados(pModu);
			}		  	
		  }
		  function limpar(){
		   	 document.form.cod_grauparentesco.value='0';
	   	     document.form.desc_grauparentesco.value='';
	   	     document.form.CampoStatus.value='';	   	     
	   	     document.form.desc_grauparentesco.focus();
		  }
		</script>   
   	  
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <jsp:useBean id="objGrauParentesco" class="beans.GrauParentesco" scope="request" />
	   <div class="hero-unit" align="center">
		    <div id="estrutura">
	      	<table width="764" frame="void">
	        	  <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Cadastro de grau parentesco</div></td></tr>
	        	  <tr><td>&nbsp;</td></tr>
		    </table>
	      
			<form name="form" id="form" method="post">
			  <table width="764" frame="void">            
	            <tr>
	              <td colspan="3"><div align="left" class="style6">Descrição:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	              <input name="desc_grauparentesco" type="text" id="desc_grauparentesco" size="20" maxlength="50" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objGrauParentesco.descricao}"/>
	               <input name="cod_grauparentesco" type="hidden" id="cod_grauparentesco" value="${objGrauParentesco.codigo}" /></div></td>
	            </tr>
	            <tr><td>&nbsp;</td></tr>
	          </table>
			   <table width="764">
	  				<tr>
	                	<td colspan="3">
	                    	<div align="left">
		                    	<c:if test="${objUsu.nivel > 2 }">
						        	<input type="button" onclick="enviar('IncluiGrauParentesco')" name="bt1" value="Incluir">   
						        	<input type="button" onclick="enviar('AlteraGrauParentesco')" name="bt3" value="alterar">   
						        	<input type="button" onclick="enviar('ExcluiGrauParentesco')" name="bt4" value="excluir">   
								</c:if>
					        	<input type="button" onclick="enviar('ConsultaGrauParentesco')" name="bt2" value="consultar">   
					        	<input type="button" onclick="enviar('ListaGrauParentesco')" name="bt5" value="listar"> 
					        	<input type="button" onclick="limpar()" name="bt6" value="limpar">
							</div>   
	                    </td>   
	  				</tr>
			  </table>
	   		</form>
		</div> 
	   </div>  
	<c:import url="rodape.jsp"/>