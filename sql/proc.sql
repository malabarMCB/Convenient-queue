create proc GetUserId
	@login nvarchar(255), @password nvarchar(255)
as
begin
	select Id
	from [User]
	where [Login] = @login and [Password] = @password
end

drop proc GetUserId

exec GetUserId 'test1', '111'
go
---

create proc GetDoctorsVisitsCount
	@userId int
as	
begin
	select count(*) as [Count]
	from DoctorVisit
	where UserId = @userId
end

drop proc GetDoctorsVisitsCount

exec GetDoctorsVisitsCount 1
go
---

create proc GetDoctorsVisits
	@userId int, @pageNum int, @itemsPerPage int
as
begin
	select DoctorVisit.Id, Doctor.Id as DoctorId,Doctor.Name, Doctor.Surname, DoctorVisit.Time, Doctor.Specialization
	from DoctorVisit
	join Doctor on Doctor.Id = DoctorId
	where UserId = @userId
	order by DoctorVisit.Id
	offset @pageNum*@itemsPerPage rows
	fetch next @itemsPerPage rows only
end

drop proc GetDoctorsVisits

exec GetDoctorsVisits 1, 1, 4
go
---