<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<script>   
   		function enviar(pModu){
			document.form.action='stu?p='+pModu;
			document.form.submit();   
		}
		</script>

	<jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	<jsp:useBean id="objNSoc" class="beans.NaoSocio" scope="session" />
	<div class="hero-unit" align="center">
		<div id="estrutura">
			<table width="764" frame="void">
			   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Listagem de n�o associados</div></td></tr>
	           <tr><td>&nbsp;</td></tr>
			</table>
			
			<form name="form" id="form" method="post">
			  <table width="762" border="1">
			  	<tr><td colspan="5"><input type="button" onclick="enviar('IncluiNaoSocio')" name="bt1" value="Voltar"></input></td></tr>
		        
		    	<tr bordercolor="#cccccc" bgcolor="#cccccc">
		    		<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Editar</strong></div></td>
		    		<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Descri��o</strong></div></td>
		    		<td><div align="center" style="font-size: 12px; color: #000000;"><strong>Telefone</strong></div></td>
		    		<td><div align="center" style="font-size: 12px; color: #000000;"><strong>CPF/CNPJ</strong></div></td>
		   	    </tr>

		          <c:forEach var="nsocio" items="${listNSoc}">
				     <c:if test="${nsocio.nome!=''}">
				     	<tr bgcolor="#ffffff">
			                <td><div align="center"><a href="stu?p=ConsultaNaoSocio&amp;cod_nsocio=${nsocio.codigo}"><img src="imagens/edit.png" width="16" height="16" border="0" align="middle" /></a></div></td>
							<td><div align="left" style="font-size: 12px; color: #000000;">${nsocio.nome}</div></td>
							<td><div align="center" style="font-size: 12px; color: #000000;">${nsocio.telefone}</div></td>
							<td><div align="center" style="font-size: 12px; color: #000000;">${ nsocio.cpfCnpj==''?'':nsocio.cpfCnpj}</div></td>
			            </tr>
					 </c:if>
		          </c:forEach>
		          <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC"><td bordercolor="#ffffff">&nbsp;</td>
				     <td bordercolor="#ffffff" colspan="5"><div align="right" style="font-size: 12px; color: #000000;"><img src="imagens/spacer_1x1px.png" width="5" border="0" /></div></td>                  
			      </tr>	       		   
		   	  </table>
		</form>  
		</div> 	
	</div>
<c:import url="rodape.jsp"/>