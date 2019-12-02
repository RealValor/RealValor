
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
	  <jsp:useBean id="objMovIni" class="beans.MovAtual" scope="session" />
	  <div class="hero-unit" align="center">
	   <div id="estrutura">
        <table width="764" frame="void">
		   <tr><td colspan="17">&nbsp;</td></tr>	  
		   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Consulta Aniversariantes</div></td></tr>     
		   <tr><td colspan="17">&nbsp;</td></tr>	  
	    </table>
           
        <form id="formNome" name="formNome" method="post" >
        	<table width="763" frame="void">
	            <tr>
					<td><div align="left" class="style6">
	            	   <img src="imagens/spacer_1x1px.png" width="5" border="0" />Aniversariantes do mês de:<img src="imagens/spacer_1x1px.png" width="5" border="0" />                
	                   <select class="input-small" name="mesini" id="mesini" style="font-size: 16px; color: #006633; font-weight: bold;" onchange='document.getElementById("mes_ani").value=mesini.value'>
	                   	  <option value="${objMovIni.mes}">${objMovIni.mesExtenso}</option>
	                      <option value="1">jan</option>
	                      <option value="2">fev</option>
	                      <option value="3">mar</option>
	                      <option value="4">abr</option>
	                      <option value="5">mai</option>
	                      <option value="6">jun</option>
	                      <option value="7">jul</option>
	                      <option value="8">ago</option>
	                      <option value="9">set</option>
	                      <option value="10">out</option>
	                      <option value="11">nov</option>
	                      <option value="12">dez</option>
	                   </select>
	                   <input name="mes_ani" id="mes_ani" type="hidden" style="font-size: 16px; color: #006633; font-weight: bold;" value="${objMovIni.mes}" title="Escolha o mes final na caixa de seleção" size="1" maxlength="1" readonly/>
	                   <input name="controle" id="controle" type="hidden" value="${mesAniversario}" size="1" maxlength="2" />
	                   <input name="urlSet" id="urlSet" type="hidden" value="${urlSet}"/>
	                   <input type="button" onclick="enviar('ConsultaAniversariante')" name="bt5" value="Confirma">
	            	</div></td>
	            </tr>
	        </table>
	        <table width="763" frame="void" >
			   <tr><td colspan="17">&nbsp;</td></tr>	  
            </table>    
			<table width="763" frame="void">	            
	            <tr>
	               <td bordercolor="ffffff"><div align="left" ><img src="imagens/spacer_1x1px.png" width="5" border="0" />Emite relatório para todos os meses:<img src="imagens/spacer_1x1px.png" width="5" border="0" />
	                  <input type="checkbox" name="todos" id="todos" value="0" title="Seleciona todos os aniversariantes"></div></td>	            	
            	</tr>
            </table>
            <table width="763" frame="void" >
			   <tr><td>&nbsp;</td></tr>	  
            </table>
        </form>
	   </div> 
	  </div>
	<c:import url="rodape.jsp"/>
		<script>
  	     window.onload = function(){visualizar();}

     	 function buscaDados(pMod){
     		document.getElementById('todos').value=0;
			if(document.getElementById('todos').checked == true){
			 	document.getElementById('todos').value=1;
			}
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

			   window.open(document.getElementById('urlSet').value+'imprimeAniversariante.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');

			   //window.open('http://localhost:8080/STU/imprimeAniversariante.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
			   //window.open('http://www.udvnmg.org/imprimeAniversariante.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
		    }
		 }
	  </script>
	