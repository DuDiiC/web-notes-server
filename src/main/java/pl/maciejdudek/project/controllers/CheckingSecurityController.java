package pl.maciejdudek.project.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.maciejdudek.project.configuration.Swagger2Config;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping("/test")
@Api(tags = {Swagger2Config.TEST_TAG})
public class CheckingSecurityController {

    @GetMapping("/unsecured")
    @ApiOperation(value = "endpoint to test requests available to non-logged in users")
    public String unsecured() {
        return "content";
    }

    @GetMapping("/securedForAdminTest")
    @ApiOperation(value = "endpoint to test admin-only requests")
    public String securedForAdminTest() {
        return "admin content";
    }

    @GetMapping("/securedForUserTest")
    @ApiOperation(value = "endpoint to test requests available only to logged in users")
    public String securedForUserTest() {
        return "user content";
    }
}
