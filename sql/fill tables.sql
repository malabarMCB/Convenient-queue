use Convenient_queue

insert into [User]
([Login], [Password])
values
('test1', '111'),
('test2', '2')

insert into Doctor
(Name, Surname, Specialization)
values
('John', 'Doe', 'Alchemist'),
('Jane', 'Doe', 'Surgeon'),
('Ronert', 'Koch', 'Epidemiologist'),
('Alexander', 'Fleming', 'Pharmacologist'),
('Edward', 'Jenner', 'Immunologist')

insert into DoctorVisitDetail
(DoctorId, AvgVisitTime)
values
(1, '00:05:00'),
(2, '00:15:00'),
(3, '00:07:00'),
(4, '00:30:00'),
(5, '00:20:00')

insert into DoctorVisit
(UserId, DoctorId, [Time])
values
(1, 1, '20180513 12:43:00'),
(1, 2, '20180514 10:00:00'),
(1, 3, '20180515 11:30:00'),
(1, 4, '20180517 16:40:00'),
(1, 5, '20180520 17:00:00')