
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
	 .style4 {color: #006600; font-weight: bold;}
	 .style9 {font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #006633; font-weight: bold;"}
	 .style10 {font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #FFCC00}
	 .style13 {
		font-size: 12px;
		font-weight: bold;
 }
 .style14 {font-size: 10px}
 -->
      </style>   
	     
	   <div id="estrutura">
	   
        <table width="764" frame="void">
		   <tr><td>&nbsp;</td></tr>	  
		   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Confirma��o de Pagamento</div></td></tr>     
		   <tr><td>&nbsp;</td></tr>	  
	    </table>
           <!-- color: #006633; -->
        <form id="formNome" name="formNome" method="post" >
        	<table width="763" frame="void">
	           <tr>
				<td><div align="left" class="style9">Seu pagamento foi conclu�do com sucesso!</div></td>
	           </tr>
	           <tr><td>&nbsp;</td></tr>
	           <tr>
				<td><div align="left" class="style9">Codigo de controle:</div></td> 
	           </tr>
	           <tr>
				<td><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">${param.notificationCode}</div></td>
				<td><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">${param.notificationType}</div></td>
	           </tr>
	        </table>
	        <table width="763" frame="void" >
			   <tr><td>&nbsp;</td></tr>	  
            </table>    
        </form>
	   </div> 
	<c:import url="rodape.jsp"/>
	