package com.bookit.step_definitions;

import com.bookit.pages.SelfPage;
import com.bookit.utilities.BookItApiUtils;
import com.bookit.utilities.BrowserUtils;
import com.bookit.utilities.DBUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import java.util.Map;

public class HWStepDef {

    String actualUIFullName ;
    String actualUIRole ;
    String actualUITeam ;
    String actualUIBatch ;
    String actualUICampus ;
    String nameFromDB ;
    String roleFromDB ;
    String batchFromDB ;
    String teamNameFromDB ;
    String locationFromDB ;
    String nameFromAPI ;
    String roleFromAPI ;
    String teamFromAPI ;
    String batchFromAPI ;
    String campusFromAPI ;


    @And("I get the current user information from UI")
    public void iGetTheCurrentUserInformationFromUI() {
        SelfPage selfPage = new SelfPage();
        BrowserUtils.waitFor(5);
        actualUIFullName = selfPage.name.getText();
        actualUIRole = selfPage.role.getText();
        actualUITeam = selfPage.team.getText();
        actualUIBatch = selfPage.batch.getText();
        actualUICampus = selfPage.campus.getText();

        System.out.println("actualUICampus = " + actualUICampus);
        System.out.println("actualUIBatch = " + actualUIBatch);

    }


    @And("I get the current user information from DataBase")
    public void iGetTheCurrentUserInformationFromDataBase() {

        String query = "select firstname,lastname,role,batch_number,name,location\n" +
                "from users u join (select t.name,t.id,t.batch_number,c.location \n" +
                "from team t join campus c on t.campus_id=c.id)j\n" +
                "on u.team_id = j.id where email='"+ApiStepDefs.emailGlobal+"';";

        Map<String,Object> dataMap = DBUtils.getRowMap(query);
        nameFromDB = dataMap.get("firstname")+" "+dataMap.get("lastname");
        roleFromDB = (String) dataMap.get("role");
        batchFromDB = "#"+dataMap.get("batch_number");
        teamNameFromDB = (String) dataMap.get("name");
        locationFromDB = (String) dataMap.get("location");
        System.out.println("dataMap = " + dataMap);


    }




    @And("I get more information about user from API")
    public void iGetMoreInformationAboutUserFromAPI() {

        JsonPath jsonPath = ApiStepDefs.response.jsonPath();
        nameFromAPI = jsonPath.getString("firstName")+" "+jsonPath.getString("lastName");
        roleFromAPI = jsonPath.getString("role");

        String[] restOfTheAPIinfo = BookItApiUtils.getMyInfo(ApiStepDefs.emailGlobal,ApiStepDefs.passwordGlobal);

        teamFromAPI = restOfTheAPIinfo[0];
        batchFromAPI = restOfTheAPIinfo[1];
        campusFromAPI = restOfTheAPIinfo[2];

        System.out.println("batchFromAPI = " + batchFromAPI);
    }


    @Then("All five information from three environment should match")
    public void all_five_information_from_three_environment_should_match() {

        Assert.assertTrue(actualUIFullName.equals(nameFromDB)&&actualUIFullName.equals(nameFromAPI));
        Assert.assertTrue(actualUICampus.equals(locationFromDB)&&actualUICampus.equals(campusFromAPI));
        Assert.assertTrue(actualUIBatch.equals(batchFromDB)&&actualUIBatch.equals(batchFromAPI));
        Assert.assertTrue(actualUIRole.equals(roleFromDB)&&actualUIRole.equals(roleFromAPI));
        Assert.assertTrue(actualUITeam.equals(teamNameFromDB)&&actualUITeam.equals(teamFromAPI));




    }


}
