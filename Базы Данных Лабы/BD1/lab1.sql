create type color_type as enum ('red', 'green', 'black', 'white');
create table computer(
    id serial primary key,
    date_manufacture date,
    color color_type,
    last_modified timestamp
);
create table idiosincrasy(
    id serial primary key references computer(id),
    level integer,
    symptom varchar(50)
);
create table human(
    id serial primary key,
    name varchar(50),
    sex varchar(50)
);
create table information(
    id serial primary key,
    from_to varchar(255),
    content_push varchar(255)
);
create table code_signal(
    id serial primary key,
    type_signal varchar(255),
    indifikator integer references computer(id)
);

--Создание функции внесения изменений
CREATE OR REPLACE FUNCTION update_computer_last_modified()
RETURNS TRIGGER AS $$
BEGIN
    NEW.last_modified := NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

--Триггер на срабатывание
CREATE TRIGGER computer_update_last_modified
BEFORE UPDATE ON computer
FOR EACH ROW
EXECUTE FUNCTION update_computer_last_modified();

