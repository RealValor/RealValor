<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
        <script>
        window.onload = function(){document.formLogin.cpf.focus();}
        function enviar(par){   
			if (par == 'entrar'){
     			document.formLogin.action='stu?p=Acesso';
   			}
		}
        </script>
		<jsp:useBean id="objUsu" class="beans.Login" scope="session" />
		<form id="formLogin" name="formLogin" method="post" action="stu?p=Acesso">
			<table width="763" align="center">
				<tr>
					<td><div align="center"><span class="style9">${retorno}</span></div></td>
				</tr>
			</table>
			<table width="763" align="center">
				
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td width="277">&nbsp;</td>
					<td width="48"><span class="style3">CPF:</span></td>
					<td width="111"><input name="cpf" type="text" class="style3" id="cpf" value="" tabindex="1" onkeypress="return soNumero(event);" onkeyup="JumpField(this,'senha');" size="13" maxlength="11" /></td>
					<td ><input name="flg" id="flg" type="hidden" value=""/></td>
				    <td width="307">&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><span class="style3">Senha:</span></td>
					<td><input name="senha" type="password" class="style3" id="senha" value='' size="10" tabindex="2" /></td>
				    <td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td><div><input name="entrar" type="submit" id="entrar" tabindex="3" onclick="enviar('entrar')" value="Entrar">
					</div></td>
				    <td>&nbsp;</td>
				</tr>

			</table>
	</form>
	<iframe src="mensagem.jsp" name="mainFrame" width="600" marginwidth="0"	height="100" marginheight="0" scrolling="auto" frameborder="0"></iframe>
	
	<c:import url="rodape.jsp"/>