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
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td width="220">&nbsp;</td>
						<td width="48"><span class="style3">CPF:</span></td>
						<td><input name="cpf" type="text" id="cpf" tabindex="1" onkeypress="return soNumero(event);" onkeyup="JumpField(this,'senha');" size="13" maxlength="11" /></td>
						<td><input name="flg" id="flg" type="hidden" value=""/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><span class="style3">Senha:</span></td>
						<td><input name="senha" type="password" class="style3" id="senha" value="" size="10" tabindex="2"/>
						<input name="tipo_unidade" id="tipo_unidade" type="hidden" value="0" readonly />
						</td>
					    <td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td><div><button name="entrar" type="submit" id="entrar" class="btn btn-large btn-warning" type="button">Entrar</button></div></td>						
					    <td>&nbsp;</td>
					</tr>
				</table>
				<iframe src="mensagem.jsp" name="mainFrame" width="600" marginwidth="0"	height="100" marginheight="0" scrolling="auto" frameborder="0"></iframe>
			</div>
	</form>
	<hr />
	<div id="estrutura">
		<table border="0" >
			<tr><td style="font-style:italic; font-size:8pt; color:#000000; font-family:Verdana, Arial, Helvetica, sans-serif">RealValor - Sistema Financeiro UDV - &copy; 2010 / CEBUDV<img src="imagens/spacer_1x1px.png" width="150" border="0" /></td></tr>
		</table> 
	</div>
	