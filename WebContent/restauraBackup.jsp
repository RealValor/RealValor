
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<c:import url="cabecalho.jsp"/>
	<link rel="stylesheet" href="css/principal.css" type="text/css" />
		<script>
	        window.onload = function(){document.getElementById("senha").focus();}
		
  	    function AbrirAjax() {
  	   	     var xmlhttp_new;
  	   	     try {
  	   	        xmlhttp_new = new ActiveXObject("Microsoft.XMLHTTP"); //Para o Internet Explorer	
  	   	     } 
  	   	     catch(e) {
  	   	        try {  	   	      
  	   	           xmlhttp_new = new getXMLHTTP(); //IE8	
  	   	        } 
  	   	        catch(ex) {
  	   	           try {  	   	         
  	   	              xmlhttp_new = new XMLHttpRequest(); //Para todos os outros Browsers (FireFox,Opera, etc ...)
  	   	           } 
  	   	           catch(exc) {
  	   	              alert("Seu navegador não tem recursos para uso de Ajax");  	   	            
  	   	              xmlhttp_new = null;
  	   	           }
  	   	        }
  	   	     }
  	   	     return xmlhttp_new;
  	    }

  	  	var result_senha=0;
  	  	
 		function XMLHttpSenha(){ //Controla as mudanças do objeto XMLHttpRequest.
   			var resultado;
 		    if (xmlhttp.readyState == 4 && xmlhttp.status == 200){ //Verifica se o arquivo foi carregado com sucesso.
 			   var quantidade = xmlhttp.responseXML; //Armazena a resposta XML.		                
 			   resultado = quantidade.getElementsByTagName("senha"); //Captura as respostas na Tag <total>
 			   //result_senha = resultado[0].text;
 			   result_senha = resultado[0].childNodes[0].data;
 			   
			   if(confirm('Restauração do backup C:\\RVoffline\\stubackup.db' )) {
				  if(result_senha*1>0){
					  if(confirm('Este procedimento substituirá as informações atuais de forma irreversível. Confirma?' )) {
						 document.formNome.action='stu?p=RestauraDados';   
				  		 document.formNome.submit();
					  }
				  }else{
					  alert('Senha inválida!');
					  document.getElementById("senha").value='';
					  document.getElementById("senha").focus();
				  }
			   }
 	        }
 	     }
   	    
		 function confirmaRestaura(){
			  if(document.getElementById("senha").value==''){
				  alert('Necessário senha do operador!');
			  }else{

			      url = "stu?p=BuscaSenha&senha="+document.getElementById("senha").value; 
				  if (document.getElementById) {
					 xmlhttp = AbrirAjax();
					 if (xmlhttp) {
							xmlhttp.onreadystatechange = XMLHttpSenha;
							xmlhttp.open("GET", url, true); //Abre a url.
							xmlhttp.setRequestHeader('Content-Type', 'text/xml');
							xmlhttp.setRequestHeader('encoding', 'ISO-8859-1');
							xmlhttp.send(null);
					 }
				  }
				  
			  }
			  return false;
		 }
		 function limpar() {
			document.getElementById("senha").value = '';
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
		   <tr><td colspan="3"><div align="left" style="font-family: Arial, Helvetica, sans-serif; font-size: 16px; color: #666666; font-weight: bold;">Recuperação de Dados</div></td></tr>     
		   <tr><td colspan="17">&nbsp;</td></tr>	  
	    </table>
           
        <form id="formNome" name="formNome" method="post" >
        	<table width="763" frame="void">
            <tr>
				<td><div align="left" class="style6">
            	   <img src="imagens/spacer_1x1px.png" width="5" border="0" />Os dados serão recuperados de C:\backup\stubackup.db<img src="imagens/spacer_1x1px.png" width="5" border="0" />
                   <input name="restaura" id="restaura" type="hidden" style="font-size: 16px; color: #006633; font-weight: bold;" value="C:/backup/stubackup.db" title="Escolha o mes final na caixa de seleção" size="1" maxlength="1" readonly />
            	</div></td>
            </tr>
            <tr>
				<td>&nbsp;</td>
            </tr>
            <tr>
				<td><div align="left" class="style6">Senha:<img src="imagens/spacer_1x1px.png" width="2" border="0" />				
					<input name="senha" type="password" class="style3" id="senha" value='' size="10" tabindex="2" />
            	   	<input type="button" onclick="confirmaRestaura()" name="bt5" value="Confirma"/>
				</div>
				</td>
			</tr>
            
            </table>
            <table width="763" frame="void" >
			   <tr><td colspan="17">&nbsp;</td></tr>	  
            </table>
        </form>
	</div> 
	<c:import url="rodape.jsp"/>
