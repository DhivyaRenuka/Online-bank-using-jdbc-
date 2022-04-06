create table acc_holder_detail(
    accno int,
    pin int,
	name text,
    phone int, 
	address text,
    balance int, 
    primary key(accno)
       );

create table login(
accno int unique,
pin int not null,
primary key(pin),
constraint fk_accno foreign key(accno) 
	       references acc_holder_detail(accno));

create table bank(
	accno int unique,
	acch_name text,
	pin int,
	deposit int,
	withdraw int,
	balance int,
	primary key(acch_name),
	constraint fk_accno foreign key(accno) 
	references login(accno));
		   
create table loanAcc(
	loan_no int,
	accno int,
	pin int,
	amount int,
	interestAmt int,
	primary key(loan_no),select * from bank;
	  constraint fk_accno foreign key(accno)
	      references  login(accno) );

select * from bank;
select * from login;
select * from acc_holder_detail;