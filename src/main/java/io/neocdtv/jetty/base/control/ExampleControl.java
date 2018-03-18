/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.jetty.base.control;

import io.neocdtv.jetty.base.entity.ExampleEntity;

/**
 * ExampleResource
 *
 * @author xix
 * @since 22.12.2017.
 */
public class ExampleControl {

  public ExampleEntity businessMethod() {
    final ExampleEntity exampleEntity = new ExampleEntity();
    exampleEntity.setExampleField("Example Value");
    return exampleEntity;
  }
}
