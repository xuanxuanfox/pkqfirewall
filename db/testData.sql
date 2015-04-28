

USE pkqfirewall;

insert into rule(content,devices,createtime) values('reject','1,2','2015-1-1');

insert into  device(id,main_type,type,ip)  values('18uu7x-f06d-4f49-a310-22c4d1590c98','1','REDHAT','192.168.9.100' );

insert into  device(id,main_type,type,ip)  values('1waege2-f06d-4f49-a310-22c4d1590c98','2','server2008','192.168.9.1' );



INSERT INTO agentVersion(ostype,versionIndex,version,flag,downUrl) VALUES ('linux', 2, 'V1.1','new','');

