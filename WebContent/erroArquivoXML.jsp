
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
 <style type="text/css">
 <!--
  body,td,th {
	font-size: 14px;
  }
  .style1 {
	color: #FFCC00;
	font-weight: bold;
 }
 .style4 {color: #006600; font-weight: bold; }
 .style9 {color: #000000}
 .style10 {color: #FFCC00}
 .style13 {
	font-size: 12px;
	font-weight: bold;
 }
 .style14 {font-size: 10px}
 -->
      </style>   
	  <jsp:useBean id="objUsu" class="beans.Login" scope="session" />
	  <div class="hero-unit" align="center">
		   <div id="estrutura">
	        <table width="764" frame="void">
			   <tr><td colspan="17">&nbsp;</td></tr>	  
			   <tr><td colspan="3"><div align="center" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;"><img src="imagens/fechar.png" width="22" height="22" border="0" align="middle" /><img src="imagens/spacer_1x1px.png" width="5" border="0" />ERRO!</div></td></tr>     
			   <tr><td colspan="17">&nbsp;</td></tr>	  
		    </table>
	           
	        <form id="formNome" name="formNome" method="post" >
	        	<table width="763" frame="void">
	        	<tr><td colspan="17">&nbsp;</td></tr>
		            <tr>
						<td><div align="left" class="style6" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #000000; font-weight: bold;">
		            	   <img src="imagens/spacer_1x1px.png" width="5" border="0" />Erro de acesso aos dados gerados Off-line!<img src="imagens/spacer_1x1px.png" width="5" border="0" />
		            	</div></td>
		            </tr>
		            <tr><td colspan="17">&nbsp;</td></tr>
	            </table>
	            <table width="763" frame="void" >
				   <tr><td colspan="17">&nbsp;</td></tr>	  
	            </table>
	        </form>
		</div>   
	  </div>
	<c:import url="rodape.jsp"/>
