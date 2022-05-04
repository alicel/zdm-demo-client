ZDM Demo Client
========================

To get started download this repository. Choose a location to download the project and run the following
```
git clone https://github.com/alicel/zdm-demo-client
```

For the migration, you will need two clusters: Origin and Target. 
You will also need to deploy the ZDM proxy (see the enablement material for all details and commands).

Schema creation
---
The application uses a simple table with a key/value structure. This table must exist on Origin and Target.

Create a keyspace called `sample_app_keyspace` on Origin and Target. 
Typically this is done through CQL, specifying the desired replication strategy. If using Astra, create the keyspace through the Astra UI instead.

Then, create the following table:
```
CREATE TABLE sample_app_keyspace.app_data (
    app_key int PRIMARY KEY,
    app_value text
)
```

Building and running the application
-----
This application must be deployed to an instance in the Origin infrastructure that can reach the ZDM proxy and the Origin cluster.

To build this application, run: 
	
	mvn clean install

The application will connect to Origin, Proxy or Target based on the value of the `connectionMode` property specified in the run command.
Accepted values for this property are `ORIGIN`, `PROXY` and `TARGET`.

To start the web server, run the following command setting `connectionMode` as appropriate (please note that this property is mandatory):

	mvn jetty:run -DconnectionMode=X

The web server listens on port 8080.

Calling the application REST endpoints
----
To insert new rows, run:

	curl -d 'startkey=5' -d 'numrows=20' -X POST http://localhost:8080/zdm-demo-client/rest/newrows

The command above will insert 20 new rows with sequential keys, starting with key 5 (included). 
The row value will include a randomly selected name and surname, and the value of this row's key: an example row would have key `15` and value `I am Stella Walsh from row 15` 

To retrieve the value of a row (for example row `12`) run:

    curl -G -d 'rowkey=12' http://localhost:8080/zdm-demo-client/rest/row 

    
