package com.teamrm.teamrm.Interfaces;


import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;

import java.util.List;

/**
 * Created by Oorya on 08/08/2016.
 */
public interface FireBaseAble
{
    void resultTicket(Ticket ticket);
    void resultUser(Users user);
    void ticketListCallback(List<Ticket> tickets);
    void ticketLiteListCallback(List<TicketLite> ticketLites);
    void resultBoolean(boolean bool);
    void companyListCallback(List<GenericKeyValueTypeable> companies);
    void productListCallback(List<Product> products);
    void categoryListCallback(List<Category> categories);
    void regionListCallback(List<Region> regions);


}
