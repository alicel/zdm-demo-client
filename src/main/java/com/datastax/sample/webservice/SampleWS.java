package com.datastax.sample.webservice;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.sample.service.Service;

@WebService
@Path("/")
public class SampleWS {

	private Logger logger = LoggerFactory.getLogger(SampleWS.class);
	private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd");

	//Service Layer.
	private Service service = new Service();

	@POST
	@Path("/newrows")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postInsertNewRows(@FormParam("startkey") Integer startKey, @FormParam("numrows") Integer numberOfRows) {
		if (startKey == null) {
			startKey = 0;
		}

		if (numberOfRows == null) {
			numberOfRows = 10;
		}
		
		service.insertRows(startKey, numberOfRows);
		return Response.status(Status.OK).entity(numberOfRows + " rows inserted, starting with key " + startKey + "\n").build();
	}

	@GET
	@Path("/row")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRow(@QueryParam("rowkey") Integer rowKey) {
		String rowValue = service.selectRowValueByKey(rowKey);
		return Response.status(Status.OK).entity(rowValue + "\n").build();
	}
}
