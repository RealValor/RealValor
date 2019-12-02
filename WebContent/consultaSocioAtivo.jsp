
	  <script>
	  window.onload = function(){
			var width = 900;
			var height = 500;

			var left = 99;
			var top = 99;
			var now=new Date();
			var seconds = now.getSeconds();

			window.open(document.getElementById('urlSet').value+'imprimeSocioAtivo.jsp?cod='+seconds,'janela', 'width='+width+', height='+height+', top='+top+', left='+left+', scrollbars=yes, status=no, toolbar=no, location=no, directories=no, menubar=no, resizable=no, fullscreen=no');
			
			//document.formNome.action='stu?p=ConsultaSocio';
			document.formNome.action='stu?p=ConsultaSocio&ctrl=1';
 			document.formNome.submit();   
		 };
		 
	  </script>
           
		<form id="formNome" name="formNome" method="post">
			<input name="urlSet" id="urlSet" type="hidden" value="${urlSet}"/>
        </form>
