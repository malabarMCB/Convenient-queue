
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

exec GetDoctorsVisits 1, 0, 4
go
---

create proc RemoveDoctorsVisits
	@visitIds nvarchar(max)
as
begin
	delete from DoctorVisit
	where DoctorVisit.Id in
	(
		select Id
		from DoctorVisit
		where @visitIds like '% '+cast(Id as nvarchar(max))+' %'
	)
end

drop proc GetDoctorsVisits

exec RemoveDoctorsVisits ' 1 4 3 '
go
---

create proc GetDoctorsCount
as
begin
	select count(*) as [Count]
	from Doctor
end

drop proc GetDoctorsCount

exec GetDoctorsCount
go
--

create proc GetDoctors
	@pageNum int, @itemsPerPage int
as
begin
	select *
	from Doctor
	order by Id
	offset @pageNum*@itemsPerPage rows
	fetch next @itemsPerPage rows only
end

drop proc GetDoctors

exec GetDoctors 0,4
go

create proc CalculateDoctorVisits
	@userId int, @doctorIds nvarchar(max)
as
begin
	declare @doctorId int

	declare @cur cursor 
	set @cur = cursor for
	select Id
	from Doctor
	where @doctorIds like '% '+cast(Id as nvarchar(max))+' %'

	open @cur
	fetch next from @cur
	into @doctorId

	while @@FETCH_STATUS = 0
	begin
		declare @userDoctorLastMeet datetime = (cast ((select max([Time]) from DoctorVisit 
			where DoctorVisit.UserId = @userId and DoctorVisit.DoctorId = @doctorId)as datetime))
		if (@userDoctorLastMeet is null or @userDoctorLastMeet < getdate())
		begin
			declare @visitTime smalldatetime
			select @visitTime = isnull(max(LastVisit.VisitTime),dateadd(day, 1, getdate()))
			from
			(
				select top 1 (DoctorVisit.Time + cast(DoctorVisitDetail.AvgVisitTime as smalldatetime)) as VisitTime
				from DoctorVisit
				join DoctorVisitDetail (nolock) on DoctorVisit.DoctorId = DoctorVisitDetail.DoctorId
				where DoctorVisit.UserId = @userId
				order by DoctorVisit.Time desc
				union		
				select top 1  (DoctorVisit.Time + cast(DoctorVisitDetail.AvgVisitTime as smalldatetime)) as VisitTime
				from DoctorVisit
				join DoctorVisitDetail (nolock) on DoctorVisit.DoctorId = DoctorVisitDetail.DoctorId
				where DoctorVisit.DoctorId = @doctorId
				order by DoctorVisit.Time desc
			) as LastVisit

			insert into DoctorVisit
			(UserId, DoctorId, [Time])
			values
			(@userId, @doctorId, @visitTime)
		end

		fetch next from @cur
		into @doctorId
	end

	close @cur
	deallocate @cur
end

drop proc CalculateDoctorVisits

delete from DoctorVisit
select * from DoctorVisit
exec CalculateDoctorVisits 1, ' 1 2 3 '
exec CalculateDoctorVisits 2, ' 3 '
go