package com.soa.springcloud.controller;

import com.soa.springcloud.entities.CommonResult;
import com.soa.springcloud.entities.User;
import com.soa.springcloud.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
//@CrossOrigin(origins = "*")
public class SearchController {
    private final SearchService searchServiceService;

    public SearchController(SearchService searchServiceService) {
        this.searchServiceService = searchServiceService;
    }

    @GetMapping("/user/search")
    public CommonResult searchTrueName(@RequestParam("str") String str) {
        /*//预处理传入参数
        if(unifiedId ==null){
            return CommonResult.failure("失败，unifiedId空");
        }*/
        //开始搜索数据
        List<User> users = searchServiceService.searchByTrueName(str);
        if(users !=null) {
            return CommonResult.success("搜索成功",users);
        }
        return CommonResult.failure("搜索失败");
    }

}
