create table tb_evento (
	evento_id bigint unsigned not null auto_increment,
  	nome varchar(80) not null,
  	dt_criacao datetime not null,
  	dt_encerramento datetime not null,
  	informacoes TEXT not null,
  	user_id bigint unsigned,

  	primary key(evento_id)
);

alter table tb_evento add constraint fk_evento_user
foreign key (user_id) references tb_user (user_id);