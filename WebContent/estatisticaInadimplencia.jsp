<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <c:import url="cabecalhoprincipal${objUsu.nivel}.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
		<script>
  	    window.onload = function(){document.formNome.tot_meses.focus();visualizar();}

     	 function buscaDados(pMod){
		    document.formNome.action='stu?p='+pMod;   
  			document.formNome.submit();
		 }

		 function enviar(pModu){
			buscaDados(pModu);
		 }
		 function visualizar(){
			var width = 900;
			var height = 500;

			var left = 99;
			var top = 99;
			var now=new Date();
			var seconds = now.getSeconds();
		    if(document.formNome.controle.value+1>1){
			   window.open(document.getElementById('urlSet').value+'imprimeInadimplencia.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
		    }
		 }

     	 function limpar(){
		    document.formNome.ano_paga.value='';	
		    document.formNome.tot_meses.value='';
		    
		    enviar('EstatisticaInadimplencia');
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
	  <jsp:useBean id="objPaga" class="beans.Login" scope="request" />
	  <jsp:useBean id="objMovIni" class="beans.MovAtual" scope="session" />
	    <div id="estrutura">

        <table width="764" frame="void">
		   <tr><td colspan="17">&nbsp;</td></tr>	  
		   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Consulta Inadimplentes</div></td></tr>     
		   <tr><td colspan="17">&nbsp;</td></tr>	  
	    </table>
           
        <form id="formNome" name="formNome" method="post" >
        	<table width="763" frame="void">
            <tr>
				<td><div align="left" class="style6">
            		Ano:<img src="imagens/spacer_1x1px.png" width="5" border="0" />                
                   <select name="ano" id="ano" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("ano_paga").value=ano.value'>
                      <option value="${objMovIni.ano}">${objMovIni.ano}</option>
                      <option value="${objMovIni.ano-1}">${objMovIni.ano-1}</option>
                      <option value="${objMovIni.ano-2}">${objMovIni.ano-2}</option>                     
                   </select>
                   <input name="ano_paga" id="ano_paga" type="hidden" style="font-size: 16px; color: #006633; font-weight: bold;" value="${objMovIni.ano}" title="Escolha o ano na caixa de seleção" size="1" maxlength="1" readonly/>

				   <img src="imagens/spacer_1x1px.png" width="5" border="0" />Atraso igual ou maior a:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
				   <input name="tot_meses" id="tot_meses" type="text" style="font-size: 16px; color: #006633; font-weight: bold;" value="3" size="1" maxlength="2" />
				   <img src="imagens/spacer_1x1px.png" width="5" border="0" />mes(es)<img src="imagens/spacer_1x1px.png" width="5" border="0" />
				   <input name="controle" id="controle" type="hidden" value="${totMeses}" size="1" maxlength="2" />
				   <input name="urlSet" id="urlSet" type="hidden" value="${urlSet}"/>
                   <input type="button" onclick="enviar('ConsultaInadimplente')" name="bt5" value="Confirma">
            	</div></td>
            </tr>
            </table>
            <table width="763" frame="void" >
			   <tr><td colspan="17">&nbsp;</td></tr>	  
            </table>
        </form>
		<form name="form" id="form" method="post" >
	  	  <table width="764" frame="void">
			<tr>
				<td>
			  	   <input type="button" onclick="limpar()" name="bt3" value="limpar">
				</td>
			</tr>
   		</form>
</div>  
	<c:import url="rodape.jsp"/>
