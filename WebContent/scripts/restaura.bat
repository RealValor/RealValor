@ECHO OFF 

if not exist C:\backup (
   md C:\backup
)

if exist C:\backup\stubackup.db (
   call C:\"Arquivos de Programas"\MySQL\"MySQL Server 5.1"\bin\mysql -u udvnmg_usuario -p57udvnm6 studb < C:\backup\stubackup.db
   rem call C:\"Program Files (x86)"\MySQL\"MySQL Server 5.1"\bin\mysql -u udvnmg_usuario -p57udvnm6 studb < C:\backup\stubackup.db
)
