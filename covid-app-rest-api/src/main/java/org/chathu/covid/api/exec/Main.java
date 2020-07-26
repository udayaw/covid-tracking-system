package org.chathu.covid.api.exec;

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
    public static void main(String args[]){


//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

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

}

