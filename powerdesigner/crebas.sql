/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022-05-11 21:56:39                          */
/*==============================================================*/


drop table if exists Class;

drop table if exists Course;

drop table if exists CourseClass;

drop table if exists Faculty;

drop table if exists Major;

drop table if exists Student;

drop table if exists StudentCourse;

drop table if exists Teacher;

/*==============================================================*/
/* Table: Class                                                 */
/*==============================================================*/
create table Class
(
   className            varchar(20) not null,
   majorCode            varchar(20) not null,
   primary key (className)
);

/*==============================================================*/
/* Table: Course                                                */
/*==============================================================*/
create table Course
(
   courseName           varchar(20) not null,
   courseNature         varchar(20),
   courseCategory       varchar(20),
   courseID             varchar(20) not null,
   facultyCode          varchar(20) not null,
   courseHours          int,
   credit               int not null,
   primary key (courseID)
);

/*==============================================================*/
/* Table: CourseClass                                           */
/*==============================================================*/
create table CourseClass
(
   courseClassID        varchar(20) not null,
   courseID             varchar(20),
   teacherID            varchar(20) not null,
   courseClassTime      varchar(20),
   courseClassAddress   varchar(20),
   courseClassWeek      varchar(20),
   primary key (courseClassID)
);

/*==============================================================*/
/* Table: Faculty                                               */
/*==============================================================*/
create table Faculty
(
   facultyName          varchar(20) not null,
   facultyCode          varchar(20) not null,
   facultyAddress       varchar(20),
   facultyTeleno        varchar(20),
   primary key (facultyCode)
);

/*==============================================================*/
/* Table: Major                                                 */
/*==============================================================*/
create table Major
(
   majorName            varchar(20) not null,
   majorCode            varchar(20) not null,
   facultyCode          varchar(20) not null,
   degreeLevel          varchar(20) not null,
   graduationCredits    varchar(20) not null,
   primary key (majorCode)
);

/*==============================================================*/
/* Table: Student                                               */
/*==============================================================*/
create table Student
(
   studentName          varchar(20) not null,
   studentID            varchar(20) not null,
   className            varchar(20) not null,
   identifier           varchar(20),
   dormitory            varchar(20),
   address              varchar(100),
   teleno               varchar(20),
   birthday             date,
   sex                  varchar(20),
   grade                varchar(20) not null,
   completedCredits     int not null,
   primary key (studentID)
);

/*==============================================================*/
/* Table: StudentCourse                                         */
/*==============================================================*/
create table StudentCourse
(
   courseClassID        varchar(20) not null,
   studentID            varchar(20) not null,
   score                int,
   primary key (courseClassID, studentID)
);

/*==============================================================*/
/* Table: Teacher                                               */
/*==============================================================*/
create table Teacher
(
   teacherName          varchar(20) not null,
   teacherID            varchar(20) not null,
   facultyCode          varchar(20) not null,
   primary key (teacherID)
);

alter table Class add constraint FK_Class_Major foreign key (majorCode)
      references Major (majorCode) on delete restrict on update restrict;

alter table Course add constraint FK_CourseFaculty foreign key (facultyCode)
      references Faculty (facultyCode) on delete restrict on update restrict;

alter table CourseClass add constraint FK_CourseOffer foreign key (courseID)
      references Course (courseID) on delete restrict on update restrict;

alter table CourseClass add constraint FK_TeacherTeaching foreign key (teacherID)
      references Teacher (teacherID) on delete restrict on update restrict;

alter table Major add constraint FK_Major_Faculty foreign key (facultyCode)
      references Faculty (facultyCode) on delete restrict on update restrict;

alter table Student add constraint FK_StudentClass foreign key (className)
      references Class (className) on delete restrict on update restrict;

alter table StudentCourse add constraint FK_StudentCourse foreign key (studentID)
      references Student (studentID) on delete restrict on update restrict;

alter table StudentCourse add constraint FK_StudentCourse2 foreign key (courseClassID)
      references CourseClass (courseClassID) on delete restrict on update restrict;

alter table Teacher add constraint FK_TeacherFaculty foreign key (facultyCode)
      references Faculty (facultyCode) on delete restrict on update restrict;

