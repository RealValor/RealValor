<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:import url="cabecalhoindex.jsp"/>
        <script>
	        window.onload = function(){document.getElementById("cpf").focus();}
        </script>
		<jsp:useBean id="objUsu" class="beans.Login" scope="session" />
		<form id="formLogin" name="formLogin" method="post" action="stu?p=Acesso">
			<table width="763" align="center">
				<tr>
					<td><div align="center"><span class="style9">${retorno}</span></div></td>
				</tr>
			</table>
			<div class="hero-unit" align="center">
				<table width="763" align="center">
					<tr>
					<td>&nbsp;</td>
					<td width="48">
					  <select class="input-large" name="unidade" id="unidade" style="font-size: 16px; color: #006633; font-weight: bold;" title="Escolha a unidade de login" onchange='document.getElementById("tipo_unidade").value=unidade.value;' >
					    <c:forEach var="unidade" items="${operadorMultiplo}">
							<option value="${unidade.nucleo.codigo}">${unidade.nucleo.nome}</option>        			 
						</c:forEach> 
	                  </select>
 			  		  <input name="tipo_unidade" id="tipo_unidade" type="hidden" style="font-size: 18px; color: #006633; font-weight: bold;" value="${objUsu.nucleo.codigo}" size="3" maxlength="6" readonly />
					</td>
					</tr>
					
					<tr>
						<td>&nbsp;</td>
						<td><div><button name="entrar" type="submit" id="entrar" class="btn btn-large btn-warning" type="button">Entrar</button></div></td>						
					    <td>&nbsp;</td>
					</tr>
				</table>
				<iframe src="mensagemSelecionaLogin.jsp" name="mainFrame" width="600" marginwidth="0"	height="100" marginheight="0" scrolling="auto" frameborder="0"></iframe>
			</div>
	</form>
	<hr />
	<div id="estrutura">
		<table border="0" >
			<tr><td style="font-style:italic; font-size:8pt; color:#000000; font-family:Verdana, Arial, Helvetica, sans-serif">RealValor - Sistema Financeiro da UDV - &copy; 2010 / CEBUDV<img src="imagens/spacer_1x1px.png" width="150" border="0" /></td></tr>
		</table> 
	</div>
	