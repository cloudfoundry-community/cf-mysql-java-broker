package org.cloudfoundry.community.broker.mysql.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.yaml.snakeyaml.Yaml

/**
 * Author: Sridharan Kuppa sridharan.kuppa@gmail.com
 * Date: 12/12/13
 */
@Controller
@RequestMapping("/v2/catalog")
class CatalogRestController {
  def settings

  @RequestMapping(method=RequestMethod.GET)
  @ResponseBody
  synchronized Map getCatalog() {
    if (!settings) {
      Yaml yaml = new Yaml()
      settings = yaml.load(this.class.getClassLoader().getResourceAsStream("settings.yml"))
    }

    return settings
  }

}
