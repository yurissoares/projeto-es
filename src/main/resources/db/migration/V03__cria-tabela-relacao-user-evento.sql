create table tb_user_evento (
	user_evento_id bigint unsigned not null auto_increment,
	evento_id bigint unsigned,
    user_id bigint unsigned,
  	dt_inscricao datetime not null,
  	concluido boolean not null,

  	primary key(user_evento_id)
);

alter table tb_user_evento add constraint fk_tb_user_evento_evento
foreign key (evento_id) references tb_evento (evento_id);

alter table tb_user_evento add constraint fk_tb_user_evento_user
foreign key (user_id) references tb_user (user_id);

