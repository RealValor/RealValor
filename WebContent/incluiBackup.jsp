
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
		<script>
  	    //window.onload = function(){visualizar();}
		  function confirmaBackup(){
			  if(confirm('Grava backup em C:\\RVoffline\\stubackup.db' )) {
				  document.formNome.action='stu?p=CopiaDeSeguranca';   
	  			  document.formNome.submit();
			  }
			  return false;
		  }
	  </script>
	  
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
	     <div id="estrutura">
        <table width="764" frame="void">
		   <tr><td colspan="17">&nbsp;</td></tr>	  
		   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Cópia de Segurança</div></td></tr>     
		   <tr><td colspan="17">&nbsp;</td></tr>	  
	    </table>
           
        <form id="formNome" name="formNome" method="post" >
        	<table width="763" frame="void">
            <tr>
				<td><div align="left" class="style6">
            	   <img src="imagens/spacer_1x1px.png" width="5" border="0" />Os dados serão gravados em C:\backup\stubackup.db<img src="imagens/spacer_1x1px.png" width="5" border="0" />
                   <input name="backup" id="backup" type="hidden" style="font-size: 16px; color: #006633; font-weight: bold;" value="C:/backup/stubackup.db" title="Escolha o mes final na caixa de seleção" size="1" maxlength="1" readonly />
            	   <input type="button" onclick="confirmaBackup()" name="bt5" value="Confirma">
            	</div></td>
            </tr>
            </table>
            <table width="763" frame="void" >
			   <tr><td colspan="17">&nbsp;</td></tr>	  
            </table>
        </form>
</div> 
	<c:import url="rodape.jsp"/>
