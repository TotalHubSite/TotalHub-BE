ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
flush privileges;

create database if not exists totalhubsite;

use totalhubsite;