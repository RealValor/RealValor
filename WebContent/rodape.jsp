<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
 <style type="text/css">
	.style11 {
	  font-family: Arial, Helvetica, sans-serif;
	  font-size: 16px;
	  font-weight: bold;
	  color: #CC3300; 
   }
 </style>

	<body>
		<form name="form2" id="form2" method="post" action="stu?p=Acesso">
			<div align="center" style="font-size:12pt; color:#DF0101; font-family:Verdana, Arial, Helvetica, sans-serif">${retorno}</div>
			<div align="right">
			</div>
  			<hr />
			<div id="estrutura">
	            <a href="stu?p=Acesso&tipo_unidade=${objUsu.nucleo.codigo}"><img src="imagens/coroa.png" border="0" align="right" title="Menu Inicial" alt="Menu Inicial"></a> 
				<c:if test="${empty objUsu.cargo}">
					<div align="left" style="font-style:italic; font-size:8pt; color:#000000; font-family:Verdana, Arial, Helvetica, sans-serif">
						Operador: ${objUsu.nome} - ${objNucleo.nome} - &copy; 2010 / CEBUDV
					</div>
				</c:if>
				<c:if test="${not empty objUsu.cargo}">
					<div align="left" style="font-style:italic; font-size:8pt; color:#000000; font-family:Verdana, Arial, Helvetica, sans-serif">
						Operador: ${objUsu.nome} - Cargo: ${objUsu.cargo} - ${objNucleo.nome} - &copy; 2010 / CEBUDV
					</div>
				</c:if>
			</div>
  		</form>
	</body>
</html>
