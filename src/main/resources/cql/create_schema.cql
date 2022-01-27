create keyspace if not exists sample_app_keyspace WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1' };

use sample_app_keyspace;

create table if not exists sample_app_keyspace.app_data
    (app_key int PRIMARY KEY,
    app_value text);



create keyspace if not exists sample_app_keyspace
    WITH replication = {'class': 'NetworkTopologyStrategy', ... };

create table if not exists sample_app_keyspace.app_data
    (app_key int PRIMARY KEY,
     app_value text);

