@ECHO OFF 

if not exist C:\backup (
   md C:\backup
)

if not exist C:\backup\temp (
   md C:\backup\temp   
)

if exist C:\backup\stubackup.db (
   	del C:\backup\stubackup.db
)

call C:\"Arquivos de Programas"\MySQL\"MySQL Server 5.1"\bin\mysqldump -u udvnmg_usuario -p57udvnm6 --databases --lock-all-tables studb >C:\backup\stubackup.db

rem call C:\"Program Files (x86)"\MySQL\"MySQL Server 5.1"\bin\mysqldump -u udvnmg_usuario -p57udvnm6 --databases --lock-all-tables studb >C:\backup\stubackup.db

cd \backup

set dia=%date:~0,2%
set mes=%date:~3,2%
set ano=%date:~6,4%
set hor=%time:~0,2%
set min=%time:~3,2%
set data_file=stu_%dia%%mes%%ano%%hor%%min%.db
	
copy /y c:\backup\stubackup.db c:\backup\temp\%data_file%