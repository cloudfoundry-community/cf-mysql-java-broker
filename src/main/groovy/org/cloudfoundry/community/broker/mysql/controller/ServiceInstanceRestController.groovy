package org.cloudfoundry.community.broker.mysql.controller

import org.cloudfoundry.community.broker.mysql.service.ServiceInstance
import org.cloudfoundry.community.broker.mysql.service.ServiceInstanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Author: Sridharan Kuppa sridharan.kuppa@gmail.com
 * Date: 12/12/13
 */

@Controller
@RequestMapping("/v2/service_instances/{id}")
class ServiceInstanceRestController {
  @Autowired
  private ServiceInstanceService service

  @RequestMapping(method = RequestMethod.PUT)
  @ResponseBody
  Map update(@PathVariable String id) {
    ServiceInstance instance = service.findById(id)
    if (!service.isExists(instance)) {
      service.create(instance)
    }
    return [:]
  }

  @RequestMapping(method = RequestMethod.DELETE)
  @ResponseBody
  Map destroy(@PathVariable String id) {
    ServiceInstance instance = service.findById(id)
    if (service.isExists(instance)) {
      service.delete(instance)
    }
    return [:]
  }
}
