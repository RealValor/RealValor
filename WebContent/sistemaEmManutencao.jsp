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
						<td width="48">
					 	<td><div align="center"><span class="style1">O sistema está em manutenção</span></div></td>
					 	<td>&nbsp;</td>
					</tr>
					<tr>
						<td width="48">
					</tr>
					<tr>
						<td width="48">
					 	<td><div align="center"><span class="style1">e será liberado em 30 minutos</span></div></td>
					 	<td>&nbsp;</td>
					</tr>
					<tr>
						<td width="48">
					 	<td>&nbsp;</td>
					</tr>
					<tr>
						<td width="48">
					 	<td><div align="center"><span class="style1">Grato pela compreensão!</span></div></td>
					</tr>
				</table>
			</div>
	</form>
	<hr />
	<div id="estrutura">
		<table border="0" >
			<tr><td style="font-style:italic; font-size:8pt; color:#000000; font-family:Verdana, Arial, Helvetica, sans-serif">RealValor - Sistema Financeiro da UDV - &copy; 2010 / CEBUDV<img src="imagens/spacer_1x1px.png" width="150" border="0" /></td></tr>
		</table> 
	</div>
	