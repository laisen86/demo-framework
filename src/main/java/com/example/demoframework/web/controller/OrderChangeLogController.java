package com.example.demoframework.web.controller;

import com.example.demoframework.domain.entity.OrderChangeLog;
import com.example.demoframework.service.OrderChangeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author zhangxueli6
 * @date 2019/2/17
 */
@RestController
@RequestMapping("/orderChangeLog")
@Validated
public class OrderChangeLogController {
    @Autowired
    private OrderChangeLogService orderChangeLogService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public OrderChangeLog query(Long id) {
        OrderChangeLog orderChangeLog = orderChangeLogService.selectByPrimaryKey(id);
        return orderChangeLog;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OrderChangeLog> list(@NotEmpty(message = "id不可以为空") String id) {
        return null;
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "/validateParma", method = RequestMethod.GET)
    public OrderChangeLog validateParma(@Validated QueryRequest request) {
        OrderChangeLog orderChangeLog = orderChangeLogService.selectByPrimaryKey(request.getId());
        return orderChangeLog;
    }
}
