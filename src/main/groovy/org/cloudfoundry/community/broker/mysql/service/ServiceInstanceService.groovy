package org.cloudfoundry.community.broker.mysql.service


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import javax.sql.DataSource

/**
 * Author: Sridharan Kuppa sridharan.kuppa@gmail.com
 * Date: 12/12/13
 */
@Service
class ServiceInstanceService {
  @Autowired JdbcTemplate jdbcTemplate

  ServiceInstance findById(String id) {
    return new ServiceInstance(id)
  }

  boolean isExists(ServiceInstance instance) {
    List<String> databases = jdbcTemplate.queryForList("SHOW DATABASES LIKE '${instance.database}'", String)
    return databases?.size() > 0
  }

  int getNumberOfExistingInstances() {
    return 1
  }

  def create(ServiceInstance instance) {
    jdbcTemplate.execute("CREATE DATABASE ${instance.database}")
  }

  def delete(ServiceInstance instance) {
    jdbcTemplate.execute("DROP DATABASE IF EXISTS ${instance.database}")
  }
}

class ServiceInstance {
  static String DATABASE_PREFIX = "cf_"
  String database

  ServiceInstance(String id) {
    String s = id.replaceAll('-', '_')
    database = "${DATABASE_PREFIX}${s}"
  }
}
