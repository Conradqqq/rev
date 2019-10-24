package com.kkwiatkowski.revolut.acceptanceTests;

import com.kkwiatkowski.revolut.App;
import com.kkwiatkowski.revolut.model.dto.request.TransferDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import spark.Spark;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

class TransferAcceptanceTest {

    private static final int DEFAULT_SPARK_PORT = 4567;
    private static final int TO_ID = 1;
    private static final int FROM_ID = 2;

    @BeforeAll
    static void setUp() {
        App.main(new String[]{});
        RestAssured.port = DEFAULT_SPARK_PORT;
    }

    @Test
    void transfersMoney() {
        checkAccountBalance(TO_ID, 100.00f);
        checkAccountBalance(FROM_ID, 200.00f);

        executeTransferRequest(new TransferDto(FROM_ID, TO_ID, BigDecimal.TEN));

        checkAccountBalance(TO_ID, 110.00f);
        checkAccountBalance(FROM_ID, 190.00f);
    }

    private void checkAccountBalance(int id, float balance) {
        when().
                get("/account/{id}", id).
        then().
                statusCode(200).
                body("id", equalTo(id),
                        "balance", is(balance));
    }

    private void executeTransferRequest(TransferDto transferDto) {
        given().
                contentType("application/json").
                body(transferDto).
        when().
                post("/transfer").
        then().
                statusCode(204);
    }

}
