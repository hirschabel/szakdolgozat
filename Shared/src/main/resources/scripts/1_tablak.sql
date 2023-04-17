drop table felhasznalo;
drop table szintadat;

create table felhasznalo
(
    id             serial primary key,
    felhasznalonev varchar(255) not null,
    jelszo         varchar(255) not null
);

create table szintadat
(
    id               integer not null
        primary key,
    etelnoveles      integer not null,
    etelvisszatoltes boolean not null,
    italnoveles      integer not null,
    italvisszatoltes boolean not null,
    szuksegestargyak integer[],
    targygeneralas   boolean[]
);