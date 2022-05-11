/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022-05-11 14:24:07                          */
/*==============================================================*/


drop table if exists 专业;

drop table if exists 学生;

drop table if exists 学生选课;

drop table if exists 开课课程;

drop table if exists 教师;

drop table if exists 班级;

drop table if exists 课程;

drop table if exists 院系;

/*==============================================================*/
/* Table: 专业                                                    */
/*==============================================================*/
create table 专业
(
   专业名称                 varchar(20) not null,
   学位等级                 varchar(20) not null,
   院系代码                 varchar(20) not null,
   毕业学分                 varchar(20),
   primary key (专业名称, 学位等级)
);

/*==============================================================*/
/* Table: 学生                                                    */
/*==============================================================*/
create table 学生
(
   姓名                   varchar(20) not null,
   学号                   varchar(20) not null,
   班级名称                 varchar(20) not null,
   身份证号                 varchar(20),
   宿舍                   varchar(20),
   家庭地址                 varchar(100),
   电话                   varchar(20),
   出生日期                 date,
   性别                   varchar(20),
   年级                   varchar(20) not null,
   已修学分                 int not null,
   primary key (学号)
);

/*==============================================================*/
/* Table: 学生选课                                                  */
/*==============================================================*/
create table 学生选课
(
   开课号                  varchar(20) not null,
   学号                   varchar(20) not null,
   成绩                   int,
   primary key (开课号, 学号)
);

/*==============================================================*/
/* Table: 开课课程                                                  */
/*==============================================================*/
create table 开课课程
(
   开课号                  varchar(20) not null,
   课程编号                 varchar(20),
   教师编号                 varchar(20) not null,
   开课时间                 varchar(20),
   开课地点                 varchar(20),
   开课周                  varchar(20),
   primary key (开课号)
);

/*==============================================================*/
/* Table: 教师                                                    */
/*==============================================================*/
create table 教师
(
   教师姓名                 varchar(20) not null,
   教师编号                 varchar(20) not null,
   院系代码                 varchar(20) not null,
   primary key (教师编号)
);

/*==============================================================*/
/* Table: 班级                                                    */
/*==============================================================*/
create table 班级
(
   班级名称                 varchar(20) not null,
   专业名称                 varchar(20) not null,
   学位等级                 varchar(20) not null,
   年级                   varchar(20),
   primary key (班级名称)
);

/*==============================================================*/
/* Table: 课程                                                    */
/*==============================================================*/
create table 课程
(
   课程名                  varchar(20) not null,
   课程性质                 varchar(20),
   课程类别                 varchar(20),
   课程编号                 varchar(20) not null,
   院系代码                 varchar(20) not null,
   学时                   int,
   学分                   int not null,
   primary key (课程编号)
);

/*==============================================================*/
/* Table: 院系                                                    */
/*==============================================================*/
create table 院系
(
   院系名称                 varchar(20) not null,
   院系代码                 varchar(20) not null,
   办公地点                 varchar(20),
   电话                   varchar(20),
   primary key (院系代码)
);

alter table 专业 add constraint FK_专业_院系 foreign key (院系代码)
      references 院系 (院系代码) on delete restrict on update restrict;

alter table 学生 add constraint FK_学生_班级 foreign key (班级名称)
      references 班级 (班级名称) on delete restrict on update restrict;

alter table 学生选课 add constraint FK_学生选课 foreign key (学号)
      references 学生 (学号) on delete restrict on update restrict;

alter table 学生选课 add constraint FK_学生选课2 foreign key (开课号)
      references 开课课程 (开课号) on delete restrict on update restrict;

alter table 开课课程 add constraint FK_教师授课 foreign key (教师编号)
      references 教师 (教师编号) on delete restrict on update restrict;

alter table 开课课程 add constraint FK_课程开课 foreign key (课程编号)
      references 课程 (课程编号) on delete restrict on update restrict;

alter table 教师 add constraint FK_教师从属 foreign key (院系代码)
      references 院系 (院系代码) on delete restrict on update restrict;

alter table 班级 add constraint FK_班级_专业 foreign key (专业名称, 学位等级)
      references 专业 (专业名称, 学位等级) on delete restrict on update restrict;

alter table 课程 add constraint FK_课程从属 foreign key (院系代码)
      references 院系 (院系代码) on delete restrict on update restrict;

