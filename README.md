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

The default name of the keyspace is `zdm_demo`, but you can specify a different keyspace name if you wish.

Create a keyspace with the chosen name on Origin and Target. 
Typically this is done through CQL, specifying the desired replication strategy. 

For example:
```
CREATE KEYSPACE IF NOT EXISTS <your_keyspace_name> WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1' };
```

If using Astra, create a keyspace with the same name through the Astra UI.

Then, create the following table:
```
CREATE TABLE <your_keyspace_name>.app_data (
    app_key int PRIMARY KEY,
    app_value text
)
```

Building and running the application
-----
This application must be deployed to an instance in the Origin infrastructure that can reach the ZDM proxy and the Origin cluster.

Modify the constructor of the `SampleDao.java` class with credentials and connection parameters valid for your Origin cluster, 
Target cluster and Proxy instances, changing the code in each `switch` case as appropriate.

To build this application, run: 
	
	mvn clean install

(This has been tested with JDK versions 8, 12 and 14)

The application will connect to Origin, Proxy or Target based on the value of the `connectionMode` property specified in the run command.
Accepted values for this property are `ORIGIN`, `PROXY` and `TARGET`.

To start the web server, run the following command setting `connectionMode` as appropriate (please note that this property is mandatory). 
If your keyspace is not called `zdm_demo`, you will also need to specify the property `keyspaceName`:

	mvn jetty:run -DkeyspaceName=<your_keyspace_name> -DconnectionMode=X

The web server listens on port 8080.

Calling the application REST endpoints
----
To insert new rows, run:

	curl -d 'startkey=5' -d 'numrows=20' -X POST http://localhost:8080/zdm-demo-client/rest/newrows

The command above will insert 20 new rows with sequential keys, starting with key 5 (included). If a starting key is not specified, it defaults to 0. If the number of rows to be inserted is not passed, it defaults to 10.

The row value will include a randomly selected name and surname, and the value of this row's key: an example row would have 
key `15` and value `I am Stella Walsh from row 15` 

To retrieve the value of a row (for example row `12`) run:

    curl -G -d 'rowkey=12' http://localhost:8080/zdm-demo-client/rest/row 

    
