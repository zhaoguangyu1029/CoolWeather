create table if not exists County(
    id integer primary key autoincrement,
    county_name text,
    county_code text,
    city_id integer
)