
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<jsp:useBean id="objUsu" class="beans.Login" scope="session" />

	<div id="estrutura"> 
	 	<img src="imagens/Nucleo.png" width="760" height="416" />
	 	<!-- 
		<div align="center" style="font-size:22pt; background-color:#279548; color:#ffffff; font-family:LiberationSerif-font">Núcleo ${objNucleo.nome}</div>
	 	 -->
		<div align="center" style="font-size:1pt; background-color:#279548; color:#ffffff; font-family:Verdana, Arial, Helvetica, sans-serif">.</div>
		<!--
		${objNucleo.regiao.descricao} 
		 -->
		<div align="center" style="font-size:22pt; background-color:#279548; color:#ffffff; font-family:LiberationSerif-font">Tesouraria Geral</div>
		<div align="center" style="font-size:1pt; background-color:#279548; color:#ffffff; font-family:Verdana, Arial, Helvetica, sans-serif">.</div>
		<div align="center" style="font-size:1pt; background-color:#279548; color:#ffffff; font-family:Verdana, Arial, Helvetica, sans-serif">.</div>
		<div align="right" style="font-weight:bold; font-size:7pt; color:#000000; font-family:Verdana, Arial, Helvetica, sans-serif">${retorno}</div>
	</div> 
  	<hr />

	<div id="estrutura"> 
		<c:if test="${empty objUsu.cargo}">
			<div align="left" style="font-style:italic; font-size:8pt; color:#000000; font-family:Verdana, Arial, Helvetica, sans-serif">
				Operador: ${objUsu.nome} - &copy; 2010 / CEBUDV<img src="imagens/spacer_1x1px.png" width="200" border="0" />
			</div>
		</c:if>
	
		<c:if test="${not empty objUsu.cargo}">
			<div align="left" style="font-style:italic; font-size:8pt; color:#000000; font-family:Verdana, Arial, Helvetica, sans-serif">
				Operador: ${objUsu.nome} - Cargo: ${objUsu.cargo} - &copy; 2010 / CEBUDV<img src="imagens/spacer_1x1px.png" width="200" border="0" />
			</div>
		</c:if>
	</div>
