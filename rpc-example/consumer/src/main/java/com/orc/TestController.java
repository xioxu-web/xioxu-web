package com.orc;

@RestController
@RequestMapping("test")
public class TestController {



    @GetMapping("/user")
    public ApiResult<User> getUser(@RequestParam("id")Long id){
        return userService.getUser(id);
    }
}
