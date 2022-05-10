package com.datastax.sample.dao;

import java.io.File;
import java.util.List;

import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleDao {

	private static Logger logger = LoggerFactory.getLogger(SampleDao.class);
	private Session session;

	private List<KeyspaceMetadata> keyspaces;

	private PreparedStatement insertRowPS;
	private PreparedStatement selectRowByKeyPS;

	public SampleDao() {

		String connectionMode = System.getProperty("connectionMode");

		if (connectionMode == null) {
			throw new RuntimeException("Missing value for connectionMode property. " +
					"Please set this property to a supported value: ORIGIN, PROXY, or TARGET");
		}

		Cluster cluster = null;

		switch (connectionMode) {
			case "ORIGIN":
				cluster = Cluster.builder()
						.addContactPoints("...") // Origin contact points
						.withCredentials("aaaa", "bbbb") // Origin username and password
						.build();
				break;
			case "PROXY":
				cluster = Cluster.builder()
						.addContactPoints("...")	// Private IP address of each proxy instance
						.withCredentials("xxxx", "yyyy")  // Target credentials, e.g. Astra client ID and client secret
						.build();
				break;
			case "TARGET":
				cluster = Cluster.builder()
						.withCloudSecureConnectBundle(new File("..."))		// path to secure connect bundle for Target Astra cluster
						.withCredentials("xxxx", "yyyy") // Target credentials, e.g. Astra client ID and client secret
						.build();
				break;
			default:
				throw new RuntimeException("Unsupported value for connectionMode property. " +
											"Please set this property to a supported value: ORIGIN, PROXY, or TARGET");
		}

		this.session = cluster.connect();

		String keyspaceName = "zdm_demo_ks";

		String groupNumber = System.getProperty("groupNumber");
		if (groupNumber != null && !groupNumber.isEmpty()) {
			keyspaceName = keyspaceName + "_" + groupNumber;
		}

		insertRowPS = session.prepare("insert into "+ keyspaceName + ".app_data (app_key, app_value) values (?, ?)");
		selectRowByKeyPS = session.prepare("select app_value from "+ keyspaceName + ".app_data where app_key = ?");
	}

	public void insertRow(Integer rowKey, String rowValue) {
		session.execute(insertRowPS.bind(rowKey, rowValue));
	}

	public String selectRowValue(Integer rowKey) {
		Row row = session.execute(selectRowByKeyPS.bind(rowKey)).one();
		if (row != null) {
			return row.getString("app_value");
		} else {
			return null;
		}
	}
}
