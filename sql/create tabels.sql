use Convenient_queue

IF OBJECT_ID('DoctorVisitDetail', 'U') IS NOT NULL
DROP TABLE DoctorVisitDetail;
IF OBJECT_ID('DoctorVisit', 'U') IS NOT NULL
DROP TABLE DoctorVisit;
IF OBJECT_ID('Doctor', 'U') IS NOT NULL
DROP TABLE Doctor;
IF OBJECT_ID('[User]', 'U') IS NOT NULL
DROP TABLE [User];
go

create table [User]
(
	Id int primary key identity(1,1),
	[Login] nvarchar(255) not null unique,
	[Password] nvarchar(255) not null
)

create table Doctor
(
	Id int primary key identity(1,1),
	Name nvarchar(max) not null,
	Surname nvarchar(max) not null,
	Specialization nvarchar(max) not null
)

create table DoctorVisit
(
	Id int primary key identity(1,1),
	UserId int not null,
	DoctorId int not null,
	Time smalldatetime not null
)

alter table DoctorVisit
add constraint DoctorVisitDoctorFK
foreign key (DoctorId) references Doctor(Id)
on delete cascade

alter table DoctorVisit
add constraint DoctorVisitUserFK
foreign key (UserId) references [User](Id)
on delete cascade

create table DoctorVisitDetail
(
	Id int primary key identity(1,1),
	DoctorId int not null unique,
	AvgVisitTime time not null
)

alter table DoctorVisitDetail
add constraint DoctorVisitDetailDoctorFK
foreign key (DoctorId) references Doctor(Id)
on delete cascade