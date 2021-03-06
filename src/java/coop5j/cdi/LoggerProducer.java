/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coop5j.cdi;

  import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.log4j.Logger;

/**
 * Logging producer for injectable log4j logger
 *
 * @author cem ikta
 */
public class LoggerProducer {
   /**
    * @param injectionPoint
    * @return logger
    */
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}