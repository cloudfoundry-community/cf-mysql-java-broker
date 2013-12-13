package org.cloudfoundry.community.broker.mysql

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Author: Sridharan Kuppa sridharan.kuppa@gmail.com
 * Date: 12/12/13
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "org.cloudfoundry.community.broker.mysql")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}