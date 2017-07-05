package com.sd.controller;

import com.sd.annotation.CheckParam;
import com.sd.dto.User;
import org.springframework.web.bind.annotation.*;

/**
 * @author SD
 * @since 2017/7/5
 */
@RestController
public class HelloController {

    public static final String successResult = "{ \"result\": \"success\" }";
    public static final String failedResult = "{ \"result\": \"failed\" }";

    @ExceptionHandler({RuntimeException.class})
    public String handle(RuntimeException x) {
        return failedResult;
    }

    @GetMapping(value = "/param/not-null")
    public String checkParamNull(@CheckParam(notNull = true) String userName) {
        return successResult;
    }

    @GetMapping(value = "/param/not-empty")
    public String checkParamEmpty(@CheckParam(notEmpty = true) String userName) {
        return successResult;
    }

    @PostMapping(value = "/param/field/not-null")
    public String checkParamFieldNull(@RequestBody @CheckParam(notNullFields = {"userName"}) User user) {
        return successResult;
    }

    @PostMapping(value = "/param/field/not-empty")
    public String checkParamFieldEmpty(@RequestBody @CheckParam(notEmptyFields = {"userName"}) User user) {
        return successResult;
    }
}
