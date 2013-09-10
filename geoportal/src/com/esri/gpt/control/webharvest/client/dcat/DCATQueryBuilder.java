/*
 * Copyright 2013 Esri.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.esri.gpt.control.webharvest.client.dcat;

import com.esri.gpt.catalog.harvest.protocols.HarvestProtocolDCAT;
import com.esri.gpt.control.webharvest.IterationContext;
import com.esri.gpt.control.webharvest.common.CommonCapabilities;
import com.esri.gpt.framework.resource.api.Native;
import com.esri.gpt.framework.resource.query.Capabilities;
import com.esri.gpt.framework.resource.query.Criteria;
import com.esri.gpt.framework.resource.query.Query;
import com.esri.gpt.framework.resource.query.QueryBuilder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DCAT query builder.
 */
public class DCATQueryBuilder implements QueryBuilder {

  /** logger */
  private static final Logger LOGGER = Logger.getLogger(DCATQuery.class.getCanonicalName());
  /** capabilities */
  private static final Capabilities capabilities = new DCATCommonCapabilities();
  /** iteration context */
  private IterationContext context;
  /** service info */
  private DCATInfo info;

  /**
   * Creates instance of the builder.
   * @param context iteration context
   * @param protocol protocol
   * @param url URL
   */
  public DCATQueryBuilder(IterationContext context, HarvestProtocolDCAT protocol, String url) {
    if (context == null) {
      throw new IllegalArgumentException("No context provided.");
    }
    this.context = context;
    this.info = new DCATInfo(url,protocol.getFormat());
  }

  @Override
  public Capabilities getCapabilities() {
    return capabilities;
  }

  @Override
  public Query newQuery(Criteria crt) {
    DCATProxy proxy = new DCATProxy(info, crt);
    Query q = new DCATQuery(context, info, proxy, crt);
    LOGGER.log(Level.FINER, "Query created: {0}", q);
    return q;
  }

  @Override
  public Native getNativeResource() {
    return null;
  }

  /**
   * DCAT capabilities.
   */
  private static class DCATCommonCapabilities extends CommonCapabilities {

    @Override
    public boolean canQueryFromDate() {
      return true;
    }

    @Override
    public boolean canQueryToDate() {
      return true;
    }
  }
  
}
