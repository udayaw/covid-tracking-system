package org.chathu.covid.api.endpoint;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.implementation.Utils;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.FeedResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.chathu.covid.api.cosmos.DBManager;
import org.chathu.covid.api.model.documents.LocationHistory;
import org.chathu.covid.api.model.types.PointLocation;
import org.chathu.covid.api.model.documents.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String args[]) throws Exception{


        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

//        Date d1 = new Date();
//        //writeCosmos1();
//        readCosmos1();
//        Date d2 = new Date();
//        System.out.println(d2.getTime() - d1.getTime());
//
//
//        DBManager.getDBManager().getCosmosClient().close();

//        Algorithm algorithm = Algorithm.HMAC256("secret");
//        String token = JWT.create()
//                .withIssuer("/covid-app-rest-api")
//                .withClaim("user","amila01")
//                .sign(algorithm);
//        System.out.println("signed token" + token);
//
//        JWTVerifier verifier = JWT.require(algorithm)
//                .withIssuer("/covid-app-rest-api")
//                .build(); //Reusable verifier instance
//        DecodedJWT jwt = verifier.verify(token);
//
//        System.out.println(jwt.getClaim("user").asString());

    }


    public static void readCosmos(){

            DBManager manager = DBManager.getDBManager();

            CosmosDatabase db = manager.getDefaultDatabase();
            CosmosContainer container = db.getContainer("User1");


            CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();


            for (FeedResponse<JsonNode> pageResponse
                    :container.queryItems("SELECT * FROM c",options, JsonNode.class).iterableByPage()) {

                for (JsonNode item : pageResponse.getElements()) {
                    System.out.println(item.get("UserName"));
                }
            }
        }


    public static void writeCosmos1(){

        DBManager manager = DBManager.getDBManager();
        CosmosDatabase db = manager.getDefaultDatabase();
        CosmosContainer container = db.getContainer("LocationHistory");
        ObjectMapper mapper = Utils.getSimpleObjectMapper();
        //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        LocationHistory doc = new LocationHistory();
        //doc.setId();
        doc.setUserId("test1");
        doc.setLocation(new PointLocation(new double[]{6.877883208581049, 79.89973862310195}));
        doc.setTimestamp(new Date());

        container.createItem(doc);

    }


    public static void writeCosmos(){

        DBManager manager = DBManager.getDBManager();
        CosmosDatabase db = manager.getDefaultDatabase();
        CosmosContainer container = db.getContainer("User1");
        ObjectMapper mapper = Utils.getSimpleObjectMapper();
        //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        User u1 = new User();
        u1.setId("2");
        //u1.setPartitionKey(null);
        u1.setFirstName("Kasun");
        u1.setLastName("Kalhara");


        JsonNode node = mapper.valueToTree(u1);

        container.createItem(node);

    }


    public static void readCosmos1(){

        DBManager manager = DBManager.getDBManager();

        CosmosDatabase db = manager.getDefaultDatabase();
        CosmosContainer container = db.getContainer("LocationHistory");


        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();


        for (FeedResponse<LocationHistory> pageResponse
                :container.queryItems("SELECT * FROM c",options, LocationHistory.class).iterableByPage()) {

            for (LocationHistory item : pageResponse.getElements()) {
                System.out.println(item.getTimestamp());
                System.out.println(item.getUserId());
            }
        }
    }

    /**
     * This function listens at endpoint "/api/HttpTrigger-Java". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTrigger-Java
     * 2. curl {your host}/api/HttpTrigger-Java?name=HTTP%20Query
     */
//    @FunctionName("HttpTrigger-Java")
//    public HttpResponseMessage run(
//            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<String> request,
//            final ExecutionContext context) {
//
//        JSONObject body = new JSONObject(request.getBody());
//        String name = body.getString("name");
//
//
//        //send data to event hub
//
//        ConnectionStringBuilder connStr = new ConnectionStringBuilder()
//                .setNamespaceName("covid-app-location-stream")
//                .setEventHubName("location-stream")
//                .setSasKeyName("RootManageSharedAccessKey")
//                .setSasKey("+1CGEnocSs5ZLZS32GZj7oXpCZZ20gjrCsHeNGE1ucc=");
//
//
//
//        // The Executor handles all asynchronous tasks and this is passed to the EventHubClient instance.
//        // This enables the user to segregate their thread pool based on the work load.
//        // This pool can then be shared across multiple EventHubClient instances.
//        // The following sample uses a single thread executor, as there is only one EventHubClient instance,
//        // handling different flavors of ingestion to Event Hubs here.
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
//
//        // Each EventHubClient instance spins up a new TCP/TLS connection, which is expensive.
//        // It is always a best practice to reuse these instances. The following sample shows this.
//        EventHubClient ehClient = null;
//        try {
//            ehClient = EventHubClient.createSync(connStr.toString(), executorService);
//            for (int i = 0; i < 10; i++) {
//
//                JSONObject j1 = new JSONObject();
//                j1.put("Name", "ABC");
//                j1.put("Name", 26);
//                EventData sendEvent = EventData.create( j1.toString().getBytes());
//
//                // Send - not tied to any partition
//                // Event Hubs service will round-robin the events across all Event Hubs partitions.
//                // This is the recommended & most reliable way to send to Event Hubs.
//                ehClient.sendSync(sendEvent);
//            }
//        } catch (EventHubException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                ehClient.closeSync();
//            } catch (EventHubException e) {
//                e.printStackTrace();
//            }
//            executorService.shutdown();
//        }
//        return request.createResponseBuilder(HttpStatus.OK).body(name).build();
//    }

}

