package org.cloudfoundry.community.broker.mysql.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import java.security.MessageDigest

/**
 * Author: Sridharan Kuppa sridharan.kuppa@gmail.com
 * Date: 12/12/13
 */
@Service
class ServiceBindingService implements EnvironmentAware {
  @Autowired JdbcTemplate jdbcTemplate
  @Autowired ServiceInstanceService instanceService

  Environment environment

  @Override
  void setEnvironment(Environment environment) {
    this.environment = environment
  }

  ServiceBinding findById(String id, String instanceId) {
    ServiceInstance instance = instanceService.findById(instanceId)
    ServiceBinding binding = new ServiceBinding(id, instance, environment)
    try {
      jdbcTemplate.execute("SHOW GRANTS FOR '${binding.username}'")
    } catch (Exception e) {
      e.message =~ /no such grant/
    }
    return binding
  }

  def save(ServiceBinding binding) {
    jdbcTemplate.execute("CREATE USER '${binding.username}' IDENTIFIED BY '${binding.password}'")
    jdbcTemplate.execute("GRANT ALL PRIVILEGES ON `${binding.database}`.* TO '${binding.username}'@'%'")
    jdbcTemplate.execute('FLUSH PRIVILEGES')
  }

  def destroy(ServiceBinding binding) {
    jdbcTemplate.execute("DROP USER '${binding.username}'")
  }
}

class ServiceBinding {
  String host, database, username, password, uri, jdbc_url
  int port

  ServiceBinding(String id, ServiceInstance instance, Environment environment) {
    MessageDigest digest = MessageDigest.getInstance("MD5")
    digest.update(id.bytes);
    this.username = new BigInteger(1, digest.digest()).toString(16).replaceAll(/[^a-zA-Z0-9]+/, '').substring(0, 16)
    this.password = UUID.randomUUID().toString()
    this.host = ''
    this.port = 0
    this.jdbc_url = environment.getProperty('spring.datasource.url')
    this.uri = this.jdbc_url.substring(5)
    this.database = instance.database
  }
}
