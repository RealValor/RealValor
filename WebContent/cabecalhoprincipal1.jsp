<%@ page contentType="text/html; charset=ISO-8859-1" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> <!-- charset=utf-8 --> 
	   <title>Real Valor - Sistema Financeiro UDV</title>
	   <!-- Bootstrap -->
       <link href="css/bootstrap.min.css" rel="stylesheet"/>
	   <link rel="stylesheet" href="css/principal.css" type="text/css" />
	   <script src="scripts/funcoes.js" type="text/javascript" language="javascript" > </script>   
	</head>
	<body>
		<script src="js/bootstrap.min.js"></script>
		<img src="imagens/LogoImagem.png" width=100% height="119" />
		<div>&nbsp;</div>
		<div id="estrutura">
			<ul id="navmenu">
			  <li><a href="stu?p=IncluiRecebimento&ctrl=limpar" title="Pagamento de Mensalidades e Doações" ><img src="imagens/spacer_1x1px.png" width="7" border="0" />Pagamento<img src="imagens/spacer_1x1px.png" width="6" border="0" /></a></li>
			  <li><a><img src="imagens/spacer_1x1px.png" width="18" border="0" />Alteração<img src="imagens/spacer_1x1px.png" width="18" border="0" /></a>
			    <ul>
			      <li><a>Cadastro<img src="imagens/spacer_1x1px.png" width="72" border="0" />+</a>
			        <ul>
			         <li><a href="stu?p=IncluiSocio" title="Manutenção das Fichas de Associados">Associado</a></li>
			        </ul>
			      </li>
			   </ul>
			   </li>
			  <li><a><img src="imagens/spacer_1x1px.png" width="10" border="0" />Consulta<img src="imagens/spacer_1x1px.png" width="10" border="0" /></a>
			    <ul>
			       <li><a>Associados<img src="imagens/spacer_1x1px.png" width="56" border="0" />+</a>
			        <ul>          
			             <li><a href="stu?p=ConsultaAniversariante" title="Impressão da Lista de Aniversariantes">Aniversariantes</a></li>
			             <li><a href="stu?p=ConsultaListaSocio&tipo=ativo" title="Impressão da Lista de Associados Ativos">Associados Ativos</a></li>
			             <li><a href="stu?p=ConsultaListaSocio&tipo=todos" title="Relação de Todos os Associados do Núcleo">Todos</a></li> <!-- stu?p=ConsultaListaSocio& -->
			          </ul>
			       </li>
			    	<li><a>Balancete<img src="imagens/spacer_1x1px.png" width="64" border="0" />+</a>
			           <ul>
			              <li><a href="stu?p=ConsultaBalancete&tipo=finalizado" title="Impressão do Balancete Mensal Finalizado">Finalizado</a></li>
			           </ul>
			       </li>      
			       <li><a>Movimento Mensal<img src="imagens/spacer_1x1px.png" width="5" border="0" />+</a>
			          <ul>
			             <li><a href="stu?p=ConsultaMovimentoSaida" title="Relatório Mensal de Saidas">Saida</a></li>
			          </ul>
			       </li>
			       <c:if test="${controleNivel>1}">
			       		<li><a href="javascript:escolheNivel();" title="Muda o nível de acesso ao sistema">Muda nível de acesso</a></li>
			       		<input name="nivel_socio" id="nivel_socio" type="hidden" value="${controleNivel}"/>
			       </c:if>
			       
			    </ul>
			  </li>
			  <li><a href="stu?p=Acesso&flg=1" title="Fechar Sistema para o Operador Atual"><img src="imagens/spacer_1x1px.png" width="204" border="0" />Sair<img src="imagens/spacer_1x1px.png" width="204" border="0" /></a></li>
			</ul>
		</div>
				<script>
		
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

	     function XMLHttpRequestChange() {    		    
			    if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
					//window.location.href = "stu?p=IncluiRecebimento";
					window.location.href = "stu?p=Acesso";
		     }
	     }

		 	function escolheNivel() {

			   var valor = document.getElementById('nivel_socio').value;
			   var nivel = prompt("Digite o nível menor ou igual a: "+valor);

			   if(nivel>valor){
			    	alert("Nível máximo permitido para este acesso: "+valor);
			    	return false;
			   }else{
					if(nivel<1){
						nivel=1; 
					}
				   
			   		document.getElementById('nivel_socio').value=nivel;
				   
					url = "stu?p=AlteraNivelAcesso&nivel_socio="+nivel; 
					if (document.getElementById) {
						xmlhttp = AbrirAjax();
						if (xmlhttp) {
							xmlhttp.onreadystatechange = XMLHttpRequestChange;
							xmlhttp.open("GET", url, true); //Abre a url.
							xmlhttp.setRequestHeader('Content-Type', 'text/xml');
							xmlhttp.setRequestHeader('encoding', 'ISO-8859-1');
							xmlhttp.send(null);
						};
					};
			   };
			};
		</script>
		
	</body>
</html>
