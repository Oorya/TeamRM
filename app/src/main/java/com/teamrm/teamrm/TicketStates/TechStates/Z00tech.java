package com.teamrm.teamrm.TicketStates.TechStates;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateTech;

/**
 * Created by root on 01/09/2016.
 */
 class Z00Tech extends TicketStateTech implements TicketStateAble {
 static {
  TicketFactory.registerProduct(ProductID.TICKET_ID_A00A,new Z00Tech());
 }
 public Z00Tech() {
  super();
 }
 public Z00Tech(int ttl)
 {
  //initials ttl example
 }

 @Override
 public TicketStateAble getNewState() {
  return new Z00Tech();
 }
}
