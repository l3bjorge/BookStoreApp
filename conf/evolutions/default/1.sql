# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table book (
  id                            integer auto_increment not null,
  title                         varchar(255),
  price                         integer,
  author                        varchar(255),
  ownername                     varchar(255),
  userid                        integer,
  description                   varchar(255),
  availability                  tinyint(1) default 0,
  constraint pk_book primary key (id)
);

create table message (
  id                            integer auto_increment not null,
  title                         varchar(255),
  message                       varchar(255),
  authorid                      integer,
  receiverid                    integer,
  author                        varchar(255),
  receivername                  varchar(255),
  constraint pk_message primary key (id)
);

create table user (
  id                            integer auto_increment not null,
  username                      varchar(255),
  password                      varchar(255),
  email                         varchar(255),
  country                       varchar(255),
  fthemes                       varchar(255),
  pinfo                         varchar(255),
  constraint uq_user_username unique (username),
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id)
);


# --- !Downs

drop table if exists book;

drop table if exists message;

drop table if exists user;

