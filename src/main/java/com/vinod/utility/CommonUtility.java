package com.vinod.utility;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.util.List;

@Slf4j
public class CommonUtility {

    public static void verifyLinks(List<String> links) {
        int count = 0;
        for (String link : links) {
            if (count == 5) break;
            try {
                int statusCode = RestAssured.get(link).statusCode();
                Assert.assertTrue(statusCode < 400, "Broken link found: " + link +" with statusCode: "+statusCode);

            }catch (Exception e){
                ExtentReportManager.logError("not a valid link reference :"+link);
                log.error("not a valid link reference :"+link);
                e.printStackTrace();
            }
            count++;
        }
    }

}
